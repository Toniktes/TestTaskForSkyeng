package ru.skyeng.task.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.skyeng.task.dto.PostalItemDto;
import ru.skyeng.task.mappers.PostalItemMapper;
import ru.skyeng.task.repositories.PostalItemRepository;

@Service
@RequiredArgsConstructor
public class PostalItemServiceImpl implements PostalItemService {
    private final PostalItemRepository postalItemRepository;
    private final PostalItemMapper mapper;

    @Override
    public PostalItemDto createItem(PostalItemDto postalItemDto) {
        return mapper.toPostalItemDto(postalItemRepository.save(mapper.toPostalItem(postalItemDto)));
    }
}
