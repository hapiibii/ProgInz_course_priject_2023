package lv.venta.services;

import java.util.List;

import lv.venta.models.Degree;
import lv.venta.models.Faculty;
import lv.venta.models.StudioProgramm;

public interface IStudioProgrammService {

	void createStudioProgramm(StudioProgramm programm);
	void updateStudioProgramm(long idstprog, Faculty faculty, Degree degree, String title) throws Exception;
	void deleteStudioProgramm(long idstprog) throws Exception;
	List<StudioProgramm> getAllStudioProgramms();
	StudioProgramm getStudioProgrammById(long idstprog);
	StudioProgramm getStudioProgrammByTitle(String title);
}
