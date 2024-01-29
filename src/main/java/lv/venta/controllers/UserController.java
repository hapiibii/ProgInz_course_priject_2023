package lv.venta.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import lv.venta.models.users.User;
import lv.venta.services.impl.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	@GetMapping("/showAll")
	public String showAllUsers (Model model) {
		model.addAttribute("myAllUsers", userService.retrieveAllUsers());
		return "all-users-page";
	}
	
	@GetMapping("/users-page")
	public String showUsersPage (Model model) {
		List<User> allUsers = userService.retrieveAllUsers();
		model.addAttribute("myAllUsers", allUsers);
		return "user-page";
	}
	
	@GetMapping("/showAll/{iduser}")
	public String showUserById(@PathVariable long iduser, Model model) {
		try {
			User user = userService.retrieveUserById(iduser);
			model.addAttribute("myAllUsers", user);
			return "one-user-page";
		} catch (Exception e) {
			return "error-page";
		}
	}
	
	@GetMapping("/delete/{iduser}")
	public String deleteUserById (@PathVariable("iduser") long iduser, Model model) {
		try {
			userService.deleteUser(iduser);
			model.addAttribute("myAllUsers", userService.retrieveAllUsers());
			return "all-users-page";
		} catch (Exception e) {
			return "error-page";
		}
	}
	
	@GetMapping("/update/{iduser}")
	public String updateUserGetFunction (@PathVariable("iduser") long iduser, Model model) {
		try {
			model.addAttribute("user", userService.retrieveUserById(iduser));
			return "user-update-page";
		} catch (Exception e) {
			return "error-page";
		}
	}
	
	@PostMapping("/update/{iduser}")
	public String updateUserPostFunction (@PathVariable("iduser") long iduser, @Valid User user, BindingResult result) {
		if (!result.hasErrors()) {
			try {
				User temp = userService.updateUser(iduser, user.getPassword(), user.getUsername());
				return "redirect:/user/showAll";
			} catch (Exception e) {
				return "redirect:/error";
			}
		}
		else {
			return "user-update-page";
		}
	}
	
	@GetMapping("/insert")
	public String insertUserGetFunction (Model model) {
		model.addAttribute("user", new User());
		return "user-insert-page";
	}
	
	@PostMapping("/insert")
	public String insertUserPostFunction (@ModelAttribute("user") User user, BindingResult result) {
		if (!result.hasErrors()) {
			try {
				userService.insertUser(user.getUsername(), user.getPassword());
				return "redirect:/user-page";
			} catch (Exception e) {
				return "error-page";
			}
		}
		return "redirect:/user-page";
	}
	
}
