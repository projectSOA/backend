package org.example.tripmanagementservice.service;

import org.example.tripmanagementservice.dto.route.RouteRequestDTO;
import org.example.tripmanagementservice.entity.Bus;
import org.example.tripmanagementservice.entity.Route;
import org.example.tripmanagementservice.entity.RouteStop;

import java.util.List;
import java.util.UUID;

public interface RouteService {
    Route createRoute(Route route);
    Route updateRoute(UUID routeId, RouteRequestDTO routeRequestDTO);
    void deleteRoute(UUID routeId);
    Route getRouteById(UUID routeId);
    List<Route> getAllRoutes();
    RouteStop addRouteStopToRoute(UUID routeId, RouteStop routeStop);
    Route removeRouteStopFromRoute(UUID routeId, UUID routeStopId);
    Bus addBusToRoute(UUID routeId, Bus bus);
    Route removeBusFromRoute(UUID routeId, UUID busId);
    List<Route> getStopRoutes(UUID stopId);
    List<Route> getTwoStopRoutes(UUID startStopId,UUID endStopId);

}
