package lv.venta.services;

import java.time.LocalDate;
import java.util.List;

import lv.venta.models.CalendarSchedule;

public interface ICalendarScheduleService {
	
	void addActivity(CalendarSchedule activity);
	void removeActivity(long idActivity) throws Exception;
	List<CalendarSchedule> showAllByDate(LocalDate date);
    List<CalendarSchedule> findByTwoWeeks(LocalDate currentDate);

}
