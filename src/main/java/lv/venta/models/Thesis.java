package lv.venta.models;

import java.time.LocalDateTime;
import java.util.Collection;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

//parlikt uz citu tabulu, ,urai nav saites

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
	
	//TODO nepieciesamas validacijas
	@Column(name = "TitleEn")
	private String titleEn;
	
	//TODO nepieciesamas validacijas
	@Column(name = "TitleLv")
	private String titleLv;
		
	//TODO nepieciesamas validacijas
	@Column(name = "Goal")
	private String goal;
	
	//TODO nepieciesamas validacijas
	@Column(name = "Tasks")
	private String tasks;
	
	//TODO servica vai konstruktora pie jauna objekta izveides jaizmanto LocalDateTime.now()
	@Column(name = "SubmitDateTime")
	private LocalDateTime submitDateTime;
	
	//TODO nepieciesamas validacijas
	@Column(name = "StatusFromSupervisor")
	private boolean statusFromSupervisor;
	
	//TODO servica vai konstruktora uzlikt submited pec noklusejuma
	@Column(name = "AcceptanceStatus")
	private AcceptanceStatus accStatus;
	
	@Column(name = "AccDateTime")
	private LocalDateTime accDateTime;

}
