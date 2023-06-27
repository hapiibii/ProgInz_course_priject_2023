package lv.venta.services;

import java.util.List;

import lv.venta.models.AcceptanceStatus;
import lv.venta.models.Thesis;
import lv.venta.models.users.AcademicPersonel;
import lv.venta.models.users.Student;

public interface IThesisService {

	void createThesis(Thesis thesis);
	void deleteThesis(long idthesis) throws Exception;
	void updateThesis(long idthesis, String titleEn, String titleLv, String goal, String tasks, Student student,
			AcademicPersonel supervisor, AcceptanceStatus status) throws Exception;
	List<Thesis> getAllThesises();
	
	//TODO pievienot funkcijas prieks Repo funkcijam
	
}
