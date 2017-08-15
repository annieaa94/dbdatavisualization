package com.conn;
 
import java.io.IOException;

import java.sql.*;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
 
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
 
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String username="";
	String password="";
	protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
         
        username = request.getParameter("username");
        password = request.getParameter("password");
        System.out.println(username+" "+password);
        validate(response);
	}
	protected void validate(HttpServletResponse response) throws IOException{
		String str="";
		ArrayList<String> lis;
        String query="select * from db_grad_cs_1917.users where user_id='" + username + "' and user_pwd='" + password +"'";
      
        if(username=="" || password==""){
        	str="Please Enter Login Details";
        }
        else{
        try
        {
        	System.out.println("INSIDE try");
          Class.forName( "com.mysql.jdbc.Driver" );
          java.sql.Connection conn = DriverManager.getConnection( "jdbc:mysql://192.168.99.100:3306/db_grad_cs_1917?autoReconnect=true&useSSL=false", "root", "ppp" );
         
          java.sql.Statement smt=conn.createStatement();
          ResultSet rs=smt.executeQuery(query);
         if(rs.next()){
        	str=username+" "+password;	
          }
         else{
        	str="Not a Valid Login Details";
         }
        }
        catch( ClassNotFoundException e )
        {
          e.printStackTrace();
        	//System.out.println("catch1");
           
        }
        catch( SQLException e )
        {
           e.printStackTrace();
        	//System.out.println("catch2");
        }
         
        }
        	str=JsonUtil.converJavaToString(str);
          response.setContentType("application/json");
	  	  response.getWriter().write(str);
    }
 
}