package lv.venta.controllers;

import java.io.File;
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
import lv.venta.models.Documents;
import lv.venta.services.impl.DocumentsServiceImpl;

@Controller
@RequestMapping("/document")
public class DocumentsController {
	
	@Autowired
	private DocumentsServiceImpl documentsService;
	
	@GetMapping("/showAll")
	public String showAllDocuments (Model model) {
		model.addAttribute("myAllDocuments", documentsService.retrieveAllDocuments());
		return "all-documents-page";
	}
	
	@GetMapping("/showAll/{iddocument}")
	public String showDocumentsById (@PathVariable long iddocument, Model model) {
		try {
			Documents documents = documentsService.retrieveDocumentById(iddocument);
			model.addAttribute("myAllDocuments", documents);
			return "one-document-page";
		}
		catch (Exception e) {
			return "error-page";
		}
	}
	
	@GetMapping("/showAll/{documentName}")
	public String showDocumentByDocumentName (@PathVariable("documentName") String documentName, Model model) {
		if (documentName != null) {
			try {
				ArrayList<Documents> temp = documentsService.retrieveDocumentByDocumentName(documentName);
				model.addAttribute("myAllDocuments", temp);
				return "all-documents-page";
			}
			catch (Exception e) {
				return "error-page";
			}
		}
		return "error-page";
	}
	
	@GetMapping("/delete/{iddocument}")
	public String deleteDocumentById (@PathVariable("iddocument") long iddocument, Model model) {
		try {
			documentsService.deleteDocumentByDocumetId(iddocument);
			model.addAttribute("myAllDocuments", documentsService.retrieveAllDocuments());
			return "all-documents-page";
		}
		catch (Exception e) {
			return "error-page";
		}
	}
	
	@GetMapping("/update/{iddocument}")
	public String updateDocumentGetFunction (@PathVariable("iddocument") long iddocument, Model model) {
		try {
			model.addAttribute("document", documentsService.retrieveDocumentById(iddocument));
			return "document-update-page";
		}
		catch (Exception e) {
			return "error-page";
		}
	}
	
	@PostMapping("/update/{iddocument}")
	public String updateDocumentPostFunction (@PathVariable("iddocument") long iddocument, @Valid Documents document, BindingResult result) {
		if (!result.hasErrors()) {
			try {
				Documents temp = documentsService.updateDocument(iddocument, document.getDocumentName(), document.getFile());
				return "redirect:/document/" + temp.getDocumentName(); //tiks izsaukts -> localhost:8080/document/dokumeta nosaukums
			}
			catch (Exception e) {
				return "redirect:/error";
			}
		}
		else {
			return "document-update-page";
		}
	}
	
	@GetMapping("/insert")
	public String insertDocumentGetFunction (Documents documents) {
		return "document-insert-page";
	}
	
	
	
	@PostMapping("/insert")
	public String insertDocumentPostFunction (@Valid Documents documents, BindingResult result, @RequestParam("file") MultipartFile file) {
		if (!result.hasErrors()) {
			try {
				if (!file.isEmpty()) {
					//Iegūst faila nosaukumu un saglabāšanas ceļu
					String fileName = file.getOriginalFilename();
					String filePath = "/path/to/save/directory" + fileName;
					//Saglabā failu vietējā failu sistēmā
					file.transferTo(new File(filePath));
					//Iesaista faila objektu ievades parametrā
					documents.setFile(new File(filePath));
				}
				documentsService.insertDocument(documents.getDocumentName(), documents.getFile());
				return "redirect:/showAll";
			}
			catch (Exception e) {
				return "error-page";
			}
		}
		else {
			return "document-insert-page";
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
