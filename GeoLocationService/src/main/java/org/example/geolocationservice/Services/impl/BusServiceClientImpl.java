package org.example.geolocationservice.Services.impl;

import org.example.geolocationservice.Services.BusServiceClient;
import org.example.geolocationservice.dtos.BusDTO;
import org.example.geolocationservice.dtos.TrajectoryDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;


@Service
public class BusServiceClientImpl implements BusServiceClient {

    private final WebClient webClient;

    public BusServiceClientImpl() {
        this.webClient = WebClient.builder()
                .baseUrl("http://localhost:8083")
                .build();
    }

    @Override
    public Mono<List<BusDTO>> getBusesByTrajectoryId(Long trajectoryId) {
        return webClient.get()
                .uri("/api/buses/trajectory/{trajectoryId}", trajectoryId)
                .retrieve()
                .bodyToFlux(BusDTO.class)
                .collectList();
    }

    @Override
    public Mono<TrajectoryDTO> getTrajectoryById(Long trajectoryId) {
        return webClient.get()
                .uri("/api/trajectories/{id}", trajectoryId)
                .retrieve()
                .bodyToMono(TrajectoryDTO.class);
    }
}
