package com.example.project.Adapter;


public class TrusteeContact {
    private String contactName;
    private String contactId;
    private double currentLatitude;
    private double currentLongitude;
    private String phoneNumber;
    private String relation = "Unknown";


    TrusteeContact(String contactName, String contactId, double currentLatitude, double currentLongitude) {
        this.contactName = contactName;
        this.contactId = contactId;
        this.currentLatitude = currentLatitude;
        this.currentLongitude = currentLongitude;
    }

    public TrusteeContact(String contactName, String contactId, double currentLatitude, double currentLongitude, String phoneNumber, String relation) {
        this.contactName = contactName;
        this.contactId = contactId;
        this.currentLatitude = currentLatitude;
        this.currentLongitude = currentLongitude;
        this.phoneNumber = phoneNumber;
        this.relation = relation;
    }

    public TrusteeContact(String contactName, String contactId, double currentLatitude, double currentLongitude, String relation) {
        this.contactName = contactName;
        this.contactId = contactId;
        this.currentLatitude = currentLatitude;
        this.currentLongitude = currentLongitude;
        this.relation = relation;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    public double getCurrentLatitude() {
        return currentLatitude;
    }

    public void setCurrentLatitude(double currentLatitude) {
        this.currentLatitude = currentLatitude;
    }

    public double getCurrentLongitude() {
        return currentLongitude;
    }

    public void setCurrentLongitude(double currentLongitude) {
        this.currentLongitude = currentLongitude;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
