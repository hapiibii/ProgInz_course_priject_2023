package lv.venta.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lv.venta.models.users.User;
import lv.venta.repos.IUserRepo;
import lv.venta.services.IUserService;

@Service
public class UserService implements IUserService {

	private final IUserRepo userRepo;
	
	@Autowired
	public UserService(IUserRepo userRepo) {
		this.userRepo = userRepo;
	}
	
	@Override
	public void createUser(User user) {
		userRepo.save(user);
		
	}

	@Override
	public void updateUser(long iduser, String password, String email) throws Exception {
		User existingUser = userRepo.findById(iduser).orElse(null);
		
		if(existingUser != null) {
			if(password != null) {
				existingUser.setPassword(password);
			}
			if(email != null) {
				existingUser.setEmail(email);
			}
			userRepo.save(existingUser);
		}
		else {
			throw new Exception("Can not find user to edit.");
		}
		
	}

	@Override
	public void deleteUser(long iduser) throws Exception {
		try {
			userRepo.deleteById(iduser);
		}
		catch (Exception e) {
			throw new Exception("Can not delete user with this ID! ", e);
		}
		
	}

}
