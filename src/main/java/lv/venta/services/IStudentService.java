package lv.venta.services;

import java.util.List;

import lv.venta.models.users.Role;
import lv.venta.models.users.Student;
import lv.venta.models.users.User;

public interface IStudentService {
	void createStudent(Student student);
	void updateStudentWithPersoncode(long idperson, String name, String surname, String personCode, Role role, User user, String matriculaNo, boolean financialDebt) throws Exception;
	void updateStudentWithoutPersoncode(long idperson, String name, String surname, Role role, User user, String matriculaNo, boolean financialDebt) throws Exception;
	void deleteStudent(long idperson) throws Exception;
	List<Student> getAllStudent();
}
