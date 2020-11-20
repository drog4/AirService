package com.drog.airservice.dao;

import com.drog.airservice.model.entity.Plane;
import com.drog.airservice.model.enums.PlaneStatusType;

import java.util.List;

public interface PlaneDao {
    List<Plane> selectAllPlanes();
    List<Plane> selectPlanesByStatus(PlaneStatusType status);

    int selectPlanesCountByAirportId(int id);

    Plane selectPlaneById(int id);
    void insertPlane(Plane plane);
    void deletePlaneById(int id);

    void updatePlaneStatusById(int id, PlaneStatusType status);
    void updatePlaneAirportIdById(int id, Integer airportId);
}
