package shimoka_yuto.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import shimoka_yuto.service.MessageService;

@WebServlet(urlPatterns = { "/deleteMessage" })
public class DeleteMessageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		request.setCharacterEncoding("UTF-8");


		//HttpSession session = request.getSession();
		//List<String> messages = new ArrayList<String>();

		//if (isValid(request, messages) == true) {
			new MessageService().delete(request.getParameter("message_id"));
			response.sendRedirect("./");
		//}
		//else {
		//	Comment comment = new Comment();
		//	comment.setMessageId(Integer.parseInt(request.getParameter("message_id")));
		//	session.setAttribute("errorMessages", messages);
		//	session.setAttribute("errorComment", comment);
		//	response.sendRedirect("./");
		//}
	}

	//private boolean isValid(HttpServletRequest request, List<String> messages) {

	//}

}
