package lv.venta.models;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "activities")
@Getter
@Setter
public class Activities {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "Idactivities")
	private long idActivities;
	
	@Column(name = "activiti")
    @NotNull
    private String activiti;
	
	 @Column(name = "activity_date")
	 @NotNull
	 @DateTimeFormat(pattern = "dd.MM.yyyy")
	 private LocalDate activityDate;

	 
	 public Activities(String activiti,LocalDate activityDate ) {
		 this.activiti = activiti;
		 this.activityDate = activityDate;
	 }
}
