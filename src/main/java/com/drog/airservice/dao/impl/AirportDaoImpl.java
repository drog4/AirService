package com.drog.airservice.dao.impl;

import com.drog.airservice.dao.AirportDao;
import com.drog.airservice.dao.BaseDao;
import com.drog.airservice.model.entity.Airport;
import com.drog.airservice.model.mapper.AirportMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class AirportDaoImpl extends BaseDao implements AirportDao {

    @Override
    public List<Airport> selectAllAirports() {
        final String sql = "SELECT * FROM airports";
        return jdbcTemplate.query(sql, new AirportMapper());
    }

    @Override
    public Airport selectAirportById(int id) {
        final String sql = String.format("SELECT id, name, capacity FROM airports WHERE id=%s", id);
        try {
            return jdbcTemplate.queryForObject(sql, new AirportMapper());
        } catch (DataAccessException ex) {
            return null;
        }

    }
}
