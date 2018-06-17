package first.project.controllers;


import first.project.entity.Status;
import first.project.repository.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/status")
public class StatusController {

    private StatusRepository statusRepository;

    @Autowired
    public StatusController(StatusRepository statusRepository) {
        this.statusRepository = statusRepository;
    }

    @GetMapping("/list")
    public String listPriority(Model model) {
        model.addAttribute("listStatus", statusRepository.findAll());
        return "status/list";
    }

    @GetMapping("/add")
    public String showForm(Model model) {
        model.addAttribute("status", new Status());
        return "status/add";
    }

    @PostMapping("/add")
    public String performForm(@ModelAttribute @Valid Status status, BindingResult result) {
        if (result.hasErrors()) {
            return "status/add";
        }
        statusRepository.save(status);
        return "redirect:/status/list";
    }

    @GetMapping("/update/{id}")
    public String showUpdate(Model model, @PathVariable long id) {
        model.addAttribute("status", statusRepository.findOne(id));
        return "status/update";
    }

    @PostMapping("update")
    public String perfromUpdate(@ModelAttribute Status status) {
        statusRepository.save(status);
        return "redirect:/status/list";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable long id) {
        statusRepository.delete(id);
        return "redirect:/status/list";
    }
}
