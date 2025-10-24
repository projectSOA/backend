package repository;

import entity.Stop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StopRepository extends JpaRepository<Stop, UUID> {

}
