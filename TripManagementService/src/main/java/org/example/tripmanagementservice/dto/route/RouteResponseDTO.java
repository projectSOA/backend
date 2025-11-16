package org.example.tripmanagementservice.dto.route;

import lombok.Data;

import java.util.UUID;

@Data
public class RouteResponseDTO {
    private UUID id;
    private String routeNumber;
    private String name;
    private String description;
    private Boolean active;
    private Double price;
    private String startStationName;
    private String endStationName;
}
