package lv.venta.services.impl;

import lv.venta.models.Activities;
import lv.venta.models.CalendarActivity;
import lv.venta.models.CalendarSchedule;
import lv.venta.models.StudioProgramm;
import lv.venta.repos.ICalendarRepo;
import lv.venta.services.ICalendarService;
import lv.venta.services.IStudioProgrammService;

import java.time.LocalDate;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CalendarService implements ICalendarService {
	private List<CalendarSchedule> calendarSchedules = new ArrayList<>();
	
	private ICalendarRepo calendarRepo;
	private IStudioProgrammService studioProgrammService;

	@Autowired
    public CalendarService(ICalendarRepo calendarRepo, IStudioProgrammService studioProgrammService) {
        this.calendarRepo = calendarRepo;
		this.studioProgrammService = studioProgrammService;
    }
	
	@Override
	public void addActivity(StudioProgramm studioProgramm, int gads, String activity, LocalDate activityEndDate, String activityImplementation) {
	    CalendarSchedule calendarSchedule = getOrCreateCalendarSchedule(gads, studioProgramm);
	    CalendarActivity calendarActivity = new CalendarActivity(activity, activityEndDate, activityImplementation, calendarSchedule);
	    calendarSchedule.getActivities().add(calendarActivity);
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
	        CalendarSchedule calendarSchedule = getCalendarSchedule(year, studioProgramm);
	        if (calendarSchedule != null) {
	            return calendarSchedule.getActivities();
	        }
	        return new ArrayList<>();
	    }

	    private CalendarSchedule getOrCreateCalendarSchedule(int year, StudioProgramm studioProgramm) {
	        for (CalendarSchedule schedule : calendarSchedules) {
	            if (schedule.getGads().getValue() == year && schedule.getStudioProgramm().equals(studioProgramm)) {
	                return schedule;
	            }
	        }
	        CalendarSchedule newSchedule = new CalendarSchedule(Year.of(year), new ArrayList<>(), studioProgramm);
	        calendarSchedules.add(newSchedule);
	        return newSchedule;
	    }

	    private CalendarSchedule getCalendarSchedule(int year, StudioProgramm studioProgramm) {
	        for (CalendarSchedule schedule : calendarSchedules) {
	            if (schedule.getGads().getValue() == year && schedule.getStudioProgramm().equals(studioProgramm)) {
	                return schedule;
	            }
	        }
	        return null;
	    }  
	    
	    
	    @Override
	    public List<CalendarActivity> getActivitiesByStudyProgrammTitle(String title) {
	        List<CalendarActivity> matchingActivities = new ArrayList<>();

	        for (StudioProgramm program : studioProgrammService.getAllStudioProgramms()) {
	            if (program.getTitle().equals(title)) {
	                Collection<CalendarSchedule> schedules = program.getActivities();
	                for (CalendarSchedule schedule : schedules) {
	                    List<CalendarActivity> activities = schedule.getActivities();
	                    matchingActivities.addAll(activities);
	                }
	            }
	        }

	        return matchingActivities;
	    }
	    
	    @Override
	    public List<CalendarActivity> getEndDates() {
	        List<CalendarActivity> activitiesWithEndDates = new ArrayList<>();

	        // Iegūstiet visus CalendarActivity objektus no datu avota (piemēram, datu bāzes)
	        List<CalendarActivity> allActivities = calendarRepo.getAllActivities();

	        // Iterējiet caur visiem aktivitāšu objektiem un pārbaudiet, vai beigu datums ir definēts
	        for (CalendarActivity activity : allActivities) {
	            LocalDate endDate = activity.getActivityEndDate();
	            if (endDate != null) {
	                activitiesWithEndDates.add(activity);
	            }
	        }

	        return activitiesWithEndDates;
	    }

	    @Override
	    public List<Activities> getDatesWithActivities() {
	        List<Activities> dates = new ArrayList<>();
	        LocalDate currentDate = LocalDate.now();
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

	        int numDays = 7;
	        for (int i = 0; i < numDays; i++) {
	            String formattedDate = currentDate.format(formatter); 
	            Activities activities = new Activities(" ", LocalDate.parse(formattedDate, formatter)); 

	            List<CalendarActivity> activitiesWithEndDates = getEndDates();
	            for (CalendarActivity activityWithEndDate : activitiesWithEndDates) {
	                LocalDate endDate = activityWithEndDate.getActivityEndDate();
	                if (endDate != null && formattedDate.equals(endDate.format(formatter))) {
	                	String activity = activityWithEndDate.getActivity(); // Iegūstam aktivitātes vērtību no CalendarActivity objekta
	                    activities.setActiviti(activity); // Iestatām aktivitātes vērtību Activities objektā
	                    break; // Pārtraucam ciklu, ja datums sakrīt
	                }
	            }

	            dates.add(activities);
	            currentDate = currentDate.plusDays(1);
	        }

	        return dates;
	    }



}

