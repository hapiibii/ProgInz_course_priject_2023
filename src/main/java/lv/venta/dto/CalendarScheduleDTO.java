package lv.venta.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CalendarScheduleDTO {
	private Long idCalendar;
	private int gads;  //from CalendarSchedule models class
	
	@NotNull
	@Size(min = 3, max = 100)
	@Pattern(regexp = "[A-ZĀČĒĢĪĶĻŅŠŪŽ]{1}[a-zāčēģīķļņšūž\\ ]+", message = "Pirmajam burtam jābut lielajam")
	private String studioProgrammTitle;//from StudioProgramm models class title
	
	@NotNull
    private String activity; //from CalendarActivity models class
	
	@NotNull
	private LocalDate activityEndDate;
	
	@NotBlank
    private String activityImplementation; //from CalendarActivity models class
}