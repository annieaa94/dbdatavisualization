package com.conn;

import java.io.IOException;
import java.sql.*;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/BAServlet3")
public class BAServlet3 extends HttpServlet {

	/**
	 * 
	 */
	//String query = ""; 
	private static final long serialVersionUID = 1L;
	String instrument_name = "";
	String url = "jdbc:mysql://192.168.99.100:3306/db_grad_cs_1917?autoReconnect=true&useSSL=false";
	String username = "root";
	String password = "ppp";
	Statement smt1,smt2;
	@Override
	public void init() throws ServletException {
		
		Connection conn;
		try {
			Class.forName("com.mysql.jdbc.Driver"); 
			conn = DriverManager.getConnection(url,username ,password );
			 smt1 = conn.createStatement();
			 smt2 = conn.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

		
		super.init();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		instrument_name = request.getParameter("selected_id");
		
		validate(response);
		

	}

	protected void validate(HttpServletResponse response) throws IOException {
		HashMap<String,String> hmBuy=new HashMap<String,String>();
		HashMap<String,String> hmSell=new HashMap<String,String>();
		HashMap<String,HashMap<String,String>> data=new HashMap<String,HashMap<String,String>>();
		ResultSet rsBuy,rsSell;
		String st1="";
		try {
			//String query="select "+table+"_name from "+table;
			String buyQuery="select date_format(deal_time,'%y-%m-%d %H:%i') as date , count(*) as buy from deal join instrument on instrument_id=deal_instrument_id where deal_type='B' and instrument_name='"+instrument_name+"'group by date_format(deal_time,'%y-%m-%d %H:%i') order by 1" ;
			String sellQuery="select date_format(deal_time,'%y-%m-%d %H:%i') as date , count(*) as sell from deal join instrument on instrument_id=deal_instrument_id where deal_type='S' and instrument_name='"+instrument_name+"'group by date_format(deal_time,'%y-%m-%d %H:%i') order by 1" ;
			
			//System.out.println(query);
			rsBuy = smt1.executeQuery(buyQuery);
			rsSell= smt2.executeQuery(sellQuery);
			
			while(rsBuy.next()){
				hmBuy.put(rsBuy.getString(1),rsBuy.getString(2));
			}
			
			while(rsSell.next()){
				hmSell.put(rsSell.getString(1),rsSell.getString(2));
			}
			
			data.put("buy", hmBuy);
			data.put("sell", hmSell);
		} catch (SQLException e) {
			e.printStackTrace();
			
		}
		st1=JsonUtil.converJavaToString(data);
		
	      response.setContentType("application/json");
	  	  response.getWriter().write(st1);
	  	 
	}

}