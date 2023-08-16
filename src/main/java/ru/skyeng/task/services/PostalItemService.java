package ru.skyeng.task.services;

import ru.skyeng.task.dto.PostalItemDto;

public interface PostalItemService {
    PostalItemDto createItem(PostalItemDto postalItemDto);

    void arriveToPostOffice(Long itemId, Long officeId);

    void leaveThePostOffice(Long itemId, Long officeId);
}
