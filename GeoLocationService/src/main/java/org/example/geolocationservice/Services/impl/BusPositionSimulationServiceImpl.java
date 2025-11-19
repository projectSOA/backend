package org.example.geolocationservice.Services.impl;

import org.example.geolocationservice.Services.BusPositionSimulationService;
import org.example.geolocationservice.Services.BusServiceClient;
import org.example.geolocationservice.Services.PositionGeneratorService;
import org.example.geolocationservice.dtos.BusDTO;
import org.example.geolocationservice.dtos.BusPositionUpdate;
import org.example.geolocationservice.dtos.TrajectoryDTO;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.List;

@Service
public class BusPositionSimulationServiceImpl implements BusPositionSimulationService {

    private final BusServiceClient busServiceClient;
    private final PositionGeneratorService positionGenerator;
    private final Map<Long, Double> busProgressMap = new ConcurrentHashMap<>();

    public BusPositionSimulationServiceImpl(BusServiceClient busServiceClient,
                                            PositionGeneratorService positionGenerator) {
        this.busServiceClient = busServiceClient;
        this.positionGenerator = positionGenerator;
    }

    @Override
    public Flux<BusPositionUpdate> startSimulation(Long trajectoryId) {
        return busServiceClient.getTrajectoryById(trajectoryId)
                .flatMapMany(trajectory ->
                        busServiceClient.getBusesByTrajectoryId(trajectoryId)
                                .flatMapMany(buses -> simulateBusMovements(buses, trajectory))
                );
    }

    private Flux<BusPositionUpdate> simulateBusMovements(List<BusDTO> buses, TrajectoryDTO trajectory) {
        buses.forEach(bus -> {
            if (!busProgressMap.containsKey(bus.id())) {
                busProgressMap.put(bus.id(), Math.random());
            }
        });

        return Flux.interval(Duration.ofSeconds(2))
                .flatMap(tick -> Flux.fromIterable(buses)
                        .map(bus -> {
                            double progress = busProgressMap.get(bus.id());
                            progress += 0.01;
                            if (progress >= 1.0) {
                                progress = 0.0;
                            }
                            busProgressMap.put(bus.id(), progress);

                            return positionGenerator.generatePosition(
                                    bus,
                                    trajectory.startStation(),
                                    trajectory.endStation(),
                                    progress
                            );
                        })
                );
    }

    @Override
    public void stopSimulation(Long trajectoryId) {
        busProgressMap.clear();
    }
}