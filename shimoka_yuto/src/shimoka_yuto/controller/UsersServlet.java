package shimoka_yuto.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import shimoka_yuto.beans.User;
import shimoka_yuto.dao.UserDao;
import shimoka_yuto.service.UserService;

@WebServlet(urlPatterns = { "/users" })

public class UsersServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		List<User> users = new UserService().getAllUser();
		UserDao userDao = new UserDao();
		for (User user : users) {
			user.setBranchName(userDao.getBranchName(user.getBranchId()));
			user.setDepartmentName(userDao.getDepartmentName(user.getDepartmentId()));
		}
		request.setAttribute("users", users);

		request.getRequestDispatcher("/users.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserService userService = new UserService();
		userService.isActivedUpdate(new UserService().getUser(Integer.parseInt(request.getParameter("user_id"))),Boolean.valueOf(request.getParameter("is_actived")));
		response.sendRedirect("users");
	}

}
