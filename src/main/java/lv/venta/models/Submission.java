package lv.venta.models;

import java.io.File;
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
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lv.venta.models.users.Student;

@Table(name = "subbmision_table")
@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Submission {
	
	@Column(name = "Idsubmission")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Setter(value = AccessLevel.NONE)
	private long idsubmission;
	
	@Column(name = "SubmissionDate")
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private LocalDateTime submissionDate;
	
	private File file;
	
	@ManyToMany
	@JoinTable(name = "student_submissions_table", joinColumns = @JoinColumn(name = "Idsubmission"), inverseJoinColumns = @JoinColumn(name = "Idstudent"))
	private Collection<Student> submissions = new ArrayList<>();
	
	
	
	public Submission(LocalDateTime submissionDate, File file) {
		this.submissionDate = submissionDate;
		this.file = file;
	}
	
	public void addSubmissions(Student student) {
		if(!submissions.contains(student)) {
			submissions.add(student);
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
