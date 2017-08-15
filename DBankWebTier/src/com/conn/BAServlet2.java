package com.conn;

import java.io.IOException;
import java.sql.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/BAServlet2")
public class BAServlet2 extends HttpServlet {

	/**
	 * 
	 */
	//String query = ""; 
	private static final long serialVersionUID = 1L;
	String counter_party_name = "";
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
		counter_party_name = request.getParameter("selected_id");
		
		validate(response);
		

	}

	protected void validate(HttpServletResponse response) throws IOException {
		
		ResultSet rsBuy,rsSell;
		String st1="",st2="";
		try {
			//String query="select "+table+"_name from "+table;
			String buyQuery="select i.instrument_name, count(*) as buy from deal d join instrument i on i.instrument_id = d.deal_instrument_id join counterparty c on c.counterparty_id = d.deal_counterparty_id where deal_type='B' and counterparty_name='"+counter_party_name+"' group by i.instrument_name " ;
			String sellQuery="select i.instrument_name, count(*) as sell from deal d join instrument i on i.instrument_id = d.deal_instrument_id join counterparty c on c.counterparty_id = d.deal_counterparty_id where deal_type='S' and counterparty_name='"+counter_party_name+"' group by i.instrument_name" ;
			
			//System.out.println(query);
			rsBuy = smt1.executeQuery(buyQuery);
			rsSell= smt2.executeQuery(sellQuery);
			st1=JsonUtil.converJavaToString(rsBuy);
			st2=JsonUtil.converJavaToString(rsSell);
			while(rsBuy.next()){
				System.out.print(rsBuy.getString(1)+" ");
				System.out.println(rsBuy.getString(2)+" ");
			}
			System.out.println("");
			while(rsSell.next()){
				System.out.print(rsSell.getString(1)+" ");
				System.out.println(rsSell.getString(2)+" ");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			
		}
		   
	      response.setContentType("application/json");
	  	  response.getWriter().write(st1);
	  	  response.getWriter().write(st2);
	}

}