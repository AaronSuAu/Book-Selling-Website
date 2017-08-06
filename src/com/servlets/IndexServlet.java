package com.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Books;

/**
 * Servlet implementation class IndexServlet
 */
@WebServlet("/user/index")
public class IndexServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public IndexServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		response.getWriter().append("Served at: ").append(request.getContextPath());
		HttpSession session = request.getSession();
		if(session==null){
			request.getRequestDispatcher("/Views/Login.jsp").forward(request, response);
		}else if (session.getAttribute("username")==null) {
			request.getRequestDispatcher("/Views/Login.jsp").forward(request, response);
		}else {
			//get random 10 data
			List<Map<String, String>> result = new ArrayList<>();
			int totalNum = Books.getTotalNum();
			final int startOfRandom10 = (int)(Math.random()*(totalNum-10));
			String sql = "SELECT * FROM books LIMIT " + startOfRandom10 +",10";
			result = Books.select(sql);
			request.setAttribute("result", result);
			request.setAttribute("isRandom", "1");
			request.getRequestDispatcher("/Views/Search.jsp").forward(request, response);
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
