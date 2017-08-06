package com.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import assignment1.DB;
import model.Books;
import model.Users;

/**
 * Servlet implementation class RegularSearchServlet
 */
@WebServlet("/regularSearch")
public class RegularSearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private List<Map<String, String>> result_list;
	private List<Map<String, String>> subresult_list;
	private String sql;
	
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegularSearchServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(true);
		String role = (String)session.getAttribute("role");
		String keyName = request.getParameter("keyName");
		String page = request.getParameter("page");
		result_list = new ArrayList<>();
		subresult_list = new ArrayList<>();
		
		if(page == null){
			switch(keyName){
			case "0"://search by author name
				String author = request.getParameter("Author").trim();
				if(author!=null && author.length()>0){
					author= author.toLowerCase();
					sql = "select * from  books where lower(author) LIKE \'%"+ author +"%\' and pause =0";
					session.setAttribute("sql", sql);//set session
					result_list = Books.select(sql);
					sendResult(0, request, response);
				}else{
					request.setAttribute("error", "The input is invalid !");
					if(role.equals("0")){
						request.getRequestDispatcher("/Views/Search.jsp").forward(request, response);
					}else if (role.equals("2")) {
						request.getRequestDispatcher("/Views/Admin.jsp").forward(request, response);
					}
					
				}
				break;
				
			case "1"://search by year
				int year1 = -1;
				int year2 = -1;
				try{
					year1 = Integer.parseInt(request.getParameter("Year1"));
					year2 = Integer.parseInt(request.getParameter("Year2"));
				}catch (Exception e) {
					request.setAttribute("error", "The year must be 4 numbers !");
					if(role.equals("0")){
						request.getRequestDispatcher("/Views/Search.jsp").forward(request, response);
					}else if (role.equals("2")) {
						request.getRequestDispatcher("/Views/Admin.jsp").forward(request, response);
					}
				}
				//check year
				if(year1 < 1000 || year1 > 2017 || year2 < 1000 || year2 > 2017){
					request.setAttribute("error", "The input is invalid !");
					if(role.equals("0")){
						request.getRequestDispatcher("/Views/Search.jsp").forward(request, response);
					}else if (role.equals("2")) {
						request.getRequestDispatcher("/Views/Admin.jsp").forward(request, response);
					}
				}else{
					sql = "select * from  books where pause =0 and publicdate >" + year1 
							+" and publicdate < " + year2;
					session.setAttribute("sql", sql);//set session
					result_list = Books.select(sql);
					sendResult(0, request, response);
				}
				break;
				
			case "2"://search by type
				String type = request.getParameter("type").trim();
				sql = "select * from  books where pause =0 and type = \'"+ type +"\'";
				session.setAttribute("sql", sql);//set session
				result_list = Books.select(sql);
				sendResult(0, request, response);
				break;
			case "3"://search by title
				String title = request.getParameter("title").trim();
				
				if(title!=null && title.length()>0){
					title = title.toLowerCase();
					sql = "select * from  books where pause =0 and lower(title) LIKE \'%"+ title +"%\'";
					session.setAttribute("sql", sql);//set session
					result_list = Books.select(sql);
					sendResult(0, request, response);
				}else{
					request.setAttribute("error", "The input is invalid !");
					if(role.equals("0")){
						request.getRequestDispatcher("/Views/Search.jsp").forward(request, response);
					}else if (role.equals("2")) {
						request.getRequestDispatcher("/Views/Admin.jsp").forward(request, response);
					}
				}
				
				break;

			default:
				request.setAttribute("error", "The input is invalid !");
				if(role.equals("0")){
					request.getRequestDispatcher("/Views/Search.jsp").forward(request, response);
				}else if (role.equals("1")) {
					request.getRequestDispatcher("/Views/Admin.jsp").forward(request, response);
				}
			}
		}else{
			int pageNum = Integer.parseInt(page);
			sql = (String)session.getAttribute("sql");
			result_list = Books.select(sql);
			sendResult(pageNum, request, response);
			
		}
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	/*
	 * send 10(or fewer) pages back
	 */
	private void sendResult(int p, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		for(int i=p*10;i<result_list.size()&&i<10+10*p;i++){
			subresult_list.add(result_list.get(i));
		}
		if(request.getSession().getAttribute("role").equals("2")){
			request.setAttribute("result", subresult_list);
			request.getRequestDispatcher("/Views/Admin.jsp").forward(request, response);
		}else{
			request.setAttribute("result", subresult_list);
			request.getRequestDispatcher("/Views/Search.jsp").forward(request, response);
		}
		
	}

}
