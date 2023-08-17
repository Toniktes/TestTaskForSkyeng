package ru.skyeng.task.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.skyeng.task.enums.Status;
import ru.skyeng.task.enums.TypeOfPostalItem;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PostalItemDto {
    @NotNull
    private TypeOfPostalItem type;
    @NotNull
    private Long addresseeIndex;
    @NotNull
    private String addresseeAddress;
    @NotNull
    private String addresseeName;
    private Status status;
}
