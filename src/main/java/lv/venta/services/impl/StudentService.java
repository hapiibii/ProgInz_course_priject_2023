package lv.venta.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lv.venta.models.users.Role;
import lv.venta.models.users.Student;
import lv.venta.models.users.User;
import lv.venta.repos.IStudentRepo;
import lv.venta.services.IStudentService;

@Service
public class StudentService implements IStudentService {

	private final IStudentRepo studentRepo;
	
	@Autowired
	public StudentService(IStudentRepo studentRepo) {
		this.studentRepo = studentRepo;
	}
	
	@Override
	public void createStudent(Student student) {
		studentRepo.save(student);
		
	}

	@Override
	public void updateStudentWithPersoncode(long idperson, String name, String surname, String personCode, Role role,
			User user, String matriculaNo, boolean financialDebt) throws Exception {

		Student existingStudent = studentRepo.findById(idperson).orElse(null);
		
		if(existingStudent != null) {
			if(name != null) {
				existingStudent.setName(name);
			}
			if(surname != null) {
				existingStudent.setSurname(surname);
			}
			if(personCode != null) {
				existingStudent.setPersoncode(personCode);
			}
			if(role != null) {
				existingStudent.setRole(role);
			}
			if(user != null) {
				existingStudent.setUser(user);
			}
			if(matriculaNo != null) {
				existingStudent.setMatriculaNo(matriculaNo);
			}
			if(financialDebt == true || financialDebt == false) {
				existingStudent.setFinancialDebt(financialDebt);
			}
			studentRepo.save(existingStudent);
		}
		else {
			throw new Exception("Can not find the student");
		}
	}

	@Override
	public void updateStudentWithoutPersoncode(long idperson, String name, String surname, Role role, User user,
			String matriculaNo, boolean financialDebt) throws Exception {
		
		Student existingStudent = studentRepo.findById(idperson).orElse(null);
		
		if(existingStudent != null) {
			if(name != null) {
				existingStudent.setName(name);
			}
			if(surname != null) {
				existingStudent.setSurname(surname);
			}
			if(role != null) {
				existingStudent.setRole(role);
			}
			if(user != null) {
				existingStudent.setUser(user);
			}
			if(matriculaNo != null) {
				existingStudent.setMatriculaNo(matriculaNo);
			}
			if(financialDebt == true || financialDebt == false) {
				existingStudent.setFinancialDebt(financialDebt);
			}
			studentRepo.save(existingStudent);
		}
		else {
			throw new Exception("Can not find the student");
		}
		
	}

	@Override
	public void deleteStudent(long idperson) throws Exception {
		try {
			studentRepo.deleteById(idperson);
		}
		catch (Exception e) {
			throw new Exception("Can not delete student with this ID! ", e);
		}
		
	}

	@Override
	public List<Student> getAllStudent() {
		return (List<Student>) studentRepo.findAll();
	}

}
