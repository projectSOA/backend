package repository;

import entity.Bus;
import enums.BusStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BusRepository extends JpaRepository<Bus, UUID> {
    List<Bus> findByBusStatusAndRouteId(BusStatus busStatus, UUID routeId);
    List<Bus> findByRouteId(UUID routeId);
    List<Bus> findByBusStatus(BusStatus busStatus);
}
