package lv.venta.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lv.venta.models.Degree;
import lv.venta.models.Faculty;
import lv.venta.models.StudioProgramm;
import lv.venta.repos.IStudioProgrammRepo;
import lv.venta.services.IStudioProgrammService;

@Service
public class StudioProgrammService implements IStudioProgrammService {

	private final IStudioProgrammRepo studioProgrammRepo;
	
	@Autowired
	public StudioProgrammService(IStudioProgrammRepo studioProgrammRepo) {
		this.studioProgrammRepo = studioProgrammRepo;
	}
	@Override
	public void createStudioProgramm(StudioProgramm programm) {
		studioProgrammRepo.save(programm);
		
	}

	@Override
	public void updateStudioProgramm(long idstprog, Faculty faculty, Degree degree, String title) throws Exception {
		StudioProgramm existingProgramm = studioProgrammRepo.findById(idstprog).orElse(null);
		
		if(existingProgramm != null) {
			if(faculty != null) {
				existingProgramm.setFaculty(faculty);
			}
			if(degree != null) {
				existingProgramm.setDegree(degree);
			}
			if(title != null) {
				existingProgramm.setTitle(title);
			}
			studioProgrammRepo.save(existingProgramm);
		}
		else {
			throw new Exception("Studio Programm not found.");
		}
	}

	@Override
	public void deleteStudioProgramm(long idstprog) throws Exception {
		try {
			studioProgrammRepo.deleteById(idstprog);
		}
		catch (Exception e) {
			throw new Exception("Could not delete studio programm with this ID.", e);
		}
		
	}

	@Override
	public List<StudioProgramm> getAllStudioProgramms() {
		return (List<StudioProgramm>) studioProgrammRepo.findAll();
	}

	@Override
	public StudioProgramm getStudioProgrammById(long idstprog) {
		Optional<StudioProgramm> programmOptional = studioProgrammRepo.findById(idstprog);
		return programmOptional.orElse(null);
	}
	@Override
	public StudioProgramm getStudioProgrammByTitle(String title) {
		return studioProgrammRepo.findByTitle(title);
	}

}
