package lv.venta.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
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
					String filePath = "/" + fileName;
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
	
	/*
	@GetMapping("/download/{iddocument}")
	public ResponseEntity<byte[]> downloadDocumentById(@PathVariable long iddocument) {
	    try {
	        Documents document = documentsService.retrieveDocumentById(iddocument);

	        if (document == null) {
	            return ResponseEntity.notFound().build();
	        }

	        // Iegūst faila nosaukumu un MIME tipu no dokumenta
	        String fileName = document.getFile().getName();
	        String mimeType = "application/octet-stream"; // Jūsu konkrētais MIME tips

	        byte[] fileData = Files.readAllBytes(document.getFile().toPath());

	        return ResponseEntity.ok()
	                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName)
	                .contentType(MediaType.parseMediaType(mimeType))
	                .body(fileData);
	    } catch (Exception e) {
	        return ResponseEntity.badRequest().build(); // Vai jebkura cita piemērota atbilde
	    }
	}
	*/
	
	
	@RequestMapping(value = "/download/{iddocument}", method = RequestMethod.GET)
    public void downloadDocumentByDocumentsName(@PathVariable("iddocument") long iddocument, HttpServletResponse response) {
        try {
            Documents document = documentsService.retrieveDocumentById(iddocument);

            File file = document.getFile();

            // Iegūst faila nosaukumu no dokumenta
            String fileName = file.getName();

            // Nosūta atbildi 
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
            response.setHeader("Content-Type", "application/octet-stream");
            response.setHeader("Content-Length", String.valueOf(file.length()));

            // Izveido ievades un izvades straumes
            FileInputStream inputStream = new FileInputStream(file);
            ServletOutputStream outputStream = response.getOutputStream();

            // Nokopē faila saturu no ievades uz izvades straumi
            byte[] buffer = new byte[4096];
            int bytesRead = -1;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            // Attālina straumes
            inputStream.close();
            outputStream.close();
        } catch (Exception e) {
        	e.printStackTrace();
        }
    }
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
