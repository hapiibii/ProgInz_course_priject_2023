package lv.venta.controllers;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import lv.venta.models.Submission;
import lv.venta.services.impl.SubmissionServiceImpl;

@Controller
@RequestMapping("/submission")
public class SubmissionController {
	
	@Autowired
	private SubmissionServiceImpl submissionService;
	
	@GetMapping("/showAll")
	public String showAllSubmissions (Model model) {
		model.addAttribute("myAllSubmissions", submissionService.retrieveAllSubmisions());
		return "all-submissions-page";
	}
	
	@GetMapping("/showAll/{idsubmission}")
	public String showSubmissionById (@PathVariable long idsubmission, Model model) {
		try {
			Submission submission = submissionService.retrieveSubmissionById(idsubmission);
			model.addAttribute("myAllSubmissions", submission);
			return "one-submission-page";
		}
		catch (Exception e) {
			return "error-page";
		}
	}
	
	@GetMapping("/delete/{idsubmission}")
	public String deleteSubmissionById (@PathVariable("idsubmission") long idsubmission, Model model) {
		try {
			submissionService.deleteSubmissionById(idsubmission);
			model.addAttribute("myAllSubmissions", submissionService.retrieveAllSubmisions());
			return "all-submissions-page";
		}
		catch (Exception e) {
			return "error-page";
		}
	}
	
	@GetMapping("/showAll/{submissionDate}")
	public String allSubmissionsByDate (@PathVariable("submissionDate") LocalDateTime submissionDate, Model model) {
		try {
			ArrayList<Submission> allSubmissionsWithDate = submissionService.filterByDate(submissionDate);
			model.addAttribute("myAllSubmissions", allSubmissionsWithDate);
			return "all-submissions-page";
		}
		catch (Exception e) {
			return "error-page";
		}
	}
	
	@GetMapping("/update/{idsubmission}")
	public String updateSubmissionGetFunction (@PathVariable("idsubmission") long idsubmission, Model model) {
		try {
			model.addAttribute("submission", submissionService.retrieveSubmissionById(idsubmission));
			return "submission-update-page";
		}
		catch (Exception e) {
			return "error-page";
		}
	}
	
	@PostMapping("/update/{idsubmission}")
	public String updateSubmissionPostFunction (@PathVariable("idsubmission") long idsubmission, @Valid Submission submission, BindingResult result) {
		if (!result.hasErrors()) {
			try {
				Submission temp = submissionService.updateSubmission(idsubmission, submission.getSubmissionDate(), submission.getFile());
				return "redirect:/submission/showAll";
			}
			catch (Exception e) {
				return "error-page";
			}
		}
		else {
			return "submission-update-page";
		}
	}
	
	@GetMapping("/insert")
	public String insertSubmissionGetFunction (Submission submission) {
		return "submission-insert-page";
	}
	
	@PostMapping("/insert")
	public String insertSubmissionPostFunction (@Valid Submission submission, BindingResult result, @RequestParam("file") MultipartFile file, Model model) {
		System.out.println(submission.getSubmissionDate());
		System.out.println(result.getErrorCount());
	//	if (!result.hasErrors()) {
			try {
				if (!file.isEmpty()) {
					//Iegūst faila nosaukumu un saglabāšanas ceļu
					String fileName = file.getOriginalFilename();
					if(fileName.endsWith(".pdf") || fileName.endsWith(".odt")) {
						String filePath = "/" + fileName;
						//Saglabā failu vietējā failu sistēmā
						file.transferTo(new File(filePath));
						//Iesaista faila objektu ievades parametrā
						submission.setFile(new File(filePath));
					}
					else {
	                    result.rejectValue("file", "file.invalidType", "Only .pdf and .odt files are allowed.");
	                    model.addAttribute("submission", submission);
	                    return "submission-insert-page";
	                }
				}
				submissionService.insertSubmission(submission.getSubmissionDate(), submission.getFile());
				return "redirect:/submission/showAll";

			}
			catch (Exception e) {
				e.printStackTrace();
				return "error-page";
			}
	//	}
	//	else {
	//		return "submission-insert-page";
	//	}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
