package ru.skyeng.task.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.skyeng.task.dto.HistoryDto;
import ru.skyeng.task.dto.PostalItemDto;
import ru.skyeng.task.services.PostalItemService;

import javax.validation.Valid;
import java.util.List;

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

    @PatchMapping("/{itemId}/arrive/{officeId}")
    public Boolean arriveToPostOffice(@PathVariable Long itemId, @PathVariable Long officeId) {
        return postalItemService.arriveToPostOffice(itemId, officeId);
    }

    @PatchMapping("/{itemId}/leave/{officeId}")
    public Boolean leaveThePostOffice(@PathVariable Long itemId, @PathVariable Long officeId) {
        return postalItemService.leaveThePostOffice(itemId, officeId);
    }

    @PatchMapping("/{itemId}")
    public Boolean getTheAddressee(@PathVariable Long itemId) {
        return postalItemService.getTheAddressee(itemId);
    }

    @GetMapping("/{itemId}")
    public List<HistoryDto> getHistory(@PathVariable Long itemId) {
        return postalItemService.getHistory(itemId);
    }

}
