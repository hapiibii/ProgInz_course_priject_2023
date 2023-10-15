package lv.venta.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lv.venta.dto.CalendarScheduleDTO;
import lv.venta.models.CalendarActivity;
import lv.venta.models.CalendarSchedule;
import lv.venta.models.StudioProgramm;
import lv.venta.repos.ICalendarRepo;
import lv.venta.repos.ICalendarScheduleRepo;
import lv.venta.repos.IStudioProgrammRepo;
import lv.venta.services.ICalendarScheduleService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CalendarScheduleService implements ICalendarScheduleService{

	private ICalendarScheduleRepo calendarScheduleRepo;
	private IStudioProgrammRepo studioProgrammRepo;
	private ICalendarRepo calendarRepo;
		
	@Autowired
	public CalendarScheduleService(ICalendarScheduleRepo calendarScheduleRepo,IStudioProgrammRepo studioProgrammRepo, ICalendarRepo calendarRepo) {
	    this.calendarScheduleRepo = calendarScheduleRepo;
	    this.studioProgrammRepo = studioProgrammRepo;
	    this.calendarRepo = calendarRepo;
	}
	
	public void addCalendarSchedule(CalendarScheduleDTO calendarScheduleDTO) {
	    // Atrod eksistējošo studiju programmu pēc nosaukuma
	    StudioProgramm studioProgramm = studioProgrammRepo.findByTitle(calendarScheduleDTO.getStudioProgrammTitle());

	    if (studioProgramm == null) {
	        throw new IllegalArgumentException("Studiju programma nav atrasta: " + calendarScheduleDTO.getStudioProgrammTitle());
	    } else {
	        // Meklēt CalendarSchedule, kuram ir tāda pati studiju programma un gads
	        CalendarSchedule existingSchedule = calendarScheduleRepo.findByStudioProgrammAndGads(studioProgramm, calendarScheduleDTO.getGads());

	        // Ja tāds neeksistē, izveido jaunu
	        if (existingSchedule == null) {
	            existingSchedule = new CalendarSchedule();
	            existingSchedule.setGads(calendarScheduleDTO.getGads());
	            existingSchedule.setStudioProgramm(studioProgramm);
	            calendarScheduleRepo.save(existingSchedule);
	        }

	        // Izveido CalendarActivity objektu un iestata vērtības
	        CalendarActivity calendarActivity = new CalendarActivity();
	        calendarActivity.setActivity(calendarScheduleDTO.getActivity());
	        calendarActivity.setActivityEndDate(calendarScheduleDTO.getActivityEndDate());
	        calendarActivity.setActivityImplementation(calendarScheduleDTO.getActivityImplementation());
	        calendarActivity.setCalendarSchedule(existingSchedule);

	        // Pievieno CalendarActivity objektu CalendarSchedule un saglabā to datu bāzē
	        existingSchedule.getActivities().add(calendarActivity);
	        calendarScheduleRepo.save(existingSchedule);
	    }
	}
		
	@Override
    public List<CalendarScheduleDTO> getAllCalendarSchedules() {
        List<CalendarSchedule> calendarSchedules = (List<CalendarSchedule>) calendarScheduleRepo.findAll();
        // Saraksts kur glabāsies DTO objekti
        List<CalendarScheduleDTO> calendarScheduleDTOList = new ArrayList<>();

        // Loops caur  CalendarSchedule un parveidot dto objektos
        for (CalendarSchedule calendarSchedule : calendarSchedules) {
            CalendarScheduleDTO dto = new CalendarScheduleDTO();
            dto.setIdCalendar(calendarSchedule.getIdCalendar());
            dto.setGads(calendarSchedule.getGads());
            dto.setStudioProgrammTitle(calendarSchedule.getStudioProgramm().getTitle());
            dto.setActivity(calendarSchedule.getActivities().get(0).getActivity());
            dto.setActivityEndDate(calendarSchedule.getActivities().get(0).getActivityEndDate());
            dto.setActivityImplementation(calendarSchedule.getActivities().get(0).getActivityImplementation());

            // pievienot dto sarakstam
            calendarScheduleDTOList.add(dto);
        }
        return calendarScheduleDTOList;
	}

	@Override
	public void deleteCalendarScheduleById(long idCalendar) {
	    Optional<CalendarSchedule> calendarSchedule = calendarScheduleRepo.findById(idCalendar);
	    if (calendarSchedule.isPresent()) {
	        calendarScheduleRepo.delete(calendarSchedule.get());
	    } else {
	        throw new IllegalArgumentException("Kalendāra ieraksts ar ID " + idCalendar + " nav atrasts.");
	    }
	}

/*	@Override
    public CalendarScheduleDTO getCalendarScheduleById(Long id) {
        Optional<CalendarSchedule> schedule = calendarScheduleRepo.findById(id);

        if (schedule.isPresent()) {
            CalendarSchedule calendarSchedule = schedule.get();
            CalendarScheduleDTO calendarScheduleDTO = new CalendarScheduleDTO();
            
            calendarScheduleDTO.setIdCalendar(calendarSchedule.getIdCalendar());
            calendarScheduleDTO.setGads(calendarSchedule.getGads());
            calendarScheduleDTO.setStudioProgrammTitle(calendarSchedule.getStudioProgramm().getTitle());
            calendarScheduleDTO.setActivity(calendarSchedule.getActivities().get(0).getActivity());
            calendarScheduleDTO.setActivityEndDate(calendarSchedule.getActivities().get(0).getActivityEndDate());
            calendarScheduleDTO.setActivityImplementation(calendarSchedule.getActivities().get(0).getActivityImplementation());

            return calendarScheduleDTO;
        } else {
            throw new IllegalArgumentException("Kalendāra ieraksts ar ID " + id + " nav atrasts.");
        }
    }*/
	/*@Override
	public void editCalendarSchedule(Long id, CalendarScheduleDTO calendarScheduleDTO) {
	    Optional<CalendarSchedule> schedule = calendarScheduleRepo.findById(id);

	    if (schedule.isPresent()) {
	        CalendarSchedule calendarSchedule = schedule.get();

	        // Veiciet izmaiņas no DTO objekta uz esošo ierakstu
	        calendarSchedule.setGads(calendarScheduleDTO.getGads());
	        calendarSchedule.getActivities().get(0).setActivity(calendarScheduleDTO.getActivity());
	        calendarSchedule.getActivities().get(0).setActivityEndDate(calendarScheduleDTO.getActivityEndDate());
	        calendarSchedule.getActivities().get(0).setActivityImplementation(calendarScheduleDTO.getActivityImplementation());

	        // Saglabājiet izmaiņas datubāzē
	        calendarScheduleRepo.save(calendarSchedule);
	    } else {
	        throw new IllegalArgumentException("Kalendāra ieraksts ar ID " + id + " nav atrasts.");
	    }
	}*/

}