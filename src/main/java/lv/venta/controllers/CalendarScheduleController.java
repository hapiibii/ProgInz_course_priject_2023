package lv.venta.controllers;


import java.io.IOException;
import java.time.LocalDate;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lv.venta.dto.CalendarScheduleDTO;
import lv.venta.models.CalendarActivity;
import lv.venta.models.CalendarSchedule;
import lv.venta.models.StudioProgramm;

import lv.venta.models.Translate;

import lv.venta.services.ICalendarScheduleService;

import lv.venta.services.ICalendarService;
import lv.venta.services.IStudioProgrammService;

@Controller
@RequestMapping("/Calendar-schedule")
public class CalendarScheduleController {	
	 private final ICalendarService calendarService;
	 private final IStudioProgrammService studioProgService;
	 private final ICalendarScheduleService calendarScheduleService;
	 
	 @Autowired
	 public CalendarScheduleController(ICalendarService calendarService, IStudioProgrammService studioProgService,
			 ICalendarScheduleService calendarScheduleService) {
		 this.calendarService = calendarService;
		 this.studioProgService = studioProgService;
		 this.calendarScheduleService = calendarScheduleService;
	 }	 
	 

	 @GetMapping
	 public String showAll(Model model) throws IOException {
		 List<CalendarSchedule> calendarSchedules = calendarService.getCalendarSchedules();   
		 model.addAttribute("calendarSchedules", calendarSchedules);
		 
		 // Calendar-schedule page translation
		 //String calendarScheduleTranslate = Translate.translate("lv", "en", "Kalendārais grafiks");
		 //String calTrans1 = calendarScheduleTranslate.split("translatedText")[1].trim().split("\"")[2];
		 //model.addAttribute("TranslateKalendGrafiks",calTrans1);
		 
		 return "calendar-schedule";
	 }

	 @GetMapping("/studio-programms")
	 public String showAllProgramms(Model model) {
	     Iterable<StudioProgramm> programms = studioProgService.findAll();
	     model.addAttribute("programms", programms);
	     return "calendar-schedule-programms";

	 }

	 @GetMapping("/studio-programms/{id}")
	 public String showProgrammActivities(@PathVariable Long id, @RequestParam(required = false) Integer year, Model model) {
	     Optional<StudioProgramm> programmOptional = studioProgService.findById(id);
	     if (programmOptional.isPresent()) {
	         StudioProgramm programm = programmOptional.get();
	         model.addAttribute("programm", programm);
	         List<Integer> years = calendarService.getAllUniqueYears();
	         model.addAttribute("years", years);
	         if (year != null) {
	             List<CalendarActivity> activities = calendarService.getActivitiesByYearAndProgram(year, programm);
	             model.addAttribute("selectedYear", year);
	             model.addAttribute("schedules", activities);
	         }
	         return "calendar-schedule-program-activities";
	     } else {
	         return "error-page";
	     }
	 }	 

	 @GetMapping("/calendar-add")
	 public String showCalendarAddForm(Model model) {
	     CalendarScheduleDTO calendarScheduleDTO = new CalendarScheduleDTO();
	     model.addAttribute("calendarScheduleDTO", calendarScheduleDTO);
	     return "calendar-add";
	 }

	 @PostMapping("/calendar-add")
	 public String addCalendarSchedule(@ModelAttribute("calendarScheduleDTO") CalendarScheduleDTO calendarScheduleDTO) {
	     calendarScheduleService.addCalendarSchedule(calendarScheduleDTO);
	     return "redirect:/Calendar-schedule/studio-programms";
	 }	 
	
	 @PostMapping("/delete/{id}")
	 public String deleteCalendarSchedule(@PathVariable Long id) {
	     calendarScheduleService.deleteCalendarScheduleById(id);
	     return "redirect:/Calendar-schedule/studio-programms";
	 }
	 
	 @GetMapping("/edit/{idActivity}")
	 public String editActivity(@PathVariable Long idActivity, Model model) {
	     Optional<CalendarActivity> activityOptional = calendarService.findActivityById(idActivity);
	     if (activityOptional.isPresent()) {
	         model.addAttribute("activity", activityOptional.get());
	         return "calendar-schedule-edit";
	     } else {
	         return "error404";
	     }
	 }
	 
	 @PostMapping("/update/{idActivity}")
	 public String updateActivity(@PathVariable Long idActivity, @ModelAttribute CalendarActivity activity) {
	     calendarService.updateActivity(idActivity, activity);
	     return "redirect:/Calendar-schedule/studio-programms"; 
	 }

}
