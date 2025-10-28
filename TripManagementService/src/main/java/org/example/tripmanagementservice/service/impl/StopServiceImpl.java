package org.example.tripmanagementservice.service.impl;

import org.example.tripmanagementservice.dto.stop.StopRequestDTO;
import org.example.tripmanagementservice.entity.Route;
import org.example.tripmanagementservice.entity.RouteStop;
import org.example.tripmanagementservice.entity.Stop;
import org.example.tripmanagementservice.exception.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import org.example.tripmanagementservice.mapper.StopMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.example.tripmanagementservice.repository.RouteRepository;
import org.example.tripmanagementservice.repository.StopRepository;
import org.example.tripmanagementservice.service.StopService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class StopServiceImpl implements StopService {

    private final StopRepository stopRepository;
    private final StopMapper stopMapper;
    private final RouteRepository routeRepository;

    @Autowired
    public StopServiceImpl(StopRepository stopRepository,  StopMapper stopMapper,  RouteRepository routeRepository) {
        this.stopRepository = stopRepository;
        this.stopMapper = stopMapper;
        this.routeRepository = routeRepository;
    }

    public Stop createStop(Stop stop){
        return stopRepository.save(stop);
    }

    public Stop updateStop(UUID stopId, StopRequestDTO stopRequestDTO){
        Stop stop = stopRepository.findById(stopId).orElseThrow(
                () -> new ResourceNotFoundException("Stop Not Found"));
        stopMapper.updateEntityFromDto(stopRequestDTO, stop);
        return stopRepository.save(stop);
    }

    public void deleteStop(UUID stopId){
        stopRepository.deleteById(stopId);
    }

    public Stop getStop(UUID stopId){
        return stopRepository.findById(stopId).orElseThrow(
                () -> new ResourceNotFoundException("Stop Not Found")
        );
    }

    public List<Stop> getStops(){
        return stopRepository.findAll();
    }

    public List<Stop> getStopsByRouteId(UUID routeId){
        Route route = routeRepository.findById(routeId).orElseThrow(
                () -> new ResourceNotFoundException("Route Not Found"));
        List<RouteStop> routeStops = route.getRouteStops();
        List<Stop> stops = new ArrayList<>();
        for (RouteStop routeStop : routeStops) {
            stops.add(routeStop.getStop());
        }
        return stops;

    }
}
