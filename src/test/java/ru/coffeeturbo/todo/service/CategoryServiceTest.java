package ru.coffeeturbo.todo.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;
import ru.coffeeturbo.todo.model.Category;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application.properties")
@Transactional
class CategoryServiceTest {

    @Autowired
    private CategoryService service;

    @Rollback
    @Test
    void create() {
        var category = Category.builder().name("test category").build();

        service.create(category);
        Assertions.assertNotEquals(0, category.getId());
        Assertions.assertEquals("test category", category.getName());
    }

    @Rollback
    @Test
    void update() {
        var category = Category.builder().name("test category").build();

        service.create(category);
        var newItemId = category.getId();
        Assertions.assertNotEquals(0, newItemId);
        Assertions.assertEquals("test category", category.getName());

        category.setName("updated Name");

        service.update(category);
        Assertions.assertEquals(newItemId, category.getId());
        Assertions.assertEquals("updated Name", category.getName());
    }

    @Rollback
    @Test
    void delete() {
        var category = Category.builder().name("test category").build();
        service.create(category);

        Assertions.assertNotEquals(0, category.getId());
        Assertions.assertEquals("test category", category.getName());
        service.delete(category);
        assertThrows(NoSuchElementException.class, () -> service.findById(category.getId()));
    }

    @Rollback
    @Test
    void findAll() {
        var category = Category.builder().name("test category 1").build();
        var categor2 = Category.builder().name("test category 2").build();
        var categor3 = Category.builder().name("test category 3").build();

        service.create(category);
        service.create(categor2);
        service.create(categor3);

        assertEquals(3, service.findAll().size());
    }
}