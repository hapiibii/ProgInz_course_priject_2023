package lv.venta.services.impl;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lv.venta.models.Submission;
import lv.venta.repos.ISubmissionRepo;
import lv.venta.services.ISubmissionService;

@Service
public class SubmissionServiceImpl implements ISubmissionService{
	
	

	@Autowired
	private ISubmissionRepo submissionRepo;
	
	// atgriež visus iesniegumus
	@Override
	public List<Submission> retrieveAllSubmisions (){
		return (List<Submission>) submissionRepo.findAll();
	}
	
	// atgriež iesniegumu pēc iesnieguma id
	@Override
	public Submission retrieveSubmissionById (long idsubmission) throws Exception {
		for (Submission temp : submissionRepo.findAll()) {
			if(temp.getIdsubmission() == idsubmission) {
				return temp;
			}
		}
		throw new Exception("Wrong id!");
	}
	
	// dzēš iesniegumu pēc id
	@Override
	public void deleteSubmissionById (long idsubmission) throws Exception {
		boolean isFound = false;
		for (Submission temp : submissionRepo.findAll()) {
			if (temp.getIdsubmission() == idsubmission) {
				submissionRepo.delete(temp);
				isFound = true;
				break;
			}
		}
		if (!isFound) {
			throw new Exception("Wrong id!");
		}
	}
	
	// filtrē pēc datuma
	@Override
	public ArrayList<Submission> filterByDate (LocalDateTime submissionDate) {
		ArrayList<Submission> filteredSubmissions = new ArrayList<>();
		for (Submission temp : submissionRepo.findAll()) {
			LocalDateTime date = temp.getSubmissionDate();
			if (date.equals(submissionDate)) {
				filteredSubmissions.add(temp);
			}
		}
		return filteredSubmissions;
	}
	
	@Override
	public Submission updateSubmission (long idsubmission, LocalDateTime submissionDate, File file) throws Exception {
		Submission submissionToUpdate = retrieveSubmissionById(idsubmission);
		if (submissionToUpdate != null) {
			submissionToUpdate.setSubmissionDate(submissionDate);
			submissionToUpdate.setFile(file);
			return submissionToUpdate;
		}
		throw new Exception("Submission with this ID don`t exist!");
	}
	
	// ievietošana
	@Override
	public Submission insertSubmission (LocalDateTime submissionDate, File file) {
		for (Submission temp : submissionRepo.findAll()) {
			if (temp.getSubmissionDate().equals(submissionDate)) {
				temp.setFile(file);
				return temp;
			}
		}
		Submission newSubmission = new Submission(submissionDate, file);
		submissionRepo.save(newSubmission);
		return newSubmission;
	}
	
}
