package lv.venta.services;

import lv.venta.models.CalendarActivity;
import lv.venta.models.CalendarSchedule;
import lv.venta.models.StudioProgramm;

import java.util.List;
import java.util.Optional;

public interface ICalendarService {
	
	List<CalendarSchedule> getCalendarSchedules();
	
    void removeActivity(StudioProgramm studioProgramm, int year, long activityId);
    
    List<CalendarActivity> getActivitiesEndingWithinTwoWeeks();
    
    List<CalendarActivity> getActivitiesByYearAndProgram(int year, StudioProgramm studioProgramm);
    
    List<CalendarActivity> getEndDates();
    
    //public CalendarSchedule getOrCreateCalendarSchedule(int year, StudioProgramm studioProgramm);
    
	public List<Integer> getAllUniqueYears();
	
	Optional<CalendarActivity> findActivityById(Long idActivity);
	
	void updateActivity(Long idActivity, CalendarActivity updatedActivity);

	

}
