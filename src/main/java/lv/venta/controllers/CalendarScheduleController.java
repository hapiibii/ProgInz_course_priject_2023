package lv.venta.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lv.venta.dto.CalendarScheduleDTO;
import lv.venta.models.CalendarActivity;
import lv.venta.models.CalendarSchedule;
import lv.venta.models.StudioProgramm;
import lv.venta.repos.ICalendarScheduleRepo;
import lv.venta.services.ICalendarScheduleService;
import lv.venta.services.ICalendarService;
import lv.venta.services.IStudioProgrammService;

@Controller
@RequestMapping("/Calendar-schedule")
public class CalendarScheduleController {	
	 private final ICalendarService calendarService;
	 private final IStudioProgrammService studioProgService;
	 private final ICalendarScheduleService calendarScheduleService;
	 private final ICalendarScheduleRepo calendarScheduleRepo;
	 
	 @Autowired
	 public CalendarScheduleController(ICalendarService calendarService, IStudioProgrammService studioProgService,ICalendarScheduleService calendarScheduleService,ICalendarScheduleRepo calendarScheduleRepo) {
		 this.calendarService = calendarService;
		 this.studioProgService = studioProgService;
		 this.calendarScheduleService = calendarScheduleService;
		 this.calendarScheduleRepo = calendarScheduleRepo;
	 }
	 
	 
	 @GetMapping("/studio-programms")
	 public String showAllProgramms(Model model) {
	     Iterable<StudioProgramm> programms = studioProgService.findAll();
	     model.addAttribute("programms", programms);
	     return "calendar-schedule-programms";
	 }

	 @GetMapping("/studio-programms/{id}")
	 public String showProgrammActivities(@PathVariable Long id, Model model) {
	     Optional<StudioProgramm> programmOptional = studioProgService.findById(id);
	     
	     if (programmOptional.isPresent()) {
	         StudioProgramm programm = programmOptional.get();
	         model.addAttribute("programm", programm);
	         // Šeit pievienojiet papildu kodu, lai izgūtu programmas aktivitātes un pievienotu tās modelim, ja nepieciešams
	         return "calendar-schedule-program-activities";
	     } else {
	         // No programm found with given ID
	         return "error404"; // assuming you have a custom error 404 page
	     }
	 }

	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	/* @GetMapping
	 public String showAll(Model model) {
		 List<CalendarScheduleDTO> schedules = calendarScheduleService.getAllCalendarSchedules();
	        model.addAttribute("schedules", schedules);
	        System.out.println(schedules);
		 return "calendar-schedule";
	 }*/

	 @GetMapping("/calendar-add")
	 public String showCalendarAddForm(Model model) {
		 CalendarScheduleDTO calendarScheduleDTO = new CalendarScheduleDTO();
	     model.addAttribute("calendarScheduleDTO", calendarScheduleDTO);
	     return "calendar-add";
	 }

	 @PostMapping("/calendar-add")
	 public String addCalendarSchedule(@ModelAttribute("calendarScheduleDTO") CalendarScheduleDTO calendarScheduleDTO) {
	     calendarScheduleService.addCalendarSchedule(calendarScheduleDTO);
	     return "redirect:/Calendar-schedule";
	 }	 
	 
	
	 @GetMapping("/delete/{id}")
	 public String showDeleteConfirmationPage(@PathVariable Long id, Model model) {
	     // Iegūt konkrētā ieraksta datus izmantojot repository
	     Optional<CalendarSchedule> schedule = calendarScheduleRepo.findById(id);

	     // Pārbaudiet, vai ieraksts ir atrasts
	     if (schedule.isPresent()) {
	         model.addAttribute("schedule", schedule.get());
	         return "calendar-schedule-delete";
	     } else {
	         // Ieraksts nav atrasts, varat veikt atbilstošas darbības šeit, piemēram, paziņojumu par kļūdu.
	         return "redirect:/Calendar-schedule";
	     }
	 }
	 
	 @PostMapping("/delete/{id}")
	 public String deleteCalendarSchedule(@PathVariable Long id) {
	     // Veiciet faktisko dzēšanas darbību šeit, izmantojot id
	     calendarScheduleService.deleteCalendarScheduleById(id);
	     return "redirect:/Calendar-schedule";
	 }
	 
	 
/*	 
     // Iegūt kālendāra grafikus pēc gada un programmas
     @GetMapping("/schedules/{year}/{programId}")
     public String showSchedulesByYearAndProgram(@PathVariable("year") int year,
                                                 @PathVariable("programId") long programId,
                                                 Model model) {
         StudioProgramm program = studioProgService.getStudioProgrammById(programId);
         List<CalendarActivity> scheduleList = calendarService.getActivitiesByYearAndProgram(year, program);
         model.addAttribute("program", program);
         model.addAttribute("scheduleList", scheduleList);
         return "schedules-by-program";
     }*/
}
