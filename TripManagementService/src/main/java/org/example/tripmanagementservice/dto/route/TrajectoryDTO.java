package org.example.tripmanagementservice.dto.route;

import lombok.Data;
import org.example.tripmanagementservice.dto.stop.StationDTO;

import java.util.UUID;

@Data
public class TrajectoryDTO {
    UUID id;
    String name;
    StationDTO startStation;
    StationDTO endStation;
}
