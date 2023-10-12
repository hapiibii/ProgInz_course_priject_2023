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
        // 1.Jaatrod eksistejosa studiju programma pec nosaukuma
        StudioProgramm studioProgramm = studioProgrammRepo.findByTitle(calendarScheduleDTO.getStudioProgrammTitle());

        if (studioProgramm == null) {
            throw new IllegalArgumentException("Studiju programma nav atrasta: " + calendarScheduleDTO.getStudioProgrammTitle());
        }
        else {
        	// 2. Jaizveido calendarSchedule, ar attiecīgu gadu un studiju programmu, kam tad tiks pievienotas itf noteiktās aktivitātes
            CalendarSchedule calendarSchedule = new CalendarSchedule();
            calendarSchedule.setGads(calendarScheduleDTO.getGads());
            calendarSchedule.setStudioProgramm(studioProgramm);

            //3. jāizveido CalendarActivity objekts un tad jaiestata vertibas
            CalendarActivity calendarActivity = new CalendarActivity();
            calendarActivity.setActivity(calendarScheduleDTO.getActivity());
            calendarActivity.setActivityEndDate(calendarScheduleDTO.getActivityEndDate());
            calendarActivity.setActivityImplementation(calendarScheduleDTO.getActivityImplementation());
            calendarActivity.setCalendarSchedule(calendarSchedule);

            // Pievienojam CalendarActivity objektu CalendarSchedule un saglabājam tos datu bāzē
            calendarSchedule.getActivities().add(calendarActivity);
            calendarScheduleRepo.save(calendarSchedule);
        }
        
    }

    	
	@Override
	public void removeCalendarSchedule(long idCalendar) {
	    Optional<CalendarSchedule> optionalCalendarSchedule = calendarScheduleRepo.findById(idCalendar);

	    if (optionalCalendarSchedule.isPresent()) {
	        CalendarSchedule calendarSchedule = optionalCalendarSchedule.get();

	        calendarScheduleRepo.delete(calendarSchedule);
	    } else {
	        throw new IllegalArgumentException("Kalendāra ieraksts ar ID " + idCalendar + " nav atrasts.");
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
            dto.setId(calendarSchedule.getIdCalendar());
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
	
}