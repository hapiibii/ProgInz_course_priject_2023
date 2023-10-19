package lv.venta.services.impl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lv.venta.models.Activities;
import lv.venta.models.CalendarActivity;
import lv.venta.services.IActivitiesService;
import lv.venta.services.ICalendarService;

@Service
public class ActivitiesService implements IActivitiesService {
	private ICalendarService calendarService;
	
	@Autowired
	public ActivitiesService(ICalendarService calendarService) {
		this.calendarService = calendarService;
	}
	
	@Override
    public List<Activities> getDatesWithActivities() {
        List<Activities> dates = new ArrayList<>();
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        int numDays = 14;
        for (int i = 0; i < numDays; i++) {
            String formattedDate = currentDate.format(formatter); 
            Activities activities = new Activities(" ", LocalDate.parse(formattedDate, formatter)); 

            List<CalendarActivity> activitiesWithEndDates = calendarService.getEndDates();
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