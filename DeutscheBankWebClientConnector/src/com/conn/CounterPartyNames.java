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

@WebServlet("/CounterPartyNames")
public class CounterPartyNames extends HttpServlet {

	/**
	 * 
	 */
	String query = ""; 
	private static final long serialVersionUID = 1L;
	String table = "";
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
		table = request.getParameter("selected_id");
		System.out.println(table);
		validate(response);
		

	}

	protected void validate(HttpServletResponse response) throws IOException {
		
		ArrayList<HashMap<String,String>> arr=new ArrayList<HashMap<String,String>>();
		ResultSet rs;
		String st1="";
		try {
			//String query="select "+table+"_name from "+table;
			String Query="select counterparty_name from counterparty" ;
		
			
			//System.out.println(query);
			rs = smt1.executeQuery(Query);
			while(rs.next()){
				HashMap<String,String> hm=new HashMap<String,String>();
				hm.put("Counterparty_name", rs.getString(1));
				arr.add(hm);
			}
			HashMap<String,String> hm1=new HashMap<String,String>();
			hm1.put("Counterparty_name","All");
			arr.add(hm1);
			
		} catch (SQLException e) {
			e.printStackTrace();
			
		}
		   st1=JsonUtil.converJavaToString(arr);
			
			  
	      response.setContentType("application/json");
	  	  response.getWriter().write(st1);
	  	  
	}

}