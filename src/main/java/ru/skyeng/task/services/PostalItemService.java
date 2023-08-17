package ru.skyeng.task.services;

import ru.skyeng.task.dto.HistoryDto;
import ru.skyeng.task.dto.PostalItemDto;

import java.util.List;

public interface PostalItemService {
    PostalItemDto createItem(PostalItemDto postalItemDto);

    Boolean arriveToPostOffice(Long itemId, Long officeId);

    Boolean leaveThePostOffice(Long itemId, Long officeId);

    Boolean getTheAddressee(Long itemId);

    List<HistoryDto> getHistory(Long itemId);
}
