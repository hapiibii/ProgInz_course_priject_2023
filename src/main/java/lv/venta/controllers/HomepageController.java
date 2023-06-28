package lv.venta.controllers;

import lv.venta.models.News;
import lv.venta.models.StudioProgramm;
import lv.venta.services.INewsService;
import lv.venta.services.impl.NewsService;
import lv.venta.models.CalendarActivity;
import lv.venta.models.CalendarSchedule;
import lv.venta.services.ICalendarService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/home-page")
public class HomepageController {

	 private final INewsService newsService;
	 private final ICalendarService calendarService;
	 private final IStudioProgrammServic studioProgService;

	 @Autowired
	 public HomepageController(INewsService newsService, ICalendarService calendarService) {
		 this.newsService = newsService;
	     this.calendarService = calendarService;
	 }
 
	 @GetMapping
	 public String showHomePage(Model model) {
	        LocalDate currentDate = LocalDate.now();
	        List<News> activeNews = newsService.getActiveNews(currentDate);
	        model.addAttribute("activeNewsList", activeNews);
	        
	        List<News> allNews = newsService.getAllNews();
	        model.addAttribute("allNews", allNews);
	        
	        // Iegūstam kalendāra grafiku
	        List<CalendarActivity> scheduleList = calendarService.getActivitiesEndingWithinTwoWeeks();
	        model.addAttribute("scheduleList", scheduleList);
	        
	        
	        return "homepage";
	  }
	  
	 @GetMapping("/all-news")
	 public String showAllNews(Model model) {
	     List<News> allNews = newsService.getAllNews();
	     model.addAttribute("allNews", allNews);
	     return "news-all";
	 }

     @GetMapping("/create-news")
     public String showCreateForm(Model model) {
        model.addAttribute("news", new News());
        return "news-add";
     }

     @PostMapping("/create-news")
     public String createNews(@ModelAttribute("news") News news) {
        newsService.createNews(news);
        return "redirect:/home-page";
     }

     @GetMapping("/edit-news/{newsId}")
     public String showEditForm(@PathVariable("newsId") long newsId, Model model) {
        News news = newsService.getNewsById(newsId);
        model.addAttribute("news", news);
        return "news-edit";
     }

     @PostMapping("/edit-news/{newsId}")
     public String updateNews(@PathVariable("newsId") long newsId, @ModelAttribute("news") News news) throws Exception {
        newsService.updateNews(newsId, news.getTitle(), news.getDescription(), news.getStartDate(), news.getEndDate());
        return "redirect:/home-page";
     }

     @GetMapping("/delete-news/{newsId}")
     public String showDeleteConfirmation(@PathVariable("newsId") long newsId, Model model) {
        News news = newsService.getNewsById(newsId);
        model.addAttribute("news", news);
        return "news-delete-confirmation";
     }

     @PostMapping("/delete-news/{newsId}")
     public String deleteNews(@PathVariable("newsId") long newsId) throws Exception {
        newsService.deleteNews(newsId);
        return "redirect:/home-page";
     }
     
    //<-----Kalendārais grafiks------->

     // Pievienot jaunu aktivitāti konkrētam gadam un studiju programmai
     
     @GetMapping("/calendar-add")
     public String showCalendarAddForm() {
         return "calendar-add";
     }
     
     @PostMapping("/add-activity")
     public String addActivity(@RequestParam("gads") int gads,
                               @RequestParam("studioProgrammId") StudioProgramm studioProgrammId,
                               @ModelAttribute("activity") CalendarActivity activity) {
         calendarService.addActivity(studioProgrammId, gads, activity.getActivity(), activity.getActivityEndDate(), activity.getActivityImplementation());
         return "redirect:/home-page";
     }

     // Dzēst aktivitāti konkrētam gadam un studiju programmai
     @PostMapping("/remove-activity")
     public String removeActivity(@RequestParam("gads") int gads,
                                  @RequestParam("studioProgrammId") StudioProgramm studioProgrammId,
                                  @RequestParam("activityId") long activityId) {
         calendarService.removeActivity(studioProgrammId, gads, activityId);
         return "redirect:/home-page";
     }

     
     //TODO japarbauda kad Albina pabeigs savu
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
