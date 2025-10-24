package dto.routeStop;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class RouteStopRequestDTO {
    @NotNull(message = "Stop order is required")
    private Integer stopOrder;

    private Integer distanceFromPrevious;
    private Integer estimatedTravelTime;
    private BigDecimal farFromStart;

    @NotNull(message = "Route ID is required")
    private UUID routeId;

    @NotNull(message = "Stop ID is required")
    private UUID stopId;
}
