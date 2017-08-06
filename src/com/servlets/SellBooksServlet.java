package com.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.jdbc.ResultSet;

import assignment1.DB;
import model.Books;
import model.Cart;
import model.Log;

/**
 * Servlet implementation class SellBooksServlet
 */
@WebServlet("/sellbook")
public class SellBooksServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SellBooksServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		String[] booksToBuy = request.getParameterValues("bookid[]");
		String userid = (String)request.getSession().getAttribute("userid");
		String error = "";
		
		boolean result = true;
		System.out.println(booksToBuy.length);
		for(int i=0; i< booksToBuy.length;i++){
			System.out.println(booksToBuy[i]);
		}
		for(int i=0;i<booksToBuy.length;i++){
			result = result && Cart.delete(userid, booksToBuy[i], "Shopping_Cart");
			String timestamp = new Timestamp(System.currentTimeMillis()).toString();
			result = result && Log.insert(userid+","+booksToBuy[i]+",0,'"+timestamp+"',"+Books.getPrice(booksToBuy[i]));
			if(!result){
				error = "Cannoy buy book[id=" + booksToBuy[i] + "] !";
				break;
			}
		}
		if(result){
			response.getWriter().append("success");
			try {
				sendemails(booksToBuy);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			response.getWriter().append(error);
		}
		
	}

	private void sendemails(String[] booksToBuy) throws SQLException {
		// TODO Auto-generated method stub
		String sql = "";
		String email = "";
		String seller = "";
		String bookname = "";
//		String time = "";
		
		//set eamil
		final String emailusername = "comp9321unsw@gmail.com";
		final String emailpassword = "zhangzhe+1s";
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
		
		//get email of sellers from database
		Connection connection= DB.getConnection();
		for(int i=0;i<booksToBuy.length;i++){
			sql = "select * from books, users where books.bookid=" + booksToBuy[i] 
					+ " and books.sellerid = users.userid";
			java.sql.ResultSet rSet = DB.generalSelect(sql, connection);
			while(rSet.next()){
				email = rSet.getString("email");
				seller = rSet.getString("username");
				bookname = rSet.getString("title");
			}
			
			//send email
			try {

				Message message = new MimeMessage(session);
				message.setFrom(new InternetAddress("comp9321unsw@gmail.com"));
				if(email != null){
					message.setRecipients(Message.RecipientType.TO,
							InternetAddress.parse(email));
						message.setSubject("DBLP book sold reminder");
						message.setText("Dear "+seller+",\nyour good [" + bookname + "] is sold." );

						Transport.send(message);

				}
				

			} catch (MessagingException e) {
				throw new RuntimeException(e);
			}
		}
		DB.closeConnection(connection);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
