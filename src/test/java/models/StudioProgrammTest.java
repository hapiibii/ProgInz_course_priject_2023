/*
package models;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import lv.venta.models.Degree;
import lv.venta.models.Faculty;
import lv.venta.models.StudioProgramm;

class StudioProgrammTest {

	StudioProgramm stp1 = new StudioProgramm();
	StudioProgramm stp2 = new StudioProgramm(Faculty.EPK, Degree.BSC, "Testa sudiju programma");
	StudioProgramm stp3 = new StudioProgramm(null, null, "22222");

	@Test
	public void testDefaultStudioProgramm() {
		assertEquals(0, stp1.getIdstprog());
		assertEquals(Faculty.UNKNOWN, stp1.getFaculty());
		assertEquals(Degree.UNKNOWN, stp1.getDegree());
		assertEquals("Nav norādīts", stp1.getTitle());
	}
	
	@Test
	public void testGoodStudioProgramm() {
		assertEquals(0, stp2.getIdstprog());
		assertEquals(Faculty.EPK, stp2.getFaculty());
		assertEquals(Degree.BSC, stp2.getDegree());
		assertEquals("Testa sudiju programma", stp2.getTitle());
	}
	
	@Test
	public void testBadStudioProgramm() {
		assertEquals(0, stp3.getIdstprog());
		assertEquals(Faculty.UNKNOWN, stp3.getFaculty());
		assertEquals(Degree.UNKNOWN, stp3.getDegree());
		assertEquals("Nav norādīts", stp3.getTitle());
	}
}
*/
