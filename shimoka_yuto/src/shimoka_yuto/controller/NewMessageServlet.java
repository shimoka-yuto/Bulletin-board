package shimoka_yuto.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import shimoka_yuto.beans.Message;
import shimoka_yuto.beans.User;
import shimoka_yuto.beans.UserMessage;
import shimoka_yuto.service.MessageService;

@WebServlet(urlPatterns = { "/newMessage" })
public class NewMessageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		//カテゴリー一覧の取得
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		List<UserMessage> messages = new MessageService().getMessage("2017/09/01 0:00:00",sdf.format(calendar.getTime()) + " 23:59:59", null);

		ArrayList<String> categorys = new ArrayList<String>();
		for(UserMessage message : messages){
			boolean boo = true;
			for(String str : categorys){
				if(message.getCategory().equals(str)){
					boo = false;
				}
			}
			if(boo){
				categorys.add(message.getCategory());
			}
		}

		request.setAttribute("categorys", categorys);
		request.getRequestDispatcher("newMessage.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		request.setCharacterEncoding("UTF-8");


		HttpSession session = request.getSession();
		List<String> messages = new ArrayList<String>();

		if (isValid(request, messages) == true) {
			User user = (User) session.getAttribute("loginUser");
			Message message = new Message();
			message.setTitle(request.getParameter("title"));
			message.setCategory(request.getParameter("category"));
			message.setText(request.getParameter("text"));
			message.setUserId(user.getId());
			message.setBranchId(user.getBranchId());
			message.setDepartmentId(user.getDepartmentId());

			new MessageService().register(message);

			response.sendRedirect("./");
		} else {
			session.setAttribute("errorMessages", messages);
			request.setAttribute("title", request.getParameter("title"));
			request.setAttribute("category", request.getParameter("category"));
			request.setAttribute("text", request.getParameter("text"));
			request.getRequestDispatcher("newMessage.jsp").forward(request, response);
		}
	}

	private boolean isValid(HttpServletRequest request, List<String> messages) {

		String title = request.getParameter("title");
		String category = request.getParameter("category");
		String text = request.getParameter("text");

		if (StringUtils.isEmpty(title)) {
			messages.add("件名を入力してください");
		}
		if (title.matches("^[ 　¥t¥n¥x0B¥f¥r]+$")) {
			messages.add("件名を入力してください");
		}
		if (!StringUtils.isEmpty(title) && 30 < title.length()) {
			messages.add("件名は30文字以下で入力してください");
		}
		if (StringUtils.isEmpty(category)) {
			messages.add("カテゴリーを入力してください");
		}
		if (category.matches("^[ 　¥t¥n¥x0B¥f¥r]+$")) {
			messages.add("カテゴリーを入力してください");
		}
		if (!StringUtils.isEmpty(category) && 10 < category.length()) {
			messages.add("カテゴリーは10文字以下で入力してください");
		}
		if (StringUtils.isEmpty(text)) {
			messages.add("本文を入力してください");
		}
		if (text.matches("^[ 　¥t¥n¥x0B¥f¥r]+$")) {
			messages.add("本文を入力してください");
		}
		if (!StringUtils.isEmpty(text) && 1000 < text.length()) {
			messages.add("本文は1000文字以下で入力してください");
		}
		if (messages.size() == 0) {
			return true;
		}
		else {
			return false;
		}
	}

}
