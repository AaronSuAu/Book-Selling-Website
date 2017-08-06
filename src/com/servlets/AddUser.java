package com.servlets;

import java.sql.Connection;

import javax.swing.text.DefaultEditorKit.InsertBreakAction;

import com.sun.org.apache.bcel.internal.generic.Select;

import assignment1.DB;

public class AddUser {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Connection connection = DB.getConnection();
		int i=0;
		for(;i<1000; i++){
			String sql = "insert into users (username, password, ifconfirmed, role) values('"+i+"', 11223344, 1, 1"+")";
			DB.generalInsert(sql, connection);
		}
		
	}

}
