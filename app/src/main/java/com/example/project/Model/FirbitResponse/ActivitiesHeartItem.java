package com.example.project.Model.FirbitResponse;

import com.google.gson.annotations.SerializedName;

public class ActivitiesHeartItem {

    @SerializedName("dateTime")
    private String dateTime;

    @SerializedName("value")
    private Value value;

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public Value getValue() {
        return value;
    }

    public void setValue(Value value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return
                "ActivitiesHeartItem{" +
                        "dateTime = '" + dateTime + '\'' +
                        ",value = '" + value + '\'' +
                        "}";
    }
}