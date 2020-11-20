package com.drog.airservice.controller;

import com.drog.airservice.model.entity.Airport;
import com.drog.airservice.service.AirportService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/airports", produces = "application/json")
@Api(value = "/airports", tags = {"Аэропорты"})
public class AirportController {

    private final AirportService airportService;

    @Autowired
    public AirportController(AirportService airportService) {
        this.airportService = airportService;
    }

    @GetMapping
    @ApiOperation(
            value = "Получить сведения об аэропортах",
            httpMethod = "GET",
            produces = "application/json",
            response = Airport.class,
            responseContainer = "List"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message ="OK"),
            @ApiResponse(code = 500, message ="Внутренняя ошибка")
    })
    public ResponseEntity<List<Airport>> getAllAirports() {
        return airportService.readAllAirports();
    }

    @GetMapping("/{id}/capacity")
    @ApiOperation(
            value = "Получить текущую загруженность аэропорта",
            httpMethod = "GET",
            produces = "application/json",
            response = String.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message ="OK"),
            @ApiResponse(code = 404, message ="Аэропорт не найден"),
            @ApiResponse(code = 500, message ="Внутренняя ошибка")
    })
    public ResponseEntity<String> getAirportCapacityInfo(
            @ApiParam (
                    value = "id аэропорта",
                    name = "id",
                    required = true,
                    example = "2"
            )
            @PathVariable(value = "id") final int id
    ) {
        return airportService.readAirportCapacityInfo(id);
    }
}
