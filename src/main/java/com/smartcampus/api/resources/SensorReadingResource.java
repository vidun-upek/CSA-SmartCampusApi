/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smartcampus.api.resources;

/**
 *
 * @author vidun
 */

import com.smartcampus.api.model.Sensor;
import com.smartcampus.api.model.SensorReading;
import com.smartcampus.api.store.InMemoryStore;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SensorReadingResource {

    private final String sensorId;

    public SensorReadingResource(String sensorId) {
        this.sensorId = sensorId;
    }

    @GET
    public List<SensorReading> getReadings() {
        Map<String, Sensor> sensors = InMemoryStore.getSensors();
        Sensor sensor = sensors.get(sensorId);

        if (sensor == null) {
            throw new NotFoundException("Sensor with id " + sensorId + " was not found.");
        }

        Map<String, List<SensorReading>> readingsMap = InMemoryStore.getSensorReadings();
        return readingsMap.getOrDefault(sensorId, new LinkedList<>());
    }

    @POST
    public Response addReading(SensorReading reading) {
        Map<String, Sensor> sensors = InMemoryStore.getSensors();
        Sensor sensor = sensors.get(sensorId);

        if (sensor == null) {
            throw new NotFoundException("Sensor with id " + sensorId + " was not found.");
        }

        if ("MAINTENANCE".equalsIgnoreCase(sensor.getStatus())) {
            throw new com.smartcampus.api.exception.SensorUnavailableException(
                    "Sensor " + sensorId + " is in MAINTENANCE mode and cannot accept new readings."
            );
        }

        Map<String, List<SensorReading>> readingsMap = InMemoryStore.getSensorReadings();
        readingsMap.putIfAbsent(sensorId, new LinkedList<>());

        if (reading.getTimestamp() == null || reading.getTimestamp().trim().isEmpty()) {
            reading.setTimestamp(LocalDateTime.now().toString());
        }

        readingsMap.get(sensorId).add(reading);

        // Required side effect: update parent sensor currentValue
        sensor.setCurrentValue(reading.getValue());

        return Response.status(Response.Status.CREATED)
                .entity(reading)
                .build();
    }
}