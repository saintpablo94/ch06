package springbook.user.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

import springbook.user.dao.UserDao;
import springbook.user.domain.Level;
import springbook.user.domain.User;

public class UserServiceImpl implements UserService {
	public static final int MIN_RECCOMEND_FOR_GOLD = 30;
	public static final int MIN_LOGCOUNT_FOR_SILVER = 50;

	UserDao userDao;
	private MailSender mailSender;

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public void setMailSender(MailSender mailSender) {
		this.mailSender = mailSender;
	}

	public void upgradeLevels() {

		List<User> users = userDao.getAll();
		for (User user : users) {
			if (canUpgradeLevel(user)) {
				upgradeLevel(user);
			}
		}
	}

	private boolean canUpgradeLevel(User user) {
		Level currentLevel = user.getLevel();
		switch (currentLevel) {
		case BASIC:
			return (user.getLogin() >= MIN_LOGCOUNT_FOR_SILVER);
		case SILVER:
			return (user.getRecommand() >= MIN_RECCOMEND_FOR_GOLD);
		case GOLD:
			return false;
		default:
			throw new IllegalArgumentException("Unknown Level : "
					+ currentLevel);
		}
	}

	protected void upgradeLevel(User user) {
		user.upgradeLevel();
		userDao.update(user);
		sendUpgradeEMail(user);
	}

	private void sendUpgradeEMail(User user) {
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo(user.getEmail());
		mailMessage.setFrom("abc@a.c");
		mailMessage.setSubject("Upgrade Infomation");
		mailMessage.setText("사용자님의 등급이 " + user.getLevel().name());

		this.mailSender.send(mailMessage);
	}

	public void add(User user) {
		if (user.getLevel() == null)
			user.setLevel(Level.BASIC);
		userDao.add(user);
	}

	static class MockUserDao implements UserDao {

		private List<User> users;
		private List<User> updated = new ArrayList<User>();

		public MockUserDao(List<User> users) {
			this.users = users;
		}

		public List<User> getUpdated() {
			return updated;
		}

		public List<User> getAll() {
			return this.users;
		}

		public void update(User user) {
			updated.add(user);
		}

		public void add(User user) {
			throw new UnsupportedOperationException();
		}

		public void deleteAll() {
			throw new UnsupportedOperationException();
		}

		public User get(String id) {
			throw new UnsupportedOperationException();
		}

		public int getCount() {
			throw new UnsupportedOperationException();
		}

	}
}
