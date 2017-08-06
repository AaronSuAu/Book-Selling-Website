package com.servlets;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Users;

/**
 * Servlet implementation class ConfirmEmailServlet
 */
@WebServlet("/confirmEmail")
public class ConfirmEmailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ConfirmEmailServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		response.getWriter().append("Served at: ").append(request.getContextPath());
		String username = request.getParameter("username").trim();
		List<Map<String, String>> usernameCheck = Users.select("select * from  users where username =\'"+ username +"\'");
		String ifconfirmed = usernameCheck.get(0).get("ifconfirmed");
		if(ifconfirmed.equals("1")){
			response.getWriter().append("This link has expired!");
		}else{
			Users.update("ifconfirmed", "1", username);
			response.getWriter().append("Your email has been confirmed!");
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
