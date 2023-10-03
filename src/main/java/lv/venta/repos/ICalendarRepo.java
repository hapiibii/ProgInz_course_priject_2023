package lv.venta.repos;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import lv.venta.models.CalendarActivity;
import lv.venta.models.CalendarSchedule;


public interface ICalendarRepo extends CrudRepository<CalendarActivity, Long> {
	List<CalendarActivity> findAll();


}
