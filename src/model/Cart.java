package model;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import assignment1.DB;

//table shopping_cart
public class Cart {
	public static List<Integer> selectByBookId(int book_id, String table_name) {
		Connection connection= DB.getConnection();
		List<Integer> result_list = new ArrayList<>();
		java.sql.ResultSet rSet = null;
		String sql = "select * from " + table_name + " where book_id = '" + String.valueOf(book_id) + "'";

		rSet = DB.generalSelect(sql, connection);
		try {
			while(rSet.next()){
				result_list.add(rSet.getInt("userid"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			DB.closeConnection(connection);
			e.printStackTrace();
		}
		DB.closeConnection(connection);
		return result_list;
	}
	
	public static List<Integer> selectByUserId(int user_id, String table_name) {
		Connection connection= DB.getConnection();
		List<Integer> result_list = new ArrayList<>();
		java.sql.ResultSet rSet = null;
		String sql = "select * from " + table_name + " where userid = '" + String.valueOf(user_id) + "'";
		rSet = DB.generalSelect(sql, connection);
		try {
			while(rSet.next()){
				result_list.add(rSet.getInt("bookid"));
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
	public static boolean insert(String userid, String bookid, String table){
		Connection connection= DB.getConnection();
		String sql = "INSERT INTO " + table + " (userid,bookid)VALUES(" + userid + "," + bookid+")";
		try {
			return DB.generalInsert(sql, connection);
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}finally {
			DB.closeConnection(connection);

		}
	}
	
	/*
	 * delete
	 */
	public static boolean delete(String userid, String bookid, String table){
		Connection connection= DB.getConnection();
		String sql = "DELETE FROM " + table + " where userid = \'" + userid + "\' and bookid = \'" + bookid + "\'";
		try {
			return DB.generalDelete(sql, connection);
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		} finally {
			DB.closeConnection(connection);
		}
		
	}
	public static boolean delete(String bookid, String table){
		Connection connection= DB.getConnection();
		String sql = "DELETE FROM " + table + " where  bookid = \'" + bookid + "\'";
		try {
			DB.generalDelete(sql, connection);
		} catch (Exception e) {
			// TODO: handle exception
			DB.closeConnection(connection);
			return false;
		}
		DB.closeConnection(connection);
		return true;
	}
}
