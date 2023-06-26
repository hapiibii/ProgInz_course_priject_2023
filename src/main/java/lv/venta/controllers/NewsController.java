package lv.venta.controllers;

import lv.venta.models.News;
import lv.venta.services.INewsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/home-page")
public class NewsController {

    private final INewsService newsService;

    @Autowired
    public NewsController(INewsService newsService) {
        this.newsService = newsService;
    }

    @GetMapping
    public String showHomePage(Model model) {
        LocalDate currentDate = LocalDate.now();
        List<News> activeNews = newsService.getActiveNews(currentDate);
        model.addAttribute("activeNewsList", activeNews);
        return "home-page";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("news", new News());
        return "create-news";
    }

    @PostMapping("/create")
    public String createNews(@ModelAttribute("news") News news) {
        newsService.createNews(news);
        return "redirect:/home-page";
    }

    @GetMapping("/edit/{newsId}")
    public String showEditForm(@PathVariable("newsId") long newsId, Model model) {
        News news = newsService.getNewsById(newsId);
        model.addAttribute("news", news);
        return "edit-news";
    }

    @PostMapping("/edit/{newsId}")
    public String updateNews(@PathVariable("newsId") long newsId, @ModelAttribute("news") News news) throws Exception {
        newsService.updateNews(newsId, news.getTitle(), news.getDescription(), news.getStartDate(), news.getEndDate());
        return "redirect:/home-page";
    }

    @GetMapping("/delete/{newsId}")
    public String showDeleteConfirmation(@PathVariable("newsId") long newsId, Model model) {
        News news = newsService.getNewsById(newsId);
        model.addAttribute("news", news);
        return "delete-confirmation";
    }

    @PostMapping("/delete/{newsId}")
    public String deleteNews(@PathVariable("newsId") long newsId) throws Exception {
        newsService.deleteNews(newsId);
        return "redirect:/home-page";
    }
    
}
