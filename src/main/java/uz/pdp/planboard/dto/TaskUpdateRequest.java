package uz.pdp.planboard.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class TaskUpdateRequest {
    private Integer taskId;
    private Integer columnId;
}
