<%@ page language="java" errorPage="/error.jsp" contentType="text/html;charset=UTF-8" %>

<%@ include file="/taglibs.jsp"%>
<html dir="rtl">

<head>
    <title>Struts-Web</title>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
<%--    <link href="${ctx}/styles/global.css" type="text/css" rel="stylesheet"/>--%>
<%--    <link href="${ctx}/images/favicon.ico" rel="SHORTCUT ICON"/>--%>
<%--    <script type="text/javascript" src="${ctx}/scripts/global.js"></script>--%>
</head>

<body>

<table border="0" style="border-collapse: collapse" width="100%" cellpadding="0" cellspacing="0">
	<tr>
		<td><tiles:insert attribute="header"/></td>
	</tr>
	<tr>
		<td align="center">
            <%@ include file="/messages.jsp"%>
            <tiles:insert attribute="body"/>
        </td>
	</tr>
	<tr>
		<td><tiles:insert attribute="footer"/></td>
	</tr>
</table>

</body>

</html>
