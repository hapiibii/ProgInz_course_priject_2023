package lv.venta.models.users;

import java.util.ArrayList;
import java.util.Collection;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lv.venta.models.Course;
import lv.venta.models.Submission;
import lv.venta.models.Thesis;

@Table(name = "student_table")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AttributeOverride(name="Idperson", column = @Column(name="Idstudent"))
public class Student extends Person {

	//TODO izveidot Data JPA annotacijas
	//TODO izveidot validaciju annotacijas
	//TODO izveidot sasaisti ar Course klasi ManyToMany
	@Column(name = "MatriculaNo")
	@NotNull
	//@Size(min = 8, max = 20)
	@Pattern(regexp = "[0-9]{8,20}")
	private String matriculaNo;
	
	@Column(name = "FinancialDebt")
	private boolean financialDebt;

	@ManyToMany
	@JoinTable(name = "student_debt_courses_table", joinColumns = @JoinColumn(name = "Idstudent"), inverseJoinColumns = @JoinColumn(name = "Idcourse"))
	private Collection<Course> debtCourses = new ArrayList<>();
	
	@OneToMany(mappedBy = "student")
	private Collection<Thesis> thesis;
	
	@ManyToMany(mappedBy = "submissions")
	private Collection<Submission> studentSubmission = new ArrayList<>();
	
	public Student(
			@NotNull @Size(min = 3, max = 15) @Pattern(regexp = "[A-Z]{1}[a-z\\ ]+", message = "Pirmajam burtam jabut lielajam") String name,
			@NotNull @Size(min = 3, max = 15) @Pattern(regexp = "[A-Z]{1}[a-z\\ ]+", message = "Pirmajam burtam jabut lielajam") String surname,
			@NotNull @Size(min = 12, max = 12) @Pattern(regexp = "[0-9]{6}-[0-9]{5}", message = "Pirmajam burtam jabut lielajam") String personcode,
			User user, @NotNull @Pattern(regexp = "[0-9]{8,20}") String matriculaNo, boolean financialDebt) {
		super(name, surname, personcode, user);
		this.matriculaNo = matriculaNo;
		this.financialDebt = financialDebt;
	}
	
	public void addDebtCourse(Course course) {
		if(!debtCourses.contains(course)) {
			debtCourses.add(course);
		}
	}
	
	public void addStudentSubmission(Submission submission) {
		if(!studentSubmission.contains(submission)) {
			studentSubmission.add(submission);
		}
	}
	
}
