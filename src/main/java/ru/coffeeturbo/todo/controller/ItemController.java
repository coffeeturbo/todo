package ru.coffeeturbo.todo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.coffeeturbo.todo.model.Item;
import ru.coffeeturbo.todo.service.ItemService;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping({"items", "items/"})
public class ItemController {

    @Autowired
    ItemService service;

    @PostMapping
    ResponseEntity<Item> create(@RequestBody Item item) {
        service.add(item);
        return new ResponseEntity<>(item, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    ResponseEntity<Item> update(@RequestBody Item item, @PathVariable int id) {
        try {
            item.setId(id);
            item = service.updateDoneStatus(item);
            return new ResponseEntity<>(item, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(Item.builder().build(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    ResponseEntity<Item> getById(@PathVariable long id) {
        try {
            var item = service.findById(id);
            return new ResponseEntity<>(item, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(Item.builder().build(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    ResponseEntity<List<Item>> getAll(
            @RequestParam(required = false) String done
    ) {
        List<Item> result;
        if (done != null && !done.equals("")) {
            result = service.findByDoneAll(Boolean.parseBoolean(done));
        } else {
            result = service.findAll();
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteItem(@PathVariable long id) {
        try {
            var serchItem = service.findById(id);
            service.delete(serchItem);

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}
