package com.infotact.aerosaga.model;

public class Telemetry {

    private String droneId;
    private String status;
    private int battery;
    private double altitude;
    private double speed;
    private double latitude;
    private double longitude;

    public Telemetry() {
    }

    public Telemetry(String droneId, String status, int battery,
                     double altitude, double speed,
                     double latitude, double longitude) {
        this.droneId = droneId;
        this.status = status;
        this.battery = battery;
        this.altitude = altitude;
        this.speed = speed;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getDroneId() {
        return droneId;
    }

    public void setDroneId(String droneId) {
        this.droneId = droneId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getBattery() {
        return battery;
    }

    public void setBattery(int battery) {
        this.battery = battery;
    }

    public double getAltitude() {
        return altitude;
    }

    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}