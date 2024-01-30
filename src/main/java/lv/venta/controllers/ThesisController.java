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

import lv.venta.models.AcceptanceStatus;
import lv.venta.models.Comment;
import lv.venta.models.Thesis;
import lv.venta.models.users.AcademicPersonel;
import lv.venta.models.users.Student;
import lv.venta.services.IAcademicPersonelService;
import lv.venta.services.ICommentService;
import lv.venta.services.IStudentService;
import lv.venta.services.IThesisService;

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
	
	//all-thesises get
	@GetMapping("/review")
	public String showAllThesises(Model model) {
		List<Thesis> allThesises = thesisService.getAllThesises();
		model.addAttribute("allThesises", allThesises);
		return "thesis-view-page";
	}
	
	//create-thesis get
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
	
	//create-thesis post
	@PostMapping("/create")
	public String сreateThesis(   Thesis thesis) {
		thesis.setAccStatus(AcceptanceStatus.submited);
		thesis.setSubmitDateTime(LocalDateTime.now());
		thesisService.createThesis(thesis);
		return "redirect:/itftable-page/review";
	}
	
	//edit-thesis get
	//add comment section for edit, so edit-comment will be as well here. If it is not possible, add a button to every thesis with "edit comment"
	@GetMapping("/edit/{idthesis}")
	public String showEditThesis(@PathVariable("idthesis") long idthesis, Model model) {
		Thesis thesis = thesisService.getThesisById(idthesis);
		List<Student> students = studentService.getAllStudent();
        List<AcademicPersonel> supervisors = personelService.getAllAcademicPersonel();
        List<AcademicPersonel> reviewers = personelService.getAllAcademicPersonel();
		model.addAttribute("thesis", thesis);
		model.addAttribute("students", students);
        model.addAttribute("supervisors", supervisors);
        model.addAttribute("reviewers", reviewers);
		return "thesis-edit-page";
	}
	
	//edit-thesis post
	@PostMapping("/edit/{idthesis}")
	public String editThesis(@PathVariable("idthesis") long idthesis, @ModelAttribute("thesis") Thesis thesis) throws Exception {
		thesisService.updateThesis(idthesis, thesis.getTitleEn(), thesis.getTitleLv(), thesis.getGoal(), thesis.getTasks(), thesis.getStudent(), thesis.getSupervisor(), thesis.getAccStatus());
		return "redirect:/itftable-page/review";
	}
	
	//delete-thesis get
	//delete thesis-delete-page
	//can try one day to 
	@GetMapping("/delete/{idthesis}")
	public String showDeleteThesisById(@PathVariable("idthesis") long idthesis, Model model) {
		try {
			thesisService.deleteThesisById(idthesis);
			model.addAttribute("thesis", thesisService.getAllThesises());
			return "redirect:/itftable-page/review";
		}
		catch (Exception e) {
			return "error-page";
		}
	}
	
	//delete-thesis post
	/*@PostMapping("/delete/{idthesis}")
	public String deleteThesisById(@PathVariable long idthesis) {
		thesisService.deleteThesisById(idthesis);
		return "redirect:/itftable-page/review";
	}*/
	
	//
	//add-comment get
		@GetMapping("/create-comment")
		public String showCreateComment(Model model) {
			List<AcademicPersonel> supervisors = personelService.getAllAcademicPersonel();
	        List<Thesis> thesises = thesisService.getAllThesises();
			model.addAttribute("comment", new Comment());
			model.addAttribute("thesises", thesises);
	        model.addAttribute("supervisors", supervisors);
			return "comment-create-page";
		}
		
	//add-comment post
		@PostMapping("/create-comment")
		public String сreateComment(@ModelAttribute("comment") Comment comment) {
	        comment.setDate(LocalDateTime.now());
			commentService.createComment(comment); 
			return "redirect:/itftable-page/review";
		}
		
	//edit-comment get
		@GetMapping("/edit-comment/{idcom}")
		public String showEditComment(@PathVariable("idcom") long idcom, Model model) {
			Comment comment = commentService.getCommentById(idcom);
			//List<AcademicPersonel> supervisors = personelService.getAllAcademicPersonel();
	        //List<Thesis> thesises = thesisService.getAllThesises();
			model.addAttribute("comment", comment);
			//model.addAttribute("thesises", thesises);
	        //model.addAttribute("supervisors", supervisors);
			return "comment-edit-page";
		}
		
	//edit-comment post
		@PostMapping("/edit-comment/{idcom}")
		public String editComment(@PathVariable("idcom") long idcom, @ModelAttribute("comment") Comment comment) throws Exception {
			commentService.updateComment(idcom, comment.getDescription());
			return "redirect:/itftable-page/review";
		}
	//delete-comment get
		@GetMapping("/delete-comment/{idcom}")
		public String showDeleteComment(@PathVariable("idcom") long idcom, Model model) {
			try {
				commentService.deleteCommentById(idcom);
				model.addAttribute("comment", commentService.getAllComments());
				return "redirect:/itftable-page/review";
			}
			catch (Exception e) {
				return "error-page";
			}
		}
		
	//delete-comment post
		/*@PostMapping("/delete-comment/{idcom}")
		public String deleteComment(@PathVariable("idcom") long idcom) throws Exception {
			commentService.deleteComment(idcom);
			return "redirect:/itftable-page/review";
		}*/
}