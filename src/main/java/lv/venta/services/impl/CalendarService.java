package lv.venta.services.impl;

import lv.venta.models.CalendarActivity;
import lv.venta.models.CalendarSchedule;
import lv.venta.models.StudioProgramm;
import lv.venta.services.ICalendarService;

import java.time.LocalDate;
import java.time.Year;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

@Service
public class CalendarService implements ICalendarService {
	private List<CalendarSchedule> calendarSchedules = new ArrayList<>();

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
	}

