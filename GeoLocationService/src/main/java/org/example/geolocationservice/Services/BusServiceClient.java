package org.example.geolocationservice.Services;

import org.example.geolocationservice.dtos.BusDTO;
import org.example.geolocationservice.dtos.TrajectoryDTO;
import reactor.core.publisher.Mono;

import java.util.List;

public interface BusServiceClient {
    Mono<List<BusDTO>> getBusesByTrajectoryId(Long trajectoryId);
    Mono<TrajectoryDTO> getTrajectoryById(Long trajectoryId);
}