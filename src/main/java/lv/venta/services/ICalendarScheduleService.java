package lv.venta.services;

import java.time.Year;
import java.util.List;

import lv.venta.dto.CalendarScheduleDTO;
import lv.venta.models.CalendarSchedule;
import lv.venta.models.StudioProgramm;

public interface ICalendarScheduleService {
	
	void addCalendarSchedule(CalendarScheduleDTO calendarScheduleDTO);
	
	void removeCalendarSchedule(long idCalendar);
	List<CalendarSchedule> getCalendarSchedules();

	List<CalendarScheduleDTO> getAllCalendarSchedules();
}
