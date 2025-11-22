package org.example.tripmanagementservice.controller;

import org.example.tripmanagementservice.dto.bus.BusRequestDTO;
import org.example.tripmanagementservice.dto.bus.BusResponseDTO;
import org.example.tripmanagementservice.dto.route.RouteRequestDTO;
import org.example.tripmanagementservice.dto.route.RouteResponseDTO;
import org.example.tripmanagementservice.dto.route.TrajectoryDTO;
import org.example.tripmanagementservice.dto.routeStop.RouteStopRequestDTO;
import org.example.tripmanagementservice.dto.routeStop.RouteStopResponseDTO;
import org.example.tripmanagementservice.entity.Bus;
import org.example.tripmanagementservice.entity.Route;
import org.example.tripmanagementservice.entity.RouteStop;
import org.example.tripmanagementservice.entity.Stop;
import org.example.tripmanagementservice.mapper.BusMapper;
import org.example.tripmanagementservice.mapper.RouteMapper;
import org.example.tripmanagementservice.mapper.RouteStopMapper;
import org.example.tripmanagementservice.mapper.StopMapper;
import org.example.tripmanagementservice.service.RouteStopService;
import org.example.tripmanagementservice.service.StopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.example.tripmanagementservice.service.RouteService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/routes")
public class RouteController {

    private final RouteService routeService;
    private final RouteMapper  routeMapper;
    private final BusMapper busMapper;
    private final RouteStopMapper routeStopMapper;
    private final StopService stopService;
    private final StopMapper stopMapper;

    @Autowired
    public RouteController(RouteService routeService, RouteMapper routeMapper, BusMapper busMapper, RouteStopMapper routeStopMapper, StopService stopService, StopMapper stopMapper) {
        this.routeService = routeService;
        this.routeMapper = routeMapper;
        this.busMapper = busMapper;
        this.routeStopMapper = routeStopMapper;
        this.stopService = stopService;
        this.stopMapper = stopMapper;
    }

    @GetMapping
    public ResponseEntity<List<RouteResponseDTO>> getRoutes() {
        List<Route> routes = routeService.getAllRoutes();
        List<RouteResponseDTO> routeResponseDTOS = routeMapper.toListResponseDto(routes);
        return ResponseEntity.ok(routeResponseDTOS);
    }

    @GetMapping("/{routeId}")
    public ResponseEntity<RouteResponseDTO> getRoute(@PathVariable UUID routeId) {
        Route route = routeService.getRouteById(routeId);
        RouteResponseDTO routeResponseDTO = routeMapper.toResponseDto(route);
        return ResponseEntity.ok(routeResponseDTO);
    }

    @GetMapping("/geo/{routeId}")
    public ResponseEntity<TrajectoryDTO> getRouteForGeo(@PathVariable UUID routeId) {
        Route route = routeService.getRouteById(routeId);
        TrajectoryDTO routeResponseDTO = routeMapper.toTrajectoryDTO(route,stopMapper);
        return ResponseEntity.ok(routeResponseDTO);
    }

    @PostMapping
    public ResponseEntity<RouteResponseDTO> addRoute(@RequestBody RouteRequestDTO routeRequestDTO) {
        Route route = routeService.createRoute(routeMapper.toEntity(routeRequestDTO));
        RouteResponseDTO routeResponseDTO = routeMapper.toResponseDto(route);
        return ResponseEntity.ok(routeResponseDTO);
    }

    @PutMapping("/{routeId}")
    public ResponseEntity<RouteResponseDTO> updateRoute(@PathVariable UUID routeId,@RequestBody RouteRequestDTO routeRequestDTO) {
        Route route = routeService.updateRoute(routeId,routeRequestDTO);
        RouteResponseDTO routeResponseDTO = routeMapper.toResponseDto(route);
        return ResponseEntity.ok(routeResponseDTO);
    }

    @PostMapping("/{routeId}/add-bus")
    public ResponseEntity<BusResponseDTO> addBusToRoute(@PathVariable UUID routeId, @RequestBody BusRequestDTO busRequestDTO){
        Bus bus = routeService.addBusToRoute(routeId,busMapper.toEntity(busRequestDTO));
        return ResponseEntity.ok(busMapper.toResponseDTO(bus));
    }

    @PostMapping("/{routeId}/stop")
    public ResponseEntity<RouteStopResponseDTO> addRouteStopToRoute(@PathVariable UUID routeId, @RequestBody RouteStopRequestDTO routeStopRequestDTO){
        RouteStop routeStop =  routeStopMapper.toEntity(routeStopRequestDTO);
        Stop stop = stopService.getStop(routeStopRequestDTO.getStopId());
        Route route = routeService.getRouteById(routeStopRequestDTO.getRouteId());
        routeStop.setStop(stop);
        routeStop.setRoute(route);
        RouteStop newRouteStop = routeService.addRouteStopToRoute(routeId,routeStop);
        return ResponseEntity.ok(routeStopMapper.toResponseDTO(newRouteStop));
    }

    @GetMapping("/{routeId}/buses")
    public ResponseEntity<List<BusResponseDTO>> getRouteBuses(@PathVariable UUID routeId){
        List<Bus> buses = routeService.getRouteById(routeId).getBuses();
        return ResponseEntity.ok(busMapper.toListResponseDTO(buses));
    }

    @GetMapping("/{routeId}/stops")
    public ResponseEntity<List<RouteStopResponseDTO>> getRouteStops(@PathVariable UUID routeId){
        List<RouteStop> routeStops = routeService.getRouteById(routeId).getRouteStops();
        return ResponseEntity.ok(routeStopMapper.toListResponseDTO(routeStops));
    }

}
