package lv.venta.repos;


import java.io.File;
import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;

import lv.venta.models.Documents;

public interface IDocumentsRepo extends CrudRepository<Documents, Long>{

	ArrayList<Documents> findByDocumentNameAndFile(String documentName, File file);
}
