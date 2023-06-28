package lv.venta.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lv.venta.models.Degree;
import lv.venta.models.users.AcademicPersonel;
import lv.venta.models.users.Role;
import lv.venta.models.users.User;
import lv.venta.repos.IAcademicPersonelRepo;
import lv.venta.services.IAcademicPersonelService;

@Service
public class AcademicPersonelService implements IAcademicPersonelService {

	private final IAcademicPersonelRepo academicPersonelRepo;
	
	@Autowired
	public AcademicPersonelService(IAcademicPersonelRepo academicPersonelRepo) {
		this.academicPersonelRepo = academicPersonelRepo;
	}
	
	@Override
	public void createAcademicPersonel(AcademicPersonel academicPersonel) {
		academicPersonelRepo.save(academicPersonel);
		
	}

	@Override
	public void updateAcademicPersonelWithPersoncode(long idperson, String name, String surname, String personcode,
			Role role, User user, Degree degree) throws Exception {
		AcademicPersonel existingPersonel = academicPersonelRepo.findById(idperson).orElse(null);
		
		if (existingPersonel != null) {
			
			if(name != null) {
				existingPersonel.setName(name);
			}
			if(surname != null) {
				existingPersonel.setSurname(surname);
			}
			if(personcode != null) {
				existingPersonel.setPersoncode(personcode);
			}
			if(role != null) {
				existingPersonel.setRole(role);
			}
			if(user != null) {
				existingPersonel.setUser(user);
			}
			if(degree != null) {
				existingPersonel.setDegree(degree);
			}
			
			academicPersonelRepo.save(existingPersonel);
		}
		else {
			throw new Exception("Can not find the person for edit!");
		}
		
	}

	@Override
	public void updateAcademicPersonelWithoutPersoncode(long idperson, String name, String surname, Role role,
			User user, Degree degree) throws Exception {

		AcademicPersonel existingPersonel = academicPersonelRepo.findById(idperson).orElse(null);
		
		if (existingPersonel != null) {
			
			if(name != null) {
				existingPersonel.setName(name);
			}
			if(surname != null) {
				existingPersonel.setSurname(surname);
			}
			if(role != null) {
				existingPersonel.setRole(role);
			}
			if(user != null) {
				existingPersonel.setUser(user);
			}
			if(degree != null) {
				existingPersonel.setDegree(degree);
			}
			
			academicPersonelRepo.save(existingPersonel);
		}
		else {
			throw new Exception("Can not find the person for edit!");
		}
		
	}

	@Override
	public void deleteAcademicPersonel(long idperson) throws Exception {

		try {
			academicPersonelRepo.deleteById(idperson);
		}
		catch (Exception e) {
			throw new Exception("Can not delete person with this ID! ", e);
		}
		
	}

}
