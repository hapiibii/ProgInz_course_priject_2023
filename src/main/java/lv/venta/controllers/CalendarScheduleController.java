package lv.venta.controllers;

import java.time.LocalDate;
import java.time.Year;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import lv.venta.dto.CalendarScheduleDTO;
import lv.venta.models.CalendarActivity;
import lv.venta.models.CalendarSchedule;
import lv.venta.models.StudioProgramm;
import lv.venta.services.ICalendarScheduleService;
import lv.venta.services.ICalendarService;
import lv.venta.services.IStudioProgrammService;

@Controller
@RequestMapping("/Calendar-schedule")
public class CalendarScheduleController {   //Domes kalendārais grafiks, kas tiek noteikts katrai fakultātei
	 private final ICalendarService calendarService;
	 private final IStudioProgrammService studioProgService;
	 private final ICalendarScheduleService calendarScheduleService;
	 
	 @Autowired
	 public CalendarScheduleController(ICalendarService calendarService, IStudioProgrammService studioProgService,ICalendarScheduleService calendarScheduleService) {
		 this.calendarService = calendarService;
		 this.studioProgService = studioProgService;
		 this.calendarScheduleService = calendarScheduleService;
	 }
	 
	 @GetMapping
	 public String showAll(Model model) {
		 List<CalendarScheduleDTO> schedules = calendarScheduleService.getAllCalendarSchedules();
	        model.addAttribute("schedules", schedules);
	        System.out.println(schedules);
		 return "calendar-schedule";
	 }

	 @GetMapping("/calendar-add")
	 public String showCalendarAddForm(Model model) {
		 
	     // Jaizveido jauns CalendarScheduleDTO objekts, lai saņemtu lietotāja ievadītos datus
	     CalendarScheduleDTO calendarScheduleDTO = new CalendarScheduleDTO();
	     model.addAttribute("calendarScheduleDTO", calendarScheduleDTO);
	     return "calendar-add"; //  HTML, kur tiek parādīta forma datu ievadīšanai
	 }

	 @PostMapping("/calendar-add")
	 public String addCalendarSchedule(@ModelAttribute("calendarScheduleDTO") CalendarScheduleDTO calendarScheduleDTO) {
	     // Izsauc servisa metodi
	     calendarScheduleService.addCalendarSchedule(calendarScheduleDTO);
	     return "redirect:/Calendar-schedule"; // Pēc veiksmīgas pievienošanas atgriežam lietotāju atpakaļ uz formas lapu
	 }

	 @PostMapping("/delete/{id}")
	 public String deleteCalendarSchedule(@PathVariable Long id) {
	     calendarScheduleService.removeCalendarSchedule(id);
	     return "redirect:/Calendar-schedule";
	 }
	 
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
     }
}
