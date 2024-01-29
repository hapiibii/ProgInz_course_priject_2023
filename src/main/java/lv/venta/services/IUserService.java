package lv.venta.services;

import java.util.List;

import lv.venta.models.users.User;

public interface IUserService {
	void createUser(User user);
	User updateUser(long iduser, String password, String email) throws Exception;
	void deleteUser(long iduser) throws Exception;
	List<User> retrieveAllUsers();
	User retrieveUserById(long iduser) throws Exception;
	User insertUser(String username, String password);
}
