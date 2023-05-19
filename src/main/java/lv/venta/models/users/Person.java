package lv.venta.models.users;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "person_table")
@Entity
@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
//TODO pielikt toString ja nepieciesams
public class Person {
	
	@Setter(value = AccessLevel.NONE)
	@Column(name = "Idperson")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long idperson;
	
	@Column(name = "Name")
	@NotNull
	@Size(min = 3, max = 15)
	@Pattern(regexp = "[A-Z]{1}[a-z\\ ]+", message = "Pirmajam burtam jabut lielajam")
	private String name;
	
	@Column(name = "Surname")
	@NotNull
	@Size(min = 3, max = 15)
	@Pattern(regexp = "[A-Z]{1}[a-z\\ ]+", message = "Pirmajam burtam jabut lielajam")
	private String surname;
	
	@Column(name = "Personcode")
	@NotNull
	//TODO apdomat un pielietot risinajumu arzemju studentiem
	@Size(min = 12, max = 12)
	@Pattern(regexp = "[0-9]{6}-[0-9]{5}", message = "Pirmajam burtam jabut lielajam")
	private String personcode;

	
	@OneToOne
	@JoinColumn(name = "Iduser")
	private User user;


	public Person(
			@NotNull @Size(min = 3, max = 15) @Pattern(regexp = "[A-Z]{1}[a-z\\ ]+", message = "Pirmajam burtam jabut lielajam") String name,
			@NotNull @Size(min = 3, max = 15) @Pattern(regexp = "[A-Z]{1}[a-z\\ ]+", message = "Pirmajam burtam jabut lielajam") String surname,
			@NotNull @Size(min = 12, max = 12) @Pattern(regexp = "[0-9]{6}-[0-9]{5}", message = "Pirmajam burtam jabut lielajam") String personcode,
			User user) {
		this.name = name;
		this.surname = surname;
		this.personcode = personcode;
		this.user = user;
	}
	
	
	
	
	
}
