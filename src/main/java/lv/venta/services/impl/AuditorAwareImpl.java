package lv.venta.services.impl;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuditorAwareImpl implements AuditorAware<String> {
	
	@Override
    public Optional<String> getCurrentAuditor() {
        SecurityContext context = SecurityContextHolder.getContext();
        if (context != null && context.getAuthentication() != null) {
            return Optional.of(context.getAuthentication().getName());
        }
        // Return a default value or throw an exception when no authenticated user is found.
        return Optional.of("anonymous");
    }

}
