package lv.venta.models;



import java.util.Collection;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "studioprogramm_table")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class StudioProgramm {
	
	@Column(name = "Idstprog")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Setter(value = AccessLevel.NONE)
	private long idstprog;
	
	@Column(name = "Faculty")
	@NotNull
	private Faculty faculty;
	
	@Column(name = "Degree")
	@NotNull
	private Degree degree;
	
	@Column(name = "Title")
	@NotNull
	@Size(min = 3, max = 100)
	@Pattern(regexp = "[A-ZĀČĒĢĪĶĻŅŠŪŽ]{1}[a-zāčēģīķļņšūž\\ ]+", message = "Pirmajam burtam jābut lielajam")
	private String title;
	
	//relation with Schedule
	@OneToMany(mappedBy = "studioProgramm")
	private Collection<CalendarSchedule> activities;

	public StudioProgramm(@NotNull Faculty faculty, @NotNull Degree degree,
			@NotNull @Size(min = 3, max = 100) @Pattern(regexp = "[A-ZĀČĒĢĪĶĻŅŠŪŽ]{1}[a-zāčēģīķļņšūž\\ ]+", message = "Pirmajam burtam jābut lielajam") String title) {
		
		this.faculty = faculty;
		this.degree = degree;
		this.title = title;
	}
	
	//add activity
	public void addActivity(CalendarSchedule activity) {
		if(!activities.contains(activity)) {
			activities.add(activity);
		}
	}
	
	//remove activity
	public void removeActivity(long idactivity) throws Exception {
		boolean foundAct = false;
		for(CalendarSchedule temp : activities) {
			if(temp.getIdactivity == idactivity) {
				activities.remove(temp);
				foundAct = true;
				break;
			}
		}
		if(!foundAct) {
			throw new Exception("Wrong activity ID!");
		}
	}
	

}
