package lv.venta.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lv.venta.models.Submission;
import lv.venta.services.ISubmissionService;

@Controller
@RequestMapping("/submission-page")
public class SubmissionPageController {

	private final ISubmissionService submissionService;
	
	public SubmissionPageController (ISubmissionService submissionService) {
		this.submissionService = submissionService;
	}
	
	@GetMapping
	public String showSubmissionPage (Model model) {
		List<Submission> allSubmissions = submissionService.retrieveAllSubmisions();
		model.addAttribute("myAllSub", allSubmissions);
		return "submission-page";
	}
	
	@GetMapping("/all-submissions")
	public String showAllSubmissions (Model model) {
		List<Submission> allSubmissions = submissionService.retrieveAllSubmisions();
		model.addAttribute("allSubmissions", allSubmissions);
		return "all-submissions-page";
	}
	
	@GetMapping("/create-submission")
	public String showSubmissionCreate (Model model) {
		model.addAttribute("submission", new Submission());
		return "submission-insert-page";
	}
	
	@PostMapping("/create-submission")
	public String showSubmissionCreatePost (@ModelAttribute("submission") Submission submission) {
		submissionService.insertSubmission(submission.getSubmissionDate(), submission.getFile());
		return "redirect:/submissions-page";
	}
	
	
	
	
	
	
	
}
