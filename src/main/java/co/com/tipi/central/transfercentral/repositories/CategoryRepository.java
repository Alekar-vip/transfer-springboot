package co.com.tipi.central.transfercentral.repositories;

import co.com.tipi.central.transfercentral.models.entities.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, UUID> {
    @Query("SELECT c FROM CategoryEntity c WHERE c.active = true")
    List<CategoryEntity> findAllActiveCategories();
}
