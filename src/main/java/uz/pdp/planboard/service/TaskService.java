package uz.pdp.planboard.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import uz.pdp.planboard.entity.Columns;
import uz.pdp.planboard.entity.Task;
import uz.pdp.planboard.entity.Users;
import uz.pdp.planboard.repo.ColumnRepository;
import uz.pdp.planboard.repo.TaskRepository;
import uz.pdp.planboard.repo.UsersRepository;

import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final ColumnRepository columnRepository;
    private final UsersRepository usersRepository;

    // Konstruktor orqali repositorylarni inject qilish
    public TaskService(TaskRepository taskRepository, ColumnRepository columnRepository, UsersRepository usersRepository) {
        this.taskRepository = taskRepository;
        this.columnRepository = columnRepository;
        this.usersRepository = usersRepository;
    }

    // Taskni yangi columnga o'tkazish
    public void updateTaskColumn(Integer taskId, Integer columnId) {
        try {
            Task task = taskRepository.findById(taskId)
                    .orElseThrow(() -> new RuntimeException("Task not found"));

            Columns column = columnRepository.findById(columnId)
                    .orElseThrow(() -> new RuntimeException("Column not found"));

            task.setColumn(column);
            taskRepository.save(task);
        } catch (Exception e) {
            // Xatolikni loglash
            System.err.println("Error updating task column: " + e.getMessage());
            throw new RuntimeException("Failed to update task column", e);
        }
    }


    public Integer getLoggedInUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                UserDetails userDetails = (UserDetails) principal;

                Optional<Users> user = usersRepository.findByPhoneNumber(userDetails.getUsername());
                return user.get().getId();
            }
        }
        return null;
    }
}
