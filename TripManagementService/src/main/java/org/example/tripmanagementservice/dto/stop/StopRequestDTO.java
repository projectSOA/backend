package org.example.tripmanagementservice.dto.stop;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class StopRequestDTO {
    @NotEmpty(message = "Route number is required")
    @Size(max = 100, message = "Name must be at most 100 characters")
    private String name;

    @NotNull(message = "Latitude must be specified")
    private Double latitude;

    @NotNull(message = "Longitude must be specified")
    private Double longitude;

    @NotNull(message = "Address must be specified")
    private String address;
}
