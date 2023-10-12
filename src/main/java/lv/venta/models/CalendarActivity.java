package lv.venta.models;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "calendar_activity")
@Getter
@Setter
public class CalendarActivity {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Idactivity")
    private long idActivity;
    
    @Column(name = "activity")
    @NotNull
    private String activity;
    
    @Column(name = "activity_end_date")
    @NotNull
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private LocalDate activityEndDate;
    
    @Column(name = "activity_implementation")
    @NotBlank
    private String activityImplementation;
    
    @ManyToOne
    @JoinColumn(name = "Idcalendar")
    private CalendarSchedule calendarSchedule;
    
    public CalendarActivity(String activity, LocalDate activityEndDate, String activityImplementation, CalendarSchedule calendarSchedule) {
        this.activity = activity;
        this.activityEndDate = activityEndDate;
        this.activityImplementation = activityImplementation;
        this.calendarSchedule = calendarSchedule;
    }

	public CalendarActivity() {
	}
    
}

