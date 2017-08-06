package com.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

import java.util.Properties;


import model.Users;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterServlet() {
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
		
		//check username and email
		String username = request.getParameter("username").trim();
		String email = request.getParameter("email").trim();
		List<Map<String, String>> usernameCheck = Users.select("select * from  users where username =\'"+ username +"\'");
		List<Map<String, String>> emailCheck = Users.select("select * from  users where email =\'"+ email +"\'");
		if(usernameCheck.size() != 0){
			error = " Username already exists !";
			response.getWriter().write(error);
		}else if(emailCheck.size() != 0){
			error = " Email already exists !";
			response.getWriter().write(error); 
		}else{
			String password = "\'"+request.getParameter("password").trim()+"\'";	
			String nickname = "\'"+request.getParameter("nickname").trim()+"\'";
			String first_name = "\'"+request.getParameter("first_name").trim()+"\'";
			String last_name = "\'"+request.getParameter("last_name").trim()+"\'";
			String year_of_birth = "\'"+request.getParameter("year_of_birth").trim()+"\'";
			String credit_card = "\'"+request.getParameter("credit_card").trim()+"\'";
			String full_address = "\'"+request.getParameter("full_address").trim()+"\'";
			String values = "\'"+username+"\'," + password + "," + first_name + "," + last_name +","
					+ nickname + ",\'" + email + "\'," + year_of_birth + "," + full_address + "," 
					+ credit_card + ",0,0";
			boolean result = Users.insert(values);
			//return result
			if(result){
				response.getWriter().write("success");  
			}else{
				response.getWriter().write("Fail !");  
			}
		    System.out.println("1");
			
			
			//send email
			final String emailusername = "comp9321unsw@gmail.com";
			final String emailpassword = "zhangzhe+1s";
			final String url = "http://localhost:8080/Assignment2/confirmEmail?username=" + username;
			Properties props = new Properties();
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.host", "smtp.gmail.com");
			props.put("mail.smtp.port", "587");

			Session session = Session.getInstance(props,
			  new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(emailusername, emailpassword);
				}
			  });
			try {

				Message message = new MimeMessage(session);
				message.setFrom(new InternetAddress("comp9321unsw@gmail.com"));
				message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(email));
				message.setSubject("DBLP account confirmation");
				message.setText("Dear " + nickname + ", please click the link to confirm your e-mail/n!");
				message.setContent("<a href=" + url + ">Confirm your mail</a>","text/html");

				Transport.send(message);


			} catch (MessagingException e) {
				throw new RuntimeException(e);
			}
		    System.out.println("2");

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
