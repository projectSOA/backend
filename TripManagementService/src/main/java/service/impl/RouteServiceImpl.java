package service.impl;

import dto.route.RouteRequestDTO;
import entity.Route;
import entity.RouteStop;
import exception.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import mapper.RouteMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.RouteRepository;
import repository.RouteStopRepository;
import service.RouteService;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class RouteServiceImpl implements RouteService {

    private  final RouteRepository routeRepository;
    private final RouteMapper routeMapper;
    private final RouteStopRepository routeStopRepository;

    @Autowired
    public RouteServiceImpl(RouteRepository routeRepository, RouteMapper routeMapper, RouteStopRepository routeStopRepository) {
        this.routeRepository = routeRepository;
        this.routeMapper = routeMapper;
        this.routeStopRepository = routeStopRepository;
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

    public Route addStopToRoute(UUID routeId, RouteStop routeStop){
        Route route = routeRepository.findById(routeId).orElseThrow(
                () -> new ResourceNotFoundException("Route Not Found"));
        routeStop.setRoute(route);
        route.getRouteStops().add(routeStop);
        return routeRepository.save(route);
    }

    public Route removeStopFromRoute(UUID routeId, UUID routeStopId){
        Route route = routeRepository.findById(routeId).orElseThrow(
                () -> new ResourceNotFoundException("Route Not Found"));
        RouteStop routeStop = routeStopRepository.findById(routeStopId).orElseThrow(
                () -> new ResourceNotFoundException("RouteStop Not Found"));
        if(!routeStop.getRoute().getId().equals(routeStopId)){
            throw new IllegalStateException("RouteStop not belong to this route");
        }
        route.getRouteStops().remove(routeStop);
        routeStop.setRoute(null);
        return routeRepository.save(route);
    }
}
