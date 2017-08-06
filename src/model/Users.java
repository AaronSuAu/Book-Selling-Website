package model;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import assignment1.DB;

public class Users {
	public static List<Map<String, String>> select(String sql) {
		Connection connection= DB.getConnection();
		List<Map<String, String>> result_list = new ArrayList<>();
		java.sql.ResultSet rSet = null;
		rSet = DB.generalSelect(sql, connection);
		try {
			while(rSet.next()){
				Map<String, String> map = new HashMap<>();
				map.put("userid", String.valueOf(rSet.getInt("userid")));
				map.put("firstname", rSet.getString("firstname"));
				map.put("lastname", rSet.getString("lastname"));
				map.put("username", rSet.getString("username"));
				map.put("nickname", rSet.getString("nickname"));
				map.put("email", rSet.getString("email"));
				map.put("birthyear", rSet.getString("birthyear"));
				map.put("address", rSet.getString("address"));
				map.put("creditcard", rSet.getString("creditcard"));
				map.put("ifconfirmed", String.valueOf(rSet.getInt("ifconfirmed")));
				map.put("role", rSet.getString("role"));
				map.put("password", rSet.getString("password"));
				result_list.add(map);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			DB.closeConnection(connection);
		}
		DB.closeConnection(connection);
		return result_list;
	}
	
	/*
	 * insert
	 */
	public static boolean insert(String values){
		Connection connection= DB.getConnection();
		String sql = "INSERT INTO Users (username,password,firstname,lastname,nickname,"
				+ "email,birthyear,address,creditcard,ifconfirmed,role)VALUES(" + values +")";
		try {
			DB.generalInsert(sql, connection);
		} catch (Exception e) {
			// TODO: handle exception
			DB.closeConnection(connection);
			return false;		
		}
		DB.closeConnection(connection);
		return true;
	}
	/*
	 * update
	 */
	public static void update(String key, String value, String username){
		Connection connection= DB.getConnection();
		String sql = "UPDATE Users SET " + key + " = \'" + value + "\' "
				+ "WHERE username = \'" + username + "\'";
		try{
			DB.generalUpdate(sql, connection);
		}catch (Exception e) {
			// TODO: handle exception
			DB.closeConnection(connection);
		}
		DB.closeConnection(connection);
		
	}

	/*
	 * check whether there is such a username
	 */
	public static int ifExist(String username){
		Connection connection= DB.getConnection();
		String sql = "SELECT * FROM Users where username = \'" + username + "\'";
		try{
			java.sql.ResultSet rSet = DB.generalSelect(sql, connection);
			if (rSet.next()) {
				return 1;
			}
			else {
				return 0;
			}
		}catch (Exception e) {
			// TODO: handle exception
			DB.closeConnection(connection);
		}
		DB.closeConnection(connection);
		return 0;
	}
	
	/*
	 * get userid by username
	 */
	public static String getUserid(String username){
		Connection connection= DB.getConnection();
		String sql = "SELECT * FROM Users where username = \'" + username + "\'";
		try{
			java.sql.ResultSet rSet = DB.generalSelect(sql, connection);
			if (rSet.next()) {
				return String.valueOf(rSet.getInt("userid"));
			}
			else {
				return "not exist";
			}
		}catch (Exception e) {
			// TODO: handle exception
			DB.closeConnection(connection);
		}
		DB.closeConnection(connection);
		return "not exist";
	}


	/*
	 * get userlog by userid
	 */
	public static List<Map<String, String>> getUserlog(String userid){
		List<Map<String, String>> result = new ArrayList<Map<String, String>>();
		Connection connection= DB.getConnection();
		String sql = "SELECT * FROM LOG where userid = \'" + userid + "\'";
		java.sql.ResultSet rSet = DB.generalSelect(sql, connection);
		try{
			while(rSet.next()){
				Map<String, String> map = new HashMap<>();
				map.put("userid", String.valueOf(rSet.getInt("userid")));
				map.put("bookid", String.valueOf(rSet.getInt("bookid")));
				String at = String.valueOf(rSet.getInt("actiontype"));
				if (at.equals("1")) {
					at = "remove books from shopping cart";
				}
				else {
					at = "buy";
				}
				map.put("actiontype", at);
				map.put("actiontime", rSet.getString("actiontime"));
				map.put("buyingprice", rSet.getString("buyingprice"));
				result.add(map);
			}
		}catch (Exception e) {
			// TODO: handle exception
			DB.closeConnection(connection);
		}
		DB.closeConnection(connection);
		return result;
	}	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
