/*
package repos;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;

import lv.venta.models.Degree;
import lv.venta.models.Faculty;
import lv.venta.models.StudioProgramm;
import lv.venta.repos.IStudioProgrammRepo;

@DataJdbcTest
class IStudioProgrammRepoTest {

	StudioProgramm stp2 = new StudioProgramm(Faculty.EPK, Degree.BSC, "Testa sudiju programma");
	
	@Autowired
	IStudioProgrammRepo studioProgRepo;
	
	@Test
	void testInsertStudioProduct() {
		studioProgRepo.save(stp2);
		
		StudioProgramm studioProgFromDB = studioProgRepo.findByTitle("Testa sudiju programma");
		assertNotNull(studioProgFromDB);
	}
}
*/