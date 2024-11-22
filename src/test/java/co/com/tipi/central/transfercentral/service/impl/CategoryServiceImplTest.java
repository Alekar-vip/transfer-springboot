package co.com.tipi.central.transfercentral.service.impl;

import co.com.tipi.central.transfercentral.models.domains.Category;
import co.com.tipi.central.transfercentral.models.entities.CategoryEntity;
import co.com.tipi.central.transfercentral.models.exceptions.CategoryException;
import co.com.tipi.central.transfercentral.models.mappers.CategoryEntityMapper;
import co.com.tipi.central.transfercentral.repositories.CategoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {
    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private CategoryEntityMapper categoryEntityMapper;

    @BeforeEach
    void setUp() {
    }

    @Test
    void findAllActiveCategories() {
        List<CategoryEntity> categoryEntities = Stream.of(new CategoryEntity(), new CategoryEntity()).collect(Collectors.toList());
        when(categoryRepository.findAllActiveCategories()).thenReturn(categoryEntities);
        when(categoryEntityMapper.categoryEntityToCategory(new CategoryEntity())).thenReturn(new Category());
        var result = categoryService.findAllActiveCategories();
        Assertions.assertNotNull(result);
        Assertions.assertFalse(result.isEmpty());
        Assertions.assertEquals(categoryEntities.size(), result.size());
    }

    @Test
    void findAllActiveCategoriesIsNull() {
        List<CategoryEntity> categoryEntities = null ;
        when(categoryRepository.findAllActiveCategories()).thenReturn(categoryEntities);
        Assertions.assertThrows(CategoryException.class, () -> categoryService.findAllActiveCategories());
    }
}