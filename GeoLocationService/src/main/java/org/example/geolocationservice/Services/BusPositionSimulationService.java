package org.example.geolocationservice.Services;

import org.example.geolocationservice.dtos.BusPositionUpdate;
import reactor.core.publisher.Flux;

import java.util.UUID;

public interface BusPositionSimulationService {

    Flux<BusPositionUpdate> startSimulation(UUID trajectoryId);
    void stopSimulation(UUID trajectoryId);
}
