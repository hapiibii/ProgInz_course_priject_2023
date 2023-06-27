package lv.venta.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Entity
@Table(name = "calendar_schedule")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class CalendarSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Idcalendar")
    private long idCalendar;

    @Column(name = "date")
    @NotNull
    private LocalDate date;

    @Column(name = "activity")
    @NotNull
    private String activity;

    @Column(name = "activity_date")
    @NotNull
    private LocalDate activityDate;

    @ManyToOne
    private StudioProgramm studioProgramm;

    public CalendarSchedule(LocalDate date, String activity, LocalDate activityDate, StudioProgramm studioProgramm) {
        this.date = date;
        this.activity = activity;
        this.activityDate = activityDate;
        this.studioProgramm = studioProgramm;
    }
}