package lv.venta.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lv.venta.models.users.Person;
import lv.venta.repos.IPersonRepo;
import lv.venta.services.impl.PersonService;

@Controller
@RequestMapping("/person")
public class PersonController {
	
	@Autowired
	private PersonService personService;
	private IPersonRepo personRepo;
	
	
	@GetMapping("/showAll")
	public String showAllPersons(Model model) {
	    try {
	        List<Person> allPersons = personService.retrieveAllPersons();
	        model.addAttribute("myAllPersons", allPersons);
	        return "all-users-page";
	    } catch (Exception e) {
	        e.printStackTrace(); 
	        return "error-page";
	    }
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
	        Person person = (Person) personService.retrieveAllPersons();
	        if (person != null) {
	            model.addAttribute("myAllPersons", person);
	            return "one-person-page";
	        } else {
	            return "person-not-found-page";
	        }
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
	
	@GetMapping("/updateById/{idperson}")
	public String updatePersonGetFunction(@PathVariable("idperson") long idperson, Model model) {
	    try {
	        Person personToUpdate = personService.retrievePersonById(idperson);

	        if (personToUpdate != null) {
	            model.addAttribute("person", personToUpdate);
	            return "person-update-page";
	        } else {
	            throw new Exception("Persona ar id " + idperson + " nav atrasta.");
	        }
	    } catch (Exception e) {
	        return "error-page";
	    }
	}

	
	@GetMapping("/update/{personcode}")
	public String updatePersonGetFunction(@PathVariable("personcode") String personcode, Model model) {
	    try {
	        Person personToUpdate = (Person) personService.retrieveAllPersons();

	        if (personToUpdate != null) {
	            model.addAttribute("person", personToUpdate);
	            return "person-update-page";
	        } else {
	        	throw new Exception("Persona ar kodu " + personcode + " nav atrasta.");
	        }
	    } catch (Exception e) {
	        return "error-page";
	    }
	}

	@GetMapping("/insert")
	public String insertPersonGetFunction(Model model) {
		model.addAttribute("person", new Person());
		return "person-insert-page";
	}
	@PostMapping("/insert")
	public String insertPersonPostFunction(@ModelAttribute("person") Person person, BindingResult result) {
		if (!result.hasErrors()) {
			try {
				personService.createPerson(person);
				return "redirect:/person/showAll";
			} catch (Exception e) {
				return "error-page";
			}
			
		}
		return "redirect:/person-page";
	}
	
	@GetMapping("/getByUser/{userId}")
    public String getPersonByUser(@PathVariable("userId") long userId, Model model) {
        try {
            Person person = personService.getPersonByUserId(userId);

            if (person != null) {
                model.addAttribute("myPerson", person);
                return "person-page";
            } else {
                return "person-not-found-page";
            }
        } catch (Exception e) {
            return "error-page";
        }
    }
	
}
