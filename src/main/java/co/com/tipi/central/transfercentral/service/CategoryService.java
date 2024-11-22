package co.com.tipi.central.transfercentral.service;

import co.com.tipi.central.transfercentral.models.domains.Category;

import java.util.List;

public interface CategoryService {
    List<Category> findAllActiveCategories();
}
