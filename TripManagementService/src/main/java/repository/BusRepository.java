package repository;

import entity.Bus;
import enums.BusStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface BusRepository extends JpaRepository<Bus, UUID> {
    List<Bus> findByBusStatusAndRouteId(BusStatus busStatus, UUID routeId);
    List<Bus> findByRouteId(UUID routeId);
    List<Bus> findByBusStatus(BusStatus busStatus);
}
