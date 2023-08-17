package ru.skyeng.task.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import ru.skyeng.task.enums.Status;

@Data
public class HistoryDto {
    @JsonProperty("postalItemId")
    private Long postalItemId;
    @JsonProperty("postOfficeId")
    private Long postOfficeId;
    @JsonProperty("status")
    private Status status;
}
