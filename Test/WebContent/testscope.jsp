<%-- 
    Document   : testscope
    Created on : 17-Jun-2016, 09:06:36
    Author     : Selvyn
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="globalHelper" class="deutschebank.thebeans.ApplicationScopeHelper" scope="application"/>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    
    <body>
        <%
            String  dbStatus = "DB NOT CONNECTED";
            globalHelper.setInfo("Selvyn was here");
            
            if( globalHelper.bootstrapDBConnection() )
                dbStatus = "DB Connected";
        %>
                
        <h1>Hello World!</h1>
        <%= dbStatus %>
    </body>
</html>
