package lv.venta.services;

import java.util.List;

import lv.venta.models.users.Person;
import lv.venta.models.users.Role;
import lv.venta.models.users.User;

public interface IPersonService {
	void createPerson(Person person);
	Person updatePersonWithPersoncode(long idperson, String name, String surname, String personcode, Role role, User user) throws Exception;
	void updatePersonWithoutPersoncode(long idperson, String name, String surname, Role role, User user) throws Exception;
	void deletePerson(long idperson) throws Exception;
	List<Person> retrieveAllPersons();
}
