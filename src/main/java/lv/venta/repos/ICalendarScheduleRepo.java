package lv.venta.repos;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import lv.venta.models.CalendarSchedule;
import lv.venta.models.StudioProgramm;

public interface ICalendarScheduleRepo  extends CrudRepository<CalendarSchedule, Long> {
  

  List<CalendarSchedule> findByStudioProgramm(StudioProgramm studioProgramm);

  CalendarSchedule findByStudioProgrammAndGads(StudioProgramm studioProgramm, int gads);
  
  @Query("SELECT DISTINCT cs.gads FROM CalendarSchedule cs ORDER BY cs.gads DESC")
  List<Integer> findAllUniqueYears();

  List<CalendarSchedule> findByGads(int year);
  
}