package ru.skyeng.task.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.skyeng.task.dto.PostalItemDto;
import ru.skyeng.task.models.PostalItem;
import ru.skyeng.task.services.PostalItemService;

import javax.validation.Valid;

@RestController
@RequestMapping("/item")
@RequiredArgsConstructor
public class PostalItemController {
    private final PostalItemService postalItemService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PostalItemDto createItem(@Valid @RequestBody PostalItemDto postalItemDto) {
        return postalItemService.createItem(postalItemDto);
    }

}
