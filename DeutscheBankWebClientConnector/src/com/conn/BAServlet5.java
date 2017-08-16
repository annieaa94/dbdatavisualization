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

@WebServlet("/BAServlet5")
public class BAServlet5 extends HttpServlet {

	/**
	 * 
	 */
	//String query = ""; 
	private static final long serialVersionUID = 1L;
	String url = "jdbc:mysql://192.168.99.100:3306/db_grad_cs_1917?autoReconnect=true&useSSL=false";
	String username = "root";
	String password = "ppp";
	String counterparty_name = "";
	Statement smt1,smt2;
	@Override
	public void init() throws ServletException {
		
		Connection conn;
		try {
			Class.forName("com.mysql.jdbc.Driver"); 
			conn = DriverManager.getConnection(url,username ,password );
			 smt1 = conn.createStatement();
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
		counterparty_name = request.getParameter("selected_id");
		validate(response);
		

	}

	protected void validate(HttpServletResponse response) throws IOException {
		ArrayList<HashMap<String,String>> arr=new ArrayList<HashMap<String,String>>();
		ResultSet rsNet;
		String st1="";
		try {
			//String query="select "+table+"_name from "+table;
			String netQuery="select i.instrument_name,abs((ifnull(buy,0)-ifnull(sell,0))) net_trades from (select instrument_name ,instrument_id from instrument) i left join (select deal_instrument_id ,sum(deal_quantity) buy from deal where deal_type= 'B' and deal_counterparty_id= (select counterparty_id from counterparty where counterparty_name = '"+counterparty_name+"') group by deal_instrument_id) b on  b.deal_instrument_id = i.instrument_id left join (select deal_instrument_id ,  sum(deal_quantity) sell from deal where deal_type= 'S'  and deal_counterparty_id=(select counterparty_id from counterparty where counterparty_name = '"+counterparty_name+"') group by deal_instrument_id) s on s.deal_instrument_id = i.instrument_id" ;
			//System.out.println(query);
			rsNet = smt1.executeQuery(netQuery);
			
			while(rsNet.next()){
				HashMap<String,String> hm=new HashMap<String,String>(); 
				hm.put("instrument_name",rsNet.getString(1));
				hm.put("net_trades", rsNet.getString(2));
				arr.add(hm);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			
		}
		st1=JsonUtil.converJavaToString(arr);
		//st2=JsonUtil.converJavaToString(hmSell);
	      response.setContentType("application/json");
	  	  response.getWriter().write(st1);
	  	 // response.getWriter().write(st2);
	}

}