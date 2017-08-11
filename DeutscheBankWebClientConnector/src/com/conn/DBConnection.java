package com.conn;
import java.io.IOException;
//import java.sql.PreparedStatement;
//import java.sql.Statement;
import java.sql.Connection;
//import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
//import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Connection")
public class DBConnection extends HttpServlet 
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String str="";
		
	      try
	      {
	        Class.forName( "com.mysql.jdbc.Driver" );
	 
	        Connection itsConnection = DriverManager.getConnection( "jdbc:mysql://192.168.99.100:3306/db_grad_cs_1917", "root", "ppp" );
	          str="true";
	       
	      }
	      catch( ClassNotFoundException e )
	      {
	         e.printStackTrace();
	         str="false";
	      }
	      catch( SQLException e )
	      {
	         e.printStackTrace();
	         str="false";
	      }
	      System.out.println(str);
	      
	      String st=JsonUtil.converJavaToString(str); 
	      response.setContentType("application/json");
	  	  response.getWriter().write(st);
	    }
	}
