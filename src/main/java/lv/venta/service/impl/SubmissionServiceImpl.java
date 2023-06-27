package lv.venta.service.impl;

import java.io.File;
import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.ArrayList;

import org.springframework.cglib.core.Local;

import lv.venta.models.Submission;
import lv.venta.service.ISubmissionService;

public class SubmissionServiceImpl implements ISubmissionService{
	
	private ArrayList<Submission> allSubmissions = new ArrayList<>();
	
	// atgriež visus iesniegumus
	@Override
	public ArrayList<Submission> retrieveAllSubmisions (){
		return allSubmissions;
	}
	
	// atgriež iesniegumu pēc iesnieguma id
	@Override
	public Submission retrieveSubmissionById (long idsubmission) throws Exception {
		for (Submission temp : allSubmissions) {
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
		for (Submission temp : allSubmissions) {
			if (temp.getIdsubmission() == idsubmission) {
				allSubmissions.remove(temp);
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
		for (Submission temp : allSubmissions) {
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
	
	
	
}
