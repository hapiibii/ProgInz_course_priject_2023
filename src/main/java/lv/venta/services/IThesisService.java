package lv.venta.services;

import java.util.List;

import lv.venta.models.AcceptanceStatus;
import lv.venta.models.Thesis;
import lv.venta.models.users.AcademicPersonel;
import lv.venta.models.users.Student;

public interface IThesisService {

	void createThesis(Thesis thesis);
	void deleteThesisById(long idthesis);
	void updateThesis(long idthesis, String titleEn, String titleLv, String goal, String tasks, Student student,
			AcademicPersonel supervisor, AcceptanceStatus accStatus) throws Exception;
	List<Thesis> getAllThesises();
	
	
	List<Thesis> getThesisesByAcceptanceStatus(AcceptanceStatus accStatus);
	
	List<Thesis> getThesisesBySupervisorStatus(boolean statusFromSupervisor);
	
	Thesis getThesisById(long thesisId);
	void updateThesis(long idthesis, String titleEn, String titleLv, String goal, String tasks, Student student,
			AcademicPersonel supervisor, AcceptanceStatus status, AcademicPersonel reviewer) throws Exception;
	
}