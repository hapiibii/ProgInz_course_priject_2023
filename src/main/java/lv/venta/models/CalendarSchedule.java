package lv.venta.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.Year;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "calendar_schedule")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class CalendarSchedule {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "Idcalendar")
	private long idCalendar;
	    
	@Column(name = "gads")
	private int gads;
	    
	@OneToMany(mappedBy = "calendarSchedule", cascade = CascadeType.ALL) // visi operācijas izmaiņas, kas tiek veiktas CalendarSchedule objektam (piemēram, dzēšana vai atjaunināšana), tiks automātiski izplatītas arī uz saistītajiem CalendarActivity objektiem
	private List<CalendarActivity> activities = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "idstprog")
    private StudioProgramm studioProgramm;

    
    
    public CalendarSchedule(int gads, List<CalendarActivity> activities, StudioProgramm studioProgramm) {
        this.gads = gads;
        this.activities = activities;
        this.studioProgramm = studioProgramm;
    }



	


}
