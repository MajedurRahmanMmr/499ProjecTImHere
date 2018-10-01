package com.example.project.Model;

public class TrustedStatus {
    String TrustedUID;
    long TimeStamp = System.currentTimeMillis();
    String TrusteeUID;
    String Status = "Pending";
    int locationRadious = 1;

    public TrustedStatus(String trustedUID, String trusteeUID, String status) {
        TrustedUID = trustedUID;
        TrusteeUID = trusteeUID;
        Status = status;
    }

    public TrustedStatus(String trustedUID, long timeStamp, String trusteeUID, String status, int locationRadious) {
        TrustedUID = trustedUID;
        TimeStamp = timeStamp;
        TrusteeUID = trusteeUID;
        Status = status;
        this.locationRadious = locationRadious;
    }

    public int getLocationRadious() {
        return locationRadious;
    }

    public void setLocationRadious(int locationRadious) {
        this.locationRadious = locationRadious;
    }

    public TrustedStatus() {
    }

    public String getTrustedUID() {
        return TrustedUID;
    }

    public void setTrustedUID(String trustedUID) {
        TrustedUID = trustedUID;
    }

    public long getTimeStamp() {
        return TimeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        TimeStamp = timeStamp;
    }

    public String getTrusteeUID() {
        return TrusteeUID;
    }

    public void setTrusteeUID(String trusteeUID) {
        TrusteeUID = trusteeUID;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
