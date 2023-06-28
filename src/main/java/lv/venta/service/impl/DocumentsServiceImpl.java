package lv.venta.service.impl;

import java.io.File;
import java.util.ArrayList;

import org.springframework.stereotype.Service;

import lv.venta.models.Documents;
import lv.venta.service.IDocumentsService;

@Service
public class DocumentsServiceImpl implements IDocumentsService{

	ArrayList<Documents> allDocuments = new ArrayList<>();
	
	// atgriež visus dokumentus
	@Override
	public ArrayList<Documents> retrieveAllDocuments () {
		return allDocuments;
	}
	
	// atgriež dokumentu pēc id
	@Override
	public Documents retrieveDocumentById (long iddocument) throws Exception {
		for (Documents temp : allDocuments) {
			if (temp.getIddocument() == iddocument) {
				return temp;
			}
		}
		throw new Exception("Wrong id!");
	}
	
	// atgriež dokumentu pēc nosaukuma
	@Override
	public ArrayList<Documents> retrieveDocumentByDocumentName (String documentName) throws Exception {
		if (documentName != null) {
			ArrayList<Documents> allDocumentsWithDocumentName = new ArrayList<>();
			for (Documents temp : allDocuments) {
				if (temp.getDocumentName().equals(documentName)) {
				}
			}
			return allDocumentsWithDocumentName;
		}
		else {
			throw new Exception("Documents name don`t exist!");
		}
	}
	
	// dzēš dokumentu pēc id
	@Override
	public void deleteDocumentByDocumetId (long iddocument) throws Exception { 
		boolean isFound = false;
		for (Documents temp : allDocuments) { 
			if (temp.getIddocument() == iddocument) {
				allDocuments.remove(temp);
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
	public Documents updateDocument (long iddocument, String documentName, File file) throws Exception {
		for (Documents temp : allDocuments) {
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
	public Documents insertDocument (String documentName, File file) {
		for (Documents temp : allDocuments) {
			if (temp.getDocumentName().equals(documentName)) {
				temp.setFile(file);
				return temp;
			}
		}
		Documents newDocument = new Documents(documentName, file);
		allDocuments.add(newDocument);
		return newDocument;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
