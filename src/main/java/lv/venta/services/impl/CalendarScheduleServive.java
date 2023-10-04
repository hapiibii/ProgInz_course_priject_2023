package lv.venta.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lv.venta.models.CalendarActivity;
import lv.venta.models.CalendarSchedule;
import lv.venta.models.StudioProgramm;
import lv.venta.repos.ICalendarRepo;
import lv.venta.repos.ICalendarScheduleRepo;
import lv.venta.services.ICalendarScheduleService;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;

@Service
public class CalendarScheduleServive implements ICalendarScheduleService{

	private ICalendarScheduleRepo calendarScheduleRepo;
	private ICalendarRepo calendarRepo;
	
	@Autowired
	public CalendarScheduleServive(ICalendarScheduleRepo calendarScheduleRepo, ICalendarRepo calendarRepo) {
	    this.calendarScheduleRepo = calendarScheduleRepo;
	    this.calendarRepo = calendarRepo;
	}
	
	@Override
	public void createCalendarSchedule(StudioProgramm studioProgramm, Year year) {
	    // vai kalendāra grafiks jau eksistē norādītajam gadam un studiju programmam
	    CalendarSchedule existingSchedule = calendarScheduleRepo.findByGadsAndStudioProgramm(year, studioProgramm);
	    if (existingSchedule != null) {
	        throw new IllegalArgumentException("Kalendāra grafiks jau eksistē šim gadam un studiju programmam.");
	    }

	    // Izveidojam jaunu kalendāra grafika objektu ar tukšām aktivitātēm
	    CalendarSchedule calendarSchedule = new CalendarSchedule(year, new ArrayList<>(), studioProgramm);

	    
	    calendarScheduleRepo.save(calendarSchedule);
	}

	
	@Override
	public void addCalenadScheduleActivity(long idCalendar, long idActivity) {
	    // Iegūstam kalendāra grafika objektu pēc ID
	    CalendarSchedule calendarSchedule = calendarScheduleRepo.findById(idCalendar)
	            .orElseThrow(() -> new IllegalArgumentException("Kalendāra grafiks ar norādīto ID nav atrasts"));

	  
	    CalendarActivity activity = calendarRepo.findById(idActivity)
	            .orElseThrow(() -> new IllegalArgumentException("Aktivitāte ar norādīto ID nav atrasta"));

	    //vai aktivitāte jau nav saistīta ar citu kalendāra grafiku
	    if (activity.getCalendarSchedule() != null) {
	        throw new IllegalArgumentException("Aktivitāte jau ir saistīta ar citu kalendāra grafiku");
	    }
	    
	    calendarSchedule.getActivities().add(activity);
	    activity.setCalendarSchedule(calendarSchedule);

	    calendarScheduleRepo.save(calendarSchedule);
	    calendarRepo.save(activity);
	}


	@Override
	public void removeCalendarSchedule(long idCalendar) {
	    CalendarSchedule calendarSchedule = calendarScheduleRepo.findById(idCalendar)
	            .orElseThrow(() -> new IllegalArgumentException("Kalendāra grafiks ar norādīto ID nav atrasts"));

	    List<CalendarActivity> activities = calendarSchedule.getActivities();
	    
	    for (CalendarActivity activity : calendarSchedule.getActivities()) {
	    	calendarRepo.delete(activity);
	    }

	    calendarScheduleRepo.delete(calendarSchedule);
	}

	@Override
	public List<CalendarSchedule> getCalendarSchedules() {
	    return (List<CalendarSchedule>) calendarScheduleRepo.findAll();
	}

	
}
