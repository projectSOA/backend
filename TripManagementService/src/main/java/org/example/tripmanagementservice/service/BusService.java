package org.example.tripmanagementservice.service;

import org.example.tripmanagementservice.dto.bus.BusRequestDTO;
import org.example.tripmanagementservice.entity.Bus;

import java.util.List;
import java.util.UUID;

public interface BusService {
    Bus createBus(Bus bus);
    Bus updateBus(UUID busId, BusRequestDTO busRequestDTO);
    void deleteBus(UUID busId);
    Bus getBusById(UUID busId);
    List<Bus> getBusByRouteId(UUID routeId);
    List<Bus> getBuses();

}
