package lv.venta.models.users;

import java.util.ArrayList;
import java.util.Collection;


import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lv.venta.models.Comment;
import lv.venta.models.Course;
import lv.venta.models.Degree;
import lv.venta.models.Thesis;

@Table(name = "academicpersonel_table")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AttributeOverride(name="Idperson", column = @Column(name="Idap"))

public class AcademicPersonel extends Person {

	@Column(name = "Degree")
	private Degree degree;
	
	@OneToMany(mappedBy = "supervisor")
	private Collection<Thesis> thesis;
	
	@ManyToMany(mappedBy = "reviewers")
	private Collection<Thesis> thesisForReview = new ArrayList<>();
	
	@OneToMany(mappedBy = "supervisor")
	private Collection<Comment> comments;
	
	public void addThesisForReviews(Thesis thesis) {
		if(!thesisForReview.contains(thesis)) {
			thesisForReview.add(thesis);
		}
	}

	public AcademicPersonel(
			@NotNull @Size(min = 3, max = 15) @Pattern(regexp = "[A-Z]{1}[a-z\\ ]+", message = "Pirmajam burtam jabut lielajam") String name,
			@NotNull @Size(min = 3, max = 15) @Pattern(regexp = "[A-Z]{1}[a-z\\ ]+", message = "Pirmajam burtam jabut lielajam") String surname,
			@NotNull @Size(min = 12, max = 12) @Pattern(regexp = "[0-9]{6}-[0-9]{5}", message = "Pirmajam burtam jabut lielajam") String personcode, 
			@NotNull Role role,
			User user, Degree degree) {
		super(name, surname, personcode, role, user);
		this.degree = degree;
	}
	
	//for foreign personel
	public AcademicPersonel(
			@NotNull @Size(min = 3, max = 15) @Pattern(regexp = "[A-Z]{1}[a-z\\ ]+", message = "Pirmajam burtam jabut lielajam") String name,
			@NotNull @Size(min = 3, max = 15) @Pattern(regexp = "[A-Z]{1}[a-z\\ ]+", message = "Pirmajam burtam jabut lielajam") String surname, 
			@NotNull Role role,
			User user, Degree degree) {
		super(name, surname, role, user);
		this.degree = degree;
	}
	
	
	
}
