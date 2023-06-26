package lv.venta.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lv.venta.models.users.AcademicPersonel;
import lv.venta.models.users.Student;

//pārlikt uz citu tabulu, kurai nav saites

@Table(name = "thesis_table")
@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Thesis {
	
	@Column(name = "Idthesis")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Setter(value = AccessLevel.NONE)
	private long idthesis;
	
	
	@Column(name = "TitleEn")
	@NotNull
	@Size(min = 3, max = 150)
	@Pattern(regexp = "[A-Z]{1}[a-z\\ ]+", message = "First character must be upper case")
	private String titleEn;
	
	
	@Column(name = "TitleLv")
	@NotNull
	@Size(min = 3, max = 150)
	@Pattern(regexp = "[A-ZĀČĒĢĪĶĻŅŠŪŽ]{1}[a-zāčēģīķļņšūž\\ ]+", message = "Pirmajam burtam jābut lielajam")
	private String titleLv;
		
	
	@Column(name = "Goal")
	@NotNull
	@Size(min = 3, max = 500)
	@Pattern(regexp = "[A-ZĀČĒĢĪĶĻŅŠŪŽ]{1}[a-zāčēģīķļņšūž\\ ]+", message = "Pirmajam burtam jābut lielajam")
	private String goal;
	
	
	@Column(name = "Tasks")
	@NotNull
	@Size(min = 3, max = 500)
	@Pattern(regexp = "[A-ZĀČĒĢĪĶĻŅŠŪŽ]{1}[a-zāčēģīķļņšūž\\ ]+", message = "Pirmajam burtam jābut lielajam")
	private String tasks;
	
	
	@Column(name = "SubmitDateTime")
	@NotNull
	@DateTimeFormat(iso = ISO.DATE_TIME)
	//@Pattern("yyyy-mm-dd'T'hh:mm:ss")
	private LocalDateTime submitDateTime;
	

	@Column(name = "StatusFromSupervisor")
	private boolean statusFromSupervisor;
	
	
	@Column(name = "AcceptanceStatus")
	@NotNull
	private AcceptanceStatus accStatus;
	
	@Column(name = "AccDateTime")
	private LocalDateTime accDateTime;
	
	@ManyToOne
	@JoinColumn(name = "Idstudent")
	private Student student;
	
	@ManyToOne
	@JoinColumn(name = "Idap")
	private AcademicPersonel supervisor;
	
	
	@ManyToMany
	@JoinTable(name = "thesis_reviewers", joinColumns = @JoinColumn(name = "Idthesis"), inverseJoinColumns = @JoinColumn(name = "Idap"))
	private Collection<AcademicPersonel>  reviewers = new ArrayList<>();
	
	@OneToMany(mappedBy = "thesis")
	private Collection<Comment> comments;
	
	
	public void addReviewer(AcademicPersonel reviewer) {
		if(!reviewers.contains(reviewer)) {
			reviewers.add(reviewer);
		}
	}

	public Thesis(String titleEn, String titleLv, String goal, String tasks, Student student,
			AcademicPersonel supervisor) {
		super();
		this.titleEn = titleEn;
		this.titleLv = titleLv;
		this.goal = goal;
		this.tasks = tasks;
		this.student = student;
		this.supervisor = supervisor;
		this.submitDateTime = LocalDateTime.now();
		this.accStatus = AcceptanceStatus.submited;
	}
	
	

}
