package org.example.geolocationservice.Services.impl;

import org.example.geolocationservice.Services.BusServiceClient;
import org.example.geolocationservice.dtos.BusDTO;
import org.example.geolocationservice.dtos.TrajectoryDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;


@Service
public class BusServiceClientImpl implements BusServiceClient {

    private final WebClient webClient;

    public BusServiceClientImpl() {
        this.webClient = WebClient.builder()
                .baseUrl("http://localhost:8082")
                .build();
    }

    @Override
    public Mono<List<BusDTO>> getBusesByTrajectoryId(UUID trajectoryId) {
        return webClient.get()
                .uri("/api/v1/routes/{trajectoryId}/buses", trajectoryId)
                .retrieve()
                .bodyToFlux(BusDTO.class)
                .collectList();
    }

    @Override
    public Mono<TrajectoryDTO> getTrajectoryById(UUID trajectoryId) {
        return webClient.get()
                .uri("/api/v1/routes/{id}", trajectoryId)
                .retrieve()
                .bodyToMono(TrajectoryDTO.class);
    }
}
