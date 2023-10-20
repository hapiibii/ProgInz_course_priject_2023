package lv.venta.services;

import java.util.List;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import lv.venta.dto.CalendarScheduleDTO;

public interface ICalendarScheduleService {
	void addCalendarSchedule(CalendarScheduleDTO calendarScheduleDTO);	
	void deleteCalendarScheduleById(long idCalendar);
	List<CalendarScheduleDTO> getAllCalendarSchedules();

	Workbook exportCalendarScheduleToExcel();
	XWPFDocument exportCalendarScheduleToWord(int year);
}