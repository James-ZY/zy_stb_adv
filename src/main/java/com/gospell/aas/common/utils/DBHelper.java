package com.gospell.aas.common.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.gospell.aas.common.config.Global;

public class DBHelper{
	    public static final String url = Global.getConfig("jdbc.url");
	    public static final String name =  Global.getConfig("jdbc.driverClassName");
	    public static final String user =  Global.getConfig("jdbc.username");
	    public static final String password =  Global.getConfig("jdbc.password");
	    
	    public Connection conn = null;  
	    public PreparedStatement pst = null;  
	  
	    public DBHelper(String sql) {  
	        try {  
	            Class.forName(name);//指定连接类型  
	            conn = DriverManager.getConnection(url, user, password);//获取连接  
	            pst = conn.prepareStatement(sql);//准备执行语句  
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        }  
	    }  
	  
	    public void close() {  
	        try {  
	            this.conn.close();  
	            this.pst.close();  
	        } catch (SQLException e) {  
	            e.printStackTrace();  
	        }  
	    }  
}
