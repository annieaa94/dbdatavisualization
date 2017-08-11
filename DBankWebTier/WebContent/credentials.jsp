<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<jsp:useBean id="item" class="com.conn.LoginServlet" scope="application"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<form  action="loginServlet" method = "post">
         User name: 
            <input type = "text" name = "username"  id="username" maxlength = "100" />
         <br />
         
         Password: 
            <input type = "password" name = "password" id="password"  maxlength = "100" />
         <input type="submit" value="Login" />
      </form>
</body>
</html>