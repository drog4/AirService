package com.drog.airservice.dao;

import com.drog.airservice.model.entity.Airport;
import java.util.List;

public interface AirportDao {

    List<Airport> selectAllAirports();
    Airport selectAirportById(int id);
}
