package com.drog.airservice.service;

import com.drog.airservice.model.entity.Plane;
import com.drog.airservice.model.entity.PlaneStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PlaneService {

    ResponseEntity<List<Plane>> readAllPlanes(String status);
    ResponseEntity<String> createPlane(Plane plane);
    ResponseEntity<String> deletePlane(int id);
    ResponseEntity<PlaneStatus> readPlaneStatus(int id);
    ResponseEntity<PlaneStatus> updatePlaneStatus(int id, Integer airportId, PlaneStatus status);
}
