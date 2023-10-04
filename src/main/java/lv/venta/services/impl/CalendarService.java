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
  public List<CalendarSchedule> getCalendarSchedules() {
      return calendarSchedules;
  }
  
   @Override
   public void removeActivity(StudioProgramm studioProgramm, Year gads, long activityId) {
        CalendarSchedule calendarSchedule = getCalendarSchedule(gads, studioProgramm);
        if (calendarSchedule != null) { 
            calendarSchedule.getActivities().removeIf(activity -> activity.getIdActivity() == activityId);
        }
  }

   @Override
   public List<CalendarActivity> getActivitiesEndingWithinTwoWeeks() {
     LocalDate currentDate = LocalDate.now();
     LocalDate twoWeeksLater = currentDate.plus(2, ChronoUnit.WEEKS);
     //TODO pabeigt
     return new ArrayList<>();
   //  return calendarSchedules.stream()
/*
     return calendarSchedules.stream()
         .flatMap(schedule -> schedule.getActivities().stream())
         .filter(activity -> activity.getActivityEndDate().isBefore(twoWeeksLater))
         .collect(Collectors.toList());*/
      }

      @Override
      public List<CalendarActivity> getActivitiesByYearAndProgram(Year year, StudioProgramm studioProgramm) {
          CalendarSchedule calendarSchedule = getCalendarSchedule(year, studioProgramm);
          if (calendarSchedule != null) {
              return calendarSchedule.getActivities();
          }
          return new ArrayList<>();
      }

      private CalendarSchedule getOrCreateCalendarSchedule(Year year, StudioProgramm studioProgramm) {
          for (CalendarSchedule schedule : calendarSchedules) {
              if (schedule.getGads() == year && schedule.getStudioProgramm().equals(studioProgramm)) {
                  return schedule;
              }
          }
          CalendarSchedule newSchedule = new CalendarSchedule(year, new ArrayList<>(), studioProgramm);
          calendarSchedules.add(newSchedule);
          return newSchedule;
      }

      private CalendarSchedule getCalendarSchedule(Year year, StudioProgramm studioProgramm) {
          for (CalendarSchedule schedule : calendarSchedules) {
              if (schedule.getGads() == year && schedule.getStudioProgramm().equals(studioProgramm)) {
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
      
      public List<CalendarActivity> getEndDates() {
          List<CalendarActivity> activitiesWithEndDates = new ArrayList<>();

          // Izmantojiet metodi "findAll" no "ICalendarRepo", lai iegūtu visus CalendarActivity objektus
          List<CalendarActivity> allActivities = calendarRepo.findAll();

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
    public void addActivity(StudioProgramm studioProgramm, Year year, String activity, LocalDate activityEndDate,
        String activityImplementation) {
        CalendarSchedule calendarSchedule = getOrCreateCalendarSchedule(year, studioProgramm);
        CalendarActivity calendarActivity = new CalendarActivity(activity, activityEndDate, activityImplementation, calendarSchedule);
        calendarSchedule.getActivities().add(calendarActivity);
      
    }



}
          