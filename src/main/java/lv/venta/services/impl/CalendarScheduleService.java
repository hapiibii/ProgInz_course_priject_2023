package lv.venta.services.impl;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
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
    
    @Override
    public XWPFDocument exportCalendarScheduleToWord() {
        List<CalendarScheduleDTO> calendarSchedule = getAllCalendarSchedules();

        XWPFDocument document = new XWPFDocument();

        // Create a paragraph with the document title
        XWPFParagraph titleParagraph = document.createParagraph();
        XWPFRun titleRun = titleParagraph.createRun();
        
        // Assuming the first entry's year is representative for the title
        int currentYear = calendarSchedule.get(0).getGads();
        String academicYearTitle = currentYear + "./" + (currentYear+1) + ". akadēmiskajam gadam";
        titleRun.setText("ITF noslēguma darbu izstrādes aktivitāšu kalendārais grafiks " + academicYearTitle);

        // Create a paragraph for the degree
        XWPFParagraph degreeParagraph = document.createParagraph();
        XWPFRun degreeRun = degreeParagraph.createRun();
        // Assuming the first entry's degree is representative for the degree paragraph
        degreeRun.setText(calendarSchedule.get(0).getStudioProgrammTitle());

        // Create a table to display the data
        XWPFTable table = document.createTable(calendarSchedule.size() + 1, 3); 

        // Set column names
        XWPFTableRow headerRow = table.getRow(0);
        String[] headers = {"Nr. p.k.", currentYear + "./" + (currentYear+1) + ". ak. gada aktivitāte", "Aktivitātes realizēšanas datums"};

        for (int i = 0; i < headers.length; i++) {
            headerRow.getCell(i).setText(headers[i]);
        }

        // Fill the table with data
        for (int i = 0; i < calendarSchedule.size(); i++) {
            CalendarScheduleDTO calendarScheduleDTO = calendarSchedule.get(i);
            XWPFTableRow dataRow = table.getRow(i + 1);
            dataRow.getCell(0).setText(String.valueOf(i+1));  // Activity number
            dataRow.getCell(1).setText(calendarScheduleDTO.getActivity());
            dataRow.getCell(2).setText(calendarScheduleDTO.getActivityImplementation());
        }

        return document;
    }
    

}