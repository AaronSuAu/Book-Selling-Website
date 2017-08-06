package assignment1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.jdbc.ResultSet;






public class DB {
	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	static final String DB_URL = "jdbc:mysql://localhost/9321_Ass2";
	
	//  Database credentials
	static final String USER = "root";
	static final String PASS = "root";
	
	public static Connection getConnection(){
		try {
			Class.forName(JDBC_DRIVER);
		} catch (ClassNotFoundException e) {
			System.out.println("Where is your MySQL JDBC Driver?");
			e.printStackTrace();
		}

		System.out.println("MySQL JDBC Driver Registered!");
		Connection connection = null;

		try {
			connection =  DriverManager
			.getConnection(DB_URL, USER, PASS);

		} catch (SQLException e) {
			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
		}
		return connection;
	}
	
	public static void closeConnection(Connection connection){
		try{
			connection.close();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public static java.sql.ResultSet generalSelect(String sql, Connection conn){
		Statement stmt = null;
		java.sql.ResultSet rSet = null;
		try {
			stmt = conn.createStatement();
			rSet = stmt.executeQuery(sql);
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rSet;
	}
	
	public static boolean generalInsert(String sql, Connection conn){
		Statement stmt = null;
	    
	    try {
	    		stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	public static boolean generalDelete(String sql, Connection conn){
		return generalInsert(sql, conn);
	}
	
	public static boolean generalUpdate(String sql, Connection conn){
		return generalInsert(sql, conn);
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Connection connection = DB.getConnection();
//		java.sql.ResultSet rSet = null;
//		Connection connection = DB.getConnection();
//		rSet = DB.generalSelect("select * from Books where title ='title1'", connection);
//		if(rSet == null){
//			System.out.println("1");
//		}else{
//			System.out.println("2");
//		}
//		try {
//			if(rSet.next()){
//				System.out.println("3");
//			}
//			else{
//				System.out.println("4");
//			}
//			DB.getConnection();
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			System.out.println("5");
//			e.printStackTrace();
//		} finally {
//			DB.closeConnection(connection);
//		}
	}

}
