package org.example.tripmanagementservice.dto.stop;

import lombok.Data;

import java.util.UUID;

@Data
public class StationDTO {
    UUID id;
    String name;
    Double latitude;
    Double longitude;
}
