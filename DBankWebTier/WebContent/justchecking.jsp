<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<form action="BAServlet3" method="get">
<select name="selected_id" onchange="this.form.submit();">
<option value="Astronomica">Astronomica</option>
<option value="Borealis">Borealis</option>
<option value="Celestial">Celestial</option>
<option value="Deuteronic">Deuteronic</option>
<option value="Eclipse">Eclipse</option>
<option value="Floral">Floral</option>
<option value="Galactia">Galactia</option>
<option value="Heliosphere">Heliosphere</option>
<option value="Interstella">Interstella</option>
<option value="Jupiter">Jupiter</option>
<option value="Koronis">Koronis</option>
<option value="Lunatic">Lunatic</option>
</select>
</form>
</body>
</html>