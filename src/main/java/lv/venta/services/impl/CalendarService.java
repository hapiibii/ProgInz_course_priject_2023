package lv.venta.services.impl;

import lv.venta.models.Activities;
import lv.venta.models.CalendarActivity;
import lv.venta.models.CalendarSchedule;
import lv.venta.models.StudioProgramm;
import lv.venta.repos.ICalendarScheduleRepo;
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
	
	
	private ICalendarRepo calendarRepo;
	private ICalendarScheduleRepo calendarScheduleRepo;
	private IStudioProgrammService studioProgrammService;
	private ICalendarService calendarService;

	@Autowired
    public CalendarService(ICalendarRepo calendarRepo,ICalendarScheduleRepo calendarScheduleRepo, IStudioProgrammService studioProgrammService, ICalendarService calendarService) {
        this.calendarRepo = calendarRepo;
		this.calendarScheduleRepo = calendarScheduleRepo;
		this.studioProgrammService = studioProgrammService;
		this.calendarService =calendarService;
    }

	@Override
	public void addActivity(Long idCalendar, String activity, LocalDate activityEndDate, String activityImplementation) {
		CalendarSchedule calendarSchedule = calendarScheduleRepo.findById(idCalendar)
				.orElseThrow(() -> new IllegalArgumentException("Kalendāra grafiks ar norādīto ID nav atrasts"));

		CalendarActivity calendarActivity = new CalendarActivity(activity, activityEndDate, activityImplementation, calendarSchedule);
		calendarRepo.save(calendarActivity);
	}

	@Override
	public void removeActivity(long activityId) {
		CalendarActivity calendarActivity = calendarRepo.findById(activityId)
				.orElseThrow(() -> new IllegalArgumentException("Aktivitāte ar norādīto ID nav atrasta"));

		calendarRepo.delete(calendarActivity);
	}

	@Override
	public List<CalendarActivity> getActivitiesByYearAndProgram(int year, StudioProgramm studioProgramm) {
	    List<CalendarActivity> activities = new ArrayList<>();

	    // Iegūstam visus kalendāra grafikus attiecīgajam gadam un studiju programmai
	    List<CalendarSchedule> calendarSchedules = (List<CalendarSchedule>) calendarScheduleRepo.findByGadsAndStudioProgramm(year, studioProgramm);

	    // Pārbaudām katru kalendara grafiku
	    for (CalendarSchedule calendarSchedule : calendarSchedules) {
	        // Iegūstam aktivitātes no kalendāra grafika
	        List<CalendarActivity> scheduleActivities = calendarSchedule.getActivities();
	        activities.addAll(scheduleActivities);
	    }

	    return activities;
	}

	@Override
	public List<CalendarActivity> getActivitiesByStudyProgrammTitle(String title) {
	    List<CalendarActivity> activities = new ArrayList<>();

	    // Iegūstam studiju programmu pēc nosaukuma
	    StudioProgramm studioProgramm = studioProgrammService.getStudioProgrammByTitle(title);

	    if (studioProgramm != null) {
	        // Iegūstam visus kalendāra grafikus attiecīgajai studiju programmai
	        List<CalendarSchedule> calendarSchedules = calendarScheduleRepo.findByStudioProgramm(studioProgramm);

	        // Pārbaudām katru kalendāra grafiku
	        for (CalendarSchedule calendarSchedule : calendarSchedules) {
	            // Iegūstam aktivitātes no kalendāra grafika
	            List<CalendarActivity> scheduleActivities = calendarSchedule.getActivities();
	            activities.addAll(scheduleActivities);
	        }
	    }

	    return activities;
	}
	
	@Override
	public List<CalendarActivity> getActivityEndDate() {
		return calendarService.getActivityEndDate();
	}

}

