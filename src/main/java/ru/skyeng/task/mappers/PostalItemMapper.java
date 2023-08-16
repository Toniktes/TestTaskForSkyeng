package ru.skyeng.task.mappers;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import ru.skyeng.task.dto.PostalItemDto;
import ru.skyeng.task.models.PostalItem;

@Component
@Mapper(componentModel = "spring")
public interface PostalItemMapper {
    PostalItem toPostalItem(PostalItemDto postalItemDto);

    PostalItemDto toPostalItemDto(PostalItem postalItem);
}
