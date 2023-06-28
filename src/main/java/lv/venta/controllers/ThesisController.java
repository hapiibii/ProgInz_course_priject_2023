package lv.venta.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import lv.venta.models.Thesis;
import lv.venta.services.IAcademicPersonelService;
import lv.venta.services.IStudentService;
import lv.venta.services.IThesisService;

@Controller
@RequestMapping("/itftable-page")
public class ThesisController {
	
	private final IThesisService thesisService;
	private final ICommentService commentService;
	private final IAcademicPersonelService academicPersonelService;
	private final IStudentService studentService;
	
	@Autowired
	public ThesisController (IThesisService thesisService, ICommentService commentService, 
			IAcademicPersonelService academicPersonelService, IStudentService studentService) {
		this.thesisService = thesisService;
		this.commentService = commentService;
		this.academicPersonelService = academicPersonelService;
		this.studentService = studentService;
	}
	
	//TODO all-thesises get
	public String showAllThesises(Model model) {
		List<Thesis> allThesises = thesisService.getAllThesises();
		model.addAttribute("allThesises", allThesises);
		return "thesis-view-page";
	}
	//TODO create-thesis get
	//TODO create-thesis post
	//TODO edit-thesis get
	//TODO edit-thesis post
	//TODO delete-thesis get
	//TODO delete-thesis post
	//TODO add-comment get
	//TODO add-comment post
	//TODO edit-comment get
	//TODO edit-comment post
	//TODO delete-comment get
	//TODO delete-comment post
}
