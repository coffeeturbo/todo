package ru.coffeeturbo.todo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.coffeeturbo.todo.model.Item;
import ru.coffeeturbo.todo.repository.ItemRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RequiredArgsConstructor
@Service
public class ItemService {

    @Autowired
    ItemRepository itemRepository;

    public Item add(Item item) {
        itemRepository.save(item);
        return item;
    }

    public Item update(Item item) throws NoSuchElementException {
        itemRepository.findById(item.getId()).orElseThrow();

        itemRepository.save(item);
        return item;
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

}
