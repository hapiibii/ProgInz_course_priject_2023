package lv.venta.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import lv.venta.models.Degree;
import lv.venta.models.Faculty;
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
    
    @GetMapping("/add-studio-program")
    public String showAddStudioProgramForm(Model model) {
        List<Faculty> faculties = studioProgramService.getAllFaculties();
        List<Degree> degrees = studioProgramService.getAllDegrees();
        model.addAttribute("faculties", faculties);
        model.addAttribute("degrees", degrees);

        // Izveidojiet jaunu tukšu studiju programmu objektu un pievienojiet to modelim
        StudioProgramm newProgram = new StudioProgramm();
        model.addAttribute("newProgram", newProgram);

        return "add-studio-program-form";
    }

    @PostMapping("/add-studio-program")
    public String addStudioProgram(@ModelAttribute("newProgram") StudioProgramm newProgram) {
        // Izveidojiet jaunu studiju programmu, izmantojot servisu
        studioProgramService.createStudioProgramm(newProgram);

        return "redirect:/studio-programs";
    }
    
    @GetMapping("/delete-studio-program/{id}")
    public String deleteStudioProgram(@PathVariable Long id) {
        // Jūsu dzēšanas kodu šeit
        try {
			studioProgramService.deleteStudioProgramm(id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return "redirect:/studio-programs"; // Pāradresē lietotāju uz studiju programmu sarakstu pēc dzēšanas
    }
    
    @GetMapping("/edit-studio-program/{id}")
    public String showEditStudioProgramForm(@PathVariable Long id, Model model) {
        StudioProgramm studioProgram = studioProgramService.getStudioProgrammById(id);
        model.addAttribute("studioProgram", studioProgram);
        return "edit-studio-program-form";
    }

    @PostMapping("/edit-studio-program/{id}")
    public String editStudioProgram(@PathVariable Long id, @ModelAttribute("studioProgram") StudioProgramm studioProgram) {
        try {
			studioProgramService.updateStudioProgramm(id, studioProgram.getFaculty(), studioProgram.getDegree(), studioProgram.getTitle());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return "redirect:/studio-programs";
    }
}
