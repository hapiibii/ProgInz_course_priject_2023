package lv.venta.services;

import java.util.List;
import java.util.Optional;

import lv.venta.models.Degree;
import lv.venta.models.Faculty;
import lv.venta.models.StudioProgramm;

public interface IStudioProgrammService {

	public Iterable<StudioProgramm> findAll();
	public Optional<StudioProgramm> findById(Long id);
	public StudioProgramm save(StudioProgramm programm);
	public void delete(StudioProgramm programm);
	
	/*
	void createStudioProgramm(StudioProgramm programm);
	void updateStudioProgramm(long idstprog, Faculty faculty, Degree degree, String title) throws Exception;
	void deleteStudioProgramm(long idstprog) throws Exception;
	List<StudioProgramm> getAllStudioProgramms();
	StudioProgramm getStudioProgrammById(long idstprog);
	StudioProgramm getStudioProgrammByTitle(String title);*/
}
