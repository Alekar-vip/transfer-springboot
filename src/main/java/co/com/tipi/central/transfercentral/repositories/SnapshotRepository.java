package co.com.tipi.central.transfercentral.repositories;

import co.com.tipi.central.transfercentral.models.entities.SnapshotEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SnapshotRepository extends CrudRepository<SnapshotEntity, UUID> {

    @Query("SELECT " +
            "   s, " +
            "   f," +
            "   t," +
            "   c," +
            "   p " +
            "FROM " +
            "   SnapshotEntity s " +
            "   LEFT JOIN FETCH FileTagedEntity f ON f.id = s.fileTaged.id" +
            "   LEFT JOIN FETCH TagEntity t ON f.tag.id = t.id" +
            "   LEFT JOIN FETCH CategoryEntity c ON c.id = t.category.id " +
            "   LEFT JOIN FETCH PartitionEntity p ON p.id = c.partition.id " +
            "WHERE " +
            "   s.transferredAt IS NULL")
    List<SnapshotEntity> fetchAllByTransferredAtIsNull();

    @Query("SELECT " +
            "   s, " +
            "   f," +
            "   t," +
            "   c," +
            "   p " +
            "FROM " +
            "   SnapshotEntity s " +
            "   LEFT JOIN FETCH FileTagedEntity f ON f.id = s.fileTaged.id" +
            "   LEFT JOIN FETCH TagEntity t ON f.tag.id = t.id" +
            "   LEFT JOIN FETCH CategoryEntity c ON c.id = t.category.id " +
            "   LEFT JOIN FETCH PartitionEntity p ON p.id = c.partition.id " +
            "WHERE " +
            "   s.id = ?1")
    SnapshotEntity fetchById(int id);
}
