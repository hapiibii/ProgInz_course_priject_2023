package lv.venta.repos;

import org.springframework.data.repository.CrudRepository;

import lv.venta.models.users.UserAuthority;

public interface IUserAuthorityRepo extends CrudRepository<UserAuthority, Long>{

}
