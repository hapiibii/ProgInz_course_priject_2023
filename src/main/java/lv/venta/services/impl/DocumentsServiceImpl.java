package lv.venta.services.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import java.util.Optional;



import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lv.venta.models.Documents;
import lv.venta.repos.IDocumentsRepo;
import lv.venta.services.IDocumentsService;

@Service
public class DocumentsServiceImpl implements IDocumentsService {

	

	@Autowired
	private IDocumentsRepo documentsRepo;

	// atgriež visus dokumentus
	@Override
	public List<Documents> retrieveAllDocuments() {
		return (List<Documents>) documentsRepo.findAll();
	}

	// atgriež dokumentu pēc id
	@Override
	public Documents retrieveDocumentById(long iddocument) throws Exception {
		Optional<Documents> optionalDoc = documentsRepo.findById(iddocument);
		if (!optionalDoc.isPresent()) {
		    throw new Exception("Wrong id!");
		}
		return optionalDoc.get();
	}

	// atgriež dokumentu pēc nosaukuma
	@Override
	public ArrayList<Documents> retrieveDocumentByDocumentName(String documentName) throws Exception {
		if (documentName != null) {
			ArrayList<Documents> allDocumentsWithDocumentName = new ArrayList<>();
			for (Documents temp : documentsRepo.findAll()) {
				if (temp.getDocumentName().equals(documentName)) {
					allDocumentsWithDocumentName.add(temp);
				}
			}
			return allDocumentsWithDocumentName;
		} else {
			throw new Exception("Documents name don`t exist!");
		}
	}

	// dzēš dokumentu pēc id
	@Override
	public void deleteDocumentByDocumetId(long iddocument) throws Exception {
		boolean isFound = false;
		for (Documents temp : documentsRepo.findAll()) {
			if (temp.getIddocument() == iddocument) {
				documentsRepo.delete(temp);
				isFound = true;
				break;
			}
		}
		if (!isFound) {
			throw new Exception("Wrong ID!");
		}
	}

	// atjauno dokumentu
	@Override
	public Documents updateDocument(long iddocument, String documentName, File file) throws Exception {
		for (Documents temp : documentsRepo.findAll()) {
			if (temp.getIddocument() == iddocument) {
				temp.setDocumentName(documentName);
				temp.setFile(file);
				return temp;
			}
		}
		throw new Exception("Wrong ID!");
	}

	// ievietošana
	@Override
	public Documents insertDocument(String documentName, File file) {
		Documents newDocument = new Documents(documentName, file);
		documentsRepo.save(newDocument);
		return newDocument;
	}
	
	@Override
	public byte[] downloadDocument(long iddocument) {
	    try {
	        // Iegūstiet dokumentu no datu bāzes
	        Documents document = retrieveDocumentById(iddocument);
	        
	        if(document.getFile() != null) {
	            Path path = document.getFile().toPath();
	            return Files.readAllBytes(path);
	        }
	        return null;
	    } catch (Exception e) {
	        e.printStackTrace();
	        return null;
	    }
	}

}
