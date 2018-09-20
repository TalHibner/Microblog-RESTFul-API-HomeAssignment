package microblog.services.impl;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import microblog.models.User;
import microblog.repositories.UserRepository;
import microblog.services.UserService;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsernameIgnoreCase(username);
    }

    @Override
    public boolean usernameExists(String username) {
        return findByUsername(username) != null;
    }
    
    @Override
    public User registerIfNeeded(String userName) {
    	
    	User user;
    	
        if(!usernameExists(userName)) {
        	user = new User();
        	user.setUsername(userName);
            user.setRegistrationDate(LocalDateTime.now());
        }
		else {
			user = findByUsername(userName);
		}


        return userRepository.save(user);
    }
    
}
