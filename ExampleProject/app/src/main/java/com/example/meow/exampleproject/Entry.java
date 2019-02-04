package com.example.meow.exampleproject;


public class Entry {
    private String fromCity;
    private String toCity;
    private String additionalInfo;
    private String phone;
    private float cost;

    public Entry() {
    }

    public Entry(String fromCity, String toCity, String additionalInfo, String phone, float cost) {
        this.fromCity = fromCity;
        this.toCity = toCity;
        this.additionalInfo = additionalInfo;
        this.phone = phone;
        this.cost = cost;
    }

    public String getFromCity() {
        return fromCity;
    }

    public void setFromCity(String fromCity) {
        this.fromCity = fromCity;
    }

    public String getToCity() {
        return toCity;
    }

    public void setToCity(String toCity) {
        this.toCity = toCity;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }
}
