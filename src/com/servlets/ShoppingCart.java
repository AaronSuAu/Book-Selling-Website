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
import java.sql.Timestamp;

import assignment1.DB;
import model.Books;
import model.Cart;
import model.Log;
import java.util.*;

@WebServlet({ "/ShoppingCart", })
public class ShoppingCart extends HttpServlet {
  private static final long serialVersionUID = 1L;

  public ShoppingCart() {
    super();
  }

  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    String userid = request.getSession().getAttribute("userid").toString();

    if (request.getParameter("type") != null) {       // post request, no need to redirect add to shopping cart
      String[] bookidArray = request.getParameterValues("bookid[]");
      String rtype = request.getParameter("type").toString();
      if (rtype.equals("0")) {					//add books
        if(Cart.insert(userid, bookidArray[0], "Shopping_Cart")){
        		response.getWriter().write("Success");
        }else{
    			response.getWriter().write("You have added to the shopping cart");
        }
      } else if (rtype.equals("1")) {				// delete books
    	  	System.out.println("aaaaaaaa");
        for (String bookid : bookidArray) {
          Cart.delete(userid, bookid, "Shopping_Cart");
          String timestamp = new Timestamp(System.currentTimeMillis()).toString();
          String v = userid + "," + bookid + ",1,\'" + timestamp  + "\'," + Books.getPrice(bookid);
          boolean feedback = Log.insert(v);
        }
      }
    } else{                       // find the users books in the shopping cart
      List<Integer> bookidList = Cart.selectByUserId(Integer.parseInt(userid), "Shopping_Cart");
      List<Map<String, String>> cartResult = new ArrayList<Map<String, String>>();
      for (Integer bookid : bookidList) {
        List<Map<String, String>> book_list = Books
            .select("select * from  books where bookid =\'" + Integer.toString(bookid) + "\' and pause = 0");
        cartResult.add(book_list.get(0));
      }
      request.setAttribute("result", cartResult);
      request.getRequestDispatcher("/Views/ShoppingCart.jsp").forward(request, response);
    }
  }

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    doGet(request, response);
  }

}