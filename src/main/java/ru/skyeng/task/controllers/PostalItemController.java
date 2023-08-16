package ru.skyeng.task.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.skyeng.task.models.PostalItem;

@RestController
@RequestMapping("/item")
public class PostalItemController {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PostalItem createItem(@RequestBody PostalItem postalItem) {
        return null;
    }

}
