package service;

import dto.route.RouteRequestDTO;
import entity.Route;
import entity.RouteStop;
import entity.Stop;

import java.util.List;
import java.util.UUID;

public interface RouteService {
    Route createRoute(Route route);
    Route updateRoute(UUID routeId, RouteRequestDTO routeRequestDTO);
    void deleteRoute(UUID routeId);
    Route getRouteById(UUID routeId);
    List<Route> getAllRoutes();
    Route addStopToRoute(UUID routeId, RouteStop routeStop);
    Route removeStopFromRoute(UUID routeId, UUID routeStopId);
}
