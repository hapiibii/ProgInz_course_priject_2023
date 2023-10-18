package lv.venta.services.impl;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import jakarta.transaction.Transactional;
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
	
	@Transactional
	@Override
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
	            existingSchedule = calendarScheduleRepo.save(existingSchedule); // <- Šeit piešķiram saglabātā objekta atgriezto vērtību existingSchedule
	        }

	        // Izveido CalendarActivity objektu un iestata vērtības
	        CalendarActivity calendarActivity = new CalendarActivity();
	        calendarActivity.setActivity(calendarScheduleDTO.getActivity());
	        calendarActivity.setActivityEndDate(calendarScheduleDTO.getActivityEndDate());
	        calendarActivity.setActivityImplementation(calendarScheduleDTO.getActivityImplementation());
	        calendarActivity.setCalendarSchedule(existingSchedule);
	        
	        // Saglabājiet CalendarActivity datu bāzē
	        calendarRepo.save(calendarActivity);

	        // Pievieno CalendarActivity objektu CalendarSchedule sarakstam
	        existingSchedule.getActivities().add(calendarActivity);
	        
	        // Saglabājiet atjaunināto CalendarSchedule
	        calendarScheduleRepo.save(existingSchedule);

	        // Iestatiet ID vērtību calendarScheduleDTO
	        calendarScheduleDTO.setIdCalendar(existingSchedule.getIdCalendar());
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
	
    @Override
    public Workbook exportCalendarScheduleToExcel() {
        List<CalendarScheduleDTO> calendarSchedule = getAllCalendarSchedules ();

        //Tiek izveidota jauna Excel darba grāmata
        Workbook workbook = new XSSFWorkbook();

        //Tiek izveidota jauna Excel darba lapa
        Sheet sheet = workbook.createSheet("Kalendarais grafiks");

        // Tiek izveidoti rindu nosaukumi
        String[] headers = {"GADS", "STUDIJU PROGRAMMA", "AKTIVITĀTE",  "AKTIVITĀTES REALIZĒŠANAS DATUMS"};

        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < headers.length; i++) {
            headerRow.createCell(i).setCellValue(headers[i]);
        }

        // Tiek inicializēts rindas numurs
        int rowNum = 1;

        // Tiek aizpildīts Excel fails ar datiem par tēzēm
        for (CalendarScheduleDTO calendarScheduleDTO : calendarSchedule) {

            Row dataRow = sheet.createRow(rowNum++);
            dataRow.createCell(0).setCellValue(calendarScheduleDTO.getGads());
            dataRow.createCell(1).setCellValue(calendarScheduleDTO.getStudioProgrammTitle());
            dataRow.createCell(2).setCellValue(calendarScheduleDTO.getActivity());
            dataRow.createCell(3).setCellValue(calendarScheduleDTO.getActivityImplementation());    
        }

        // Tiek uzstādīts kolonnu platums
        for (int i = 0; i < 4; i++) {
            sheet.setColumnWidth(i, 8000);
        }
        return workbook;
    }	

}