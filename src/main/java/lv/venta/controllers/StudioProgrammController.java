package lv.venta.controllers;

import lv.venta.models.StudioProgramm;
import lv.venta.services.IStudioProgrammService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.Optional;

@Controller
@RequestMapping("/studioprogramms")
public class StudioProgrammController {

    @Autowired
    private IStudioProgrammService studioProgrammService;

    @GetMapping
    public String showAllProgramms(Model model) {
        model.addAttribute("programms", studioProgrammService.findAll());
        return "studioprogramms-list";
    }

    @GetMapping("/add")
    public String addProgramm(Model model) {
        model.addAttribute("programm", new StudioProgramm());
        return "studioprogramms-edit";
    }

    @GetMapping("/edit/{id}")
    public String editProgramm(@PathVariable Long id, Model model) {
        Optional<StudioProgramm> programm = studioProgrammService.findById(id);
        if (programm.isPresent()) {
            model.addAttribute("programm", programm.get());
            return "studioprogramms-edit";
        }
        return "redirect:/studioprogramms";
    }

    @GetMapping("/delete/{id}")
    public String confirmDelete(@PathVariable Long id, Model model) {
        Optional<StudioProgramm> programm = studioProgrammService.findById(id);
        if (programm.isPresent()) {
            model.addAttribute("programm", programm.get());
            return "studioprogramms-delete";
        }
        return "redirect:/studioprogramms";
    }

    @PostMapping("/deleteConfirmed/{id}")
    public String deleteProgramm(@PathVariable Long id) {
        Optional<StudioProgramm> programm = studioProgrammService.findById(id);
        programm.ifPresent(studioProgrammService::delete);
        return "redirect:/studioprogramms";
    }

    @PostMapping("/save")
    public String saveProgramm(@Valid @ModelAttribute StudioProgramm programm, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "studioprogramms-edit";
        }
        studioProgrammService.save(programm);
        return "redirect:/studioprogramms";
    }
}
