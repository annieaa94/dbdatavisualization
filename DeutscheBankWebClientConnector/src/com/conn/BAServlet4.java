package com.conn;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/BAServlet4")
public class BAServlet4 extends HttpServlet {

	/**
	 * 
	 */
	//String query = ""; 
	private static final long serialVersionUID = 1L;
	String url = "jdbc:mysql://192.168.99.100:3306/db_grad_cs_1917";
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
		
		validate(response);
		

	}

	protected void validate(HttpServletResponse response) throws IOException {
		ArrayList<HashMap<String,String>> arr=new ArrayList<HashMap<String,String>>();
		ResultSet rsBuy,rsSell;
		String st1="";
		try {
			//String query="select "+table+"_name from "+table;
			String buyQuery="SELECT instrument_name as Instrument,  avg(deal_amount) as AverageBuyPrice from deal join instrument i on i.instrument_id = deal.deal_instrument_id where deal_type = 'B' group by instrument_name" ;
			String sellQuery="SELECT instrument_name as Instrument,  avg(deal_amount) as AverageSellPrice from deal join instrument i on i.instrument_id = deal.deal_instrument_id where deal_type = 'S' group by instrument_name;" ;
			
			//System.out.println(query);
			rsBuy = smt1.executeQuery(buyQuery);
			rsSell= smt2.executeQuery(sellQuery);
			
			while(rsBuy.next() && rsSell.next()){
				HashMap<String,String> hm=new HashMap<String,String>(); 
				hm.put("instrument_name",rsBuy.getString(1));
				hm.put("buy_quantity", rsBuy.getString(2));
				hm.put("sell_quantity", rsSell.getString(2));
				arr.add(hm);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			
		}
		 st1=JsonUtil.converJavaToString(arr);
		 response.setContentType("application/json");
	  	  response.getWriter().write(st1);
	  	  
	}

}