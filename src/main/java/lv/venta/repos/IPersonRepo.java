package lv.venta.repos;

import org.springframework.data.repository.CrudRepository;

import lv.venta.models.users.Person;

public interface IPersonRepo extends CrudRepository<Person, Long> {

}
