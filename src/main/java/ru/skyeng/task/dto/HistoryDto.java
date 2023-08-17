package ru.skyeng.task.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import ru.skyeng.task.enums.Status;

@Data
@Builder
public class HistoryDto {
    @JsonProperty("postalItemId")
    private Long postalItemId;
    @JsonProperty("postOfficeId")
    private Long postOfficeId;
    @JsonProperty("status")
    private Status status;
}
