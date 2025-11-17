package org.example.tripmanagementservice.service;

import org.example.tripmanagementservice.dto.routeStop.RouteStopRequestDTO;
import org.example.tripmanagementservice.entity.RouteStop;

import java.util.List;
import java.util.UUID;

public interface RouteStopService {
    RouteStop createRouteStop(RouteStop routeStop);
    RouteStop updateRouteStop(UUID routeStopId, RouteStopRequestDTO routeStopRequestDTO);
    void deleteRouteStop(UUID routeStopId);
    RouteStop getRouteStopById(UUID routeStopId);
    List<RouteStop> getRouteStops();
    List<RouteStop> getRouteStopsByRouteId(UUID routeId);
}
