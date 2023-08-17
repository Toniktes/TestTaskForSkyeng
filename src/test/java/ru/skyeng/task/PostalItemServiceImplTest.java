package ru.skyeng.task;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;
import ru.skyeng.task.dto.HistoryDto;
import ru.skyeng.task.dto.PostalItemDto;
import ru.skyeng.task.enums.Status;
import ru.skyeng.task.enums.TypeOfPostalItem;
import ru.skyeng.task.exception.ValidationException;
import ru.skyeng.task.mappers.HistoryMapper;
import ru.skyeng.task.mappers.PostalItemMapper;
import ru.skyeng.task.models.PostalItem;
import ru.skyeng.task.repositories.HistoryRepository;
import ru.skyeng.task.repositories.PostalItemRepository;
import ru.skyeng.task.services.PostalItemServiceImpl;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class PostalItemServiceImplTest {
    private final PostalItemServiceImpl postalItemService;
    private final PostalItemRepository postalItemRepository;
    private final PostalItemMapper postalItemMapper;
    private final HistoryRepository historyRepository;
    private final HistoryMapper historyMapper;
    private PostalItemDto postalItemDto;

    @BeforeEach
    public void setUp() {
        postalItemDto = PostalItemDto.builder()
                .type(TypeOfPostalItem.MESSAGE)
                .addresseeIndex(184041L)
                .addresseeAddress("Кировская 27")
                .addresseeName("Name")
                .status(Status.SENT)
                .build();
        postalItemRepository.save(postalItemMapper.toPostalItem(postalItemDto));
    }

    @AfterEach
    public void afterEach() {
        postalItemRepository.deleteAll();
    }

    @Test
    void createItem_whenInvoked_thenSaveItem() {
        PostalItemDto savedItem = postalItemService.createItem(postalItemDto);

        assertEquals(postalItemDto, savedItem);
    }

    @Test
    void arriveToPostOffice_whenInvoked_thenChangeStatusAndReturnTrue() {
        Boolean answer = postalItemService.arriveToPostOffice(1L, 1L);

        assertTrue(true, String.valueOf(answer));
        assertEquals(Status.ARRIVED_AT_THE_POST_OFFICE, postalItemRepository.getById(1L).getStatus());
    }

    @Test
    void arriveToPostOffice_whenInvoked_thenThrowException() {
        PostalItem postalItem = postalItemRepository.getById(1L);
        postalItem.setStatus(Status.RECEIVED_BY_THE_ADDRESSEE);
        postalItemRepository.save(postalItem);

        assertThrows(ValidationException.class, () -> postalItemService.arriveToPostOffice(1L, 1L));
    }

    @Test
    void leaveThePostOffice_whenInvoked_thenChangeStatusAndReturnTrue() {
        PostalItem postalItem = postalItemRepository.getById(1L);
        postalItem.setStatus(Status.ARRIVED_AT_THE_POST_OFFICE);
        postalItemRepository.save(postalItem);
        Boolean answer = postalItemService.leaveThePostOffice(1L, 1L);

        assertTrue(true, String.valueOf(answer));
        assertEquals(Status.LEFT_THE_POST_OFFICE, postalItemRepository.getById(1L).getStatus());
    }

    @Test
    void leaveThePostOffice_whenInvoked_thenThrowException() {
        assertThrows(ValidationException.class, () -> postalItemService.leaveThePostOffice(1L, 1L));
    }

    @Test
    void getTheAddressee_whenInvoked_thenChangeStatusAndReturnTrue() {
        Boolean answer = postalItemService.getTheAddressee(1L);

        assertTrue(true, String.valueOf(answer));
        assertEquals(Status.RECEIVED_BY_THE_ADDRESSEE, postalItemRepository.getById(1L).getStatus());
    }

    @Test
    void getHistory_whenInvoked_thenReturnListOfHistory() {
        postalItemService.arriveToPostOffice(1L, 1L);
        List<HistoryDto> answer = postalItemService.getHistory(1L);

        assertEquals(answer, historyMapper.toHistoryDtoList(historyRepository.findAllByPostalItemId(1L)));
    }
}
