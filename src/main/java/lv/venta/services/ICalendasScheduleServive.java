package lv.venta.services;

import lv.venta.models.CalendarSchedule;
import lv.venta.models.StudioProgramm;

public interface ICalendasScheduleServive {
	
	void createCalendarSchedule(StudioProgramm studioProgramm, int year);
	void addCalenadScheduleActivity(long idCalendar, long idActivity);
	void removeCalendarSchedule(long idCalendar);
}
