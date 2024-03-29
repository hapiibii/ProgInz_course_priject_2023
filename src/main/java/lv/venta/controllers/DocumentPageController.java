package lv.venta.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
	public String showDocumentPage (Model model) throws IOException {
		List<Documents> allDocuments = documentService.retrieveAllDocuments();
        model.addAttribute("myAllDoc", allDocuments);
        /*
        String transText = Translate.translate("lv", "en", "Dokumenti");
        String transIesniegsana = Translate.translate("lv", "en", "Iesniegšana");
        String transAizstavesana = Translate.translate("lv", "en", "Aizstāvēšana");
        String transITFDome = Translate.translate("lv", "en", "ITF Dome");
        String transDarbuIzstrade = Translate.translate("lv", "en", "Darbu izstrāde");
        String transKalendaraisGrafiks = Translate.translate("lv", "en", "Kalendārais grafiks");
        String transArhivs = Translate.translate("lv", "en", "Arhīvs");
        
        String res = transText.split("translatedText")[1].trim().split("\"")[2];
        String res2 = transIesniegsana.split("translatedText")[1].trim().split("\"")[2];
        String res3 = transAizstavesana.split("translatedText")[1].trim().split("\"")[2];
        String res4 = transITFDome.split("translatedText")[1].trim().split("\"")[2];
        String res5 = transDarbuIzstrade.split("translatedText")[1].trim().split("\"")[2];
        String res6 = transKalendaraisGrafiks.split("translatedText")[1].trim().split("\"")[2];
        String res7 = transArhivs.split("translatedText")[1].trim().split("\"")[2];
        
        model.addAttribute("TranslateDocuments",res);
        model.addAttribute("TranslateIesnieg",res2);
        model.addAttribute("TranslateAiztavesana",res3);
        model.addAttribute("TranslateITFDome", res4);
        model.addAttribute("TranslateDarbuIzstrade", res5);
        model.addAttribute("TranslateKalendaraisGrafiks", res6);
        model.addAttribute("TranslateArhivs", res7);
        */
        return "document-page";
	}
	
	
	@GetMapping("/all-documents")
	public String showAllDocuments (Model model) {
		List<Documents> allDocuments = documentService.retrieveAllDocuments();
		model.addAttribute("allDocuemnts", allDocuments);
		return "all-documents-page";
	}
	
	@GetMapping("/create-documents")
	public String showCreate (Model model) {
		model.addAttribute("document", new Documents());
		return "document-insert-page";
	}
	
	@PostMapping("/create-documents")
	public String createDocuments (@ModelAttribute("document") Documents documents) {
		documentService.insertDocument(documents.getDocumentName(), documents.getFile());
		return "redirect:/document-page";
		
	}
	
	
	@GetMapping("/update-document/{iddocument}")
	public String updateDocumentGetFunc (@PathVariable("iddocument") long iddocument, Model model) throws Exception {
		Documents documents = documentService.retrieveDocumentById(iddocument);
		model.addAttribute("document", documents);
		return "document-update-page";
	}
	
	@PostMapping("/update-document/{iddocument}")
	public String updateDocumentPostFunc (@PathVariable("iddocument") long iddocument, @ModelAttribute("documents") Documents documents ) throws Exception {
		documentService.updateDocument(iddocument, documents.getDocumentName(), documents.getFile());
		return "redirect:/all-documents";
	}
	/*
	@GetMapping("/delete-document/{iddocument}")
	public String deleteDocumentGetFunc (@PathVariable("iddocument") long iddocument, Model model) {
		Documents documents = documentService.deleteDocumentByDocumetId(iddocument);
		model.addAttribute("documents", documents);
		return "document-delete-confirm";
	}
	*/
	
	
}
