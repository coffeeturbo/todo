package ru.coffeeturbo.todo.repository;

import org.springframework.data.repository.CrudRepository;
import ru.coffeeturbo.todo.model.Category;

public interface CategoryRepository extends CrudRepository<Category, Long> {
}
