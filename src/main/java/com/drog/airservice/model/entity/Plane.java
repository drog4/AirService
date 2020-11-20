package com.drog.airservice.model.entity;

import com.drog.airservice.model.enums.PlaneStatusType;

public class Plane {
    private int id;
    private String name;
    private Integer airportId;
    private PlaneStatusType status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAirportId() {
        return airportId;
    }

    public void setAirportId(Integer airportId) {
        this.airportId = airportId;
    }

    public PlaneStatusType getStatus() {
        return status;
    }

    public void setStatus(PlaneStatusType status) {
        this.status = status;
    }
}
