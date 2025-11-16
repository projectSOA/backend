package org.example.tripmanagementservice.controller;

import org.example.tripmanagementservice.dto.routeStop.RouteStopRequestDTO;
import org.example.tripmanagementservice.dto.routeStop.RouteStopResponseDTO;
import org.example.tripmanagementservice.entity.RouteStop;
import org.example.tripmanagementservice.mapper.RouteStopMapper;
import org.example.tripmanagementservice.service.RouteStopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/route-stops")
public class RouteStopController {
    private final RouteStopMapper routeStopMapper;
    private final RouteStopService routeStopService;

    @Autowired
    public RouteStopController(RouteStopMapper routeStopMapper, RouteStopService routeStopService){
        this.routeStopMapper = routeStopMapper;
        this.routeStopService = routeStopService;
    }

    @GetMapping("/{routeStopId}")
    public ResponseEntity<RouteStopResponseDTO> getStop(@PathVariable UUID routeStopId){
        RouteStop routeStop = routeStopService.getRouteStopById(routeStopId);
        return ResponseEntity.ok(routeStopMapper.toResponseDTO(routeStop));
    }

    @PutMapping("/{stopId}")
    public ResponseEntity<RouteStopResponseDTO> updateRouteStopToRoute(@PathVariable UUID stopId, @RequestBody RouteStopRequestDTO routeStopRequestDTO){
        RouteStop routeStop = routeStopService.updateRouteStop(stopId,routeStopRequestDTO);
        return ResponseEntity.ok(routeStopMapper.toResponseDTO(routeStop));
    }
}
