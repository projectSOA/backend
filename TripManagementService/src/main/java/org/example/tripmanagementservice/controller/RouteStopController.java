package org.example.tripmanagementservice.controller;

import org.example.tripmanagementservice.dto.routeStop.RouteStopResponseDTO;
import org.example.tripmanagementservice.dto.stop.StopRequestDTO;
import org.example.tripmanagementservice.dto.stop.StopResponseDTO;
import org.example.tripmanagementservice.entity.RouteStop;
import org.example.tripmanagementservice.entity.Stop;
import org.example.tripmanagementservice.mapper.RouteStopMapper;
import org.example.tripmanagementservice.mapper.StopMapper;
import org.example.tripmanagementservice.service.RouteStopService;
import org.example.tripmanagementservice.service.StopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

    @GetMapping
    public ResponseEntity<List<RouteStopResponseDTO>> getRouteStops(){
        List<RouteStop> routeStops = routeStopService.getRouteStops();
        return ResponseEntity.ok(routeStopMapper.toListResponseDTO(routeStops));
    }

    @GetMapping("/{routeStopId}")
    public ResponseEntity<StopResponseDTO> getStop(@PathVariable UUID stopId){
        Stop stop = stopService.getStop(stopId);
        return ResponseEntity.ok(stopMapper.toResponseDTO(stop));
    }

    @PostMapping
    public ResponseEntity<StopResponseDTO> addStop(@RequestBody StopRequestDTO stopRequestDTO){
        Stop stop = stopService.createStop(stopMapper.toEntity(stopRequestDTO));
        return ResponseEntity.ok(stopMapper.toResponseDTO(stop));
    }

    @PutMapping("/{stopId}")
    public ResponseEntity<StopResponseDTO> updateStop(@PathVariable UUID stopId, @RequestBody StopRequestDTO stopRequestDTO){
        Stop stop = stopService.updateStop(stopId,stopRequestDTO);
        return ResponseEntity.ok(stopMapper.toResponseDTO(stop));
    }
}
