package com.mevy.libraryapi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mevy.libraryapi.entities.Category;
import com.mevy.libraryapi.repositories.CategoryRepository;
import com.mevy.libraryapi.services.exceptions.DataBindingViolationException;
import com.mevy.libraryapi.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class CategoryService {
    
    @Autowired
    private CategoryRepository categoryRepository;

    public Category findById(Long id){
        return categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(Category.class, id));
    }

    @Transactional
    public Category create(Category category){
        category.setId(null);
        category = categoryRepository.save(category);
        return category;
    }

    public void deleteById(Long id){
        findById(id);
        try {
            categoryRepository.deleteById(id);
        } catch(DataIntegrityViolationException e){
            throw new DataBindingViolationException();
        }
    }

    @Transactional
    public void update(Category category){
        try {
            Category oldCategory = categoryRepository.getReferenceById(category.getId());
            updateData(oldCategory, category);
        } catch(EntityNotFoundException e){
            new ResourceNotFoundException(Category.class, category.getId());
        }
    }

    private void updateData(Category oldCategory, Category category){
        oldCategory.setName(category.getName());
    }

}
