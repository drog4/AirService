package com.drog.airservice.dao.impl;

import com.drog.airservice.dao.BaseDao;
import com.drog.airservice.dao.PlaneDao;
import com.drog.airservice.model.entity.Plane;
import com.drog.airservice.model.enums.PlaneStatusType;
import com.drog.airservice.model.mapper.PlaneMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

@Repository
public class PlaneDaoImpl extends BaseDao implements PlaneDao {

    @Override
    public List<Plane> selectAllPlanes() {
        final String sql = "SELECT * FROM planes";
        return jdbcTemplate.query(sql, new PlaneMapper());
    }

    @Override
    public List<Plane> selectPlanesByStatus(PlaneStatusType status) {
        if (status == null) {
            return Collections.emptyList();
        }
        final String sql = String.format("SELECT * FROM planes WHERE status='%s'", status.name());
        return jdbcTemplate.query(sql, new PlaneMapper());
    }

    @Override
    public int selectPlanesCountByAirportId(int id) {
        final String sql = String.format("SELECT COUNT(id) FROM planes WHERE airport_id=%s AND status='%s'",
                                         id, PlaneStatusType.AIRPORT.name());
        try {
            return jdbcTemplate.queryForObject(sql, Integer.class);
        } catch (DataAccessException ex) {
            return 0;
        }
    }

    @Override
    public Plane selectPlaneById(int id) {
        final String sql = "SELECT * FROM planes WHERE id=" + id;
        try {
            return jdbcTemplate.queryForObject(sql, new PlaneMapper());
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public void insertPlane(Plane plane) {
        if (plane == null || plane.getAirportId() == null || plane.getName() == null) {
            return;
        }
        final String sql = String.format("INSERT INTO planes (name, airport_id, status) VALUES ('%s', %s, '%s')",
                                      plane.getName(), plane.getAirportId(), PlaneStatusType.AIRPORT.name());
        jdbcTemplate.update(sql);
    }

    @Override
    public void deletePlaneById(int id) {
        final String sql = "DELETE FROM planes WHERE id="+id;
        jdbcTemplate.update(sql);
    }

    @Override
    public void updatePlaneStatusById(int id, PlaneStatusType status) {
        final String sql = String.format("UPDATE planes SET status='%s' WHERE id=%s", status.name(), id);
        jdbcTemplate.update(sql);
    }

    @Override
    public void updatePlaneAirportIdById(int id, Integer airportId) {
        final String sql = String.format("UPDATE planes SET airport_id=%s WHERE id=%s", airportId, id);
        jdbcTemplate.update(sql);
    }
}
