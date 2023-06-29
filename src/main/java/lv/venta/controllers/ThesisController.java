package lv.venta.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lv.venta.models.Comment;
import lv.venta.models.Thesis;
import lv.venta.services.ICommentService;
import lv.venta.services.IThesisService;

@Controller
@RequestMapping("/itftable-page")
public class ThesisController {
	
	private final IThesisService thesisService;
	private final ICommentService commentService;
	
	@Autowired
	public ThesisController (IThesisService thesisService, ICommentService commentService) {
		this.thesisService = thesisService;
		this.commentService = commentService;
	}
	
	//TODO all-thesises get
	@GetMapping("itf-table/review")
	public String showAllThesises(Model model) {
		List<Thesis> allThesises = thesisService.getAllThesises();
		model.addAttribute("allThesises", allThesises);
		return "thesis-view-page";
	}
	
	//TODO create-thesis get
	@GetMapping("/itf-table/create")
	public String showCreateThesis(Model model) {
		model.addAttribute("thesis", new Thesis());
		return "thesis-create-page";
	}
	
	//TODO create-thesis post
	@PostMapping("/itf-table/create")
	public String сreateThesis(@ModelAttribute("thesis") Thesis thesis) {
		thesisService.createThesis(thesis);
		return "redirect:/itf-table/review";
	}
	
	//TODO edit-thesis get
	@GetMapping("/itf-table/edit/{idthesis}")
	public String showEditThesis(@PathVariable("idthesis") long idthesis, Model model) {
		Thesis thesis = thesisService.getThesisById(idthesis);
		model.addAttribute("thesis", thesis);
		return "thesis-edit-page";
	}
	
	//TODO edit-thesis post
	@PostMapping("/itf-table/edit/{idthesis}")
	public String editThesis(@PathVariable("idthesis") long idthesis, @ModelAttribute("thesis") Thesis thesis) throws Exception {
		thesisService.updateThesis(idthesis, thesis.getTitleEn(), thesis.getTitleLv(), thesis.getGoal(), thesis.getTasks(), thesis.getStudent(), thesis.getSupervisor(), thesis.getAccStatus());
		return "redirect:/itf-table/review";
	}
	
	//TODO delete-thesis get
	@GetMapping("/itf-table/delete/{idthesis}")
	public String showDeleteThesis(@PathVariable("idthesis") long idthesis, Model model) {
		Thesis thesis = thesisService.getThesisById(idthesis);
		model.addAttribute("thesis", thesis);
		return "thesis-delete-page";
	}
	
	//TODO delete-thesis post
	@PostMapping("/itf-table/delete/{idthesis}")
	public String deleteThesis(@PathVariable("idthesis") long idthesis) throws Exception {
		thesisService.deleteThesis(idthesis);
		return "redirect:/itf-table/review";
	}
	
	//
	//TODO add-comment get
		@GetMapping("/itf-table/create-coments")
		public String showCreateComment(Model model) {
			model.addAttribute("comment", new Comment());
			return "thesis-edit-page";
		}
		
	//TODO add-comment post
		@PostMapping("/itf-table/create-coments/{personelId}/{thesisId}")
		public String сreateComment(Comment comment, @PathVariable("personelId") long personelId, @PathVariable("thesisId")long thesisId) {
			commentService.createComment(comment.getDescription(), personelId, thesisId); //TODO ???? kā te rīkoties??
			return "redirect:/itf-table/review";
		}
		
	//TODO edit-comment get
		@GetMapping("/itf-table/edit-coment/{idcom}")
		public String showEditComment(@PathVariable("idcom") long idcom, Model model) {
			Comment comment = commentService.getCommentById(idcom);
			model.addAttribute("comment", comment);
			return "thesis-edit-page";
		}
		
	//TODO edit-comment post
		@PostMapping("/itf-table/edit-coment/{idcom}")
		public String editComment(@PathVariable("idcom") long idcom, @ModelAttribute("comment") Comment comment) throws Exception {
			commentService.updateComment(idcom, comment.getDescription());
			return "redirect:/itf-table/review";
		}
	//TODO delete-comment get
		@GetMapping("/itf-table/delete-coments/{idcom}")
		public String showDeleteComment(@PathVariable("idcom") long idcom, Model model) {
			Comment comment = commentService.getCommentById(idcom);
			model.addAttribute("comment", comment);
			return "thesis-edit-page";
		}
		
	//TODO delete-comment post
		@PostMapping("/itf-table/delete-coments/{idcom}")
		public String deleteComment(@PathVariable("idcom") long idcom) throws Exception {
			commentService.deleteComment(idcom);
			return "redirect:/itf-table/review";
		}
}
