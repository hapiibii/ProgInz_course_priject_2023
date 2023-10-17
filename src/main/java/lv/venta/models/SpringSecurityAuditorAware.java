package lv.venta.models;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;

public class SpringSecurityAuditorAware implements AuditorAware<String> {

	@Override
	public Optional<String> getCurrentAuditor() {
		
		//returns a String representing the username
		return Optional.ofNullable("Admin").filter(s -> !s.isEmpty());
		
	}

}
