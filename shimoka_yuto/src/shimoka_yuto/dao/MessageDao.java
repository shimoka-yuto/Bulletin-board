package shimoka_yuto.dao;

import static shimoka_yuto.utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import shimoka_yuto.beans.Message;
import shimoka_yuto.exception.SQLRuntimeException;

public class MessageDao {

	public void insert(Connection connection, Message message) {

		PreparedStatement ps = null;
		try {

			//sql文生成
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO messages ( ");
			sql.append("title");
			sql.append(", text");
			sql.append(", category");
			sql.append(", user_id");
			sql.append(", branch_id");
			sql.append(", department_id");
			sql.append(", insert_date");
			sql.append(", update_date");
			sql.append(") VALUES (");
			sql.append("?"); // title
			sql.append(", ?"); // text
			sql.append(", ?"); // category
			sql.append(", ?"); // user_id
			sql.append(", ?"); // branch_id
			sql.append(", ?"); // department_id
			sql.append(", CURRENT_TIMESTAMP"); // insert_date
			sql.append(", CURRENT_TIMESTAMP"); // update_date
			sql.append(")");

			ps = connection.prepareStatement(sql.toString());

			ps.setString(1, message.getTitle());
			ps.setString(2, message.getText());
			ps.setString(3, message.getCategory());
			ps.setInt(4, message.getUserId());
			ps.setInt(5, message.getBranchId());
			ps.setInt(6, message.getDepartmentId());

			ps.executeUpdate();

		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}


	public void delete(Connection connection, String message_id) {

		PreparedStatement ps = null;
		try {

			//sql文生成
			StringBuilder sql = new StringBuilder();
			sql.append("DELETE FROM messages WHERE id = ?");
			ps = connection.prepareStatement(sql.toString());

			ps.setString(1, message_id);

			ps.executeUpdate();

		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

}
