package lv.venta.repos;

import org.springframework.data.repository.CrudRepository;

import lv.venta.models.users.User;

public interface IUserRepo extends CrudRepository<User, Long> {

	User findByUsername (String username);
	
}
