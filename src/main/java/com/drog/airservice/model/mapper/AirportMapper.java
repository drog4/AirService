package com.drog.airservice.model.mapper;

import com.drog.airservice.model.entity.Airport;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AirportMapper implements RowMapper<Airport> {

    @Override
    public Airport mapRow(ResultSet rs, int rowNum) throws SQLException {
        final Airport airport = new Airport();
        airport.setId(rs.getInt("id"));
        airport.setName(rs.getString("name"));
        airport.setCapacity(rs.getInt("capacity"));

        return airport;
    }
}
