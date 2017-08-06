package model;

import java.security.KeyStore.Entry;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import assignment1.DB;

public class Books {
	static java.sql.ResultSet rSet = null;
	public static List<Map<String, String>> select(String sql){
		Connection connection= DB.getConnection();
		List<Map<String, String>> result_list = new ArrayList<>();
		rSet = DB.generalSelect(sql, connection);
		try {
			while(rSet.next()){
				Map<String, String> map = new HashMap<>();
				map.put("bookid", String.valueOf(rSet.getInt("bookid")));
				map.put("title", rSet.getString("title"));
				map.put("type", rSet.getString("type"));
				map.put("author", rSet.getString("author"));
				map.put("publicdate", rSet.getString("publicdate").substring(0,4));
				if(rSet.getString("venue") != null){
					map.put("venue", rSet.getString("venue"));
				}
				map.put("price", String.valueOf(rSet.getInt("price")));
				map.put("imgsrc", rSet.getString("imgsrc"));
				map.put("sellerid", rSet.getString("sellerid"));
				map.put("pause", rSet.getString("pause"));
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
	 * get total num of records
	 */
	public static int getTotalNum(){
		Connection connection= DB.getConnection();
		String sql = "SELECT COUNT(*) AS count FROM books";
		rSet = DB.generalSelect(sql, connection);
		int count;
		try {
			rSet.next();
			count = rSet.getInt("count");
			DB.closeConnection(connection);
			return count;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			DB.closeConnection(connection);
			return 0;
		}
	}
	
	/*
	 * get price of book
	 */
	public static String getPrice(String bookid){
		Connection connection= DB.getConnection();
		String sql = "SELECT * FROM books where bookid = \'" + bookid + "\'";
		rSet = DB.generalSelect(sql, connection);
		try {
			rSet.next();
			String price = String.valueOf(rSet.getInt("price"));
			return price;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			DB.closeConnection(connection);
			return "0";
		}
	}

	/*
	 * get bookid of books which is paused or unpaused
	 */
	public static List<Map<String, String>> getBooks(String sellerid, String ifPaused){
		List<String> books = new ArrayList<String>();
		Connection connection= DB.getConnection();
		String sql = "SELECT * FROM books where sellerid = \'" + sellerid + "\' and pause = \'" + ifPaused + "\'";
		rSet = DB.generalSelect(sql, connection);
		return select(sql);
	}


	/*
	 * seller pause(1) or unpause(0) books
	 */
	public static void pauseBooks(String sellerid, String[] bookidArray, String toPause){
		Connection connection= DB.getConnection();
		for (String bookid : bookidArray) {
			String sql = "UPDATE books SET pause = \'" + toPause + "\' where sellerid = \'" + sellerid + "\' and bookid = \'" + bookid + "\'";
			DB.generalUpdate(sql, connection);
		}
		DB.closeConnection(connection);
	}

	public static boolean insertBook(String sql){
		Connection connection= DB.getConnection();
		try {
			return DB.generalInsert(sql, connection);
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}finally {
			DB.closeConnection(connection);

		}
	}
	
	public static boolean delete(String bookid){
		Connection connection= DB.getConnection();
		String sql = "DELETE from books where bookid=" + bookid;
		try{
			DB.generalDelete(sql, connection);
		}catch (Exception e) {
			// TODO: handle exception
			return false;
		}finally {
			DB.closeConnection(connection);
		}
		return true;
	}

//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		
//		//****************************select********************
//		Connection connection= DB.getConnection();
//		List<Map<String, String>> result_list = new ArrayList<>();
//		result_list = Books.select("select * from books where bookid = 13147", connection);
//		for(Map<String, String> map:result_list){
//			Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator();
//			while(iterator.hasNext()){
//				Map.Entry<String, String> pair = iterator.next();
//				System.out.println(pair.getKey() + ": " + pair.getValue());
//			}
//		}
//		DB.closeConnection(connection);
//		//****************************end of select********************
//
//	}

}
