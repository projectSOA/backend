package org.example.tripmanagementservice.service;

import org.example.tripmanagementservice.dto.stop.StopRequestDTO;
import org.example.tripmanagementservice.entity.Stop;

import java.util.List;
import java.util.UUID;

public interface StopService {
    Stop createStop(Stop stop);
    Stop updateStop(UUID stopId, StopRequestDTO stopRequestDTO);
    void deleteStop(UUID stopId);
    Stop getStop(UUID stopId);
    List<Stop> getStops();
    List<Stop> getStopsByRouteId(UUID routeId);
}
