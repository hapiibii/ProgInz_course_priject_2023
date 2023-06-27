package lv.venta.services.impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lv.venta.models.CalendarSchedule;
import lv.venta.repos.ICalendarScheduleRepo;
import lv.venta.services.ICalendarScheduleService;

@Service
public class CalendarScheduleService implements ICalendarScheduleService {

    private final ICalendarScheduleRepo calendarRepo;

    @Autowired
    public CalendarScheduleService(ICalendarScheduleRepo calendarRepo) {
        this.calendarRepo = calendarRepo;
    }

    @Override
    public void addActivity(CalendarSchedule activity) {
        calendarRepo.save(activity);
    }

    @Override
    public void removeActivity(long idActivity) throws Exception {
        CalendarSchedule activity = calendarRepo.findById(idActivity).orElse(null);
        if (activity != null) {
            calendarRepo.delete(activity);
        } else {
            throw new Exception("Activity not found with ID: " + idActivity);
        }
    }

    @Override
    public List<CalendarSchedule> showAllByDate(LocalDate date) {
        return calendarRepo.findByDate(date);
    }

    @Override
    public List<CalendarSchedule> findByTwoWeeks(LocalDate currentDate) {
        LocalDate twoWeeksLater = currentDate.plusWeeks(2);
        return calendarRepo.findByDateBetween(currentDate, twoWeeksLater);
    }
}

