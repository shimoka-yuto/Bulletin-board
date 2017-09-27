package shimoka_yuto.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import shimoka_yuto.beans.User;
import shimoka_yuto.dao.UserDao;
import shimoka_yuto.service.LoginService;

@WebServlet(urlPatterns = { "/login" })
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		request.getRequestDispatcher("login.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		String accountOrEmail = request.getParameter("accountOrEmail");
		String password = request.getParameter("password");

		LoginService loginService = new LoginService();
		User user = loginService.login(accountOrEmail, password);

		HttpSession session = request.getSession();
		session.removeAttribute("id");
		session.removeAttribute("pass");

		if (user != null && !user.getIsActived()) {

			UserDao userDao = new UserDao();
			user.setBranchName(userDao.getBranchName(user.getBranchId()));
			user.setDepartmentName(userDao.getDepartmentName(user.getDepartmentId()));
			session.setAttribute("loginUser", user);
			response.sendRedirect("./");
		} else {

			List<String> messages = new ArrayList<String>();
			messages.add("ログインに失敗しました。");
			session.setAttribute("errorMessages", messages);
			request.setAttribute("id", accountOrEmail);
			request.setAttribute("pass", password);
			RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");
			dispatcher.forward(request, response);
			response.sendRedirect("login");
		}
	}

}
