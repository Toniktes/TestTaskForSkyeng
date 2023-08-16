package ru.skyeng.task.mappers;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import ru.skyeng.task.dto.HistoryDto;
import ru.skyeng.task.models.History;

@Component
@Mapper(componentModel = "spring")
public interface HistoryMapper {
    History toHistory(HistoryDto historyDto);

    HistoryDto toHistoryDto(History history);
}
