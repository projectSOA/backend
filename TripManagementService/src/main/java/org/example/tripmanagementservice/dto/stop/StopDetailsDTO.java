package org.example.tripmanagementservice.dto.stop;

import org.example.tripmanagementservice.dto.routeStop.RouteStopResponseDTO;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class StopDetailsDTO {
    private UUID id;
    private String name;
    private Double latitude;
    private Double longitude;
    private String address;
    private List<RouteStopResponseDTO> routeStop;
}
