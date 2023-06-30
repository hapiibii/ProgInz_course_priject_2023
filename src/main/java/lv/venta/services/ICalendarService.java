package lv.venta.services;

import lv.venta.models.CalendarActivity;
import lv.venta.models.StudioProgramm;

import java.time.LocalDate;
import java.util.List;

public interface ICalendarService {
	
	void addActivity(Long idCalendar, String activity, LocalDate activityEndDate, String activityImplementation);
    void removeActivity(long activityId);
    List<CalendarActivity> getActivitiesByYearAndProgram(int year, StudioProgramm studioProgramm);
    List<CalendarActivity> getActivitiesByStudyProgrammTitle(String title);
	List<CalendarActivity> getActivityEndDate();
}
