package first.project.controllers;

import first.project.entity.Priority;
import first.project.repository.PriorityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/priority")
public class PriorityController {

    private PriorityRepository priorityRepository;

    @Autowired
    public PriorityController(PriorityRepository priorityRepository) {
        this.priorityRepository = priorityRepository;
    }

    @GetMapping("/list")
    public String listPriority(Model model) {
        model.addAttribute("listPriority", priorityRepository.findAll());
        return "priority/list";
    }

    @GetMapping("/add")
    public String showForm(Model model) {
        model.addAttribute("priority", new Priority());
        return "priority/add";
    }

    @PostMapping("/add")
    public String performForm(@ModelAttribute @Valid Priority priority, BindingResult result) {
        if (result.hasErrors()) {
            return "priority/add";
        }
        priorityRepository.save(priority);
        return "redirect:/priority/list";
    }

    @GetMapping("/update/{id}")
    public String showUpdate(Model model, @PathVariable long id) {
        model.addAttribute("priority", priorityRepository.findOne(id));
        return "priority/update";
    }

    @PostMapping("update")
    public String perfromUpdate(@ModelAttribute Priority priority) {
        priorityRepository.save(priority);
        return "redirect:/priority/list";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable long id) {
        priorityRepository.delete(id);
        return "redirect:/priority/list";
    }
}
