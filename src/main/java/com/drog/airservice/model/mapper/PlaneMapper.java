package com.drog.airservice.model.mapper;


import com.drog.airservice.model.entity.Plane;
import com.drog.airservice.model.enums.PlaneStatusType;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PlaneMapper implements RowMapper<Plane> {

    @Override
    public Plane mapRow(ResultSet rs, int rowNum) throws SQLException {
        final Plane plane = new Plane();
        plane.setId(rs.getInt("id"));
        plane.setName(rs.getString("name"));
        plane.setStatus(PlaneStatusType.getStatusType(rs.getString("status")));
        final int airportId = rs.getInt("airport_id");
        if (!rs.wasNull()) {
            plane.setAirportId(airportId);
        }

        return plane;
    }
}
