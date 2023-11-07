package com.store.pixelworld.service;

import com.store.pixelworld.model.Category;
import com.store.pixelworld.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;
    public List<Category> getAllCategory(){
        return categoryRepository.findAll();

    }
    public void addCategory(Category category){
        categoryRepository.save(category);

    }
    public void removedCategoryById(int id){
        categoryRepository.deleteById(id);
    }

    public Optional <Category> getCategoryById(int id){
        return categoryRepository.findById(id);
    }
}
