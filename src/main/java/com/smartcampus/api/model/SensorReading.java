/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smartcampus.api.model;

/**
 *
 * @author vidun
 */
import java.time.LocalDateTime;

public class SensorReading {

    private String id;
    private double value;
    private String timestamp;

    public SensorReading() {
        this.timestamp = LocalDateTime.now().toString();
    }

    public SensorReading(String id, double value) {
        this.id = id;
        this.value = value;
        this.timestamp = LocalDateTime.now().toString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
