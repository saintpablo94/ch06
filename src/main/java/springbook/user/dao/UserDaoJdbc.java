package springbook.user.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import springbook.user.domain.Level;
import springbook.user.domain.User;

public class UserDaoJdbc implements UserDao {
	private JdbcTemplate jdbcTemplate;

	private RowMapper<User> userMapper = new RowMapper<User>() {
		public User mapRow(ResultSet rs, int rowNum) throws SQLException {
			User user = new User();
			user.setId(rs.getString("id"));
			user.setName(rs.getString("name"));
			user.setPassword(rs.getString("password"));
			user.setEmail(rs.getString("email"));
			user.setLevel(Level.valueOf(rs.getInt("level")));
			user.setLogin(rs.getInt("login"));
			user.setRecommand(rs.getInt("recommend"));
			return user;
		}
	};

	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public void add(final User user) {

		final String query = 
				"insert into users(id, name, password,email,level,login,recommend) "
				+ "values(?,?,?,?,?,?,?)";
		this.jdbcTemplate.update(query, 
				user.getId(), 
				user.getName(),
				user.getPassword(),
				user.getEmail(),
				user.getLevel().intValue(),
				user.getLogin(),
				user.getRecommand());
	}

	public void deleteAll() {

		final String query = "delete from users";
		this.jdbcTemplate.update(query);
	}

	public User get(String id) {
		final String query = "select * from users where id = ?";
		return this.jdbcTemplate.queryForObject(query, new Object[] { id },
				this.userMapper);
	}

	public int getCount() {

		final String query = "select count(*) from users";
		return this.jdbcTemplate.queryForObject(query, Integer.class);

	}

	public List<User> getAll() {
		final String query = "select * from users order by id";
		return jdbcTemplate.query(query, this.userMapper);
	}

	public void update(User user) {
		final String query = 
				" update users set "
				+ " name = ?,"
				+ " password = ?,"
				+ " email = ?,"
				+ " level = ?,"
				+ " login = ?,"
				+ " recommend = ?" 
				+ " where id = ? ";
		this.jdbcTemplate.update(
				query, 
				user.getName(),
				user.getPassword(),
				user.getEmail(),
				user.getLevel().intValue(),
				user.getLogin(),
				user.getRecommand(),
				user.getId()); 
		
	}

}
