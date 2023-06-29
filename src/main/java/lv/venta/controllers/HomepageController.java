package lv.venta.controllers;

import lv.venta.models.News;
import lv.venta.models.StudioProgramm;
import lv.venta.services.INewsService;
import lv.venta.services.IStudioProgrammService;
import lv.venta.services.impl.NewsService;
import lv.venta.models.Activities;
import lv.venta.models.CalendarActivity;
import lv.venta.models.CalendarSchedule;
import lv.venta.models.Documents;
import lv.venta.services.IActivitiesService;
import lv.venta.services.ICalendarService;
import lv.venta.services.IDocumentsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

import javax.print.Doc;

@Controller
@RequestMapping("/home-page")
public class HomepageController {

	 private final INewsService newsService;
	 private final ICalendarService calendarService;
	 private final IStudioProgrammService studioProgService;
	 private final IDocumentsService documentService;
	 private final IActivitiesService activitiesService;

	 @Autowired
	 public HomepageController(INewsService newsService, ICalendarService calendarService, IStudioProgrammService studioProgService, IDocumentsService documentService,  IActivitiesService activitiesService) {
		 this.newsService = newsService;
	     this.calendarService = calendarService;
	     this.studioProgService = studioProgService;
	     this.documentService = documentService;
	     this.activitiesService = activitiesService;
	 }
 
	 @GetMapping
	 public String showHomePage(Model model) {
	        LocalDate currentDate = LocalDate.now();
	        List<News> activeNews = newsService.getActiveNews(currentDate);
	        model.addAttribute("activeNewsList", activeNews);
	        
	        List<Activities> activities = activitiesService.getDatesWithActivities();
	        model.addAttribute("activeDatesList", activities);
	        
	        List<Documents> allDocuments = documentService.retrieveAllDocuments();
	        model.addAttribute("documentsList", allDocuments);
	        
	        return "homepage";
	  }
	 
	 //News --> 
	 
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
     
     // Documents 
     
     @GetMapping("/all-documents")
     public String showAllDocuments (Model model) {
    	 List<Documents> allDocuments = documentService.retrieveAllDocuments();
    	 model.addAttribute("documentsList", allDocuments);
    	 return "all-documents-page";
     }
     
     @GetMapping("/create-document")
     public String showDocumentCreate (Model model) {
    	 model.addAttribute("document", new Documents());
    	 return "document-insert-page";
     }
     
     @PostMapping("/create-document")
     public String createDocument (@ModelAttribute("document") Documents document) {
    	 documentService.insertDocument(document.getDocumentName(), document.getFile());
    	 return "redirect:/home-page";
     }
     
     @GetMapping("/delete-document/{iddocument}")
     public String showDeleteFunction (@PathVariable("iddocument") long iddocument, Model model) throws Exception {
    	 Documents doc = documentService.retrieveDocumentById(iddocument);
    	 model.addAttribute("document", doc);
    	 return "document-delete-page";
     }
     
     @PostMapping("/delete-document/{iddocument}")
     public String deleteDocument (@PathVariable("iddocument") long iddocument) throws Exception {
    	 documentService.deleteDocumentByDocumetId(iddocument);
    	 return "redirect:/home-page";
     }
     
}
