package co.com.tipi.central.transfercentral.repositories;

import co.com.tipi.central.transfercentral.models.entities.AnnexEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AnnexRepository extends CrudRepository<AnnexEntity, UUID> {


    @Query("SELECT " +
            "   a, " +
            "   f," +
            "   t," +
            "   c," +
            "   p " +
            "FROM " +
            "   AnnexEntity a " +
            "   LEFT JOIN FETCH FileTagedEntity f ON f.id = a.fileTaged.id" +
            "   LEFT JOIN FETCH TagEntity t ON f.tag.id = t.id" +
            "   LEFT JOIN FETCH CategoryEntity c ON c.id = t.category.id " +
            "   LEFT JOIN FETCH PartitionEntity p ON p.id = c.partition.id " +
            "WHERE " +
            "   a.transferredAt IS NULL")
    List<AnnexEntity> fetchAllByTransferredAtIsNull();

    @Query("SELECT " +
            "   a, " +
            "   f," +
            "   t," +
            "   c," +
            "   p " +
            "FROM " +
            "   AnnexEntity a " +
            "   LEFT JOIN FETCH FileTagedEntity f ON f.id = a.fileTaged.id" +
            "   LEFT JOIN FETCH TagEntity t ON f.tag.id = t.id" +
            "   LEFT JOIN FETCH CategoryEntity c ON c.id = t.category.id " +
            "   LEFT JOIN FETCH PartitionEntity p ON p.id = c.partition.id " +
            "WHERE " +
            "   a.id = ?1")
    AnnexEntity fetchById(int id);
}
