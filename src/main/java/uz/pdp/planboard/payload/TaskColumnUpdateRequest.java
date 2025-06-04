package uz.pdp.planboard.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class TaskColumnUpdateRequest {
    private Integer taskId;
    private Integer columnId;
}
