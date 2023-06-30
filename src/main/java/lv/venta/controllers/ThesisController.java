package lv.venta.controllers;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lv.venta.models.AcceptanceStatus;
import lv.venta.models.Comment;
import lv.venta.models.Thesis;
import lv.venta.models.users.AcademicPersonel;
import lv.venta.models.users.Student;
import lv.venta.services.IAcademicPersonelService;
import lv.venta.services.ICommentService;
import lv.venta.services.IStudentService;
import lv.venta.services.IThesisService;
import lv.venta.services.IUserService;

@Controller
@RequestMapping("/itftable-page")
public class ThesisController {
	
	private final IThesisService thesisService;
	private final ICommentService commentService;
	private final IStudentService studentService;
	private final IAcademicPersonelService personelService;
	
	@Autowired
	public ThesisController (IThesisService thesisService, ICommentService commentService, IStudentService studentService, IAcademicPersonelService personelService) {
		this.thesisService = thesisService;
		this.commentService = commentService;
		this.studentService = studentService;
		this.personelService = personelService;
	}
	
	//TODO all-thesises get
	@GetMapping("/review")
	public String showAllThesises(Model model) {
		List<Thesis> allThesises = thesisService.getAllThesises();
		model.addAttribute("allThesises", allThesises);
		return "thesis-view-page";
	}
	
	//TODO create-thesis get
	@GetMapping("/create")
	public String showCreateThesis(Model model) {
		List<Student> students = studentService.getAllStudent();
        List<AcademicPersonel> supervisors = personelService.getAllAcademicPersonel();
        List<AcademicPersonel> reviewers = personelService.getAllAcademicPersonel();
		model.addAttribute("thesis", new Thesis());
		model.addAttribute("students", students);
        model.addAttribute("supervisors", supervisors);
        model.addAttribute("reviewers", reviewers);
		return "thesis-create-page";
	}
	
	//TODO create-thesis post
	@PostMapping("/create")
	public String сreateThesis(@ModelAttribute("thesis") Thesis thesis) {
		thesis.setAccStatus(AcceptanceStatus.submited);
		thesis.setSubmitDateTime(LocalDateTime.now());
		thesisService.createThesis(thesis);
		return "redirect:/itftable-page/review";
	}
	
	//TODO edit-thesis get
	@GetMapping("/edit/{idthesis}")
	public String showEditThesis(@PathVariable("idthesis") long idthesis, Model model) {
		Thesis thesis = thesisService.getThesisById(idthesis);
		model.addAttribute("thesis", thesis);
		return "thesis-edit-page";
	}
	
	//TODO edit-thesis post
	@PostMapping("/edit/{idthesis}")
	public String editThesis(@PathVariable("idthesis") long idthesis, @ModelAttribute("thesis") Thesis thesis) throws Exception {
		thesisService.updateThesis(idthesis, thesis.getTitleEn(), thesis.getTitleLv(), thesis.getGoal(), thesis.getTasks(), thesis.getStudent(), thesis.getSupervisor(), thesis.getAccStatus());
		return "redirect:/itftable-page/review";
	}
	
	//TODO delete-thesis get
	@GetMapping("/delete/{idthesis}")
	public String showDeleteThesis(@PathVariable("idthesis") long idthesis, Model model) {
		Thesis thesis = thesisService.getThesisById(idthesis);
		model.addAttribute("thesis", thesis);
		return "thesis-delete-page";
	}
	
	//TODO delete-thesis post
	@PostMapping("/delete/{idthesis}")
	public String deleteThesis(@PathVariable("idthesis") long idthesis) throws Exception {
		thesisService.deleteThesis(idthesis);
		return "redirect:/itftable-page/review";
	}
	
	//
	//TODO add-comment get
		@GetMapping("/create-coments")
		public String showCreateComment(Model model) {
			model.addAttribute("comment", new Comment());
			return "thesis-edit-page";
		}
		
	//TODO add-comment post
		@PostMapping("/create-coments/{personelId}/{thesisId}")
		public String сreateComment(Comment comment, @PathVariable("personelId") long personelId, @PathVariable("thesisId")long thesisId) {
			commentService.createComment(comment.getDescription(), personelId, thesisId); //TODO ???? kā te rīkoties??
			return "redirect:/itftable-page/review";
		}
		
	//TODO edit-comment get
		@GetMapping("/edit-coment/{idcom}")
		public String showEditComment(@PathVariable("idcom") long idcom, Model model) {
			Comment comment = commentService.getCommentById(idcom);
			model.addAttribute("comment", comment);
			return "thesis-edit-page";
		}
		
	//TODO edit-comment post
		@PostMapping("/edit-coment/{idcom}")
		public String editComment(@PathVariable("idcom") long idcom, @ModelAttribute("comment") Comment comment) throws Exception {
			commentService.updateComment(idcom, comment.getDescription());
			return "redirect:/itftable-page/review";
		}
	//TODO delete-comment get
		@GetMapping("/delete-coments/{idcom}")
		public String showDeleteComment(@PathVariable("idcom") long idcom, Model model) {
			Comment comment = commentService.getCommentById(idcom);
			model.addAttribute("comment", comment);
			return "thesis-edit-page";
		}
		
	//TODO delete-comment post
		@PostMapping("/delete-coments/{idcom}")
		public String deleteComment(@PathVariable("idcom") long idcom) throws Exception {
			commentService.deleteComment(idcom);
			return "redirect:/itftable-page/review";
		}
}
