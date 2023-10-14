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
import lombok.Setter;

@Table(name = "studioprogramm_table")
@Entity
@Getter
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
	
	@OneToMany(mappedBy = "studioProgramm")
	private Collection<CalendarSchedule> activities;

	public void setIdstprog(long idstprog) {
		this.idstprog = idstprog;
	}

	public void setFaculty(Faculty faculty) {
		if(faculty == null ) {
			this.faculty = Faculty.UNKNOWN;
		}else {
			this.faculty = faculty;
		}	
	}

	public void setDegree(Degree degree) {
		if(degree == null) {
			this.degree = Degree.UNKNOWN;
		}else {
			this.degree = degree;
		}		
	}

	public void setTitle(String title) {
		if(title == null || !title.matches("[A-ZĀČĒĢĪĶĻŅŠŪŽ]{1}[a-zāčēģīķļņšūž\\ ]+")) {
			this.title = "Nav norādīts";
		}else {
			this.title = title;
		}	
	}

	public void setActivities(Collection<CalendarSchedule> activities) {
		this.activities = activities;
	}
	
	public StudioProgramm(@NotNull Faculty faculty, @NotNull Degree degree,
			@NotNull @Size(min = 3, max = 100) @Pattern(regexp = "[A-ZĀČĒĢĪĶĻŅŠŪŽ]{1}[a-zāčēģīķļņšūž\\ ]+", message = "Pirmajam burtam jābut lielajam") String title) {
		super();
		setFaculty(faculty);
		setDegree(degree);
		setTitle(title);
	}

	public StudioProgramm() {
		faculty = Faculty.UNKNOWN;
		degree = Degree.UNKNOWN;
		title = "Nav norādīts";		
	}
}