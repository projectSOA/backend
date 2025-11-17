package org.example.tripmanagementservice.service.impl;

import org.example.tripmanagementservice.dto.bus.BusRequestDTO;
import org.example.tripmanagementservice.entity.Bus;
import org.example.tripmanagementservice.entity.Route;
import org.example.tripmanagementservice.exception.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import org.example.tripmanagementservice.mapper.BusMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.example.tripmanagementservice.repository.BusRepository;
import org.example.tripmanagementservice.repository.RouteRepository;
import org.example.tripmanagementservice.service.BusService;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class BusServiceImpl implements BusService {

    private final BusRepository busRepository;
    private final BusMapper busMapper;
    private final RouteRepository routeRepository;

    @Autowired
    public BusServiceImpl(BusRepository busRepository, @Lazy BusMapper busMapper, RouteRepository routeRepository) {
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
