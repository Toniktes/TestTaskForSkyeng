package ru.skyeng.task.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.skyeng.task.dto.HistoryDto;
import ru.skyeng.task.dto.PostalItemDto;
import ru.skyeng.task.enums.Status;
import ru.skyeng.task.exception.NotFoundException;
import ru.skyeng.task.exception.ValidationException;
import ru.skyeng.task.mappers.HistoryMapper;
import ru.skyeng.task.mappers.PostalItemMapper;
import ru.skyeng.task.models.History;
import ru.skyeng.task.models.PostalItem;
import ru.skyeng.task.repositories.HistoryRepository;
import ru.skyeng.task.repositories.PostOfficeRepository;
import ru.skyeng.task.repositories.PostalItemRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostalItemServiceImpl implements PostalItemService {
    private final PostalItemRepository postalItemRepository;
    private final PostOfficeRepository postOfficeRepository;
    private final HistoryRepository historyRepository;
    private final PostalItemMapper postalItemMapper;
    private final HistoryMapper historyMapper;

    @Override
    public PostalItemDto createItem(PostalItemDto postalItemDto) {
        PostalItem postalItem = postalItemMapper.toPostalItem(postalItemDto);
        postalItem.setStatus(Status.SENT);
        return postalItemMapper.toPostalItemDto(postalItemRepository.save(postalItem));
    }

    @Override
    public void arriveToPostOffice(Long itemId, Long officeId) {
        validation(itemId, officeId);
        PostalItem postalItem = postalItemRepository.getById(itemId);
        postalItem.setStatus(Status.ARRIVED_AT_THE_POST_OFFICE);
        postalItemRepository.save(postalItem);

        historyRepository.save(new History(null, itemId, officeId, Status.ARRIVED_AT_THE_POST_OFFICE));
    }

    @Override
    public void leaveThePostOffice(Long itemId, Long officeId) {
        validation(itemId, officeId);
        PostalItem postalItem = postalItemRepository.getById(itemId);
        if (postalItem.getStatus() != Status.ARRIVED_AT_THE_POST_OFFICE) {
            throw new ValidationException("статус отправления должен быть ARRIVED_AT_THE_POST_OFFICE");
        }

        postalItem.setStatus(Status.LEFT_THE_POST_OFFICE);
        postalItemRepository.save(postalItem);

        historyRepository.save(new History(null, itemId, officeId, Status.LEFT_THE_POST_OFFICE));
    }

    private void validation(Long itemId, Long officeId) {
        if (!postalItemRepository.existsById(itemId)) {
            throw new NotFoundException("нет отправления с id: " + itemId);
        }
        if (!postOfficeRepository.existsById(officeId)) {
            throw new NotFoundException("нет отделения с id: " + officeId);
        }
    }

    private void validation(Long itemId) {
        if (!postalItemRepository.existsById(itemId)) {
            throw new NotFoundException("нет отправления с id: " + itemId);
        }
    }

    @Override
    public PostalItemDto getTheAddressee(Long itemId) {
        validation(itemId);
        PostalItem postalItem = postalItemRepository.getById(itemId);
        postalItem.setStatus(Status.RECEIVED_BY_THE_ADDRESSEE);
        postalItemRepository.save(postalItem);

        historyRepository.save(new History(null, itemId, null, Status.RECEIVED_BY_THE_ADDRESSEE));

        return postalItemMapper.toPostalItemDto(postalItemRepository.save(postalItem));
    }

    @Override
    public List<HistoryDto> getHistory(Long itemId) {
        validation(itemId);
        return historyRepository.findAllById(itemId)
                .stream()
                .sorted()
                .map(historyMapper::toHistoryDto)
                .collect(Collectors.toList());
    }
}
