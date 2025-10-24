package dto.routeStop;

import dto.route.RouteResponseDTO;
import dto.stop.StopResponseDTO;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class RouteStopDetails {
    private UUID id;
    private Integer stopOrder;
    private Integer distanceFromPrevious;
    private Integer estimatedTravelTime;
    private BigDecimal farFromStart;
    private RouteResponseDTO route;
    private StopResponseDTO stop;
}
