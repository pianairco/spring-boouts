<%@ page import="ir.piana.dev.struts.dynamic.form.FormPersistDef" %>
<%@ page import="ir.piana.dev.struts.dynamic.form.FormManager" %>
<%@ page import="ir.piana.dev.struts.dynamic.form.ElementControl" %>
<%@ page language="java" errorPage="error.jsp" contentType="text/html;charset=UTF-8" %>
<%@ include file="taglibs.jsp"%>

<html dir="rtl">
<head>
    <link href="../styles/global.css" type=text/css rel=stylesheet>
    <link href="styles/tab.css" type=text/css rel=stylesheet>
    <script language="JavaScript" src="scripts/tab.js"></script>
    <script language="JavaScript" src="scripts/checkDigitAndDate.js"></script>
    <script language="JavaScript" src="scripts/NumberFormat.js"></script>
    <script language="JavaScript" src="scripts/autoMask.js"></script>
    <script language="JavaScript" src="scripts/popup.js"></script>
    <script type='text/javascript' src='dwr/interface/Manager.js'></script>
    <script type='text/javascript' src='dwr/engine.js'></script>
    <link href="styles/tab.css" type=text/css rel=stylesheet>
    <link href="styles/global.css" type=text/css rel=stylesheet>
    <script type='text/javascript' src='scripts/jquery-1.12.2.min.js'></script>
    <link type='text/css' href='styles/kamadatepicker.min.css?version=1.5.1' rel='stylesheet' />
    <script type='text/javascript' src='scripts/jquery-ui-1.8.2.custom.min.js'></script>
    <link type='text/css' href='css/jquery-ui-1.8.2.custom.css' rel='stylesheet' />
    <script language="javascript" src='scripts/kamadatepicker.min.js?version=1.5.1' type="text/javascript"></script>
</head>
<body >

<script language="javascript">
    function resetSearchPanel()
    {
        <%="document.forms[0].elements['search.branch.branchCode'].value = '';"%>
    }
</script>

<%
    FormPersistDef formDef = (FormPersistDef) request.getAttribute("form-def");
    FormManager formManager = (FormManager) request.getAttribute("form-manager");
    String title = "";
    String[] split = formDef.getName().split("@");
    String formActionUrl = split[0] + "?method=" + split[1] + "&persist=true";
%>

<script language="javascript">
    function resetSearchPanel()
    {
        <%
        for(ElementControl SQLParamDef : formDef.getControls()) {
            String d = "document.forms[0].elements['" + SQLParamDef.getName() + "'].value = '';";
        %>
        <%=d%>
        <%
        }
        %>
    }
</script>

<table width="100%" class="content" border="0" style="border-collapse:collapse">
    <tr class="VNformheader">
        <td width="20%">
            <%
                if(formDef.getTitle() != null) {
                    String pageTitle = formDef.getTitle();
            %>
            <h4 ><%=pageTitle%></h4>
            <%
                }
            %>
        </td>
        <td width="80%" align=left >
            <%--<button type="button" name="newBranch" onclick="location =<%=formSelectDef.getActionURL().concat("?method=executeCommonInsert-edit")%>"><bean:message key="branch.newBranch"/></button>&nbsp;&nbsp;--%>
        </td>
    </tr>
    <tr>
        <td colspan="2" style="background-color: #fffff0;">&nbsp;</td>
    </tr>
</table>

<jsp:include page="common-controls.jsp">
    <jsp:param name="forList" value="false"/>
</jsp:include>

</body>
</html>
<%--<html:javascript formName="customerMessageForm" staticJavascript="false" cdata="false"/>--%>
<%--<script type="text/javascript" src="<c:url value="/scripts/validator.jsp"/>"></script>--%>