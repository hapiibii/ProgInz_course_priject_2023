package lv.venta.services.impl.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;

import lv.venta.models.users.User;
import lv.venta.repos.IUserRepo;
import lv.venta.utils.MyUserDetails;

public class UserDetailsServiceImpl implements UserDetailsManager{
	
	@Autowired
	public IUserRepo userRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepo.findByUsername(username);
		if (user != null ) {
			MyUserDetails details = new MyUserDetails(user);
			return details;
		}
		else {
			throw new UsernameNotFoundException(username + " nav atrasts");
		}
	}

	@Override
	public void createUser(UserDetails user) {
		MyUserDetails myDetails = (MyUserDetails) user;
		User myUser = myDetails.getUser();
		userRepo.save(myUser);
	}

	@Override
	public void updateUser(UserDetails user) {
		MyUserDetails myDetails = (MyUserDetails) user;
		User myUser = myDetails.getUser();
		userRepo.save(myUser);
		
	}

	@Override
	public void deleteUser(String username) {
		User user = userRepo.findByUsername(username);
		if (user != null) { //eksistē tāds lietotājs pēc norādītā username 
			userRepo.delete(user);
		}
		
	}

	@Override
	public void changePassword(String oldPassword, String newPassword) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean userExists(String username) {
		User user = userRepo.findByUsername(username);
		if (user != null) { //eksistē tāds lietotājs pēc norādītā username 
			return true;
		}
		else {
			return false;
		}
	}

}
