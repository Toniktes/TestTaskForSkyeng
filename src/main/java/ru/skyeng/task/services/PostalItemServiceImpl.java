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
    public Boolean arriveToPostOffice(Long itemId, Long officeId) {
        validation(itemId, officeId);
        PostalItem postalItem = postalItemRepository.getById(itemId);
        if (postalItem.getStatus() == Status.RECEIVED_BY_THE_ADDRESSEE || postalItem.getStatus() == Status.ARRIVED_AT_THE_POST_OFFICE) {
            throw new ValidationException("статус отправления должен быть SENT или LEFT_THE_POST_OFFICE");
        }
        postalItem.setStatus(Status.ARRIVED_AT_THE_POST_OFFICE);
        postalItemRepository.save(postalItem);

        historyRepository.save(new History(null, itemId, officeId, Status.ARRIVED_AT_THE_POST_OFFICE));
        return true;
    }

    @Override
    public Boolean leaveThePostOffice(Long itemId, Long officeId) {
        validation(itemId, officeId);
        PostalItem postalItem = postalItemRepository.getById(itemId);
        if (postalItem.getStatus() != Status.ARRIVED_AT_THE_POST_OFFICE) {
            throw new ValidationException("статус отправления должен быть ARRIVED_AT_THE_POST_OFFICE");
        }

        postalItem.setStatus(Status.LEFT_THE_POST_OFFICE);
        postalItemRepository.save(postalItem);

        historyRepository.save(new History(null, itemId, officeId, Status.LEFT_THE_POST_OFFICE));

        return true;
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
    public Boolean getTheAddressee(Long itemId) {
        validation(itemId);
        PostalItem postalItem = postalItemRepository.getById(itemId);
        postalItem.setStatus(Status.RECEIVED_BY_THE_ADDRESSEE);
        postalItemRepository.save(postalItem);

        historyRepository.save(new History(null, itemId, null, Status.RECEIVED_BY_THE_ADDRESSEE));

        postalItemRepository.save(postalItem);

        return true;
    }

    @Override
    public List<HistoryDto> getHistory(Long itemId) {
        validation(itemId);
        return historyMapper.toHistoryDtoList(historyRepository.findAllByPostalItemId(itemId));
    }
}
