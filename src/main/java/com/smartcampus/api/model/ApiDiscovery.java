/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smartcampus.api.model;

/**
 *
 * @author vidun
 */
import java.util.Map;

public class ApiDiscovery {

    private String name;
    private String version;
    private String description;
    private Map<String, String> admin;
    private Map<String, String> resources;

    public ApiDiscovery() {
    }

    public ApiDiscovery(String name, String version, String description,
                        Map<String, String> admin,
                        Map<String, String> resources) {
        this.name = name;
        this.version = version;
        this.description = description;
        this.admin = admin;
        this.resources = resources;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Map<String, String> getAdmin() {
        return admin;
    }

    public void setAdmin(Map<String, String> admin) {
        this.admin = admin;
    }

    public Map<String, String> getResources() {
        return resources;
    }

    public void setResources(Map<String, String> resources) {
        this.resources = resources;
    }
}
