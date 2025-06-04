package uz.pdp.planboard.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.planboard.dto.TaskUpdateRequest;
import uz.pdp.planboard.entity.Columns;
import uz.pdp.planboard.entity.Task;
import uz.pdp.planboard.payload.TaskColumnUpdateRequest;
import uz.pdp.planboard.repo.ColumnRepository;
import uz.pdp.planboard.repo.TaskRepository;
import uz.pdp.planboard.service.TaskService;



@RestController
public class TaskController {

    private final TaskService taskService;
    private final TaskRepository taskRepository;
    private final ColumnRepository columnRepository;

    @Autowired
    public TaskController(TaskService taskService, TaskRepository taskRepository, ColumnRepository columnRepository) {
        this.taskService = taskService;
        this.taskRepository = taskRepository;
        this.columnRepository = columnRepository;
    }

    @PostMapping("/update-task-column")
    public @ResponseBody String updateTaskColumn(@RequestBody TaskUpdateRequest request) {
        Task task = taskRepository.findById(request.getTaskId()).orElseThrow();
        Columns column = columnRepository.findById(request.getColumnId()).orElseThrow();
        task.setColumn(column);
        taskRepository.save(task);
        return "Task moved successfully!";
    }

}
