package ru.coffeeturbo.todo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.coffeeturbo.todo.model.Category;
import ru.coffeeturbo.todo.service.CategoryService;

import java.util.List;

@RequestMapping({"categories", "categories/"})
@RestController
public class CategoryController {

    @Autowired
    private CategoryService service;

    @GetMapping
    public ResponseEntity<List<Category>> listAll() {
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }
}
