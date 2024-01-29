package lv.venta.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lv.venta.models.users.Person;
import lv.venta.models.users.Role;
import lv.venta.models.users.User;
import lv.venta.repos.IPersonRepo;
import lv.venta.services.IPersonService;

@Service
public class PersonService implements IPersonService {

	private final IPersonRepo personRepo;
	
	@Autowired
	public PersonService(IPersonRepo personRepo) {
		this.personRepo = personRepo;
	}
	
	@Override
	public List<Person> retrieveAllPersons () {
		return (List<Person>) personRepo.findAll();
	}
	
	@Override
	public void createPerson(Person person) {
		personRepo.save(person);
		
	}
	

	@Override
	public Person updatePersonWithPersoncode(long idperson, String name, String surname, String personcode, Role role,
			User user) throws Exception {
		Person existingPerson = personRepo.findById(idperson).orElse(null);
		
		if(existingPerson != null) {
			if(name != null) {
				existingPerson.setName(name);
			}
			if(surname != null) {
				existingPerson.setSurname(surname);
			}
			if(personcode != null) {
				existingPerson.setPersoncode(personcode);
			}
			if(role != null) {
				existingPerson.setRole(role);
			}
			if(user != null) {
				existingPerson.setUser(user);
			}
			personRepo.save(existingPerson);
		}
		else {
			throw new Exception("Can not find person to edit.");
		}
		return existingPerson;
		
	}

	@Override
	public void updatePersonWithoutPersoncode(long idperson, String name, String surname, Role role, User user)
			throws Exception {
		Person existingPerson = personRepo.findById(idperson).orElse(null);
		
		if(existingPerson != null) {
			if(name != null) {
				existingPerson.setName(name);
			}
			if(surname != null) {
				existingPerson.setSurname(surname);
			}
			if(role != null) {
				existingPerson.setRole(role);
			}
			if(user != null) {
				existingPerson.setUser(user);
			}
			personRepo.save(existingPerson);
		}
		else {
			throw new Exception("Can not find person to edit.");
		}
		
	}

	@Override
	public void deletePerson(long idperson) throws Exception {
		try {
			personRepo.deleteById(idperson);
		}
		catch (Exception e) {
			throw new Exception("Can not delete person with this ID! ", e);
		}
		
	}

	@Override
	public Person getPersonByUserId(long iduser) {
	    Optional<Person> optionalPerson = personRepo.findById(iduser);
	    return optionalPerson.orElse(null);
	}

	
	
}
