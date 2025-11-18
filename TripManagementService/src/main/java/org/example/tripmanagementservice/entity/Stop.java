package org.example.tripmanagementservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Entity
@Data
public class Stop {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotEmpty(message = "Stop name is required")
    @Size(max = 100, message = "Name must be at most 100 characters")
    private String name;

    @NotNull(message = "Latitude must be specified")
    private Double latitude;

    @NotNull(message = "Longitude must be specified")
    private Double longitude;

    @NotNull(message = "Address must be specified")
    private String address;

    @OneToMany(mappedBy = "stop",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<RouteStop> routeStops;
}
