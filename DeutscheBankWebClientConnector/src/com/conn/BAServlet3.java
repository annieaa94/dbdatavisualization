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
		ArrayList<HashMap<String,String>> arr=new ArrayList<HashMap<String,String>>();
		
		ResultSet rsBuy,rsSell;
		String st1="";
		try {
			//String query="select "+table+"_name from "+table;
			String buyQuery="select tm.time_minute, d.deal_amount "+
"from ( "+
		"select str_to_date(date_format(deal_time, '%Y-%m-%d %H:%i'), '%Y-%m-%d %H:%i') time_minute "+
		"from deal "+
		"where deal_type = 'B' and deal_instrument_id=(select instrument_id from instrument where instrument_name = '"+ instrument_name + "')" +
		"group by str_to_date(date_format(deal_time, '%Y-%m-%d %H:%i'), '%Y-%m-%d %H:%i') "+
	") tm "+
	"join ( "+
	"	select deal_time, max(deal_id) deal_id "+
	"	from deal d "+
    "    where deal_instrument_id = (select instrument_id from instrument where instrument_name = '"+ instrument_name + "')"+
	"		and deal_type = 'B' "+
	"	group by deal_time "+
"	) d1 on d1.deal_id = ( "+
"		select deal_id "+
   "     from ( "+
	"		select deal_time, max(deal_id) deal_id "+
	"		from deal d "+
	"		where deal_instrument_id = (select instrument_id from instrument where instrument_name = '"+ instrument_name + "')" +
	"			and deal_type = 'B' "+
	"		group by deal_time "+
	"	) d1 "+
      "  where d1.deal_time < tm.time_minute + interval 1 minute "+
      "  order by deal_time desc "+
       " limit 1 "+
	") "+
    "join deal d on d.deal_id = d1.deal_id";
			String sellQuery="select tm.time_minute, d.deal_amount "+
					"from ( "+
					"select str_to_date(date_format(deal_time, '%Y-%m-%d %H:%i'), '%Y-%m-%d %H:%i') time_minute "+
					"from deal "+
					"where deal_type = 'S' and deal_instrument_id=(select instrument_id from instrument where instrument_name = '"+ instrument_name + "')" +
					"group by str_to_date(date_format(deal_time, '%Y-%m-%d %H:%i'), '%Y-%m-%d %H:%i') "+
				") tm "+
				"join ( "+
				"	select deal_time, max(deal_id) deal_id "+
				"	from deal d "+
			    "    where deal_instrument_id = (select instrument_id from instrument where instrument_name = '"+ instrument_name + "')" +
				"		and deal_type = 'S' "+
				"	group by deal_time "+
			"	) d1 on d1.deal_id = ( "+
			"		select deal_id "+
			   "     from ( "+
				"		select deal_time, max(deal_id) deal_id "+
				"		from deal d "+
				"		where deal_instrument_id = (select instrument_id from instrument where instrument_name = '"+ instrument_name + "')" +
				"			and deal_type = 'S' "+
				"		group by deal_time "+
				"	) d1 "+
			      "  where d1.deal_time < tm.time_minute + interval 1 minute "+
			      "  order by deal_time desc "+
			       " limit 1 "+
				") "+
			    "join deal d on d.deal_id = d1.deal_id";
			
			//System.out.println(query);
			rsBuy = smt1.executeQuery(buyQuery);
			rsSell= smt2.executeQuery(sellQuery);
			
			while(rsBuy.next() && rsSell.next()){
				HashMap<String,String> hm=new HashMap<String,String>(); 
				hm.put("Time",rsBuy.getString(1));
				hm.put("buy_price", rsBuy.getString(2));
				hm.put("sell_price", rsSell.getString(2));
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