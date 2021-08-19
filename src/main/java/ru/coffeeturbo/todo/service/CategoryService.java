package ru.coffeeturbo.todo.service;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.coffeeturbo.todo.model.Category;
import ru.coffeeturbo.todo.repository.CategoryRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RequiredArgsConstructor
@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public Category create(Category category) {
        categoryRepository.save(category);
        return category;
    }

    public Category update(Category category) {
        categoryRepository.save(category);
        return category;
    }

    public Category findById(long id) throws NoSuchElementException {
        return categoryRepository.findById(id).orElseThrow();
    }

    public boolean delete(Category category) {
        categoryRepository.delete(category);
        return true;
    }

    public List<Category> findAll() {
        return StreamSupport
                .stream(categoryRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

}
