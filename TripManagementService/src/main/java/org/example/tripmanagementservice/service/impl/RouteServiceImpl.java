package org.example.tripmanagementservice.service.impl;

import org.example.tripmanagementservice.dto.route.RouteRequestDTO;
import org.example.tripmanagementservice.entity.Bus;
import org.example.tripmanagementservice.entity.Route;
import org.example.tripmanagementservice.entity.RouteStop;
import org.example.tripmanagementservice.exception.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import org.example.tripmanagementservice.mapper.RouteMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.example.tripmanagementservice.repository.BusRepository;
import org.example.tripmanagementservice.repository.RouteRepository;
import org.example.tripmanagementservice.repository.RouteStopRepository;
import org.example.tripmanagementservice.service.RouteService;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class RouteServiceImpl implements RouteService {

    private  final RouteRepository routeRepository;
    private final RouteMapper routeMapper;
    private final RouteStopRepository routeStopRepository;
    private final BusRepository busRepository;

    @Autowired
    public RouteServiceImpl(RouteRepository routeRepository, RouteMapper routeMapper, RouteStopRepository routeStopRepository, BusRepository busRepository) {
        this.routeRepository = routeRepository;
        this.routeMapper = routeMapper;
        this.routeStopRepository = routeStopRepository;
        this.busRepository = busRepository;
    }

    public Route createRoute(Route route){
        return routeRepository.save(route);
    }

    public Route updateRoute(UUID routeId, RouteRequestDTO routeRequestDTO){
        Route route = routeRepository.findById(routeId).orElseThrow(
                () -> new ResourceNotFoundException("Route Not Found"));

        routeMapper.updateEntityFromDto(routeRequestDTO, route);
        return routeRepository.save(route);
    }

    public void deleteRoute(UUID routeId){
        routeRepository.deleteById(routeId);
    }

    public Route getRouteById(UUID routeId){
        return routeRepository.findById(routeId).orElseThrow(
                () -> new ResourceNotFoundException("Route Not Found"));
    }

    public List<Route> getAllRoutes(){
        return routeRepository.findAll();
    }

    public Route addRouteStopToRoute(UUID routeId, RouteStop routeStop){
        Route route = routeRepository.findById(routeId).orElseThrow(
                () -> new ResourceNotFoundException("Route Not Found"));
        routeStop.setRoute(route);
        route.getRouteStops().add(routeStop);
        return routeRepository.save(route);
    }

    public Route removeRouteStopFromRoute(UUID routeId, UUID routeStopId){
        Route route = routeRepository.findById(routeId).orElseThrow(
                () -> new ResourceNotFoundException("Route Not Found"));
        RouteStop routeStop = routeStopRepository.findById(routeStopId).orElseThrow(
                () -> new ResourceNotFoundException("RouteStop Not Found"));
        if(!routeStop.getRoute().getId().equals(routeId)){
            throw new IllegalStateException("RouteStop not belong to this route");
        }
        route.getRouteStops().remove(routeStop);
        routeStop.setRoute(null);
        return routeRepository.save(route);
    }

    public Route addBusToRoute(UUID routeId, Bus bus){
        Route route = routeRepository.findById(routeId).orElseThrow(
                () -> new ResourceNotFoundException("Route Not Found"));
        bus.setRoute(route);
        route.getBuses().add(bus);
        return routeRepository.save(route);
    }

    public Route removeBusFromRoute(UUID routeId, UUID busId){
        Route route = routeRepository.findById(routeId).orElseThrow(
                () -> new ResourceNotFoundException("Route Not Found"));
        Bus bus = busRepository.findById(busId).orElseThrow(
                () -> new ResourceNotFoundException("Bus Not Found"));
        if(!bus.getRoute().getId().equals(routeId)){
            throw new IllegalStateException("Bus not belong to this route");
        }
        route.getBuses().remove(bus);
        bus.setRoute(null);
        return routeRepository.save(route);
    }
}
