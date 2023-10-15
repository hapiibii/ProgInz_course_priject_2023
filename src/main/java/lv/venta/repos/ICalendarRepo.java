package lv.venta.repos;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import lv.venta.models.CalendarActivity;
import lv.venta.models.StudioProgramm;

public interface ICalendarRepo extends CrudRepository<CalendarActivity, Long> {
	List<CalendarActivity> findAll();

	 @Query("SELECT ca FROM CalendarActivity ca JOIN ca.calendarSchedule cs WHERE cs.gads = :year AND cs.studioProgramm = :program")
	 List<CalendarActivity> findByYearAndProgram(@Param("year") int year, @Param("program") StudioProgramm studioProgramm);
}
