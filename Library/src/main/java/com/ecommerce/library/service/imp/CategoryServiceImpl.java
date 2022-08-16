package com.ecommerce.library.service.imp;

import com.ecommerce.library.dto.CategoryDto;
import com.ecommerce.library.model.Category;
import com.ecommerce.library.repository.CategoryRepository;
import com.ecommerce.library.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Category save(Category category) {
        Category categorySave = new Category(category.getName());
        return categoryRepository.save(categorySave);
    }


    @Override
    public Category findById(Long id) {
        Optional<Category> foundCategory = categoryRepository.findById(id);
        if(foundCategory.isEmpty()) {
            new Exception("Not found Category ID");
        }
        return foundCategory.get();
    }

    @Override
    public Category update(Category category) {
        Category categoryUpdate = null;
       try{
           categoryUpdate = categoryRepository.findById(category.getId()).get();
           categoryUpdate.setName(category.getName());
           categoryUpdate.set_activated(category.is_activated());
           categoryUpdate.set_deleted(category.is_deleted());
       }catch (Exception e) {
           e.printStackTrace();
       }
        return categoryRepository.save(categoryUpdate);
    }

    @Override
    public void deleteById(Long id) {
        Category category = categoryRepository.findById(id).get();
        category.set_deleted(true);
        category.set_activated(false);
        categoryRepository.save(category);
    }

    @Override
    public void enableById(Long id) {
        Category category = categoryRepository.findById(id).get();
        category.set_activated(true);
        category.set_deleted(false);
        categoryRepository.save(category);
    }

    @Override
    public List<Category> findAllCategoryActivated() {
        return categoryRepository.findAllCategoryActivated();
    }

    @Override
    public List<CategoryDto> getCategoryAndProduct() {
        return categoryRepository.getCategoryAndProducts();
    }
}
