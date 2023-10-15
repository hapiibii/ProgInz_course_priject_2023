package lv.venta.services;

import lv.venta.models.CalendarActivity;
import lv.venta.models.CalendarSchedule;
import lv.venta.models.StudioProgramm;

import java.time.LocalDate;
import java.time.Year;
import java.util.List;

public interface ICalendarService {
	
	
	
	List<CalendarSchedule> getCalendarSchedules();
//	void addActivity(String studioProgrammTitle, int year, String activity, LocalDate activityEndDate, String activityImplementation);
    void removeActivity(StudioProgramm studioProgramm, int year, long activityId);
    List<CalendarActivity> getActivitiesEndingWithinTwoWeeks();
    List<CalendarActivity> getActivitiesByYearAndProgram(int year, StudioProgramm studioProgramm);
   // List<CalendarActivity> getActivitiesByStudyProgrammTitle(String title);
    List<CalendarActivity> getEndDates();
    public CalendarSchedule getOrCreateCalendarSchedule(int year, StudioProgramm studioProgramm);
	public List<Integer> getAllUniqueYears();
	

}
