package com.douzon.guestbook.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzon.guestbook.dao.GuestBookDao;
import com.douzon.guestbook.vo.GuestBookVo;
import com.douzon.web.WebUtils;

@WebServlet("/gb")
public class GuestbookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8"); // 나중에 필터로 빠진다.
		
		// 요청 분리(식별)
		String action = request.getParameter("a");
		/*String viewPage = null;
		String requestUri = request.getRequestURI();
		String contextPath = request.getContextPath();
		String commandName = requestUri.substring(contextPath.length());
		System.out.println(requestUri);
		System.out.println(contextPath);
		System.out.println(commandName);*/

		if ("deleteform".equals(action)) {
			WebUtils.forward(request, response, "/WEB-INF/views/deleteform.jsp");
		} else if ("delete".equals(action)) {
			long no = Long.parseLong(request.getParameter("no"));
			String password = request.getParameter("password");
			GuestBookVo vo = new GuestBookVo();
			vo.setNo(no);
			vo.setPassword(password);
			new GuestBookDao().delete(vo);

			WebUtils.redirect(request, response, request.getContextPath() + "/gb");
		} else if ("add".equals(action)) {
			String name = request.getParameter("name");
			String password = request.getParameter("password");
			String message = request.getParameter("message");

			GuestBookVo vo = new GuestBookVo();
			vo.setName(name);
			vo.setPassword(password);
			vo.setMessage(message);

			new GuestBookDao().insert(vo);

			WebUtils.redirect(request, response, request.getContextPath() + "/gb");
		} else {
			// default action : 디폴트 요청 처리
			// index.jsp같은
			GuestBookDao dao = new GuestBookDao();
			List<GuestBookVo> list = dao.getList();

			// 이 list는 request가 나갈때 사라진다. request가 list를 참조하는 값을 가지고있다.
			// 데이터를 request 범위에 저장
			request.setAttribute("list", list);
			WebUtils.forward(request, response, "/WEB-INF/views/index.jsp");

		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
