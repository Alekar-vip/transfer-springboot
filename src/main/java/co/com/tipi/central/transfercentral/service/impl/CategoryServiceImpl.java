package co.com.tipi.central.transfercentral.service.impl;

import co.com.tipi.central.transfercentral.models.domains.Category;
import co.com.tipi.central.transfercentral.models.entities.CategoryEntity;
import co.com.tipi.central.transfercentral.models.exceptions.CategoryException;
import co.com.tipi.central.transfercentral.models.mappers.CategoryEntityMapper;
import co.com.tipi.central.transfercentral.repositories.CategoryRepository;
import co.com.tipi.central.transfercentral.service.CategoryService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {
    private static final String ERROR_MESSAGE = "No se encuntra permanencia en docking en categoria";
    final CategoryRepository categoryRepository;

    final CategoryEntityMapper categoryEntityMapper;
    @Override
    @Transactional(readOnly = true)
    public List<Category> findAllActiveCategories() {
        List<CategoryEntity> categoryEntities = categoryRepository.findAllActiveCategories();
        if (categoryEntities == null || categoryEntities.isEmpty()){
            throw new CategoryException(ERROR_MESSAGE);
        }
        return categoryEntities.stream().map(categoryEntityMapper::categoryEntityToCategory).toList();
    }
}
