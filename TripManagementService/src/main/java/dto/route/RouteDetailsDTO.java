package dto.route;

import dto.bus.BusResponseDTO;
import dto.routestop.RouteStopResponseDTO;
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
    private List<RouteStopResponseDTO> routeStops;
    private List<BusResponseDTO> buses;
}
