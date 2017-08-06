package com.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.jasper.tagplugins.jstl.core.If;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import assignment1.DB;
import model.Books;
import model.Users;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String error = "";
		String inputUsername = request.getParameter("username");
		String inputPassword = request.getParameter("password");
		
		
		//TODO get user record from database
		List<Map<String, String>> result_list = new ArrayList<>();
		result_list = Users.select("select * from  users where username =\'"+ inputUsername +"\'");
		//check input
		
		if(result_list.size() != 1){
			error = " Wrong Username!";
			response.getWriter().write("Failed");
			request.setAttribute("Error", error);
			request.getRequestDispatcher("/Views/Login.jsp").forward(request, response);
		}else if (result_list.get(0).get("role").equals("3")) {
			error = " Sorry, you are blocked!";
			request.setAttribute("Error", error);
			request.getRequestDispatcher("/Views/Login.jsp").forward(request, response);
		}else if (result_list.get(0).get("ifconfirmed").equals("0")) {
			error = " Sorry, you need to confirm your email!";
			request.setAttribute("Error", error);
			request.getRequestDispatcher("/Views/Login.jsp").forward(request, response);
		}else{
			String passFromDatabase = result_list.get(0).get("password");
			if(passFromDatabase.equals(inputPassword)){
				request.getSession().setAttribute("username", inputUsername);
				request.getSession().setAttribute("userid", result_list.get(0).get("userid"));
				request.getSession().setAttribute("role", result_list.get(0).get("role"));
				//get random 10 data
				List<Map<String, String>> result = new ArrayList<>();
				int totalNum = Books.getTotalNum();
				final int startOfRandom10 = (int)(Math.random()*(totalNum-10));
				String sql = "SELECT * FROM books LIMIT " + startOfRandom10 +",10";
				result = Books.select(sql);
				request.setAttribute("result", result);
				request.setAttribute("isRandom", "1");
				if(result_list.get(0).get("role").equals("2") && request.getParameter("isFromAdmin")!=null){
				     request.getRequestDispatcher("/Views/Admin.jsp").forward(request, response);
				    }else{
				     request.getRequestDispatcher("/Views/Search.jsp").forward(request, response);
				    }
			}else{
				error = " Wrong password!";
				request.setAttribute("Error", error);
				request.getRequestDispatcher("/Views/Login.jsp").forward(request, response);
			}
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
