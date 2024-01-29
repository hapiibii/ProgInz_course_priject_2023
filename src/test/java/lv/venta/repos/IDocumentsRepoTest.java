/*
package lv.venta.repos;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import lv.venta.models.Documents;

@DataJpaTest
class IDocumentsRepoTest {

	Documents doc2 = new Documents("Dokuments", new File("document"));
	
	@Autowired
	IDocumentsRepo documentRepo;
	
	@Test
	void testInsertDocuments() {
		documentRepo.save(doc2);
		
		ArrayList<Documents> docFromDB = documentRepo.findByDocumentNameAndFile("Dokuments", new File("document"));
		assertNotNull(docFromDB);
	}
	

}
*/