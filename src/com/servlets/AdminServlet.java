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
import model.Users;
import model.Books;
import model.Cart;
import model.Log;
import java.util.*;

@WebServlet({ "/Admin", })
public class AdminServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  public AdminServlet() {
    super();
  }

  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    String key = request.getParameter("key").toString();
    String username = request.getParameter("value").toString();

    if (key.equals("1")) {
      if (Users.ifExist(username) == 1) {
        Users.update("role", "3", username);
        response.getWriter().append("Success");
      }
      else {
        response.getWriter().append("The user doesn't exist");
      }
    }

    if (key.equals("2")) {
      List<Map<String, String>> result = new ArrayList<Map<String, String>>();
      String userid = Users.getUserid(username);
      System.out.println(username);
      if (!userid.equals("not exist")) {
        result = Users.getUserlog(userid);
      }
      request.setAttribute("userlog", result);
      request.getRequestDispatcher("/Views/Admin.jsp").forward(request, response);
    }
  }

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    doGet(request, response);
  }

}