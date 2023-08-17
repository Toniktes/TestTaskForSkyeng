package ru.skyeng.task;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.skyeng.task.controllers.PostalItemController;
import ru.skyeng.task.dto.HistoryDto;
import ru.skyeng.task.dto.PostalItemDto;
import ru.skyeng.task.enums.Status;
import ru.skyeng.task.enums.TypeOfPostalItem;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class PostalItemControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private PostalItemController postalItemController;

    @Test
    void shouldReturn201whenCreateItem() throws Exception {
        PostalItemDto postalItemDto = PostalItemDto.builder()
                .type(TypeOfPostalItem.MESSAGE)
                .addresseeIndex(184041L)
                .addresseeAddress("Кировская 27")
                .addresseeName("Name")
                .build();
        Mockito.when(postalItemController.createItem(Mockito.any())).thenReturn(postalItemDto);
        mockMvc.perform(post("/item")
                        .content(objectMapper.writeValueAsString(postalItemDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(postalItemDto)));
    }

    @Test
    void shouldReturnTrueWhenArriveToPostOffice() throws Exception {
        Mockito.when(postalItemController.arriveToPostOffice(Mockito.any(), Mockito.any())).thenReturn(true);
        mockMvc.perform(patch("/item/{itemId}/arrive/{officeId}", 1, 1)
                        .content(objectMapper.writeValueAsString(true))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturnTrueWhenLeaveThePostOffice() throws Exception {
        Mockito.when(postalItemController.leaveThePostOffice(Mockito.any(), Mockito.any())).thenReturn(true);
        mockMvc.perform(patch("/item/{itemId}/leave/{officeId}", 1, 1)
                        .content(objectMapper.writeValueAsString(true))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturnTrueWhenGetTheAddressee() throws Exception {
        Mockito.when(postalItemController.getTheAddressee(Mockito.any())).thenReturn(true);
        mockMvc.perform(patch("/item/{itemId}", 1)
                        .content(objectMapper.writeValueAsString(true))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturnListOfHistoryWhenGetHistory() throws Exception {
        HistoryDto historyDto = HistoryDto.builder()
                .postalItemId(1L)
                .postOfficeId(1L)
                .status(Status.ARRIVED_AT_THE_POST_OFFICE)
                .build();
        Mockito.when(postalItemController.getHistory(Mockito.any())).thenReturn(List.of(historyDto));
        mockMvc.perform(get("/item/{itemId}", 1)
                        .content(objectMapper.writeValueAsString(true))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
