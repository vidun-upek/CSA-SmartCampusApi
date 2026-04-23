/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smartcampus.api.resources;

/**
 *
 * @author vidun
 */
import com.smartcampus.api.model.ApiDiscovery;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/")
public class DiscoveryResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public ApiDiscovery getApiDiscovery() {

        Map<String, String> admin = new LinkedHashMap<>();
        admin.put("moduleLeader", "Cassim Farook");
        admin.put("Developer", "Vidun Shanuka");
        admin.put("contactEmail", "vidun.20240591@iit.ac.lk");

        Map<String, String> resources = new LinkedHashMap<>();
        resources.put("rooms", "/api/v1/rooms");
        resources.put("sensors", "/api/v1/sensors");

        return new ApiDiscovery(
                "Smart Campus Sensor & Room Management API",
                "v1",
                "RESTful API for managing smart campus rooms and sensors",
                admin,
                resources
        );
    }
}