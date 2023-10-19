package lv.venta.models;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

import org.junit.jupiter.api.Test;

class DocumentsTest {

	Documents doc1 = new Documents();
	Documents doc2 = new Documents("Dokuments", new File("document"));
	Documents doc3 = new Documents("123", new File(""));
	Documents doc4 = new Documents(null, null);
	
	@Test
	public void testDefaultDocuments () {
		assertEquals(0, doc1.getIddocument());
		assertEquals("", doc1.getDocumentName());
		assertEquals(null, doc1.getFile());
	}
	
	@Test
	public void testGoodDocuments () {
		assertEquals(0, doc2.getIddocument());
		assertEquals("Dokuments", doc2.getDocumentName());
		assertEquals(new File("document"), doc2.getFile());
	}
	
	@Test
	public void testBadDocuments () {
		assertEquals(0, doc3.getIddocument());
		assertEquals("123", doc3.getDocumentName());
		//assertEquals("", doc3.getFile());
	}
	
	

}
