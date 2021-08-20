package ru.coffeeturbo.todo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.coffeeturbo.todo.model.Category;
import ru.coffeeturbo.todo.model.Item;
import ru.coffeeturbo.todo.repository.CategoryRepository;
import ru.coffeeturbo.todo.repository.ItemRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RequiredArgsConstructor
@Service
public class ItemService {

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    CategoryRepository categoryRepository;

    public Item add(Item item) {

        if (item.getCreated() == null) {
            item.setCreated(new Date(System.currentTimeMillis()));
        }
        findOrAddCategories(item);

        itemRepository.save(item);
        return item;
    }

    public Item updateDoneStatus(Item item) throws NoSuchElementException {
        var memoryItem = itemRepository.findById(item.getId())
                .orElseThrow();
        memoryItem.setDone(item.isDone());
        itemRepository.save(memoryItem);
        return memoryItem;
    }

    public Item findById(long id) throws NoSuchElementException {
        return itemRepository.findById(id).orElseThrow();
    }

    public List<Item> findAll() {
        return StreamSupport
                .stream(itemRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    public List<Item> findByDoneAll(boolean done) {
        return itemRepository.findByDone(done);
    }

    public boolean delete(Item item) {
        itemRepository.delete(item);
        return true;
    }

    private void findOrAddCategories(Item item) {
        if (item.getCategories() != null
                && !item.getCategories().isEmpty()) {

            List<Category> addedCats = new ArrayList<>();
            for (Category category : item.getCategories()) {

                var memoryItem = categoryRepository.findById(category.getId())
                        .orElseThrow();
                addedCats.add(memoryItem);
            }
            item.setCategories(addedCats);
        }
    }

}
