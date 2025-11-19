package org.example.geolocationservice.Services;

import org.example.geolocationservice.dtos.BusDTO;
import org.example.geolocationservice.dtos.TrajectoryDTO;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

public interface BusServiceClient {
    Mono<List<BusDTO>> getBusesByTrajectoryId(UUID trajectoryId);
    Mono<TrajectoryDTO> getTrajectoryById(UUID trajectoryId);
}