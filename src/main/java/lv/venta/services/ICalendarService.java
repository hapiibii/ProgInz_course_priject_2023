package lv.venta.services;

import lv.venta.models.CalendarActivity;
import lv.venta.models.CalendarSchedule;
import lv.venta.models.StudioProgramm;

import java.util.List;
import java.util.Optional;

public interface ICalendarService {
	
	List<CalendarSchedule> getCalendarSchedules();
	
	void removeActivity(long activityId);
    
    List<CalendarActivity> getActivitiesEndingWithinTwoWeeks();
    
    List<CalendarActivity> getActivitiesByYearAndProgram(int year, StudioProgramm studioProgramm);
    
    List<CalendarActivity> getEndDates();
    
	List<Integer> getAllUniqueYears();
	
	Optional<CalendarActivity> findActivityById(Long idActivity);
	
	void updateActivity(Long idActivity, CalendarActivity updatedActivity);

	

}
