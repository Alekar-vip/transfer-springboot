package co.com.tipi.central.transfercentral.repositories;

import co.com.tipi.central.transfercentral.models.entities.PartitionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PartitionRepository extends JpaRepository<PartitionEntity, UUID> {

    @Query("SELECT p FROM PartitionEntity p WHERE p.active = true ORDER BY p.fillingPriority ASC")
    List<PartitionEntity> fetchAllOrderByPriority();


    @Query("SELECT p FROM PartitionEntity p WHERE p.id = ?1")
    PartitionEntity fetchById(int id);
}
