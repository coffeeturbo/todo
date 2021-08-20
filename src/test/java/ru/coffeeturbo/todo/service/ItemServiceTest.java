package ru.coffeeturbo.todo.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;
import ru.coffeeturbo.todo.model.Item;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Transactional
class ItemServiceTest {

    @Autowired
    private ItemService itemService;

    @Test
    @Rollback
    void whenAddItemSuccess() {
        var item = Item.builder()
                .description("Test item")
                .done(false)
                .created(new Date(System.currentTimeMillis()))
                .build();

        itemService.add(item);
        assertNotEquals(0, item.getId());
        assertEquals("Test item", item.getDescription());
        assertFalse(item.isDone());
        assertNotNull(item.getCreated());
    }

    @Test
    @Rollback
    void whenCreateThenUpdateSuccess() {
        var item = Item.builder()
                .description("Test description")
                .done(false)
                .created(new Date(System.currentTimeMillis()))
                .build();

        itemService.add(item);
        assertEquals(item.getDescription(), "Test description");
        item.setDescription("Update test description");
        itemService.updateDoneStatus(item);
        assertEquals(item.getDescription(), "Update test description");
    }

    @Test
    @Rollback
    void findByIdSuccess() {
        var item = Item.builder()
                .description("find description")
                .done(false)
                .created(new Date(System.currentTimeMillis()))
                .build();

        itemService.add(item);
        var findItem = itemService.findById(item.getId());
        assertEquals(findItem.getDescription(), "find description");
    }

    @Test
    @Rollback
    void findAllSuccess() {
        var item = Item.builder()
                .description("find description")
                .done(false)
                .created(new Date(System.currentTimeMillis()))
                .build();

        var item2 = Item.builder()
                .description("find description")
                .done(false)
                .created(new Date(System.currentTimeMillis()))
                .build();

        itemService.add(item);
        itemService.add(item2);

        var findItems = itemService.findAll();
        assertEquals(2, findItems.size());
    }

    @Test
    @Rollback
    void whenDeleteSuccess() {
        var item = Item.builder()
                .description("find description")
                .done(false)
                .created(new Date(System.currentTimeMillis()))
                .build();

        itemService.add(item);
        itemService.delete(item);

        var findItems = itemService.findAll();
        assertEquals(0, findItems.size());
    }

}