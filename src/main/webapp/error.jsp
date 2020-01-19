<%@ page import="java.util.UUID" %>
<%@ page language="java" isErrorPage="true" %>

<head><title>Doh!</title></head>

An Error has occurred in this application.

<%
    UUID uuid = UUID.randomUUID();
    System.out.println("An error occurred; log UUID:" + uuid + " ");
    exception.printStackTrace();
%>

<pre>logUUID:<%=uuid%></pre>