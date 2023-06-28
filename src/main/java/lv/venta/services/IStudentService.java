package lv.venta.services;

import lv.venta.models.users.Role;
import lv.venta.models.users.Student;
import lv.venta.models.users.User;

public interface IStudentService {
	void createStudent(Student student);
	void updateStudent(long idperson, String name, String surname, String personCode, Role role, User user, String matriculaNo, boolean financialDebt) throws Exception;
	void deleteStudent(long idstudent) throws Exception;
}
