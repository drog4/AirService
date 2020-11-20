package com.drog.airservice.service.impl;

import com.drog.airservice.dao.AirportDao;
import com.drog.airservice.dao.PlaneDao;
import com.drog.airservice.model.entity.Airport;
import com.drog.airservice.service.AirportService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AirportServiceImpl implements AirportService {

    private final AirportDao airportDao;
    private final PlaneDao planeDao;

    @Autowired
    public AirportServiceImpl(AirportDao airportDao, PlaneDao planeDao) {
        this.airportDao = airportDao;
        this.planeDao = planeDao;
    }

    @Override
    public ResponseEntity<List<Airport>> readAllAirports() {
        return ResponseEntity.ok(airportDao.selectAllAirports());
    }

    @Override
    public ResponseEntity<String> readAirportCapacityInfo(int id) {
        final Airport airport = airportDao.selectAirportById(id);
        if (airport == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        final int planesInPortCount = planeDao.selectPlanesCountByAirportId(id);
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("total", airport.getCapacity());
        jsonObject.put("used", planesInPortCount);
        jsonObject.put("free", airport.getCapacity() - planesInPortCount);
        return ResponseEntity.ok(jsonObject.toString());
    }
}
