package dto.bus;

import enums.BusStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Data
public class BusRequestDTO {
    @NotNull(message = "Registration number is required")
    private String registrationNumber;

    private LocalTime startTime;
    private LocalTime endTime;
    private BusStatus busStatus;
    private LocalDate lastMaintenance;

    @NotNull(message = "Route ID is required")
    private UUID routeId;
}
