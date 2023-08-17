package ru.skyeng.task.services;

import ru.skyeng.task.dto.HistoryDto;
import ru.skyeng.task.dto.PostalItemDto;

import java.util.List;

public interface PostalItemService {
    PostalItemDto createItem(PostalItemDto postalItemDto);

    void arriveToPostOffice(Long itemId, Long officeId);

    void leaveThePostOffice(Long itemId, Long officeId);

    PostalItemDto getTheAddressee(Long itemId);

    List<HistoryDto> getHistory(Long itemId);
}
