package shimoka_yuto.dao;

import static shimoka_yuto.utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import shimoka_yuto.beans.Comment;
import shimoka_yuto.exception.SQLRuntimeException;

public class CommentDao {

	public void insert(Connection connection, Comment comment) {

		PreparedStatement ps = null;
		try {

			//sql文生成
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO comments ( ");
			sql.append("text");
			sql.append(", user_id");
			sql.append(", branch_id");
			sql.append(", department_id");
			sql.append(", message_id");
			sql.append(", insert_date");
			sql.append(", update_date");
			sql.append(") VALUES (");
			sql.append("?"); // text
			sql.append(", ?"); // user_id
			sql.append(", ?"); // branch_id
			sql.append(", ?"); // department_id
			sql.append(", ?"); // message_id
			sql.append(", CURRENT_TIMESTAMP"); // insert_date
			sql.append(", CURRENT_TIMESTAMP"); // update_date
			sql.append(")");

			ps = connection.prepareStatement(sql.toString());

			ps.setString(1, comment.getText());
			ps.setInt(2, comment.getUserId());
			ps.setInt(3, comment.getBranchId());
			ps.setInt(4, comment.getDepartmentId());
			ps.setInt(5, comment.getMessageId());

			ps.executeUpdate();

		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}


	public void delete(Connection connection, String comment_id) {

		PreparedStatement ps = null;
		try {

			//sql文生成
			StringBuilder sql = new StringBuilder();
			sql.append("DELETE FROM comments WHERE id = ?");
			ps = connection.prepareStatement(sql.toString());

			ps.setString(1, comment_id);

			ps.executeUpdate();

		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

}
