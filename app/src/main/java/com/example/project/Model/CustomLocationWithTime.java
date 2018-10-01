package com.example.project.Model;

public class CustomLocationWithTime {
    Double latitude = 0.0;
    Double longitude = 0.0;

    Long timeStamp = System.currentTimeMillis();

    public CustomLocationWithTime(Double latitude, Double longitude, Long timeStamp) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.timeStamp = timeStamp;
    }

    public CustomLocationWithTime() {
    }

    public CustomLocationWithTime(Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }

    @Override
    public String toString() {
        return " Lat " + getLatitude() + " Long " + getLongitude() + " Time " + getTimeStamp();
    }
}
