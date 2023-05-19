package lv.venta.models.users;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "user_table")
@Entity
@Getter
@Setter
@NoArgsConstructor
//TODO pielikt toString, ja nepieciešams
public class User {

	@Setter(value = AccessLevel.NONE)
	@Column(name = "Iduser")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long iduser;
	
	//@Column(name = "Username")
	//@NotNull
	//@Pattern(regexp = "[a-z]")
	//private String username;
	
	
	//TODO kad pievienos Spring security, tad jauzliek passwordEncoder
	@NotNull
	@Column(name = "Password")
	//TODO papildinat ar validaciju, kad ir zinams passwordEncoder
	private String password;
	
	@NotNull
	@Column(name = "Email")
	@Email
	private String email;

	
	@OneToOne(mappedBy = "user")
	private Person person;
	
	public User(@NotNull String password, @NotNull @Email String email) {
		super();
		this.password = password;
		this.email = email;
	}
	
	
	
}
