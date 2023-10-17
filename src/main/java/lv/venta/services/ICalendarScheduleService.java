package lv.venta.services;

import java.util.List;

import lv.venta.dto.CalendarScheduleDTO;

public interface ICalendarScheduleService {
	void addCalendarSchedule(CalendarScheduleDTO calendarScheduleDTO);	
	void deleteCalendarScheduleById(long idCalendar);
	List<CalendarScheduleDTO> getAllCalendarSchedules();
	//CalendarScheduleDTO getCalendarScheduleById(Long id);
	//public void editCalendarSchedule(Long id, CalendarScheduleDTO calendarScheduleDTO);
}