package shimoka_yuto.service;

import static shimoka_yuto.utils.CloseableUtil.*;
import static shimoka_yuto.utils.DBUtil.*;

import java.sql.Connection;

import shimoka_yuto.beans.User;
import shimoka_yuto.dao.UserDao;
import shimoka_yuto.utils.CipherUtil;

public class LoginService {

	public User login(String accountOrEmail, String password) {

		Connection connection = null;
		try {
			connection = getConnection();

			UserDao userDao = new UserDao();
			String encPassword = CipherUtil.encrypt(password);
			User user = userDao.getUser(connection, accountOrEmail, encPassword);

			commit(connection);

			return user;
		} catch (RuntimeException e) {
			rollback(connection);
			throw e;
		} catch (Error e) {
			rollback(connection);
			throw e;
		} finally {
			close(connection);
		}
	}

}
