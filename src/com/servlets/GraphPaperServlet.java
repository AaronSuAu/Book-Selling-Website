package com.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import assignment1.DB;

/**
 * Servlet implementation class GraphPaperServlet
 */
@WebServlet("/GraphPaperServlet")
public class GraphPaperServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GraphPaperServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		long start = System.currentTimeMillis();
		String paper = request.getParameter("type").toString().trim();
		String key = request.getParameter("keyName").toString().trim();
		String entityName = "title";
		if(key.equals("3")){
			entityName = "name";
		}else if(key.equals("0")){
			entityName = "title";
		}
		Connection conn = DB.getConnection();
		String sql = "select * from entityStore where attribute_value='"+paper+"';";
		java.sql.ResultSet rSet = DB.generalSelect(sql, conn);
		java.sql.ResultSet rSet2 = null;
		List<String> authorList = new ArrayList<>();
		Set<String> authorListResult = new HashSet<>(); 
		System.out.println("start");
		try {
			if(rSet.next()){
				String entity_id = rSet.getString("entity_id");
				if(key.equals("3")){
					sql = "select * from graph where node_from = '"+entity_id+"'";
				}else{
					sql = "select * from graph where node_to = '"+entity_id+"'";
				}
				rSet2 = DB.generalSelect(sql, conn);
				while(rSet2.next()){
					if(key.equals("3")){
						authorList.add(rSet2.getString("node_to"));
					} else{
						authorList.add(rSet2.getString("node_from"));
					}
				}
				for(String s:authorList){
					sql = "select * from entityStore where entity_attribute = '"+entityName+"' and entity_id = '"+s+"'";
					java.sql.ResultSet rSet3 = DB.generalSelect(sql, conn);
					System.out.println(s);
					if(rSet3.next()){
						authorListResult.add(rSet3.getString("attribute_value"));
					}
				}
				for(String s:authorListResult){
					//System.out.println(s);
				}
				System.out.println(authorListResult.size());
			}else{
				
			}
			if(authorListResult.isEmpty()){
				//System.out.println("No result");
			}
			request.setAttribute("resultGraph", authorListResult);
			request.setAttribute("target", paper);
		    request.getRequestDispatcher("/Views/Graph.jsp").forward(request, response);
			long end = System.currentTimeMillis();
			System.out.println(end - start);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
