package shimoka_yuto.dao;

import static shimoka_yuto.utils.CloseableUtil.*;
import static shimoka_yuto.utils.DBUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import shimoka_yuto.beans.User;
import shimoka_yuto.exception.NoRowsUpdatedRuntimeException;
import shimoka_yuto.exception.SQLRuntimeException;

public class UserDao {

	public User getUser(Connection connection, String accountOrEmail, String password) {

		PreparedStatement ps = null;
		try {
			String sql = "SELECT * FROM users WHERE account = ? AND password = ?";

			ps = connection.prepareStatement(sql);
			ps.setString(1, accountOrEmail);
			ps.setString(2, password);

			ResultSet rs = ps.executeQuery();
			List<User> userList = toUserList(rs);
			if (userList.isEmpty() == true) {
				return null;
			} else if (2 <= userList.size()) {
				throw new IllegalStateException("2 <= userList.size()");
			} else {
				return userList.get(0);
			}
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	private List<User> toUserList(ResultSet rs) throws SQLException {

		List<User> ret = new ArrayList<User>();
		try {
			while (rs.next()) {
				int id = rs.getInt("id");
				String account = rs.getString("account");
				String password = rs.getString("password");
				String name = rs.getString("name");
				int branch_id = rs.getInt("branch_id");
				int department_id = rs.getInt("department_id");
				boolean is_actived = rs.getBoolean("is_actived");
				Timestamp insertDate = rs.getTimestamp("insert_date");
				Timestamp updateDate = rs.getTimestamp("update_date");

				User user = new User();
				user.setId(id);
				user.setAccount(account);
				user.setPassword(password);
				user.setName(name);
				user.setBranchId(branch_id);
				user.setDepartmentId(department_id);
				user.setIsActived(is_actived);
				user.setInsertDate(insertDate);
				user.setUpdateDate(updateDate);

				ret.add(user);
			}
			return ret;
		} finally {
			close(rs);
		}
	}

	/*private byte[] getIcon(ResultSet rs) throws SQLException {
		byte[] ret = null;
		InputStream binaryStream = rs.getBinaryStream("icon");
		if (binaryStream != null) {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			StreamUtil.copy(binaryStream, baos);
			ret = baos.toByteArray();
		}
		return ret;
	}*/

	public void insert(Connection connection, User user) {

		PreparedStatement ps_branchs = null;
		PreparedStatement ps_departments = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {

			//branch_idの取得
			int branch_id = 0;
			String select_branchs = "SELECT id FROM branchs WHERE name = ?";
			ps_branchs = connection.prepareStatement(select_branchs);
			ps_branchs.setString(1, user.getBranchName());
			rs = ps_branchs.executeQuery();
			while(rs.next()){
				branch_id = rs.getInt("id");
			}

			//department_idの取得
			int department_id = 0;
			String select_departments = "SELECT id FROM departments WHERE name = ?";
			ps_departments = connection.prepareStatement(select_departments);
			ps_departments.setString(1, user.getDepartmentName());
			rs = ps_departments.executeQuery();
			while(rs.next()){
				department_id = rs.getInt("id");
			}

			//sql文生成
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO users ( ");
			sql.append("account");
			sql.append(", password");
			sql.append(", name");
			sql.append(", branch_id");
			sql.append(", department_id");
			sql.append(", is_actived");
			sql.append(", insert_date");
			sql.append(", update_date");
			sql.append(") VALUES (");
			sql.append("?"); //account
			sql.append(", ?"); // password
			sql.append(", ?"); // name
			sql.append(", " + branch_id); // branch_id
			sql.append(", " + department_id); // department_id
			sql.append(", ?"); // is_actived
			sql.append(", CURRENT_TIMESTAMP"); // insert_date
			sql.append(", CURRENT_TIMESTAMP"); // update_date
			sql.append(")");

			ps = connection.prepareStatement(sql.toString());
			ps.setString(1, user.getAccount());
			ps.setString(2, user.getPassword());
			ps.setString(3, user.getName());
			ps.setBoolean(4,false);

			ps.executeUpdate();
		}
		catch (SQLException e) {
			throw new SQLRuntimeException(e);
		}
		finally {
			close(ps);
			close(ps_branchs);
			close(ps_departments);
		}
	}


	public void update(Connection connection, User user, HttpServletRequest request) {

		PreparedStatement ps_branchs = null;
		PreparedStatement ps_departments = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {

			//branch_idの取得
			int branch_id = 0;
			String select_branchs = "SELECT id FROM branchs WHERE name = ?";
			ps_branchs = connection.prepareStatement(select_branchs);
			ps_branchs.setString(1, user.getBranchName());
			rs = ps_branchs.executeQuery();
			while(rs.next()){
				branch_id = rs.getInt("id");
			}

			//department_idの取得
			int department_id = 0;
			String select_departments = "SELECT id FROM departments WHERE name = ?";
			ps_departments = connection.prepareStatement(select_departments);
			ps_departments.setString(1, user.getDepartmentName());
			rs = ps_departments.executeQuery();
			while(rs.next()){
				department_id = rs.getInt("id");
			}

			//sql文生成
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE users SET");
			sql.append("  account = ?");
			if(!StringUtils.isEmpty(request.getParameter("password"))){
				sql.append(", password = ?");
			}
			sql.append(", name = ?");
			sql.append(", branch_id = ?");
			sql.append(", department_id = ?");
			sql.append(", update_date = CURRENT_TIMESTAMP");
			sql.append(" WHERE");
			sql.append(" id = ?");
			sql.append(" AND");
			sql.append(" update_date = ?");

			ps = connection.prepareStatement(sql.toString());

			ps.setString(1, user.getAccount());
			if(!StringUtils.isEmpty(request.getParameter("password"))){
				ps.setString(2, user.getPassword());
				ps.setString(3, user.getName());
				ps.setInt(4, branch_id);
				ps.setInt(5, department_id);
				ps.setInt(6, user.getId());
				ps.setTimestamp(7, new Timestamp(user.getUpdateDate().getTime()));
			}
			else {
				ps.setString(2, user.getName());
				ps.setInt(3, branch_id);
				ps.setInt(4, department_id);
				ps.setInt(5, user.getId());
				ps.setTimestamp(6, new Timestamp(user.getUpdateDate().getTime()));
			}


			int count = ps.executeUpdate();
			if (count == 0) {
				throw new NoRowsUpdatedRuntimeException();
			}

		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}

	}

	public void isActivedUpdate(Connection connection, User user, boolean boo) {

		PreparedStatement ps = null;
		try {

			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE users SET");
			sql.append(" is_actived = ?");
			sql.append(", update_date = CURRENT_TIMESTAMP");
			sql.append(" WHERE");
			sql.append(" id = ?");
			sql.append(" AND");
			sql.append(" update_date = ?");

			ps = connection.prepareStatement(sql.toString());
			ps.setBoolean(1, boo);
			ps.setInt(2, user.getId());
			ps.setTimestamp(3, new Timestamp(user.getUpdateDate().getTime()));

			int count = ps.executeUpdate();
			if (count == 0) {
				throw new NoRowsUpdatedRuntimeException();
			}

		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}

	}


	public User getUser(Connection connection, int id) {

		PreparedStatement ps = null;
		try {
			String sql = "SELECT * FROM users WHERE id = ?";

			ps = connection.prepareStatement(sql);
			ps.setInt(1, id);

			ResultSet rs = ps.executeQuery();
			List<User> userList = toUserList(rs);
			if (userList.isEmpty() == true) {
				return null;
			} else if (2 <= userList.size()) {
				throw new IllegalStateException("2 <= userList.size()");
			} else {
				return userList.get(0);
			}
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	public User getUser(Connection connection, String account) {

		PreparedStatement ps = null;
		try {
			String sql = "SELECT * FROM users WHERE account = ?";

			ps = connection.prepareStatement(sql);
			ps.setString(1, account);

			ResultSet rs = ps.executeQuery();
			List<User> userList = toUserList(rs);
			if (userList.isEmpty() == true) {
				return null;
			} else if (2 <= userList.size()) {
				throw new IllegalStateException("2 <= userList.size()");
			} else {
				return userList.get(0);
			}
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	public List<User> getAllUser(Connection connection, int num) {

		PreparedStatement ps = null;

		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * FROM users ");
			sql.append("ORDER BY department_id ASC limit " + num);

			ps = connection.prepareStatement(sql.toString());

			ResultSet rs = ps.executeQuery();
			List<User> ret = toUserList(rs);
			return ret;
		}
		catch (SQLException e) {
			throw new SQLRuntimeException(e);
		}
		finally {
			close(ps);
		}
	}


	public ArrayList<String> getBranchs() {

		Connection connection = null;
		connection = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ArrayList<String> branch_name = new ArrayList<String>();
			String str = "SELECT name FROM branchs ";
			ps = connection.prepareStatement(str);
			rs = ps.executeQuery();
			while(rs.next()){
				branch_name.add(rs.getString("name"));
			}
			return branch_name;

		}
		catch (SQLException e) {
			throw new SQLRuntimeException(e);
		}
		finally {
			close(ps);
			}
	}


	public ArrayList<String> getDepartments() {

	Connection connection = null;
	connection = getConnection();
	PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ArrayList<String>department_name = new ArrayList<String>();
			String str = "SELECT name FROM departments ";
			ps = connection.prepareStatement(str);
			rs = ps.executeQuery();
			while(rs.next()){
				department_name.add(rs.getString("name"));
			}
			return department_name;

		}
		catch (SQLException e) {
			throw new SQLRuntimeException(e);
		}
		finally {
			close(ps);
			}
	}

	public String getBranchName(int branch_id){

		Connection connection = null;
		connection = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {

			String branch_name = null;
			String string = "SELECT name FROM branchs WHERE id = ?";
			ps = connection.prepareStatement(string);
			ps.setInt(1, branch_id);
			rs = ps.executeQuery();
			while(rs.next()){
				branch_name = rs.getString("name");
			}
			return branch_name;

		}
		catch (SQLException e) {
			throw new SQLRuntimeException(e);
		}
		finally {
			close(ps);
		}
	}

	public String getDepartmentName(int department_id){

		Connection connection = null;
		connection = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {

			String department_name = null;
			String string = "SELECT name FROM departments WHERE id = ?";
			ps = connection.prepareStatement(string);
			ps.setInt(1, department_id);
			rs = ps.executeQuery();
			while(rs.next()){
				department_name = rs.getString("name");
			}
			return department_name;

		}
		catch (SQLException e) {
			throw new SQLRuntimeException(e);
		}
		finally {
			close(ps);
		}
	}
}
