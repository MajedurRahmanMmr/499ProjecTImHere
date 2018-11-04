package com.example.project.Model.FirbitResponse;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class Value {

    @SerializedName("customHeartRateZones")
    private List<Object> customHeartRateZones;

    @SerializedName("heartRateZones")
    private List<HeartRateZonesItem> heartRateZones;

    public List<Object> getCustomHeartRateZones() {
        return customHeartRateZones;
    }

    public void setCustomHeartRateZones(List<Object> customHeartRateZones) {
        this.customHeartRateZones = customHeartRateZones;
    }

    public List<HeartRateZonesItem> getHeartRateZones() {
        return heartRateZones;
    }

    public void setHeartRateZones(List<HeartRateZonesItem> heartRateZones) {
        this.heartRateZones = heartRateZones;
    }

    @Override
    public String toString() {
        return
                "Value{" +
                        "customHeartRateZones = '" + customHeartRateZones + '\'' +
                        ",heartRateZones = '" + heartRateZones + '\'' +
                        "}";
    }
}