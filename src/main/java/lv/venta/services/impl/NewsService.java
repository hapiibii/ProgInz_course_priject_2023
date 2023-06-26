package lv.venta.services.impl;

import lv.venta.models.News;
import lv.venta.repos.INewsRepo;
import lv.venta.services.INewsService;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NewsService implements INewsService {
    
    private final INewsRepo newsRepo;
    
    @Autowired
    public NewsService(INewsRepo newsRepo) {
        this.newsRepo = newsRepo;
    }

    @Override
    public void createNews(News news) {
        newsRepo.save(news);
    }

    @Override
    public void updateNews(News news) {
        newsRepo.save(news);
    }

    @Override
    public void deleteNews(long newsId) {
        newsRepo.deleteById(newsId);
    }

    @Override
    public List<News> getAllNews() {
        return (List<News>) newsRepo.findAll();
    }

    @Override
    public List<News> getActiveNews(LocalDate currentDate) {
    	return newsRepo.findByEndDateGreaterThan(currentDate);
    }
}
