package ru.skyeng.task.mappers;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import ru.skyeng.task.dto.HistoryDto;
import ru.skyeng.task.models.History;

import java.util.List;

@Component
@Mapper(componentModel = "spring")
public interface HistoryMapper {
    List<HistoryDto> toHistoryDtoList(List<History> histories);
}
