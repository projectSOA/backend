package org.example.tripmanagementservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Entity
public class Route {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotEmpty(message = "Route number is required")
    private String routeNumber;

    @Size(max = 100, message = "Name must be at most 100 characters")
    private String name;

    @Size(max = 255, message = "Description must be at most 255 characters")
    private String description;

    @NotNull(message = "Active status must be specified")
    private Boolean active;

    @NotNull(message = "Price is required")
    private Double price;

    @OneToMany(mappedBy = "route",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Bus> buses;

    @OneToMany(mappedBy = "route",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<RouteStop> routeStops;
}
