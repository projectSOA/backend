package service.impl;

import dto.bus.BusRequestDTO;
import entity.Bus;
import entity.Route;
import exception.ResourceNotFoundException;
import mapper.BusMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.BusRepository;
import repository.RouteRepository;
import service.BusService;

import java.util.List;
import java.util.UUID;

@Service
public class BusServiceImpl implements BusService {

    private final BusRepository busRepository;
    private final BusMapper busMapper;
    private final RouteRepository routeRepository;

    @Autowired
    public BusServiceImpl(BusRepository busRepository,  BusMapper busMapper,  RouteRepository routeRepository) {
        this.busRepository = busRepository;
        this.busMapper = busMapper;
        this.routeRepository = routeRepository;

    }

    public Bus createBus(Bus bus){
        return busRepository.save(bus);
    }

    public Bus updateBus(UUID busId, BusRequestDTO busRequestDTO){
        Bus bus = busRepository.findById(busId).orElseThrow(
                () -> new ResourceNotFoundException("Bus Not Found"));
        busMapper.updateEntityFromDto(busRequestDTO, bus);
        return busRepository.save(bus);
    }

    public void deleteBus(UUID busId){
        busRepository.deleteById(busId);
    }

    public Bus getBusById(UUID busId){
        return  busRepository.findById(busId).orElseThrow(
                () -> new ResourceNotFoundException("Bus Not Found")
        );
    }

    public List<Bus> getBusByRouteId(UUID routeId){
        Route route = routeRepository.findById(routeId).orElseThrow(
                () -> new ResourceNotFoundException("Route Not Found"));
        return route.getBuses();
    }

    public List<Bus> getBuses(){
        return busRepository.findAll();
    }
}
