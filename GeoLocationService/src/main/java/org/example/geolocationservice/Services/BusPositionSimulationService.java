package org.example.geolocationservice.Services;

import org.example.geolocationservice.dtos.BusPositionUpdate;
import reactor.core.publisher.Flux;

public interface BusPositionSimulationService {

    Flux<BusPositionUpdate> startSimulation(Long trajectoryId);
    void stopSimulation(Long trajectoryId);
}
