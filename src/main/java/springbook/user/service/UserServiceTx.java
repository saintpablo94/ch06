package springbook.user.service;

import springbook.user.domain.User;

public class UserServiceTx implements UserService {
	UserService userService;
	
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void upgradeLevels() {
		userService.upgradeLevels();
	}

	public void add(User user) {
		userService.add(user);
	}

}
