package ru.skyeng.task.dto;

import ru.skyeng.task.enums.TypeOfPostalItem;

public class PostalItemDto {
    private TypeOfPostalItem type;
    private Long addresseeIndex;
    private String addresseeAddress;
    private String addresseeName;
}
