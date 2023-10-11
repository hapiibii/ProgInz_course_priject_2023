package lv.venta.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import lv.venta.models.StudioProgramm;
import lv.venta.services.impl.StudioProgrammService;
@Controller
public class StudioProgramController {

    private final StudioProgrammService studioProgramService;

    @Autowired
    public StudioProgramController(StudioProgrammService studioProgramService) {
        this.studioProgramService = studioProgramService;
    }

    @GetMapping("/studio-programs")
    public String showAllStudioPrograms(Model model) {
        List<StudioProgramm> studioPrograms = studioProgramService.getAllStudioProgramms();
        model.addAttribute("studioPrograms", studioPrograms);
        return "all-studio-programs"; 
    }
}
