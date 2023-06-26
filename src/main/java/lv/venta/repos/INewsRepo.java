package lv.venta.repos;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import lv.venta.models.News;

public interface INewsRepo extends CrudRepository<News, Long> {

	List<News> findByEndDateGreaterThan(LocalDate currentDate);

}
