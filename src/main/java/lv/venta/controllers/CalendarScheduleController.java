package lv.venta.controllers;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lv.venta.models.CalendarActivity;
import lv.venta.models.CalendarSchedule;
import lv.venta.models.StudioProgramm;
import lv.venta.services.ICalendarService;
import lv.venta.services.IStudioProgrammService;

@Controller
@RequestMapping("/Calendar-schedule")
public class CalendarScheduleController {
	 private final ICalendarService calendarService;
	 private final IStudioProgrammService studioProgService;
	 
	 @Autowired
	 public CalendarScheduleController(ICalendarService calendarService, IStudioProgrammService studioProgService) {
		 this.calendarService = calendarService;
		 this.studioProgService = studioProgService;
	 }
	 
	 @GetMapping
	 public String showAll(Model model) {
		 List<CalendarSchedule> calendarSchedules = calendarService.getCalendarSchedules();   
		 model.addAttribute("calendarSchedules", calendarSchedules);
		 return "calendar-schedule";
	 }
	 
	 @GetMapping("/calendar-add")
     public String showCalendarAddForm(Model model) {
		 List<StudioProgramm> allStudioProgramms = studioProgService.getAllStudioProgramms();
		 model.addAttribute("calendarSchedules", new  CalendarSchedule());
		 model.addAttribute("allStudioProgramms", allStudioProgramms);
         return "calendar-add";
     }
     
     @PostMapping("/calendar-add")
     public String addActivity(@ModelAttribute("calendarSchedule") CalendarSchedule calendarSchedule, CalendarActivity calendarActivity) {

         calendarService.addActivity(calendarSchedule.getStudioProgramm(), calendarSchedule.getGads(), calendarActivity.getActivity(), calendarActivity.getActivityEndDate(), calendarActivity.getActivityImplementation());
         return "redirect:/Calendar-schedule";
     }

     // Dzēst aktivitāti konkrētam gadam un studiju programmai
     @PostMapping("/remove-activity")
     public String removeActivity(@RequestParam("gads") int gads,
                                  @RequestParam("studioProgrammId") StudioProgramm studioProgrammId,
                                  @RequestParam("activityId") long activityId) {
         calendarService.removeActivity(studioProgrammId, gads, activityId);
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
