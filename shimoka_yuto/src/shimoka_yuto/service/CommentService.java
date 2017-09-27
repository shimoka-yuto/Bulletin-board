package shimoka_yuto.service;

import static shimoka_yuto.utils.CloseableUtil.*;
import static shimoka_yuto.utils.DBUtil.*;

import java.sql.Connection;
import java.util.List;

import shimoka_yuto.beans.Comment;
import shimoka_yuto.beans.UserComment;
import shimoka_yuto.dao.CommentDao;
import shimoka_yuto.dao.UserCommentDao;

public class CommentService {

	public void register(Comment comment) {
		Connection connection = null;

		try {
			connection = getConnection();

			CommentDao commentDao = new CommentDao();
			commentDao.insert(connection, comment);

			commit(connection);
		}
		catch (RuntimeException e) {
			rollback(connection);
			throw e;
		}
		catch (Error e) {
			rollback(connection);
			throw e;
		}
		finally {
			close(connection);
		}
	}

	private static final int LIMIT_NUM = 1000;

	public List<UserComment> getComment() {
		Connection connection = null;

		try {
			connection = getConnection();

			UserCommentDao commentDao = new UserCommentDao();
			List<UserComment> ret = commentDao.getUserComments(connection, LIMIT_NUM);

			commit(connection);

			return ret;
		}
		catch (RuntimeException e) {
			rollback(connection);
			throw e;
		}
		catch (Error e) {
			rollback(connection);
			throw e;
		}
		finally {
			close(connection);
		}
	}


	public void delete(String comment_id){
		Connection connection = null;
		try {
			connection = getConnection();

			CommentDao commentDao = new CommentDao();
			commentDao.delete(connection, comment_id);

			commit(connection);
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
