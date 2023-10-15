package lv.venta.services.impl;

import lv.venta.models.CalendarActivity;
import lv.venta.models.CalendarSchedule;
import lv.venta.models.StudioProgramm;
import lv.venta.repos.ICalendarRepo;
import lv.venta.repos.ICalendarScheduleRepo;
import lv.venta.services.ICalendarService;
import lv.venta.services.IStudioProgrammService;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CalendarService implements ICalendarService {
	
	private List<CalendarSchedule> calendarSchedules = new ArrayList<>();
	
	private ICalendarRepo calendarRepo;
	private ICalendarScheduleRepo calendarSchedulrRepo;

	@Autowired
    public CalendarService(ICalendarRepo calendarRepo, ICalendarScheduleRepo calendarSchedulrRepo) {
        this.calendarRepo = calendarRepo;
		this.calendarSchedulrRepo = calendarSchedulrRepo;
    }

	@Override
	public List<CalendarSchedule> getCalendarSchedules() {
	    return calendarSchedules;
	}
	
	@Override
	public void removeActivity(StudioProgramm studioProgramm, int gads, long activityId) {
		    CalendarSchedule calendarSchedule = getCalendarSchedule(gads, studioProgramm);
		    if (calendarSchedule != null) { 
		        calendarSchedule.getActivities().removeIf(activity -> activity.getIdActivity() == activityId);
		    }
	}

	@Override
	public List<CalendarActivity> getActivitiesEndingWithinTwoWeeks() {
		 LocalDate currentDate = LocalDate.now();
		 LocalDate twoWeeksLater = currentDate.plus(2, ChronoUnit.WEEKS);

		 return calendarSchedules.stream()
				 .flatMap(schedule -> schedule.getActivities().stream())
				 .filter(activity -> activity.getActivityEndDate().isBefore(twoWeeksLater))
				 .collect(Collectors.toList());
	 }

	@Override
	public List<CalendarActivity> getActivitiesByYearAndProgram(int year, StudioProgramm studioProgramm) {
	    return calendarRepo.findByYearAndProgram(year, studioProgramm);
	}
	
	private CalendarSchedule getCalendarSchedule(int year, StudioProgramm studioProgramm) {
	    for (CalendarSchedule schedule : calendarSchedules) {
	       if (schedule.getGads() == year && schedule.getStudioProgramm().equals(studioProgramm)) {
	           return schedule;
	       }
	    }
	    return null;
	}  
	
	@Override
	public List<Integer> getAllUniqueYears() {
	    return calendarSchedulrRepo.findAllUniqueYears();
	}
	    
	public List<CalendarActivity> getEndDates() {
		List<CalendarActivity> activitiesWithEndDates = new ArrayList<>();
	    List<CalendarActivity> allActivities = calendarRepo.findAll();

	    // Iterē caur visiem aktivitāšu objektiem un pārbaudiet, vai beigu datums ir definēts
	    for (CalendarActivity activity : allActivities) {
	    	LocalDate endDate = activity.getActivityEndDate();
	    	if (endDate != null) {
	    		activitiesWithEndDates.add(activity);
	        }
	    }

	        return activitiesWithEndDates;
	 }
	
	@Override
	public Optional<CalendarActivity> findActivityById(Long idActivity) {
	    return calendarRepo.findById(idActivity);
	}
	
	@Override
	public void updateActivity(Long idActivity, CalendarActivity updatedActivity) {
	    Optional<CalendarActivity> existingActivityOptional = calendarRepo.findById(idActivity);
	    if (existingActivityOptional.isPresent()) {
	        CalendarActivity existingActivity = existingActivityOptional.get();
	        
	        existingActivity.setActivity(updatedActivity.getActivity());
	        existingActivity.setActivityImplementation(updatedActivity.getActivityImplementation());
	        
	        calendarRepo.save(existingActivity);
	    }
	}
}
