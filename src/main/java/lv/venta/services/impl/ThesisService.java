package lv.venta.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lv.venta.models.AcceptanceStatus;
import lv.venta.models.Thesis;
import lv.venta.models.users.AcademicPersonel;
import lv.venta.models.users.Student;
import lv.venta.repos.IThesisRepo;
import lv.venta.services.IThesisService;

@Service
public class ThesisService implements IThesisService {

	private final IThesisRepo thesisRepo;
	
	@Autowired
	public ThesisService(IThesisRepo thesisRepo) {
		this.thesisRepo = thesisRepo;
	}
	
	@Override
	public void createThesis(Thesis thesis) {
		thesisRepo.save(thesis);
	}

	@Override
	public void deleteThesisById(long idthesis) {
		Optional<Thesis> thesis = thesisRepo.findById(idthesis);
	    if (thesis.isPresent()) {
	        thesisRepo.delete(thesis.get());
	    } else {
	        throw new IllegalArgumentException("Thesis with such ID can not be found.");
	    }
		
	}

	@Override
	public void updateThesis(long idthesis, String titleEn, String titleLv, String goal, String tasks, Student student,
			AcademicPersonel supervisor, AcceptanceStatus status, AcademicPersonel reviewer) throws Exception {
		Thesis existingThesis = thesisRepo.findById(idthesis).orElse(null);
		
		if(existingThesis != null) {
			if(titleEn != null) {
				existingThesis.setTitleEn(titleEn);
			}
			
			if(titleLv != null) {
				existingThesis.setTitleLv(titleLv);
			}
			
			if(goal != null) {
				existingThesis.setGoal(goal);
			}
			
			if(tasks != null) {
				existingThesis.setTasks(tasks);
			}
			
			if(student != null) {
				existingThesis.setStudent(student);
			}
			
			if(supervisor != null) {
				existingThesis.setSupervisor(supervisor);
			}
			
			if(status != null) {
				existingThesis.setAccStatus(status);
			}
			if(reviewer != null) {
				existingThesis.addReviewer(reviewer);
			}
			thesisRepo.save(existingThesis);
		}
		else {
			throw new Exception("Thesis has not be found.");
		}
	}

	@Override
	public List<Thesis> getAllThesises() {
		return (List<Thesis>) thesisRepo.findAll();
	}

	@Override
	public List<Thesis> getThesisesByAcceptanceStatus(AcceptanceStatus accStatus) {
		
		return thesisRepo.findByAccStatus(accStatus);
	}

	@Override
	public List<Thesis> getThesisesBySupervisorStatus(boolean statusFromSupervisor) {
		
		return thesisRepo.findByStatusFromSupervisor(statusFromSupervisor);
	}

	@Override
	public Thesis getThesisById(long thesisId) {
		 return thesisRepo.findById(thesisId).orElse(null);
	}
	

}
