package lv.venta.models.users;

import java.util.Collection;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lv.venta.models.Course;
import lv.venta.models.Degree;

@Table(name = "academicpersonel_table")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AttributeOverride(name="Idperson", column = @Column(name="Idap"))

public class AcademicPersonel extends Person {

	@Column(name = "Degree")
	private Degree degree;
}
