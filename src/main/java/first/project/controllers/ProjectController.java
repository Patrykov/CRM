package first.project.controllers;

import first.project.entity.Activity;
import first.project.entity.Project;
import first.project.entity.User;
import first.project.repository.ActivityRepository;
import first.project.repository.ProjectRepository;
import first.project.repository.UserRepository;
import first.project.service.CurrentUser;
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
@RequestMapping("/project")
public class ProjectController {

    private ProjectRepository projectRepository;
    private UserRepository userRepository;
    private ActivityRepository activityRepository;

    @Autowired
    public ProjectController(ProjectRepository projectRepository, UserRepository userRepository, ActivityRepository activityRepository) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
        this.activityRepository = activityRepository;
    }

    @ModelAttribute("usersList")
    List<User> getUser() {
        return userRepository.findAll();
    }

    @GetMapping("/list")
    public String listProject(Model model) {
        model.addAttribute("listProject", projectRepository.findAll());
        return "project/list";
    }

    @GetMapping("/add")
    public String showForm(Model model) {
        model.addAttribute("project", new Project());
        return "project/add";
    }

    @PostMapping("/add")
    public String performForm(@ModelAttribute @Valid Project project, BindingResult result, @AuthenticationPrincipal CurrentUser customUser) {
        if (result.hasErrors()) {
            return "project/add";
        }
        projectRepository.save(project);
        Activity activity = new Activity();
        activity.setTitle("Project  " + project.getName() + " was added by:  " + customUser.getUsername()
                + " " + "<a href='/project/list'>Show</a>");
//        activity.setUser(customUser.getUser());
        activityRepository.save(activity);
        return "redirect:/project/list";
    }

    @GetMapping("/update/{id}")
    public String showUpdate(Model model, @PathVariable long id) {
        model.addAttribute("project", projectRepository.findOne(id));
        return "project/update";
    }

    @PostMapping("update")
    public String perfromUpdate(@ModelAttribute Project project) {
        projectRepository.save(project);
        return "redirect:/project/list";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable long id) {
        projectRepository.delete(id);
        return "redirect:/project/list";
    }
}
