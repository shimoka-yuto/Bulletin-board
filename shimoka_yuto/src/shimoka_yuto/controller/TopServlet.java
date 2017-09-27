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

import org.apache.commons.lang.StringUtils;

import shimoka_yuto.beans.UserComment;
import shimoka_yuto.beans.UserMessage;
import shimoka_yuto.service.CommentService;
import shimoka_yuto.service.MessageService;

@WebServlet(urlPatterns = { "/index.jsp" })

public class TopServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {


		request.setCharacterEncoding("UTF-8");



		//絞り込み用の日付取得
		String start_date = null;
		String end_date = null;

		if(StringUtils.isEmpty(request.getParameter("start_date"))){
			start_date = "2017/09/01 0:00:00";
		}
		else{
			start_date = request.getParameter("start_date") + " 0:00:00";
			request.setAttribute("start_date", request.getParameter("start_date"));
		}
		if(StringUtils.isEmpty(request.getParameter("end_date"))){
			Calendar calendar = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
			end_date = sdf.format(calendar.getTime()) + " 23:59:59";
		}
		else {
			end_date =request.getParameter("end_date") + " 23:59:59";
			request.setAttribute("end_date", request.getParameter("end_date"));
		}

		String category = null;
		if(StringUtils.isEmpty(request.getParameter("category"))){
			category = null;
		}
		else{
			category = request.getParameter("category");
		}

		List<UserMessage> messages = new MessageService().getMessage(start_date, end_date, category);
		List<UserComment> comments = new CommentService().getComment();

		//カテゴリー一覧の取得
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

		request.setAttribute("messages", messages);
		request.setAttribute("comments", comments);
		request.setAttribute("category", category);
		request.setAttribute("categorys", categorys);

		request.getRequestDispatcher("/top.jsp").forward(request, response);

	}

}

