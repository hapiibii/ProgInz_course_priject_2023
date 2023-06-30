package lv.venta.repos;

import org.springframework.data.repository.CrudRepository;

import lv.venta.models.CalendarActivity;
import lv.venta.models.CalendarSchedule;

public interface CalendarScheduleRepo  extends CrudRepository<CalendarSchedule, Long> {

}
