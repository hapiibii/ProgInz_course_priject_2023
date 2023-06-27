package lv.venta.repos;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import lv.venta.models.CalendarSchedule;

public interface ICalendarScheduleRepo extends CrudRepository<CalendarSchedule,Long> {
	List<CalendarSchedule> findByMonth(LocalDate activityDate);
	List<CalendarSchedule> findByDate(LocalDate date);
	List<CalendarSchedule> findByDateBetween(LocalDate currentDate, LocalDate twoWeeksLater);
}
