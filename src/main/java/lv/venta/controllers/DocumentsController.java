package lv.venta.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
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
	
	@GetMapping("/document-page")
    public String showDocumentPage(Model model) {
		
        List<Documents> allDocuments = documentsService.retrieveAllDocuments();
        
        model.addAttribute("myAllDocuments", allDocuments);
        return "document-page"; 
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

				return "redirect:/document/showAll"; //tiks izsaukts -> localhost:8080/document/dokumeta nosaukums

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
	public String insertDocumentGetFunction (Model model) {
		//Documents document = new Documents();
		model.addAttribute("document", new Documents());
		return "document-insert-page";
	}
	
	
	
	@PostMapping("/insert")
	public String insertDocumentPostFunction (@ModelAttribute("document") Documents document, BindingResult result, @RequestParam("file") MultipartFile file) {
		//if (!result.hasErrors()) {
			try {
				if (!file.isEmpty() && file != null) {
					//Iegūst faila nosaukumu un saglabāšanas ceļu
					String fileName = file.getOriginalFilename();
					String filePath = fileName;
					//Saglabā failu vietējā failu sistēmā
					file.transferTo(new File(filePath));
					//Iesaista faila objektu ievades parametrā
					document.setFile(new File(filePath));
				}
				documentsService.insertDocument(document.getDocumentName(), document.getFile());
				return "redirect:/document-page";
			}
			catch (Exception e) {
				e.printStackTrace();
				return "error-page";
			}
		
	}
	
	
	@GetMapping("/download/{iddocument}")
    public ResponseEntity<byte[]> downloadDocument(@PathVariable("iddocument") Long iddocument) throws Exception {
        // Atrodam dokumentu no datu bāzes
        Documents doc = documentsService.retrieveDocumentById(iddocument);

        if (doc == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        byte[] fileData = Files.readAllBytes(doc.getFile().toPath());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", doc.getDocumentName() + ".pdf"); // pieņemot, ka tā ir PDF datne
        headers.setContentLength(fileData.length);

        return new ResponseEntity<>(fileData, headers, HttpStatus.OK);
    }
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
