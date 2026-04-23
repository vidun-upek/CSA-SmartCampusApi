/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smartcampus.api.store;

/**
 *
 * @author vidun
 */
import com.smartcampus.api.model.Room;
import com.smartcampus.api.model.Sensor;
import java.util.LinkedHashMap;
import java.util.Map;
import com.smartcampus.api.model.SensorReading;
import java.util.List;

public class InMemoryStore {

    private static final Map<String, Room> rooms = new LinkedHashMap<>();
    private static final Map<String, Sensor> sensors = new LinkedHashMap<>();
    private static final Map<String, List<SensorReading>> sensorReadings = new LinkedHashMap<>();
    static {
        Room room1 = new Room("LIB-301", "Library Quiet Study", 40);
        Room room2 = new Room("ENG-201", "Engineering Lab", 25);

        rooms.put(room1.getId(), room1);
        rooms.put(room2.getId(), room2);
    }

    public static Map<String, Room> getRooms() {
        return rooms;
    }

    public static Map<String, Sensor> getSensors() {
        return sensors;
    }
    
    public static Map<String, List<SensorReading>> getSensorReadings() {
    return sensorReadings;
}
}