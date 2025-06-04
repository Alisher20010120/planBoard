package uz.pdp.planboard.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import uz.pdp.planboard.entity.Columns;
import uz.pdp.planboard.entity.Task;

import uz.pdp.planboard.entity.Users;
import uz.pdp.planboard.payload.TaskFilter;
import uz.pdp.planboard.repo.AttachmentRepository;
import uz.pdp.planboard.repo.ColumnRepository;
import uz.pdp.planboard.repo.TaskRepository;
import uz.pdp.planboard.repo.UsersRepository;
import uz.pdp.planboard.service.TaskService;
import uz.pdp.planboard.specification.TaskFilterSpecification;

import java.util.List;


@Controller
@RequiredArgsConstructor
public class PageController {
    private final ColumnRepository columnRepository;
    private final UsersRepository usersRepository;
    private final TaskRepository taskRepository;
    private final AttachmentRepository attachmentRepository;
    private final TaskService taskService;


    @GetMapping("/login")
    public String login() {
        return "login";
    }
    @GetMapping("/cabinet")
    public String page(
            @ModelAttribute TaskFilter taskFilter,
            @RequestParam(required = false)String columnName,
            @RequestParam(required = false) String isCompleted, Model model) {
        if (columnName != null) {
            Columns column;
            if (isCompleted!=null) {
                column = new Columns(null, columnName, true, true);
            } else {
                column = new Columns(null, columnName, false, true);
            }
            columnRepository.save(column);

        }
        Specification<Task> spec = TaskFilterSpecification.filterSpecification(taskFilter);
        model.addAttribute( "attachment",attachmentRepository.findAll());
        model.addAttribute("columns", columnRepository.findAll());
        List<Task> tasks = taskRepository.findAll(spec);
        model.addAttribute("tasks", tasks);
        model.addAttribute("users", usersRepository.findAll());
        model.addAttribute("taskFilter", taskFilter);
        Integer userId = taskService.getLoggedInUserId();
        Users users = usersRepository.findById(userId).orElseThrow();
        model.addAttribute("logginUser",users);
        return "cabinet";
    }



    @GetMapping("addColumn")
    public String addColumn() {
        return "addColumn";
    }

    @GetMapping("/addTask")
    public String addTask(Model model,@RequestParam(required = false) Integer columnId) {
        Columns columns = columnRepository.findById(columnId).orElseThrow();
        model.addAttribute("columns", columns);
        model.addAttribute("users",usersRepository.findAll());
        return "addTask";
    }


    @GetMapping("/savetask")
    public String saveTask(
            @RequestParam(required = false) Integer columnId,
            @RequestParam(required = false)String title,
            @RequestParam(required = false)String description,
            Model model
            ) {
        Columns columns = columnRepository.findById(columnId).orElseThrow();

        Task task = new Task(null,title,description,null, null,columns,null,null);
        taskRepository.save(task);
        return "redirect:/cabinet";
    }

    @GetMapping("/cabinet/filter")
    public String index(Model model, @ModelAttribute TaskFilter taskFilter) {
        Specification<Task> spec = TaskFilterSpecification.filterSpecification(taskFilter);
        List<Task> tasks = taskRepository.findAll(spec);
        model.addAttribute("tasks", tasks);
        model.addAttribute("users", usersRepository.findAll());
        model.addAttribute("taskFilter", taskFilter); // TaskFilter obyekti modelga qo'shilgan

        return "cabinet";
    }

    @GetMapping("/report")
    public String report(Model model) {
     model.addAttribute("criminals",taskRepository.getCriminalResult());
        return "report";
    }
    @GetMapping("/developer")
    public String developer(Model model) {
        model.addAttribute("developers",taskRepository.getDeveloperResults());
        return "developerResult";
    }

    @PostMapping("/logout")
    public String logout() {
       SecurityContextHolder.clearContext();
       return "redirect:/cabinet";
    }
}
