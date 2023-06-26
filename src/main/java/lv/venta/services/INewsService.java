package lv.venta.services;

import lv.venta.models.News;

import java.time.LocalDate;
import java.util.List;

public interface INewsService {
    void createNews(News news);
    void updateNews(long newsId, String title, String description, LocalDate startDate, LocalDate endDate);
    void deleteNews(long newsId);
    List<News> getAllNews();
    List<News> getActiveNews(LocalDate currentDate);
	
}
