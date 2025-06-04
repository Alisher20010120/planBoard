package uz.pdp.planboard.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.planboard.entity.*;
import uz.pdp.planboard.repo.*;
import uz.pdp.planboard.service.TaskService;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class TaskCon {
    private final TaskRepository taskRepository;
    private final UsersRepository usersRepository;
    private final TaskService taskService;
    private final CommentRepository commentRepository;
    private final AttachmentRepository attachmentRepository;
    private final AttachmentContentRepository attachmentContentRepository;

    public TaskCon(TaskRepository taskRepository, UsersRepository usersRepository, TaskService taskService, CommentRepository commentRepository,
                   AttachmentRepository attachmentRepository, AttachmentContentRepository attachmentContentRepository) {
        this.taskRepository = taskRepository;
        this.usersRepository = usersRepository;
        this.taskService = taskService;
        this.commentRepository = commentRepository;
        this.attachmentRepository = attachmentRepository;
        this.attachmentContentRepository = attachmentContentRepository;
    }

    @GetMapping("/inTask")
    public String inTask(@RequestParam Integer taskId, Model model) {
        System.out.println("taskId = " + taskId);
        model.addAttribute("tasks", taskRepository.findById(taskId).get());
        model.addAttribute("users", usersRepository.findAll());
        model.addAttribute("comments", commentRepository.findAll());
        return "task";
    }

    @GetMapping("/addUser")
    public String addUser(@RequestParam Integer userId, @RequestParam Integer taskId, Model model) {

        Users users = usersRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task not found with ID: " + taskId));


        List<Users> currentUsers = task.getUsers();
        if (currentUsers == null) {
            currentUsers = new ArrayList<>();
        } else if (currentUsers.contains(users)) {
            // Agar foydalanuvchi allaqachon ro'yxatda bo'lsa, qayta qo'shilmaydi
            return "redirect:/inTask?taskId=" + taskId + "&error=UserAlreadyAdded";
        }
        currentUsers.add(users);
        task.setUsers(currentUsers);

        taskRepository.save(task);


        return "redirect:/inTask?taskId=" + taskId;
    }

    @PostMapping("/removeUser")
    public String removeUser(@RequestParam Integer userId, @RequestParam Integer taskId) {

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task not found with ID: " + taskId));
        Users user = usersRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));


        List<Users> currentUsers = task.getUsers();
        if (currentUsers != null) {
            currentUsers.removeIf(u -> u.getId().equals(user.getId()));
            task.setUsers(currentUsers);
            taskRepository.save(task);
        }

        return "redirect:/inTask?taskId=" + taskId;
    }

    @GetMapping("/addComment")
    public String addComment(@RequestParam String text, @RequestParam Integer taskId, Model model) {
        Integer userId = taskService.getLoggedInUserId();
        Optional<Users> users = usersRepository.findById(userId);
        Optional<Task> task = taskRepository.findById(taskId);
        Comment comment = new Comment(null, text, users.get(), task.get());
        commentRepository.save(comment);
        return "redirect:/inTask?taskId=" + taskId;
    }

    @PostMapping("/addBigTask")
    public String addBigTask( @RequestParam Integer taskId,
                              @RequestParam String title,
                              @RequestParam(required = false) String description,
                              @RequestParam(required = false) LocalDate deadline,
                              @RequestParam(required = false) MultipartFile picture
    ) throws IOException {

        Optional<Task> task = taskRepository.findById(taskId);
        if (task.isPresent()) {
            task.get().setTitle(title);
            if (description != null) {
                task.get().setDescription(description);
            }
            if (deadline != null) {
                task.get().setDeadline(deadline);
            }
            if (picture != null) {
                String originalFilename = picture.getOriginalFilename();
                Attachment attachment = new Attachment(originalFilename);
                attachmentRepository.save(attachment);
                task.get().setAttachment(attachment);
                AttachmentContent attachmentContent = new AttachmentContent();
                attachmentContent.setAttachment(attachment);
                attachmentContent.setFile(picture.getBytes());
                attachmentContentRepository.save(attachmentContent);
            }
            taskRepository.save(task.get());
        }

        return "redirect:/cabinet";
    }


}
