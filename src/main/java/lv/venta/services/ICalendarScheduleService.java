package lv.venta.services;

import java.util.List;

import lv.venta.models.CalendarSchedule;
import lv.venta.models.StudioProgramm;

public interface ICalendarScheduleService {
	
	void createCalendarSchedule(StudioProgramm studioProgramm, int year);
	void addCalenadScheduleActivity(long idCalendar, long idActivity);
	void removeCalendarSchedule(long idCalendar);
	List<CalendarSchedule> getCalendarSchedules();
}
