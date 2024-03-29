package lv.venta.services.impl;

import lv.venta.models.News;
import lv.venta.repos.INewsRepo;
import lv.venta.services.INewsService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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
    	LocalDate currentDate = LocalDate.now();

        // Pārbauda, vai sākuma datums nav agrāks par šodienas datumu
        if (news.getStartDate().isBefore(currentDate)) {
            news.setStartDate(currentDate);
        }

        // Pārbauda, vai sākuma datums ir vēlāks par beigu datumu
        if (news.getStartDate().isAfter(news.getEndDate())) {
            news.setEndDate(news.getStartDate());
        }

        newsRepo.save(news);
    }

    @Override
    public void updateNews(long newsId, String title, String description, LocalDate startDate, LocalDate endDate) throws Exception {
        News existingNews = newsRepo.findById(newsId).orElse(null);

        if (existingNews != null) {
            LocalDate currentDate = LocalDate.now();

            // Pārbauda, vai sākuma datums nav agrāks par šodienas datumu
            if (startDate != null && startDate.isBefore(currentDate)) {
                startDate = currentDate;
            }

            // Pārbauda, vai sākuma datums ir vēlāks par beigu datumu
            if (startDate != null && endDate != null && startDate.isAfter(endDate)) {
                endDate = startDate;
            }

            // Iestat jaunās vērtības, ja tās ir
            if (title != null) {
                existingNews.setTitle(title);
            }
            if (description != null) {
                existingNews.setDescription(description);
            }
            if (startDate != null) {
                existingNews.setStartDate(startDate);
            }
            if (endDate != null) {
                existingNews.setEndDate(endDate);
            }

            newsRepo.save(existingNews);
        } else {
            throw new Exception("Jaunums netika atrasts");
        }
    }

    @Override
    public void deleteNews(long newsId) throws Exception {
        try {
            newsRepo.deleteById(newsId);
        } catch (Exception e) {
            throw new Exception("Neizdevās dzēst jaunumu ar ID: " + newsId, e);
        }
    }

    @Override
    public List<News> getAllNews() {
        return (List<News>) newsRepo.findAll();
    }

    @Override
    public List<News> getActiveNews(LocalDate currentDate) {
    	//startDate ir mazāks vai vienāds ar currentDate un endDate ir lielāks vai vienāds ar currentDate
        return newsRepo.findByStartDateLessThanEqualAndEndDateGreaterThanEqual(currentDate, currentDate);
    }

    @Override
    public News getNewsById(long newsId) {
        Optional<News> newsOptional = newsRepo.findById(newsId);
        return newsOptional.orElse(null);
    }
    
    
}
