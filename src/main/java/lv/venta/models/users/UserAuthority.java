package lv.venta.models.users;

import java.util.ArrayList;
import java.util.Collection;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Table(name = "UserAuthority")
@Entity
public class UserAuthority {
	
	@Column(name = "UserAuthorityId")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long userAuthorityId;
	
	@Column(name = "Title")
	@Pattern(regexp = "[A-Z]{3,8}")
	@NotNull
	private String title;
	
	
	@ManyToMany
	@JoinTable(name = "Users_Authorities", joinColumns = @JoinColumn(name = "UserAuthorityId"), inverseJoinColumns = @JoinColumn(name = "Iduser"))
	private Collection<User> users = new ArrayList<>();
	
	
	
	
	
	

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public long getUserAuthorityId() {
		return userAuthorityId;
	}
	
	public UserAuthority () {
		
	}
	
	public UserAuthority (String title) {
		setTitle(title);
	}
	
	
	public void addUser(User user) {
		if (!users.contains(user)) {
			users.add(user);
		}
	}
	
	public void removeUser(User user) {
		if (!users.contains(user)) {
			users.remove(user);
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
