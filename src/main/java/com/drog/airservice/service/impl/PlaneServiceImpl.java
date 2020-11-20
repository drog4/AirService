package com.drog.airservice.service.impl;

import com.drog.airservice.dao.AirportDao;
import com.drog.airservice.dao.PlaneDao;
import com.drog.airservice.model.entity.Airport;
import com.drog.airservice.model.entity.Plane;
import com.drog.airservice.model.entity.PlaneStatus;
import com.drog.airservice.model.enums.PlaneStatusType;
import com.drog.airservice.service.PlaneService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class PlaneServiceImpl implements PlaneService {

    private final PlaneDao planeDao;
    private final AirportDao airportDao;

    @Autowired
    public PlaneServiceImpl(PlaneDao planeDao, AirportDao airportDao) {

        this.planeDao = planeDao;
        this.airportDao = airportDao;
    }

    @Override
    public ResponseEntity<List<Plane>> readAllPlanes(String status) {

        if (status == null) {
            return ResponseEntity.ok(planeDao.selectAllPlanes());
        }
        PlaneStatusType planeStatusType = PlaneStatusType.getStatusType(status);
        if (planeStatusType == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(planeDao.selectPlanesByStatus(planeStatusType));
    }

    @Override
    public ResponseEntity<String> createPlane(Plane plane) {
        if (plane == null || plane.getName() == null || plane.getAirportId() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        final Airport airport = airportDao.selectAirportById(plane.getAirportId());
        if (airport == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        final int planeInAirportCount = planeDao.selectPlanesCountByAirportId(plane.getAirportId());
        if (planeInAirportCount < airport.getCapacity()) {
            planeDao.insertPlane(plane);
            final Optional<Plane> lastInsertPlane = planeDao.selectAllPlanes()
                    .stream()
                    .max(Comparator.comparingInt(Plane::getId));

            if (lastInsertPlane.isPresent()) {
                final JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", lastInsertPlane.get().getId());
                return ResponseEntity.ok(jsonObject.toString());
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }
    }

    @Override
    public ResponseEntity<String> deletePlane(int id) {
        final Plane plane = planeDao.selectPlaneById(id);
        if (plane == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        planeDao.deletePlaneById(id);
        return ResponseEntity.ok("");
    }

    @Override
    public ResponseEntity<PlaneStatus> readPlaneStatus(int id) {
        final Plane plane = planeDao.selectPlaneById(id);
        if (plane == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        final PlaneStatus planeStatus = new PlaneStatus();
        planeStatus.setStatus(plane.getStatus().name());
        return ResponseEntity.ok(planeStatus);
    }

    @Override
    public ResponseEntity<PlaneStatus> updatePlaneStatus(int id, Integer airportId, PlaneStatus status) {
        final Plane plane = planeDao.selectPlaneById(id);
        if (plane == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        PlaneStatusType planeStatusType = PlaneStatusType.getStatusType(status.getStatus());
        if (planeStatusType == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        switch (planeStatusType) {
            case AIR:
                if (plane.getStatus() != PlaneStatusType.AIR) {
                    plane.setStatus(PlaneStatusType.AIR);
                    planeDao.updatePlaneStatusById(id, planeStatusType);
                    planeDao.updatePlaneAirportIdById(id, null);
                }
                break;
            case AIRPORT:
                if (plane.getStatus() != PlaneStatusType.AIRPORT) {
                    if (airportId == null) {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
                    }
                    final Airport airport = airportDao.selectAirportById(airportId);
                    if (airport == null) {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
                    }
                    final int planesInAirportCount = planeDao.selectPlanesCountByAirportId(airportId);
                    if (planesInAirportCount < airport.getCapacity()) {
                        plane.setStatus(PlaneStatusType.AIRPORT);
                        planeDao.updatePlaneStatusById(id, planeStatusType);
                        planeDao.updatePlaneAirportIdById(id, airportId);
                    } else {
                        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
                    }
                    break;
                }
                    default:
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        PlaneStatus newPlaneStatus = new PlaneStatus();
        newPlaneStatus.setStatus(plane.getStatus().name());
        return ResponseEntity.ok(newPlaneStatus);
    }
}