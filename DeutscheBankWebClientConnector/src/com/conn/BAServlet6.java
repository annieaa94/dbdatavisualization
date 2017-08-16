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

@WebServlet("/BAServlet6")
public class BAServlet6 extends HttpServlet {

	/**
	 * 
	 */
	//String query = ""; 
	private static final long serialVersionUID = 1L;
//	String instrument_name = "";
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
		//instrument_name = request.getParameter("selected_id");
		
		validate(response);
		

	}

	protected void validate(HttpServletResponse response) throws IOException {
		ArrayList<HashMap<String,String>> arr=new ArrayList<HashMap<String,String>>();
		ResultSet rsReal;
		ResultSet rsEffec;
		String st1="";
		try {
			//String query="select "+table+"_name from "+table;
			String tradeQuery1="select c.counterparty_name,(ifnull(sell,0)-ifnull(buy,0)) net_amount from (select counterparty_id, counterparty_name from counterparty ) c left join (select deal_counterparty_id,sum(deal_amount * deal_quantity) buy from deal where deal_type= 'B' group by deal_counterparty_id) b  on  b.deal_counterparty_id=c.counterparty_id left join (select deal_counterparty_id,  sum(deal_amount * deal_quantity) sell from deal where deal_type= 'S'  group by deal_counterparty_id) s  on s.deal_counterparty_id=c.counterparty_id" ;
			String tradeQuery2="select i.counterparty_name" +
				    ", sum((ifnull(buy, 0) - ifnull(sell, 0)) * ifnull(prices.deal_amount, 0) + (ifnull(total_sell, 0) - ifnull(total_buy, 0))) ending_price " +
				"from ("+
					"select instrument_id, counterparty_id, counterparty_name "+
					"from instrument, counterparty"+
				") i "+ 
					
				    "left join ("+
						"select deal_counterparty_id, deal_instrument_id ,sum(deal_quantity) buy, sum(deal_quantity * deal_amount) total_buy "+
						"from deal "+
						"where deal_type= 'B' "+ 
						"group by deal_instrument_id, deal_counterparty_id"+
					") b on  b.deal_instrument_id = i.instrument_id and b.deal_counterparty_id = i.counterparty_id "+

					"left join ("+
						"select deal_counterparty_id, deal_instrument_id , sum(deal_quantity) sell, sum(deal_quantity * deal_amount) total_sell "+
						"from deal "+
						"where deal_type= 'S' "+
						"group by deal_instrument_id, deal_counterparty_id"+
					") s on s.deal_instrument_id = i.instrument_id and s.deal_counterparty_id = i.counterparty_id "+
				    
				   "left join ("+
						"select d.deal_instrument_id, d.deal_type, d.deal_amount "+
						"from deal d "+
							"join ("+
							"	select max(deal_id) max_id "+
								"from deal d1 "+
								"	join ("+
								"		select deal_instrument_id, deal_type, max(deal_time) max_dt "+
								"		from deal d "+
								"		group by deal_instrument_id, deal_type"+
								"	) d2 on d2.deal_instrument_id = d1.deal_instrument_id "+
									"	and d2.max_dt = d1.deal_time "+
								"		and d2.deal_type = d1.deal_type "+
								"group by d1.deal_instrument_id, d1.deal_type "+
						"	) id on d.deal_id = id.max_id "+
					") prices on prices.deal_instrument_id = i.instrument_id "+
					"	and prices.deal_type = case "+
						"	when ifnull(sell,0) > ifnull(buy,0) then 'B' "+
				          "  else 'S' "+
						"end "+
				"group by i.counterparty_name";
			rsReal = smt1.executeQuery(tradeQuery1);
			rsEffec = smt2.executeQuery(tradeQuery2);
			//rsSell= smt2.executeQuery(sellQuery);
			
			while(rsReal.next() && rsEffec.next()){
				HashMap<String,String> hm = new HashMap<String,String>();
				hm.put("counterparty_name",rsReal.getString(1));
				hm.put("realised", rsReal.getString(2));
				hm.put("effective", rsEffec.getString(2));
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