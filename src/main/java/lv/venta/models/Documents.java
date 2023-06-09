package lv.venta.models;

import java.io.File;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Table(name = "documents_table")
@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Documents {

	@Column(name = "Iddocument")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Setter(value = AccessLevel.NONE)
	private long iddocument;
	
	@Column(name = "DocumentName")
	@NotNull
	@Size(min = 3, max = 35)
	@Pattern(regexp = "[A-Z]{1}[a-z\\ ]+")
	private String documentName;
	
	@Column(name = "File")
	private File file;

	public Documents(@NotNull @Size(min = 3, max = 35) @Pattern(regexp = "[A-Z]{1}[a-z\\ ]+") String documentName,
			File file) {
		this.documentName = documentName;
		this.file = file;
	}
	
	
	
	
	
	
	
	
	
	
}
