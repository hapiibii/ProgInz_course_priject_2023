package lv.venta.models;

import java.util.ArrayList;
import java.util.Collection;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lv.venta.models.users.Student;

@Table(name = "course_table")
@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Course {

	
	@Column(name = "Idcourse")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Setter(value = AccessLevel.NONE)
	private long idcourse;
	
	@Column(name = "Title")
	@NotNull
	@Size(min = 3, max = 35)
	@Pattern(regexp = "[A-Z]{1}[a-z\\ ]+", message = "Pirmajam burtam jabut lielajam")
	private String title;
	
	@Column(name = "CreditPoints")
	@Min(0)
	@Max(24)
	private int creditPoints;

	@ManyToMany(mappedBy = "debtCourses")
	private Collection<Student> debtStudents = new ArrayList<>();
	
	
	public Course(
			@NotNull @Size(min = 3, max = 35) @Pattern(regexp = "[A-Z]{1}[a-z\\ ]+", message = "Pirmajam burtam jabut lielajam") String title,
			@Min(0) @Max(24) int creditPoints) {
		this.title = title;
		this.creditPoints = creditPoints;
	}
	
	public void addStudent(Student student) {
		if(!debtStudents.contains(student)) {
			debtStudents.add(student);
		}
	}
	
	//izveidot removeStudent
	public void removeStudent(String matriculaNo) throws Exception {
		boolean foundStudent = false;
		for(Student temp : debtStudents) {
			if(temp.getMatriculaNo().equals(matriculaNo)) {
				debtStudents.remove(temp);
				foundStudent = true;
				break;
			}
		}
		if (!foundStudent) {
			throw new Exception("Wrong Matricula Number!");
		}
	}
	
	
}
