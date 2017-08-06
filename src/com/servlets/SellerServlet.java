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

@WebServlet({ "/Seller", })
public class SellerServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  public SellerServlet() {
    super();
  }

  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    String sellerid = request.getSession().getAttribute("userid").toString();

    // seller pause(1) or unpause(0) books based on value of ifPause (4 / 5)
    if(request.getParameterValues("bookid[]")!=null){
	    	String[] bookidArray = request.getParameterValues("bookid[]");
	    	System.out.println("1");
	    if (bookidArray.length != 0) {
	        String ifPause = request.getParameter("type").toString();
	        if (ifPause.equals("4")) {
	        		System.out.println("2");
	           ifPause = "1";
	        } else {
	        	System.out.println("3");
	           ifPause = "0";
	        }
	        Books.pauseBooks(sellerid, bookidArray, ifPause);
	    }
    }else{
    	// get bookid of books which is paused or unpaused
    		if(request.getParameter("pause") == null){
    	        request.setAttribute("result", Books.getBooks(sellerid, "0"));
    		}else{
    	        request.setAttribute("result", Books.getBooks(sellerid, "1"));
    		}
    	    request.getRequestDispatcher("/Views/Seller.jsp").forward(request, response);
    }
  }
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    doGet(request, response);
  }

}