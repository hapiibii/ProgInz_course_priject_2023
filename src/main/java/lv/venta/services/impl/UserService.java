package lv.venta.services.impl;

import java.util.List;
import java.util.Optional;

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
	
	// atgriež visus lietotājus
	@Override
	public List<User> retrieveAllUsers () {
		return (List<User>) userRepo.findAll();
	}
	
	//atgriež lietotāju pēc id
	@Override
	public User retrieveUserById (long iduser) throws Exception {
		Optional<User> optionalUser = userRepo.findById(iduser);
		if (!optionalUser.isPresent()) {
			throw new Exception("Wrong id!");
		}
		return optionalUser.get();
	}
	
	
	
	@Override
	public void createUser(User user) {
		userRepo.save(user);
		
	}

	@Override
	public User updateUser(long iduser, String password, String username) throws Exception {
		User existingUser = userRepo.findById(iduser).orElse(null);
		
		if(existingUser != null) {
			if(password != null) {
				existingUser.setPassword(password);
			}
			userRepo.save(existingUser);
		}
		else {
			throw new Exception("Can not find user to edit.");
		}
		return existingUser;
		
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
	
	@Override
	public User insertUser (String username, String password) {
		User newUser = new User(username, username, password);
		userRepo.save(newUser);
		return newUser;
		
	}

}
