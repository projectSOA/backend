package org.example.tripmanagementservice.service.impl;

import org.example.tripmanagementservice.dto.routeStop.RouteStopRequestDTO;
import org.example.tripmanagementservice.entity.Route;
import org.example.tripmanagementservice.entity.RouteStop;
import org.example.tripmanagementservice.exception.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import org.example.tripmanagementservice.mapper.RouteStopMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.example.tripmanagementservice.repository.RouteRepository;
import org.example.tripmanagementservice.repository.RouteStopRepository;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class RouteStopServiceImpl {

    private final RouteStopRepository routeStopRepository;
    private final RouteStopMapper routeStopMapper;
    private final RouteRepository routeRepository;

    @Autowired
    public RouteStopServiceImpl(RouteStopRepository routeStopRepository,  RouteStopMapper routeStopMapper,  RouteRepository routeRepository) {
        this.routeStopRepository = routeStopRepository;
        this.routeStopMapper = routeStopMapper;
        this.routeRepository = routeRepository;
    }

    public RouteStop createRouteStop(RouteStop routeStop){
        return  routeStopRepository.save(routeStop);
    }

    public RouteStop updateRouteStop(UUID routeStopId, RouteStopRequestDTO routeStopRequestDTO){
        RouteStop routeStop = routeStopRepository.findById(routeStopId).orElseThrow(
                () -> new ResourceNotFoundException("RouteStop Not Found"));
        routeStopMapper.updateEntityFromDto(routeStopRequestDTO, routeStop);
        return routeStopRepository.save(routeStop);
    }

    public void deleteRouteStop(UUID routeStopId){
        routeStopRepository.deleteById(routeStopId);
    }

    public RouteStop getRouteStopById(UUID routeStopId){
        return routeStopRepository.findById(routeStopId).orElseThrow(
                () -> new ResourceNotFoundException("RouteStop Not Found"));
    }

    public List<RouteStop> getRouteStops(){
        return routeStopRepository.findAll();
    }

    public List<RouteStop> getRouteStopsByRouteId(UUID routeId){
        Route route = routeRepository.findById(routeId).orElseThrow(
                () -> new ResourceNotFoundException("Route Not Found"));
        return route.getRouteStops();
    }

}
