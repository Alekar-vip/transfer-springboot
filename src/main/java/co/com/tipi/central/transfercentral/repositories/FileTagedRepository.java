package co.com.tipi.central.transfercentral.repositories;

import co.com.tipi.central.transfercentral.models.entities.FileTagedEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface FileTagedRepository extends JpaRepository<FileTagedEntity, UUID> {

    @Query("SELECT " +
            "   f," +
            "   t," +
            "   c," +
            "   p " +
            "FROM FileTagedEntity f " +
            "   LEFT JOIN FETCH TagEntity t ON f.tag.id = t.id" +
            "   LEFT JOIN FETCH CategoryEntity c ON c.id = t.category.id " +
            "   LEFT JOIN FETCH PartitionEntity p ON p.id = c.partition.id " +
            "WHERE f.transferredAt IS NULL")
    List<FileTagedEntity> fetchAllByTransferredAtIsNull();
    @Query("SELECT " +
            "   f," +
            "   t," +
            "   c," +
            "   p " +
            "FROM FileTagedEntity f " +
            "   LEFT JOIN FETCH TagEntity t ON f.tag.id = t.id" +
            "   LEFT JOIN FETCH CategoryEntity c ON c.id = t.category.id " +
            "   LEFT JOIN FETCH PartitionEntity p ON p.id = c.partition.id " +
            "WHERE f.id = ?1")
    FileTagedEntity fetchById(int id);
}
