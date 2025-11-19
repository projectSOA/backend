package org.example.geolocationservice.Services;

import org.example.geolocationservice.dtos.BusDTO;
import org.example.geolocationservice.dtos.BusPositionUpdate;
import org.example.geolocationservice.dtos.StationDTO;

public interface PositionGeneratorService {

    BusPositionUpdate generatePosition(BusDTO bus, StationDTO startStation,
                                       StationDTO endStation, double progress);
}
