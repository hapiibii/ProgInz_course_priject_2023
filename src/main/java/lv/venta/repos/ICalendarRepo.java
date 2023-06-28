package lv.venta.repos;

import org.springframework.data.repository.CrudRepository;

import lv.venta.models.CalendarSchedule;


public interface ICalendarRepo extends CrudRepository<CalendarSchedule, Long> {

}
