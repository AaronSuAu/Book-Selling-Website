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
import model.Users;

/**
 * Servlet implementation class AdvancedSearchServlet
 */
@WebServlet("/advancedSearch")
public class AdvancedSearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private List<Map<String, String>> result_list;
	private List<Map<String, String>> subresult_list;
	private String sql;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdvancedSearchServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		response.getWriter().append("Served at: ").append(request.getContextPath());
		HttpSession session = request.getSession(true);
		result_list = new ArrayList<>();
		subresult_list = new ArrayList<>();
		String page = request.getParameter("page");
		
		if(page==null){
			String type = request.getParameter("type");
			String venue = request.getParameter("venue").trim().toLowerCase();
			String author = request.getParameter("author").trim().toLowerCase();
			String year1 = request.getParameter("year1");
			String year2 = request.getParameter("year2");
			String title = request.getParameter("title").trim().toLowerCase();
			System.out.println(type+venue+author+year1+year2+title);
			
			String typesql = "bookid is not null and ";
			if(type!=null && !type.equals("")){
				typesql = "type=" + "\'"+type+"\' and ";
			}
			String venuesql = "bookid is not null and ";
			if(venue!=null && !venue.equals("")){
				venuesql = "lower(venue) LIKE " + "\'%"+venue+"%\' and ";
			}
			String authorsql = "bookid is not null and ";
			if(author!=null && !author.equals("")){
				authorsql = "lower(author) LIKE " + "\'%"+author+"%\' and ";
			}
			String titlesql = "bookid is not null and ";
			if(title!=null && !title.equals("")){
				titlesql = "lower(title) LIKE " + "\'%"+title+"%\' and ";
			}
			String yearsql = "bookid is not null";
			if(year1!=null && year2!=null){
				yearsql = "publicdate>="+year1+" and publicdate<="+year2;
			}
			sql = "SELECT * from Books where " + typesql + venuesql + authorsql 
					+ titlesql + yearsql;
			System.out.println(sql);
			result_list = Books.select(sql);
			System.out.println(result_list.size());
			request.getSession().setAttribute("sql", sql);
			sendResult(0, request, response);

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
		if(request.getParameter("isAdmin")==null){
			request.setAttribute("result", subresult_list);
			request.getRequestDispatcher("/Views/Search.jsp").forward(request, response);
		}else{
			request.setAttribute("result", subresult_list);
			request.getRequestDispatcher("/Views/Admin.jsp").forward(request, response);
		}
		
	}

}
