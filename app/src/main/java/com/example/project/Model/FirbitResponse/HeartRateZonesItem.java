package com.example.project.Model.FirbitResponse;

import com.google.gson.annotations.SerializedName;

public class HeartRateZonesItem {

    @SerializedName("min")
    private int min;

    @SerializedName("max")
    private int max;

    @SerializedName("name")
    private String name;

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return
                "HeartRateZonesItem{" +
                        "min = '" + min + '\'' +
                        ",max = '" + max + '\'' +
                        ",name = '" + name + '\'' +
                        "}";
    }
}