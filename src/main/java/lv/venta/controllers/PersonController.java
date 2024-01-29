package lv.venta.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import lv.venta.models.users.Person;
import lv.venta.services.impl.PersonService;

@Controller
@RequestMapping("/person")
public class PersonController {
	
	@Autowired
	private PersonService personService;
	
	@GetMapping("/showAll")
	public String showAllPersons (Model model) {
		model.addAttribute("myAllPersons", personService.retrieveAllPersons());
		return "all-users-page";
	}
	
	@GetMapping("/person-page")
	public String showPersonPage (Model model) {
		List<Person> allPersons = personService.retrieveAllPersons();
		model.addAttribute("myAllPersons", allPersons);
		return "person-page";
	}
	
	@GetMapping("showAll/{personcode}")
	public String showPersonByPersoncode(@PathVariable String personcode, Model model) {
		try {
			Person person = personService.updatePersonWithPersoncode(person.getIdperson(), person.getName(), person.getSurname(), person.getPersoncode(), person.getRole(), person.getUser());
			model.addAttribute("myAllPersons", person);
			return "one-person-page";
		} catch (Exception e) {
			return "error-page";
		}
	}
	
	@GetMapping("/delete/{idperson}")
	public String deletePersonById(@PathVariable("idperson") long idperson, Model model) {
		try {
			personService.deletePerson(idperson);
			model.addAttribute("myAllPersons", personService.retrieveAllPersons());
			return "all-person-page";
		} catch (Exception e) {
			return "error-page";
		}
	}
	
	@GetMapping("/update/{personcode}")
	public String updatePersonGetFunction(@PathVariable("personcode") String personcode, Model model) {
		try {
			model.addAttribute("person", personService.updatePersonWithPersoncode(person.getIdperson(), person.getName(), person.getSurname(), person.getPersoncode(), person.getRole(), person.getUser());
			return "person-update-page";
		} catch (Exception e) {
			return "error-page";
		}
	}
	
	
	
	
	
}
