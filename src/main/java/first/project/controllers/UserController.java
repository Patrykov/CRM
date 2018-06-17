package first.project.controllers;

import first.project.entity.User;
import first.project.repository.UserRepository;
import first.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/user")
public class UserController {

    private UserRepository userRepository;
    private UserService userService;

    @Autowired
    public UserController(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @GetMapping("/list")
    public String listUser(Model model) {
        model.addAttribute("listUser", userRepository.findAll());
        return "user/list";
    }

    @GetMapping("/add")
    public String showForm(Model model) {
        model.addAttribute("user", new User());
        return "user/add";
    }

    @PostMapping("/add")
    public String performForm(@ModelAttribute @Valid User user, BindingResult result) {
        if (result.hasErrors()) {
            return "user/add";
        }
        userService.saveUser(user);
        return "redirect:/user/list";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable long id) {
        userRepository.delete(id);
        return "redirect:/user/list";
    }

    @GetMapping("/update/{id}")
    public String showUpdate(Model model, @PathVariable long id) {
        model.addAttribute("user", userRepository.findOne(id));
        return "user/update";
    }

    @PostMapping("/update")
    public String performUpdate(@ModelAttribute @Valid User user, BindingResult result) {
        if (result.hasErrors()) {
            return "user/update";
        }
        userRepository.save(user);
        return "redirect:/user/list";
    }


}
