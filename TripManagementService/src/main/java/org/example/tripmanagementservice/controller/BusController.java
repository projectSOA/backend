package org.example.tripmanagementservice.controller;

import org.example.tripmanagementservice.dto.bus.BusResponseDTO;
import org.example.tripmanagementservice.entity.Bus;
import org.example.tripmanagementservice.entity.Route;
import org.example.tripmanagementservice.mapper.BusMapper;
import org.example.tripmanagementservice.service.BusService;
import org.example.tripmanagementservice.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/bus/")
public class BusController {

    private final BusMapper busMapper;
    private final BusService busService;
    private final RouteService routeService;

    @Autowired
    public BusController(BusMapper busMapper, BusService busService, RouteService routeService){
        this.busMapper = busMapper;
        this.busService = busService;
        this.routeService = routeService;
    }

    @GetMapping
    public ResponseEntity<List<BusResponseDTO>> getBuses(){
        List<Bus> buses = busService.getBuses();
        return ResponseEntity.ok(busMapper.toListResponseDTO(buses));
    }

    @GetMapping("/{busId}")
    public ResponseEntity<BusResponseDTO> getBus(@PathVariable UUID busId){
        Bus bus = busService.getBusById(busId);
        return ResponseEntity.ok(busMapper.toResponseDTO(bus));
    }

    @GetMapping("/{startStopId}/{endStopId}")
    public ResponseEntity<List<Bus>> getDisponibleBuses(@PathVariable UUID startStopId,@PathVariable UUID endStopId){
        List<Route> routes = routeService.getTwoStopRoutes(startStopId,endStopId);
        List<Bus> buses = routes.stream().flatMap(route->route.getBuses().stream()).toList();
        return ResponseEntity.ok(buses);
    }
}
