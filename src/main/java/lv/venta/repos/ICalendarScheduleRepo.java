package lv.venta.repos;

import java.time.Year;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import lv.venta.models.CalendarSchedule;
import lv.venta.models.StudioProgramm;

public interface ICalendarScheduleRepo  extends CrudRepository<CalendarSchedule, Long> {
	
	CalendarSchedule findByGadsAndStudioProgramm(Year year, StudioProgramm studioProgramm);

	List<CalendarSchedule> findByStudioProgramm(StudioProgramm studioProgramm);
	
}
