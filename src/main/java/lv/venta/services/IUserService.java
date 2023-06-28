package lv.venta.services;

import lv.venta.models.users.User;

public interface IUserService {
	void createUser(User user);
	void updateUser(long iduser, String password, String email) throws Exception;
	void deleteUser(long iduser) throws Exception;
}
