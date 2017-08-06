package com.servlets;
import model.Users;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ChangeUserInfoServlet
 */
@WebServlet("/user/changeUserInfo")
public class ChangeUserInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChangeUserInfoServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		response.getWriter().append("Served at: ").append(request.getContextPath());
		String error;
		String key = request.getParameter("key").trim();
		String value = request.getParameter("value").trim();
		if(key.equals("email")){
			Users.update(key, value, (String)request.getSession().getAttribute("username"));
			List<Map<String, String>> emailCheck = Users.select("select * from  users where email =\'"+ value +"\'");
			if(emailCheck.size() != 0){
				error = " Email already exists !";
				response.getWriter().write(error);
			}else{
				Users.update(key, value, (String)request.getSession().getAttribute("username"));
				response.getWriter().append("success");
			}
		}else{
			Users.update(key, value, (String)request.getSession().getAttribute("username"));
			response.getWriter().append("success");
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
