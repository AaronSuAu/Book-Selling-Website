package model;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sun.corba.se.impl.oa.poa.ActiveObjectMap.Key;
import com.sun.mail.imap.protocol.Status;
import com.sun.org.apache.bcel.internal.generic.NEW;

import assignment1.DB;

public class Log {
	public static List<Map<String, String>> select(String sql) {
		Connection connection= DB.getConnection();
		List<Map<String, String>> result_list = new ArrayList<>();
		java.sql.ResultSet rSet = null;
		rSet = DB.generalSelect(sql, connection);
		try {
			while(rSet.next()){
				Map<String, String> map = new HashMap<>();
				map.put("userid", String.valueOf(rSet.getInt("userid")));
				map.put("bookid", String.valueOf(rSet.getInt("bookid")));
				map.put("actiontype", String.valueOf(rSet.getInt("actiontype")));
				map.put("actiontime", rSet.getString("actiontime"));
				map.put("buyingprice", rSet.getString("buyingprice"));
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
		String sql = "INSERT INTO Log (userid,bookid,actiontype,actiontime,buyingprice)VALUES(" + values +")";
		System.out.println(sql);
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

	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//example of insert
//		String timestamp = new Timestamp(System.currentTimeMillis()).toString();
//		System.out.println(timestamp);
//		String v = "5,35,0,\'" + timestamp  + "\',200";
//		boolean status = insert(v);
//		System.out.println(status);
	}

}
