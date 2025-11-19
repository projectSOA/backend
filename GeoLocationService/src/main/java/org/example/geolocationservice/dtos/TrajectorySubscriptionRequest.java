package org.example.geolocationservice.dtos;

import java.util.UUID;

public record TrajectorySubscriptionRequest(
        UUID trajectoryId
) {}