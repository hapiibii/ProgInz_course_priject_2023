package lv.venta.service;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;

import lv.venta.models.Submission;

public interface ISubmissionService {

	ArrayList<Submission> retrieveAllSubmisions();

	Submission retrieveSubmissionById(long idsubmission) throws Exception;

	void deleteSubmissionById(long idsubmission) throws Exception;

	ArrayList<Submission> filterByDate(LocalDateTime submissionDate);

	Submission updateSubmission(long idsubmission, LocalDateTime submissionDate, File file) throws Exception;

}
