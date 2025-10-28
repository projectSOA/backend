package org.example.tripmanagementservice.dto.routeStop;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class RouteStopResponseDTO {
    private UUID id;
    private Integer stopOrder;
    private Integer distanceFromPrevious;
    private Integer estimatedTravelTime;
    private BigDecimal farFromStart;
    private UUID routeId;
    private UUID stopId;
}
