package dto.stop;

import lombok.Data;

import java.util.UUID;

@Data
public class StopResponseDTO {
    private UUID id;
    private String name;
    private Double latitude;
    private Double longitude;
    private String address;
}
