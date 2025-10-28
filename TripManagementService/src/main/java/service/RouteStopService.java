package service;

import dto.routeStop.RouteStopRequestDTO;
import entity.RouteStop;

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
