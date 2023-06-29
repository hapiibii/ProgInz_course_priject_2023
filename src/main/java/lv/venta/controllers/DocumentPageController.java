package lv.venta.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lv.venta.models.Documents;
import lv.venta.services.IDocumentsService;

@Controller
@RequestMapping("/document-page")
public class DocumentPageController {

	private final IDocumentsService documentService;
	
	@Autowired
	public DocumentPageController (IDocumentsService documentService) {
		this.documentService = documentService;
	}
	
	@GetMapping
	public String showDocumentPage (Model model) {
		List<Documents> allDocuments = documentService.retrieveAllDocuments();
        model.addAttribute("myAllDoc", allDocuments);
        
        return "document-page";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
