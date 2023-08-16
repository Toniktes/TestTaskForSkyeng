package ru.skyeng.task.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.skyeng.task.dto.PostalItemDto;
import ru.skyeng.task.enums.Status;
import ru.skyeng.task.exception.NotFoundException;
import ru.skyeng.task.mappers.PostalItemMapper;
import ru.skyeng.task.models.History;
import ru.skyeng.task.models.PostalItem;
import ru.skyeng.task.repositories.HistoryRepository;
import ru.skyeng.task.repositories.PostOfficeRepository;
import ru.skyeng.task.repositories.PostalItemRepository;

@Service
@RequiredArgsConstructor
public class PostalItemServiceImpl implements PostalItemService {
    private final PostalItemRepository postalItemRepository;
    private final PostOfficeRepository postOfficeRepository;
    private final HistoryRepository historyRepository;
    private final PostalItemMapper mapper;

    @Override
    public PostalItemDto createItem(PostalItemDto postalItemDto) {
        PostalItem postalItem = mapper.toPostalItem(postalItemDto);
        postalItem.setStatus(Status.SENT);
        return mapper.toPostalItemDto(postalItemRepository.save(postalItem));
    }

    @Override
    public void arriveToPostOffice(Long itemId, Long officeId) {
        if (!postalItemRepository.existsById(itemId)) {
            throw new NotFoundException("нет отправления с id: " + itemId);
        }
        if (!postOfficeRepository.existsById(officeId)) {
            throw new NotFoundException("нет отделения с id: " + officeId);
        }
        PostalItem postalItem = postalItemRepository.getById(itemId);
        postalItem.setStatus(Status.AT_POST_OFFICE);
        postalItemRepository.save(postalItem);

        historyRepository.save(new History(0L, itemId, officeId));
    }
}
