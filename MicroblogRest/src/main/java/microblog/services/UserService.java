package microblog.services;

import microblog.models.User;


public interface UserService {

    boolean usernameExists(String username);

	User findByUsername(String username);

	User registerIfNeeded(String userName);

}
