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
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lv.venta.models.users.AcademicPersonel;
import lv.venta.models.users.Student;

@Entity
@Table(name = "thesis_table")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Thesis extends Auditable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "idthesis")
	@Setter(value = AccessLevel.NONE)
	private long idthesis;

	@NotNull
	@Size(min = 3, max = 150)
	//@Pattern(regexp = "[A-Z]{1}[a-z\\ ]+", message = "First character must be uppercase")
	@Column(name = "title_en")
	private String titleEn;

	@NotNull
	@Size(min = 3, max = 150)
	//@Pattern(regexp = "[A-ZĀČĒĢĪĶĻŅŠŪŽ]{1}[a-zāčēģīķļņšūž\\ ]+", message = "Pirmajam burtam jābūt lielajam")
	@Column(name = "title_lv")
	private String titleLv;

	@NotNull
	@Size(min = 3, max = 500)
	//@Pattern(regexp = "[A-ZĀČĒĢĪĶĻŅŠŪŽ]{1}[a-zāčēģīķļņšūž\\ ]+", message = "Pirmajam burtam jābūt lielajam")
	@Column(name = "goal")
	private String goal;

	@NotNull
	@Size(min = 3, max = 500)
	//@Pattern(regexp = "[A-ZĀČĒĢĪĶĻŅŠŪŽ]{1}[a-zāčēģīķļņšūž\\ ]+", message = "Pirmajam burtam jābūt lielajam")
	@Column(name = "tasks")
	private String tasks;

	@NotNull
	@Column(name = "submit_date_time")
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private LocalDateTime submitDateTime;

	@Column(name = "status_from_supervisor")
	private boolean statusFromSupervisor;

	@NotNull
	@Column(name = "acceptance_status")
	private AcceptanceStatus accStatus;

	private LocalDateTime accDateTime;

	@ManyToOne
	@JoinColumn(name = "idstudent")
	private Student student;

	@ManyToOne
	@JoinColumn(name = "idap")
	private AcademicPersonel supervisor;

	@ManyToMany
	@JoinTable(name = "thesis_reviewers", joinColumns = @JoinColumn(name = "idthesis"), inverseJoinColumns = @JoinColumn(name = "idap"))
	private Collection<AcademicPersonel> reviewers = new ArrayList<>();

	@OneToMany(mappedBy = "thesis")
	private Collection<Comment> comments;
	
	@ManyToMany
	@JoinTable(name = "thesis_submissions_table", joinColumns = @JoinColumn(name = "idthesis"), inverseJoinColumns = @JoinColumn(name = "idsubmission"))
	private Collection<Submission> thesisSubmission = new ArrayList<>();

	public void addReviewer(AcademicPersonel reviewer) {
		if (!reviewers.contains(reviewer)) {
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
		this.statusFromSupervisor = true;
	}
	
}