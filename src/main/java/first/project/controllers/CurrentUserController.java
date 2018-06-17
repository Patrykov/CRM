package first.project.controllers;


import first.project.entity.*;
import first.project.repository.ActivityRepository;
import first.project.repository.ProjectRepository;
import first.project.repository.StatusRepository;
import first.project.repository.TaskRepository;
import first.project.service.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class CurrentUserController {

    private ProjectRepository projectRepository;
    private TaskRepository taskRepository;
    private StatusRepository statusRepository;
    private ActivityRepository activityRepository;

    @Autowired
    public CurrentUserController(ProjectRepository projectRepository, TaskRepository taskRepository, StatusRepository statusRepository, ActivityRepository activityRepository) {
        this.projectRepository = projectRepository;
        this.taskRepository = taskRepository;
        this.statusRepository = statusRepository;
        this.activityRepository = activityRepository;
    }

    //UserProjects
    @GetMapping("/userProjectList")
    public String listProject(Model model, @AuthenticationPrincipal CurrentUser currentUser) {
        model.addAttribute("listUserProject", projectRepository.findAllByUsersEquals(currentUser.getUser()));
        return "currentUser/currentUserProjectList";
    }

    //UserTasks
    @GetMapping("/userTaskList")
    public String listTask(Model model, @AuthenticationPrincipal CurrentUser currentUser) {
        model.addAttribute("listUserTask", taskRepository.findAllByUserEquals(currentUser.getUser()));
        return "currentUser/currentUserTaskList";
    }

    //EditTask
    @GetMapping("/userTaskUpdate/{id}")
    public String showform(Model model, @PathVariable long id) {
        model.addAttribute("userUpdate", taskRepository.findOne(id));
        return "currentUser/CurrentUserUpdate";
    }

    @PostMapping("/userTaskUpdate")
    public String perfromForm(@RequestParam long id, @RequestParam long status, @AuthenticationPrincipal CurrentUser currentUser) {

        Task t = taskRepository.findOne(id);
        Status s = statusRepository.findOne(status);
        t.setStatus(s);
        taskRepository.save(t);
        Activity activity = new Activity();
        activity.setTitle("Status in Project: " + t.getProject().getName() + " has changed for:  " + t.getStatus().getName() + " by: " + currentUser.getUser().getLogin());
        activity.setUser(currentUser.getUser());
        activityRepository.save(activity);

        return "redirect:/userTaskList";
    }

    @ModelAttribute("statuses")
    public List<Status> status() {
        return statusRepository.findByActivityIsNotNullOrderBySortAsc();
    }

    //homePage
    @GetMapping("/userHome")
    public String home(Model model, @AuthenticationPrincipal CurrentUser currentUser) {
        model.addAttribute("userProjectList", projectRepository.findbyUserscreatedlimit(currentUser.getUser().getId()));
        model.addAttribute("userStatusChange", activityRepository.findAllByUserId(currentUser.getUser().getId()));
        return "currentUser/currentUserHome";
    }

    //checkRolePage
    @GetMapping("/checkrole")
    public String loginPage(HttpServletRequest httpServletRequest) {
        if (httpServletRequest.isUserInRole("ADMIN")) {
            return "redirect:/home";
        } else if (httpServletRequest.isUserInRole("USER")) {
            return "redirect:/userHome";
        }
        return "redirect:/";
    }
}

