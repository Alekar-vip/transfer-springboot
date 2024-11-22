package co.com.tipi.central.transfercentral.repositories;

import co.com.tipi.central.transfercentral.models.entities.MarkEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MarkRepository extends CrudRepository<MarkEntity, UUID> {

    @Query("SELECT " +
            "   sm," +
            "   fi," +
            "   ti," +
            "   ci," +
            "   pi," +
            "   fe," +
            "   te," +
            "   ce," +
            "   pe " +
            "FROM " +
            "   MarkEntity sm " +
            "   LEFT JOIN FETCH FileTagedEntity fi ON fi.id = sm.fileTagedInit.id" +
            "   LEFT JOIN FETCH TagEntity ti ON fi.tag.id = ti.id" +
            "   LEFT JOIN FETCH CategoryEntity ci ON ci.id = ti.category.id " +
            "   LEFT JOIN FETCH PartitionEntity pi ON pi.id = ci.partition.id " +
            "   LEFT JOIN FETCH FileTagedEntity fe ON fe.id = sm.fileTagedEnd.id" +
            "   LEFT JOIN FETCH TagEntity te ON fe.tag.id = te.id" +
            "   LEFT JOIN FETCH CategoryEntity ce ON ci.id = te.category.id " +
            "   LEFT JOIN FETCH PartitionEntity pe ON pi.id = ce.partition.id " +
            "WHERE sm.transferredAt IS NULL")
    List<MarkEntity> fetchAllByTransferredAtIsNull();


    @Query("SELECT " +
            "   sm," +
            "   fi," +
            "   ti," +
            "   ci," +
            "   pi," +
            "   fe," +
            "   te," +
            "   ce," +
            "   pe " +
            "FROM " +
            "   MarkEntity sm " +
            "   LEFT JOIN FETCH FileTagedEntity fi ON fi.id = sm.fileTagedInit.id" +
            "   LEFT JOIN FETCH TagEntity ti ON fi.tag.id = ti.id" +
            "   LEFT JOIN FETCH CategoryEntity ci ON ci.id = ti.category.id " +
            "   LEFT JOIN FETCH PartitionEntity pi ON pi.id = ci.partition.id " +
            "   LEFT JOIN FETCH FileTagedEntity fe ON fe.id = sm.fileTagedEnd.id" +
            "   LEFT JOIN FETCH TagEntity te ON fe.tag.id = te.id" +
            "   LEFT JOIN FETCH CategoryEntity ce ON ci.id = te.category.id " +
            "   LEFT JOIN FETCH PartitionEntity pe ON pi.id = ce.partition.id " +
            "WHERE sm.id = ?1")
    MarkEntity fetchById(int id);
}
