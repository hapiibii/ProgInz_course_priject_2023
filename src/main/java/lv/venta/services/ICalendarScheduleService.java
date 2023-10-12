package lv.venta.services;

import java.util.List;

import lv.venta.dto.CalendarScheduleDTO;

public interface ICalendarScheduleService {
	
	void addCalendarSchedule(CalendarScheduleDTO calendarScheduleDTO);
	
	void removeCalendarSchedule(long idCalendar);

	List<CalendarScheduleDTO> getAllCalendarSchedules();

	
}