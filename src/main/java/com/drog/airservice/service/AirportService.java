package com.drog.airservice.service;

import com.drog.airservice.model.entity.Airport;
import org.springframework.http.ResponseEntity;
import java.util.List;

public interface AirportService {

    ResponseEntity<List<Airport>> readAllAirports();

    ResponseEntity<String> readAirportCapacityInfo(int id);
}
