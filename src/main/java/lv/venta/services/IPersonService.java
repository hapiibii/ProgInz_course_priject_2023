package lv.venta.services;

import lv.venta.models.users.Person;
import lv.venta.models.users.Role;

public interface IPersonService {
	void createPerson(Person person);
	void updatePerson(long idperson, String name, String surname, String personcode, Role role) throws Exception;
	void deletePerson(long idperson) throws Exception;
}
