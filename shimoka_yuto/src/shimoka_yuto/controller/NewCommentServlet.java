package shimoka_yuto.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import shimoka_yuto.beans.Comment;
import shimoka_yuto.beans.User;
import shimoka_yuto.service.CommentService;

@WebServlet(urlPatterns = { "/newComment" })
public class NewCommentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		request.setCharacterEncoding("UTF-8");


		HttpSession session = request.getSession();
		List<String> messages = new ArrayList<String>();

		if (isValid(request, messages) == true) {
			User user = (User) session.getAttribute("loginUser");
			Comment comment = new Comment();
			comment.setText(request.getParameter("comment"));
			comment.setUserId(user.getId());
			comment.setMessageId(Integer.parseInt(request.getParameter("message_id")));
			comment.setBranchId(user.getBranchId());
			comment.setDepartmentId(user.getDepartmentId());

			new CommentService().register(comment);

			response.sendRedirect("./");
		}
		else {
			Comment comment = new Comment();
			comment.setMessageId(Integer.parseInt(request.getParameter("message_id")));
			session.setAttribute("errorMessages", messages);
			session.setAttribute("errorComment", comment);
			session.setAttribute("comment_text", request.getParameter("comment"));
			response.sendRedirect("./");
		}
	}

	private boolean isValid(HttpServletRequest request, List<String> messages) {

		String text = request.getParameter("comment");

		if (StringUtils.isEmpty(text)) {
			messages.add("コメントを入力してください");
		}
		if (text.matches("^[ 　¥t¥n¥x0B¥f¥r]+$")) {
			messages.add("コメントを入力してください");
		}
		if (!StringUtils.isEmpty(text) && 500 < text.length()) {
			messages.add("500文字以下で入力してください");
		}
		if (messages.size() == 0) {
			return true;
		}
		else {
			return false;
		}
	}

}
