package lv.venta.models;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.Set;

import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

class DocumentsValidationTest {

	Documents doc2 = new Documents("Dokuments", new File("document"));
	Documents doc3 = new Documents("123", new File(""));
	
	ValidatorFactory validFactory = Validation.buildDefaultValidatorFactory();
	Validator validor = validFactory.getValidator();
	
	@Test
	void testGoodDocuments () {
		Set<ConstraintViolation<Documents>> violation = validor.validate(doc2);
		assertEquals(0, violation.size());
		
	}
	
	@Test
	void testBadDocuments() {
		Set<ConstraintViolation<Documents>> violation = validor.validate(doc3);
		assertEquals(1, violation.size());
		
	}

}
