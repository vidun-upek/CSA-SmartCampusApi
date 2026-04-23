/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smartcampus.api.resources;

/**
 *
 * @author vidun
 */
import com.smartcampus.api.model.Room;
import com.smartcampus.api.model.Sensor;
import com.smartcampus.api.store.InMemoryStore;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path("/sensors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SensorResource {

    private final Map<String, Sensor> sensors = InMemoryStore.getSensors();
    private final Map<String, Room> rooms = InMemoryStore.getRooms();

    @GET
    public List<Sensor> getAllSensors(@QueryParam("type") String type) {
        if (type == null || type.trim().isEmpty()) {
            return new ArrayList<>(sensors.values());
        }

        return sensors.values()
                .stream()
                .filter(sensor -> sensor.getType() != null
                        && sensor.getType().equalsIgnoreCase(type))
                .collect(Collectors.toList());
    }

    @POST
    public Response createSensor(Sensor sensor, @Context UriInfo uriInfo) {

        if (sensor == null || sensor.getId() == null || sensor.getId().trim().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"message\":\"Sensor id is required.\"}")
                    .build();
        }

        if (sensor.getType() == null || sensor.getType().trim().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"message\":\"Sensor type is required.\"}")
                    .build();
        }

        if (sensor.getStatus() == null || sensor.getStatus().trim().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"message\":\"Sensor status is required.\"}")
                    .build();
        }

        if (sensor.getRoomId() == null || sensor.getRoomId().trim().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"message\":\"roomId is required.\"}")
                    .build();
        }

        if (sensors.containsKey(sensor.getId())) {
            return Response.status(Response.Status.CONFLICT)
                    .entity("{\"message\":\"A sensor with this id already exists.\"}")
                    .build();
        }

        Room linkedRoom = rooms.get(sensor.getRoomId());

        if (linkedRoom == null) {
            throw new com.smartcampus.api.exception.LinkedResourceNotFoundException(
                    "The specified roomId " + sensor.getRoomId() + " does not exist."
            );
        }

        sensors.put(sensor.getId(), sensor);

        if (linkedRoom.getSensorIds() == null) {
            linkedRoom.setSensorIds(new ArrayList<>());
        }
        linkedRoom.getSensorIds().add(sensor.getId());

        URI location = uriInfo.getAbsolutePathBuilder()
                .path(sensor.getId())
                .build();

        return Response.created(location)
                .entity(sensor)
                .build();
    }

    @GET
    @Path("/{sensorId}")
    public Sensor getSensorById(@PathParam("sensorId") String sensorId) {
        Sensor sensor = sensors.get(sensorId);

        if (sensor == null) {
            throw new NotFoundException("Sensor with id " + sensorId + " was not found.");
        }

        return sensor;
    }

    @Path("/{sensorId}/readings")
    public SensorReadingResource getSensorReadingResource(@PathParam("sensorId") String sensorId) {
        Sensor sensor = sensors.get(sensorId);

        if (sensor == null) {
            throw new NotFoundException("Sensor with id " + sensorId + " was not found.");
        }

        return new SensorReadingResource(sensorId);
    }
}