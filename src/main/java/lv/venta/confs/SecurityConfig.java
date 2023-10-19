package lv.venta.confs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import jakarta.servlet.DispatcherType;
import lv.venta.services.impl.security.UserDetailsServiceImpl;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
	

	@Bean
	public WebSecurityCustomizer webSecurityCustomizer () {
		return (web) -> web.ignoring().requestMatchers(new AntPathRequestMatcher("/h2-console"));
	}
	
	@Bean
	public UserDetailsServiceImpl userDetailsManager() {
	  UserDetailsServiceImpl manager = new UserDetailsServiceImpl();
	  return manager;
	}
	 
	@Bean
	public PasswordEncoder passwordEncoderSimple2() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
	
	@Bean
	public AuthenticationManager authentificationManager(HttpSecurity http) throws Exception {
		AuthenticationManagerBuilder authentificationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
		authentificationManagerBuilder.userDetailsService(userDetailsManager()).passwordEncoder(passwordEncoderSimple2());
		return authentificationManagerBuilder.build();
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain (HttpSecurity http) throws Exception {
		http.authorizeHttpRequests()
		.requestMatchers("/style.css").permitAll()
		.requestMatchers("/images/**").permitAll()
		.requestMatchers("/Calendar-schedule/studio-programms").hasAnyAuthority("USER")
		.requestMatchers("/Calendar-schedule/studio-programms/**").hasAnyAuthority("USER")
		.requestMatchers("/Calendar-schedule/calendar-add").hasAnyAuthority("ADMIN")
		.requestMatchers("/Calendar-schedule/delete/**").hasAnyAuthority("ADMIN")
		.requestMatchers("/Calendar-schedule/edit/**").hasAnyAuthority("ADMIN")
		.requestMatchers("/Calendar-schedule/update/**").hasAnyAuthority("ADMIN")
		.requestMatchers("/Calendar-schedule/export").permitAll()
		.requestMatchers("/Calendar-schedule/export/**").permitAll()
		.requestMatchers("/document-page").hasAnyAuthority("USER")
		.requestMatchers("/document-page/all-documents").hasAnyAuthority("USER")
		.requestMatchers("/document-page/create-documents").hasAnyAuthority("USER")
		.requestMatchers("/document-page/update-document/**").hasAnyAuthority("ADMIN")
		.requestMatchers("/document").hasAnyAuthority("USER")
		.requestMatchers("/document/showAll").hasAnyAuthority("USER")
		.requestMatchers("/document/showAll/**").hasAnyAuthority("USER")
		.requestMatchers("/document/delete/**").hasAnyAuthority("ADMIN")
		.requestMatchers("/document/update/**").hasAnyAuthority("ADMIN")
		.requestMatchers("/document/insert").hasAnyAuthority("USER")
		.requestMatchers("/home-page").permitAll()
		.requestMatchers("/home-page/all-news").hasAnyAuthority("USER")
		.requestMatchers("/home-page/create-news").hasAnyAuthority("USER")
		.requestMatchers("/home-page/edit-news/**").hasAnyAuthority("USER")
		.requestMatchers("/home-page/delete-news/**").hasAnyAuthority("ADMIN")
		.requestMatchers("/home-page/all-documents").hasAnyAuthority("USER")
		.requestMatchers("/home-page/create-document").hasAnyAuthority("USER")
		.requestMatchers("/home-page/delete-document/**").hasAnyAuthority("ADMIN")
		.requestMatchers("/studioprogramms").hasAnyAuthority("ADMIN")
		.requestMatchers("/studioprogramms/**").hasAnyAuthority("ADMIN")		
		.requestMatchers("/submission").hasAnyAuthority("USER")
		.requestMatchers("/submission/showAll").hasAnyAuthority("USER")
		.requestMatchers("/submission/showAll/**").hasAnyAuthority("USER")
		.requestMatchers("/submission/delete/**").hasAnyAuthority("ADMIN")
		.requestMatchers("/submission/update/**").hasAnyAuthority("ADMIN")
		.requestMatchers("/submission/insert").hasAnyAuthority("USER")
		.requestMatchers("/submission-page").hasAnyAuthority("USER")
		.requestMatchers("/submission-page/all-submissions").hasAnyAuthority("USER")
		.requestMatchers("/submission-page/create-submission").hasAnyAuthority("USER")
		.requestMatchers("/itftable-page").hasAnyAuthority("USER")
		.requestMatchers("/itftable-page/review").hasAnyAuthority("USER")
		.requestMatchers("/itftable-page/create").hasAnyAuthority("USER")
		.requestMatchers("/itftable-page/edit/**").hasAnyAuthority("ADMIN")
		.requestMatchers("/itftable-page/delete/**").hasAnyAuthority("ADMIN")
		.requestMatchers("/itftable-page/create-comment").hasAnyAuthority("USER")
		.requestMatchers("/itftable-page/create-comment/**").hasAnyAuthority("USER")
		.requestMatchers("/itftable-page/edit-comment/**").hasAnyAuthority("USER")
		.requestMatchers("/itftable-page/delete-comment/**").hasAnyAuthority("USER")
		.requestMatchers("/h2-console").permitAll()
	    .requestMatchers("/h2-console/**").permitAll()
	    ///document/download/**
	    .dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
		.and()
		.formLogin().permitAll()
		.and()
		.logout().permitAll();
		
		
		
		return http.build();
	}
	

	public void configure(WebSecurity web) throws Exception {
	    web.ignoring()
	       .requestMatchers("/resources/static/css/**");
	}
	
}
