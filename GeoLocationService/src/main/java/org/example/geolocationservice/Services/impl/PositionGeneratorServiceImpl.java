package org.example.geolocationservice.Services.impl;

import org.example.geolocationservice.Services.PositionGeneratorService;
import org.example.geolocationservice.dtos.BusDTO;
import org.example.geolocationservice.dtos.BusPositionUpdate;
import org.example.geolocationservice.dtos.StationDTO;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Random;

@Service
public class PositionGeneratorServiceImpl implements PositionGeneratorService {

    private final Random random = new Random();

    @Override
    public BusPositionUpdate generatePosition(BusDTO bus, StationDTO startStation,
                                              StationDTO endStation, double progress) {
        double lat = interpolate(startStation.latitude(), endStation.latitude(), progress);
        double lon = interpolate(startStation.longitude(), endStation.longitude(), progress);

        BusPositionUpdate.Position position = new BusPositionUpdate.Position(
                lat + (random.nextGaussian() * 0.0001),
                lon + (random.nextGaussian() * 0.0001)
        );

        double speed = 20.0 + random.nextDouble() * 40.0;

        String status;
        if (progress < 0.05) {
            status = "AT_START_STATION";
        } else if (progress > 0.95) {
            status = "APPROACHING_END_STATION";
        } else {
            status = "IN_TRANSIT";
        }

        return new BusPositionUpdate(
                bus.routeId(),
                bus.id(),
                bus.registrationNumber(),
                position,
                speed,
                Instant.now().toString(),
                status
        );
    }

    private double interpolate(double start, double end, double progress) {
        return start + (end - start) * progress;
    }
}
