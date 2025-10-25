package dto.bus;

import enums.BusStatus;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Data
public class BusResponseDTO {
    private UUID id;
    private String registrationNumber;
    private LocalTime startTime;
    private LocalTime endTime;
    private BusStatus busStatus;
    private LocalDate lastMaintenance;
    private UUID routeId;
}
