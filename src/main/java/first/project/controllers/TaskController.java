package first.project.controllers;

import first.project.entity.*;
import first.project.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/task")
public class TaskController {

    private TaskRepository taskRepository;
    private ProjectRepository projectRepository;
    private StatusRepository statusRepository;
    private PriorityRepository priorityRepository;
    private UserRepository userRepository;
    private ActivityRepository activityRepository;


    @Autowired
    public TaskController(TaskRepository taskRepository, ProjectRepository projectRepository, StatusRepository statusRepository, PriorityRepository priorityRepository, UserRepository userRepository, ActivityRepository activityRepository) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
        this.statusRepository = statusRepository;
        this.priorityRepository = priorityRepository;
        this.userRepository = userRepository;
        this.activityRepository = activityRepository;
    }

    @ModelAttribute("projects")
    List<Project> getProjects() {
        return projectRepository.findAll();
    }

    @ModelAttribute("statuses")
    public List<Status> status() {
        return statusRepository.findByActivityIsNotNullOrderBySortAsc();
    }

    @ModelAttribute("priorities")
    public List<Priority> getPriority() {
        return priorityRepository.findAll();
    }

    @ModelAttribute("users")
    List<User> getUser() {
        return userRepository.findAll();
    }

    @GetMapping("/list")
    public String listTask(Model model) {
        model.addAttribute("listTask", taskRepository.findAll());
        return "task/list";
    }

    @GetMapping("/add")
    public String showForm(Model model) {
        model.addAttribute("task", new Task());
        return "task/add";
    }

    @PostMapping("/add")
    public String performForm(@ModelAttribute @Valid Task task, BindingResult result, @AuthenticationPrincipal UserDetails customUser) {
        if (result.hasErrors()) {
            return "task/add";
        }
        taskRepository.save(task);
        Activity activity = new Activity();
        activity.setTitle("Task " + task.getPriority().getName() + " was added to Project: " + task.getProject().getName()
                + " by: " + customUser.getUsername() + " " + "<a href='/task/list'>Show</a>");
        activityRepository.save(activity);
        return "redirect:/task/list";
    }

    @GetMapping("/update/{id}")
    public String showUpdate(Model model, @PathVariable long id) {
        model.addAttribute("task", taskRepository.findOne(id));
        return "task/update";
    }

    @PostMapping("update")
    public String perfromUpdate(@ModelAttribute Task task, @AuthenticationPrincipal UserDetails customUser) {
        taskRepository.save(task);

        Activity activity = new Activity();
        activity.setTitle("Status in Project: " + task.getProject().getName() + " has changed by: " + customUser.getUsername()
                + " for: " + task.getStatus().getName() + " " + "<a href='/task/list'>Show</a>");
        activityRepository.save(activity);
        return "redirect:/task/list";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable long id) {
        taskRepository.delete(id);
        return "redirect:/task/list";
    }

}

