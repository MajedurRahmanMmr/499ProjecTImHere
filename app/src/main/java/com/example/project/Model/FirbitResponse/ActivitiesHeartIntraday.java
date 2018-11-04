package com.example.project.Model.FirbitResponse;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class ActivitiesHeartIntraday {

    @SerializedName("datasetType")
    private String datasetType;

    @SerializedName("dataset")
    private List<Object> dataset;

    @SerializedName("datasetInterval")
    private int datasetInterval;

    public String getDatasetType() {
        return datasetType;
    }

    public void setDatasetType(String datasetType) {
        this.datasetType = datasetType;
    }

    public List<Object> getDataset() {
        return dataset;
    }

    public void setDataset(List<Object> dataset) {
        this.dataset = dataset;
    }

    public int getDatasetInterval() {
        return datasetInterval;
    }

    public void setDatasetInterval(int datasetInterval) {
        this.datasetInterval = datasetInterval;
    }

    @Override
    public String toString() {
        return
                "ActivitiesHeartIntraday{" +
                        "datasetType = '" + datasetType + '\'' +
                        ",dataset = '" + dataset + '\'' +
                        ",datasetInterval = '" + datasetInterval + '\'' +
                        "}";
    }
}