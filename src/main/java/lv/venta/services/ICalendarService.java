package lv.venta.services;

import lv.venta.models.Activities;
import lv.venta.models.CalendarActivity;
import lv.venta.models.CalendarSchedule;
import lv.venta.models.StudioProgramm;

import java.time.LocalDate;
import java.util.List;

public interface ICalendarService {
    void addActivity(StudioProgramm studioProgramm, int year, String activity, LocalDate activityEndDate, String activityImplementation);
    void removeActivity(StudioProgramm studioProgramm, int year, long activityId);
    List<CalendarActivity> getActivitiesEndingWithinTwoWeeks();
    List<CalendarActivity> getActivitiesByYearAndProgram(int year, StudioProgramm studioProgramm);
    List<CalendarActivity> getActivitiesByStudyProgrammTitle(String title);
    List<CalendarActivity> getEndDates();
    List<Activities>getDatesWithActivities();
}
