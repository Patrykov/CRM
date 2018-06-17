package first.project.controllers;

import first.project.entity.User;
import first.project.repository.ActivityRepository;
import first.project.repository.ProjectRepository;
import first.project.repository.UserRepository;
import first.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class HomeController {

    private UserRepository userRepository;
    private UserService userService;
    private ProjectRepository projectRepository;
    private ActivityRepository activityRepository;

    @Autowired
    public HomeController(UserRepository userRepository, UserService userService, ProjectRepository projectRepository, ActivityRepository activityRepository) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.projectRepository = projectRepository;
        this.activityRepository = activityRepository;
    }


    //firstPage
    @GetMapping("/")
    public String firstPage() {
        return "firstPage";
    }

    //loginUser
    @GetMapping("/login")
    public String hello() {

        return "home/login";
    }

    //homePage
    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute("fiveProject", projectRepository.findbycreatedlimit());
        model.addAttribute("fiveActivity", activityRepository.findbycreatedlimit());
        return "home/home";
    }

    //logout
    @GetMapping("/logout")
    public String bye() {
        return "home/logout";
    }

    //403 błąd logowania
    @GetMapping("/403")
    public String error403() {
        return "home/403";
    }

    //register
    @GetMapping("/register")
    public String showForm(Model model) {
        model.addAttribute("user", new User());
        return "home/register";
    }

    @PostMapping("/register")
    public String performForm(@ModelAttribute @Valid User user, BindingResult result) {
        if (result.hasErrors()) {
            return "home/register";
        }
        userService.saveUser(user);
        return "redirect:/";
    }

    //Login do panelu Admina
    @GetMapping("/loginToAdmin")
    public String adminLogin() {

        return "admin/login";
    }
}

