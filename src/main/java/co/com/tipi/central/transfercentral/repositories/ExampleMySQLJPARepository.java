package co.com.tipi.central.transfercentral.repositories;

import co.com.tipi.central.transfercentral.models.entities.ExampleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ExampleMySQLJPARepository extends JpaRepository<ExampleEntity, UUID> {
    Optional<ExampleEntity> findByExample(String example);
}
