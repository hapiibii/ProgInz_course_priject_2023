package lv.venta.models;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lv.venta.models.users.AcademicPersonel;

@Table(name = "comment_table")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Comment extends Auditable {
	
	@Setter(value = AccessLevel.NONE)
	@Column(name = "Idcom")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long idcom;
	
	@Column(name = "Description")
	private String description;
	
	@Column(name = "commentDate")
	private LocalDateTime date;
	
	@ManyToOne
	@JoinColumn(name = "Idap")
	private AcademicPersonel supervisor;
	
	@ManyToOne
	@JoinColumn(name = "Idthesis")
	private Thesis thesis;

	public Comment(String description, AcademicPersonel personel, Thesis thesis) {
		super();
		this.description = description;
		this.supervisor = personel;
		this.thesis = thesis;
		this.date = LocalDateTime.now();
	}	
}
