package uz.pdp.planboard.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskFilter {
    private String title;
    private Integer userId;
    private LocalDate deadline;

}
