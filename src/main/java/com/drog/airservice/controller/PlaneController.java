package com.drog.airservice.controller;

import com.drog.airservice.model.entity.Plane;
import com.drog.airservice.model.entity.PlaneStatus;
import com.drog.airservice.service.PlaneService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/planes", produces = "application/json")
@Api(value = "/planes", tags = {"Самолёты"})
public class PlaneController {

    private final PlaneService planeService;

    @Autowired
    public PlaneController(PlaneService planeService) {
        this.planeService = planeService;
    }

    @GetMapping
    @ApiOperation(
            value = "Получить текущие самолеты (с возможностью фильтрации)",
            httpMethod = "GET",
            produces = "application/json",
            response = Plane.class,
            responseContainer = "List"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message ="OK"),
            @ApiResponse(code = 400, message ="Неверный фильтр"),
            @ApiResponse(code = 500, message ="Внутренняя ошибка")
    })
    public ResponseEntity<List<Plane>> getAllShips(
            @ApiParam(
                    value = "Статус (местоположение) самолета",
                    name = "status",
                    allowableValues = "AIR, AIRPORT",
                    example = "AIR"
            )
            @RequestParam(value = "status", required = false) final String status
    ) {
        return planeService.readAllPlanes(status);
    }

    @PostMapping
    @ApiOperation(
            value = "Создать новый самолёт",
            httpMethod = "POST",
            produces = "application/json",
            response = Integer.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message ="OK"),
            @ApiResponse(code = 400, message ="Отсутствует(ют) параметр(ы) самолёта"),
            @ApiResponse(code = 404, message ="Указан неверный аэропорт"),
            @ApiResponse(code = 422, message ="Аэропорт переполнен"),
            @ApiResponse(code = 500, message ="Самолет не был добавлен")
    })
    public ResponseEntity<String> postPlane(
            @RequestBody final Plane plane
    ) {
        return planeService.createPlane(plane);
    }

    //200, 404, 500
    @DeleteMapping("/{id}")
    @ApiOperation(
            value = "Удалить самолёт",
            httpMethod = "DELETE",
            produces = "application/json"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message ="OK"),
            @ApiResponse(code = 404, message ="Самолет не найден"),
            @ApiResponse(code = 500, message ="Внутренняя ошибка")
    })
    public ResponseEntity<String> deletePlane(
            @ApiParam(
                    value = "Id удаляемого самолета",
                    name = "id",
                    required = true,
                    example = "1"
            )
            @PathVariable(value = "id") final int id
    ) {
        return planeService.deletePlane(id);
    }

    @GetMapping("/{id}/status")
    @ApiOperation(
            value = "Получить статус (местоположение) самолёта",
            httpMethod = "GET",
            produces = "application/json",
            response = PlaneStatus.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message ="OK"),
            @ApiResponse(code = 404, message ="Самолет не найден"),
            @ApiResponse(code = 500, message ="Внутренняя ошибка")
    })
    public ResponseEntity<PlaneStatus> getPlaneStatus(
            @ApiParam(
                    value = "Id самолета, у которого узнается местоположение",
                    name = "id",
                    required = true,
                    example = "2"
            )
            @PathVariable(value = "id") final int id
    ) {
        return planeService.readPlaneStatus(id);
    }

    @PutMapping("/{id}/status")
    @ApiOperation(
            value = "Обновить статус (местоположение) самолёта",
            httpMethod = "PUT",
            produces = "application/json",
            response = PlaneStatus.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message ="OK"),
            @ApiResponse(code = 400, message ="Отсутствует статус (местоположение) самолёта либо id аэропорта"),
            @ApiResponse(code = 404, message ="Самолёт/аэропорт не найден"),
            @ApiResponse(code = 422, message ="Аэропорт переполнен"),
            @ApiResponse(code = 500, message ="Внутренняя ошибка")
    })
    public ResponseEntity<PlaneStatus> putPlaneStatus(
            @PathVariable(value = "id") final int id,
            @RequestParam(value = "airport_id", required = false) final Integer airportId,
            @RequestBody final PlaneStatus status
    ) {
            return planeService.updatePlaneStatus(id, airportId, status);
        }
    }

