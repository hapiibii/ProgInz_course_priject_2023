package lv.venta.service;

import java.io.File;
import java.util.ArrayList;

import lv.venta.models.Documents;

public interface IDocumentsService {

	ArrayList<Documents> retrieveAllDocuments();

	Documents retrieveDocumentById(long iddocument) throws Exception;

	ArrayList<Documents> retrieveDocumentByDocumentName(String documentName) throws Exception;

	Documents insertDocument(String documentName, File file);

	void deleteDocumentByDocumetId(long iddocument) throws Exception;

	Documents updateDocument(long iddocument, String documentName, File file) throws Exception;

}
