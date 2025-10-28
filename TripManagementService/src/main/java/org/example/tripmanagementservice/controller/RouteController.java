package org.example.tripmanagementservice.controller;

import org.example.tripmanagementservice.dto.route.RouteRequestDTO;
import org.example.tripmanagementservice.entity.Route;
import org.example.tripmanagementservice.mapper.RouteMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.example.tripmanagementservice.service.RouteService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/route")
public class RouteController {

    private final RouteService routeService;
    private final RouteMapper  routeMapper;

    @Autowired
    public RouteController(RouteService routeService,  RouteMapper routeMapper) {
        this.routeService = routeService;
        this.routeMapper = routeMapper;
    }

    @GetMapping
    public ResponseEntity<List<Route>> getRoutes() {
        List<Route> routes = routeService.getAllRoutes();
        return ResponseEntity.ok(routes);
    }

    @PostMapping
    public ResponseEntity<Route> addRoute(@RequestBody RouteRequestDTO routeRequestDTO) {
        Route route = routeService.createRoute(routeMapper.toEntity(routeRequestDTO));
        return ResponseEntity.ok(route);
    }


}
