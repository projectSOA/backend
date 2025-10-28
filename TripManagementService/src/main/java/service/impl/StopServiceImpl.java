package service.impl;

import dto.stop.StopRequestDTO;
import entity.Bus;
import entity.Route;
import entity.RouteStop;
import entity.Stop;
import exception.ResourceNotFoundException;
import mapper.BusMapper;
import mapper.StopMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.RouteRepository;
import repository.StopRepository;
import service.StopService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
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
