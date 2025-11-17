package org.example.tripmanagementservice.dto.route;

import org.example.tripmanagementservice.dto.bus.BusResponseDTO;
import org.example.tripmanagementservice.dto.routeStop.RouteStopResponseDTO;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class RouteDetailsDTO {
    private UUID id;
    private String routeNumber;
    private String name;
    private String description;
    private Boolean active;
    private Double price;
    private List<RouteStopResponseDTO> routeStops;
    private List<BusResponseDTO> buses;
}
