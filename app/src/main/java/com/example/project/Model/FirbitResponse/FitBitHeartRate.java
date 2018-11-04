package com.example.project.Model.FirbitResponse;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class FitBitHeartRate {

    @SerializedName("activities-heart-intraday")
    private ActivitiesHeartIntraday activitiesHeartIntraday;

    @SerializedName("activities-heart")
    private List<ActivitiesHeartItem> activitiesHeart;

    public ActivitiesHeartIntraday getActivitiesHeartIntraday() {
        return activitiesHeartIntraday;
    }

    public void setActivitiesHeartIntraday(ActivitiesHeartIntraday activitiesHeartIntraday) {
        this.activitiesHeartIntraday = activitiesHeartIntraday;
    }

    public List<ActivitiesHeartItem> getActivitiesHeart() {
        return activitiesHeart;
    }

    public void setActivitiesHeart(List<ActivitiesHeartItem> activitiesHeart) {
        this.activitiesHeart = activitiesHeart;
    }

    @Override
    public String toString() {
        return
                "FitBitHeartRate{" +
                        "activities-heart-intraday = '" + activitiesHeartIntraday + '\'' +
                        ",activities-heart = '" + activitiesHeart + '\'' +
                        "}";
    }
}