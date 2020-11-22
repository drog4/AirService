package com.drog.airservice.model.entity;

import com.drog.airservice.model.enums.PlaneStatusType;
import lombok.Data;

@Data
public class Plane {
    private int id;
    private String name;
    private Integer airportId;
    private PlaneStatusType status;

}
