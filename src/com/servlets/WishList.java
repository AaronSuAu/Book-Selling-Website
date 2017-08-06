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
import model.Cart;
import java.util.*;

@WebServlet({ "/WishList", })
public class WishList extends HttpServlet {
  private static final long serialVersionUID = 1L;

  public WishList() {
    super();
  }

  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    String userid = request.getSession().getAttribute("userid").toString();

    if (request.getParameter("type") != null) {       // post request, no need to redirect
      String[] bookidArray = request.getParameterValues("bookid[]");
      String rtype = request.getParameter("type").toString();
      if (rtype.equals("2")) {							//add books to the wishlist
        if(Cart.insert(userid, bookidArray[0], "Wish_List")){
    			response.getWriter().write("Success");
        }else{
			response.getWriter().write("You have added to the wish list!");

        }
      } else if (rtype.equals("3")) {				//delete
        for (String bookid : bookidArray) {
          Cart.delete(userid, bookid, "Wish_List");
        }
      } else if (rtype.equals("7")) {
          for (String bookid : bookidArray) {		//move to shopping cart
              Cart.delete(userid, bookid, "Wish_List");
              Cart.insert(userid, bookid, "Shopping_Cart");
            }
      }
    } else{                       // find the users books in the wish list
      List<Integer> bookidList = Cart.selectByUserId(Integer.parseInt(userid), "Wish_List");
      List<Map<String, String>> wishResult = new ArrayList<Map<String, String>>();
      for (Integer bookid : bookidList) {
        List<Map<String, String>> book_list = Books
            .select("select * from  books where bookid =\'" + Integer.toString(bookid) + "\' and pause = 0");
        wishResult.add(book_list.get(0));
      }
      request.setAttribute("result", wishResult);
      request.getRequestDispatcher("/Views/WishList.jsp").forward(request, response);
    }
  }

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    doGet(request, response);
  }

}