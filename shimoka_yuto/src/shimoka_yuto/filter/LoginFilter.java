package shimoka_yuto.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import shimoka_yuto.beans.User;
import shimoka_yuto.dao.UserDao;
import shimoka_yuto.service.UserService;

@WebFilter("/*")
public class LoginFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		String path = ((HttpServletRequest) request).getServletPath();

		if (!path.equals("/login") && !path.matches(".*css.*")) {
			User user = (User) ((HttpServletRequest) request).getSession().getAttribute("loginUser");

			if(user == null || user.getIsActived() == true){
				 ((HttpServletResponse) response).sendRedirect("login");
			}
			else{
				if(path.equals("/users") || path.equals("/signup") || path.matches(".*settings.*")){
					if (user.getDepartmentId() != 1) {
						 ((HttpServletResponse) response).sendRedirect("./");
					}
					else {
						UserDao userDao = new UserDao();
						user.setBranchName(userDao.getBranchName(user.getBranchId()));
						user.setDepartmentName(userDao.getDepartmentName(user.getDepartmentId()));
						((HttpServletRequest) request).getSession().setAttribute("loginUser", new UserService().getUser(user.getId()));
						user = (User) ((HttpServletRequest) request).getSession().getAttribute("loginUser");
						user.setBranchName(userDao.getBranchName(user.getBranchId()));
						user.setDepartmentName(userDao.getDepartmentName(user.getDepartmentId()));
						chain.doFilter(request, response); // サーブレットを実行
					}
				}
				else{
					UserDao userDao = new UserDao();
					user.setBranchName(userDao.getBranchName(user.getBranchId()));
					user.setDepartmentName(userDao.getDepartmentName(user.getDepartmentId()));
					((HttpServletRequest) request).getSession().setAttribute("loginUser", new UserService().getUser(user.getId()));
					user = (User) ((HttpServletRequest) request).getSession().getAttribute("loginUser");
					user.setBranchName(userDao.getBranchName(user.getBranchId()));
					user.setDepartmentName(userDao.getDepartmentName(user.getDepartmentId()));
					chain.doFilter(request, response); // サーブレットを実行
				}

			}
		}
		else {
			chain.doFilter(request, response); // サーブレットを実行
		}

	}

	@Override
	public void init(FilterConfig config) {

	}

	@Override
	public void destroy() {
	}

}