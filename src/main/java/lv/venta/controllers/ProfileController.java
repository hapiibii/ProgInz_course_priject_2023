package lv.venta.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import lv.venta.models.users.Person;
import lv.venta.models.users.User;
import lv.venta.services.impl.PersonService;
import lv.venta.services.impl.UserService;

@Controller
@RequestMapping("/profile")
public class ProfileController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private PersonService personService;
	
	@GetMapping("/view")
    public String viewUserProfile(Model model, HttpSession session) throws Exception {
        Long userId = (Long) session.getAttribute("userId");

        if (userId != null) {
            User user = userService.retrieveUserById(userId);
            Person person = personService.getPersonByUserId(userId);

            model.addAttribute("user", user);
            model.addAttribute("person", person);
            return "redirect:/login";
            
        } else {
             return "profile-page";
        }
    }
	
	@GetMapping("/{iduser}")
	public String viewUserProfile(@PathVariable("iduser") long iduser, Model model) throws Exception {
	    User user = userService.retrieveUserById(iduser);
	    Person person = personService.getPersonByUserId(iduser);

	    model.addAttribute("user", user);
	    model.addAttribute("person", person);

	    return "profile-page";
	}
	
	
	@GetMapping("/edit/{iduser}")
	public String editUserProfileGetFunction(@PathVariable("iduser") long iduser, Model model) throws Exception {
	    User user = userService.retrieveUserById(iduser);
	    Person person = personService.getPersonByUserId(iduser);

	    model.addAttribute("user", user);
	    model.addAttribute("person", person);
	    model.addAttribute("editedUser", new User()); // Pievieno jaunu tukšu User objektu

	    return "profile-edit-page";
	}

	@PostMapping("/edit/{iduser}")
	public String editUserProfilePostFunction(@PathVariable("iduser") long iduser, @ModelAttribute("editedUser") User editedUser,
	        @ModelAttribute("person") Person person, BindingResult result) {
	    if (!result.hasErrors()) {
	        try {
	            userService.updateUser(iduser, editedUser.getPassword(), editedUser.getUsername());
	            personService.updatePersonWithPersoncode(person.getIdperson(), person.getName(), person.getSurname(), person.getPersoncode(), person.getRole(), person.getUser());

	            return "redirect:/profile/" + iduser;
	        } catch (Exception e) {
	            return "redirect:/error";
	        }
	    }

	    return "profile-edit-page";
	}
	
    @GetMapping("/delete/{iduser}")
    public String deleteUserProfile(@PathVariable("iduser") long iduser, Model model) {
        try {
            userService.deleteUser(iduser);
            return "redirect:/home"; 
        } catch (Exception e) {
            return "redirect:/error";
        }
    }
    
    
}
