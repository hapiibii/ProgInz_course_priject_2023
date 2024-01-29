/*
package models;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import lv.venta.models.Degree;
import lv.venta.models.Faculty;
import lv.venta.models.StudioProgramm;

class StudioProgrammValidation {

	StudioProgramm stp2 = new StudioProgramm(Faculty.EPK, Degree.BSC, "Testa sudiju programma");
	StudioProgramm stp3 = new StudioProgramm(null, null, "22222");

	ValidatorFactory validFactory = Validation.buildDefaultValidatorFactory();
	Validator validor = validFactory.getValidator();
	
	@Test
	public void testGoodStudioProgramm() {
		Set<ConstraintViolation<StudioProgramm>> violations = validor.validate(stp2);
		assertEquals(0, violations.size());
	} 
	
	@Test
	public void testBadStudioProgramm() {
		Set<ConstraintViolation<StudioProgramm>> violations = validor.validate(stp3);
		assertEquals(0, violations.size());
	}

}
*/