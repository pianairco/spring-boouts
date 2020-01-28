<%@ page import="org.apache.struts.util.MessageResources"%>
<%@ page import="ir.piana.dev.struts.dynamic.form.FormPersistDef" %>
<%@ page import="ir.piana.dev.struts.dynamic.form.FormSelectDef" %>
<%@ page language="java" errorPage="/error.jsp" contentType="text/html;charset=UTF-8" %>
<%@ include file="/taglibs.jsp"%>

<html dir="rtl">
<head>
    <link href="../styles/global.css" type=text/css rel=stylesheet>
    <link href="styles/tab.css" type=text/css rel=stylesheet>
    <script language="JavaScript" src="scripts/popup.js"></script>
    <script language="JavaScript" src="scripts/tab.js"></script>
    <script language="JavaScript" src="scripts/autoMask.js"></script>
</head>
<body >

<%
    String MESSGAE_KEY = "org.apache.struts.action.MESSAGE";
    String title = "";
%>


<tr id="dwindow" style="position:absolute;background-color:#EBEBEB;cursor:auto;left:0px;top:0px;display:none" onSelectStart="return false">
<table bgcolor="#bbbbbb" border="0" cellpadding="1" cellspacing="0" width="100%">
<tr>
    <td>
        <%
            if(request.getAttribute("form-def") instanceof FormPersistDef) {
        %>

        <jsp:include page="common-edit.jsp" flush="true"/>
        <%--<%@include file="common-edit.jsp" %>--%>

        <%
            } else if(request.getAttribute("form-def") instanceof FormSelectDef) {
        %>

        <jsp:include page="common-list.jsp" flush="true"/>

        <%--<%@include file="common-list.jsp" %>--%>

        <%
            }
        %>
    </td>
</tr>
</table>
</body>
</html>
<%--<html:javascript formName="customerMessageForm" staticJavascript="false" cdata="false"/>--%>
<%--<script type="text/javascript" src="<c:url value="/scripts/validator.jsp"/>"></script>--%>