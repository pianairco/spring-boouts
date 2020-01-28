<%@ page import="java.util.ArrayList" %>
<%@ page import="ir.piana.dev.struts.dynamic.form.FormManager" %>
<%@ page import="ir.piana.dev.struts.dynamic.form.FormSelectDef" %>
<%@ page import="ir.piana.dev.struts.dynamic.sql.SQLParamDef" %>
<%@ page import="ir.piana.dev.struts.dynamic.form.ElementControl" %>
<%@ page import="java.util.List" %>
<%@ page import="ir.piana.dev.struts.dynamic.form.FormSelectColumnDef" %>
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
//    String MESSGAE_KEY = "org.apache.struts.action.MESSAGE";
//    MessageResources messageResources = (MessageResources) request.getSession().getServletContext().getAttribute(MESSGAE_KEY);
    FormManager formManager = (FormManager) request.getAttribute("form-manager");
    String title = "";
    FormSelectDef formDef = (FormSelectDef) request.getAttribute("form-def");
    String[] split = formDef.getName().split("@");
    String formActionUrl = split[0] + "?method=" + split[1] + "&persist=true";

    //>>edit start
    String sortPropertyParam = formDef.getSortProperty();
    String sortOrderParam = formDef.getSortOrder();
//    String sortHref = formDef.getActionURL().concat("?").concat("sort_mode=true&method=")
//            .concat(displayDef.getActionMethod()).concat("&");

    String[] splited = formDef.getName().split("@");
    String sortHref = split[0].concat(".do?")
            .concat("method=").concat(splited[1])
            .concat("&activity=")
            .concat(formDef.getTableActivity())
            .concat("&sort_mode=true&");
    if(sortHref.startsWith("/"))
        sortHref = sortHref.substring(1);
    //<<edit end
    //--dont edit-->>
    String sortOrder = "asc";
    String sortedStyleClass = "";
    String columnStyleClass = "";
    String sortableStyleClass = "sortable";
    if(session.getAttribute(sortOrderParam) != null) {
        sortOrder = (String) session.getAttribute(sortOrderParam);
        if(sortOrder == null)
            sortOrder = "asc";
        if(sortOrder.equals("asc")) {
            sortedStyleClass = "order1 sortable sorted";
        } else {
            sortedStyleClass = "order2 sortable sorted";
        }
    }
    String sortProperty = "";
    if (session.getAttribute(sortPropertyParam) != null)
        sortProperty=(String) session.getAttribute(sortPropertyParam);
    String href = "";
    String commonHref = "<a href='"+sortHref+sortPropertyParam+"=";
    //--dont edit--<<
%>

<script language="javascript">
    function resetSearchPanel()
    {
        <%
        for(ElementControl elementControl : formDef.getControls()) {
            String d = "document.forms[0].elements['" + elementControl.getName() + "'].value = '';";
        %>
        <%=d%>
        <%
        }
        %>
    }
</script>

<table bgcolor="#bbbbbb" border="0" cellpadding="1" cellspacing="0" width="100%">
    <tr >
        <td  >
            <table width="100%" class="content" border="0" style="border-collapse:collapse">
                <tr class="VNformheader" >
                    <td width="100%" >
                        <%
                            if(formDef.getTitle() != null) {
                                String pageTitle = formDef.getTitle();
                        %>
                        <h4 >
                            <%=pageTitle%>
                        </h4>
                        <%
                            }
                        %>
                    </td>
                </tr>
            </table>
            <table width="100%" class="content" border="0" style="border-collapse:collapse; background-color: white;">
                <tr class=caption valign=top align=center>
                    <td width="20%" style="background-color: #f0f0f0; border: 2px solid #bbbbbb;">
                        <table width="100%">
                            <tr >&nbsp;</tr>
                            <tr>
                                <td>
                                    <jsp:include page="common-controls.jsp">
                                        <jsp:param name="forList" value="true"/>
                                    </jsp:include>
                                </td>
                            </tr>
                            <tr >&nbsp;</tr>
                        </table>

                    </td>
                    <td width="80%" colspan=4 valign=top>
                        <%
                            if(formDef != null) { //#if formDef
                                Object pageSizeObject = request.getSession().getAttribute("common.search.pageSize");
                                String pageSize = "30";
                                if(pageSizeObject != null)
                                    pageSize = (String) pageSizeObject;
                                String tableName = (formDef.getQueryName() + "_List").replaceAll("-", "_");
                                String tableQuery = formDef.getQueryName();
//                                String tableQuery = formDef.getQueryName() + "Query";
                                String tableSize = (formDef.getQueryName() + "_Size").replaceAll("-", "_");
                                String tableId = (formDef.getQueryName() + "_table").replaceAll("-", "_");
                                String decorator = formDef.getDecorator();
                        %>

                        <%
                            if(request.getAttribute(tableQuery) != null && request.getAttribute(tableName) == null) {
                        %>

                        <dtsource:jdbc pagesize="<%=new Long(pageSize)%>" id="<%=tableId%>" list="<%=tableName%>" sizelist="<%=tableSize%>" defaultsortName="" alias="" table="">
                            <%=request.getAttribute(tableQuery)%>
                        </dtsource:jdbc>

                        <%
                            } else {
                                Object attribute = request.getAttribute(tableQuery);
                                if(attribute == null) {
                                    request.setAttribute(tableSize, 0);
                                    request.setAttribute(tableName, new ArrayList<>());
                                }
                            }
                        %>

                        <table width="100%" style="background-color: #bbbbbb;">
                            <tr>
                                <td>
                                    <display:table name="<%=tableName%>" requestURI="" class="list" id="<%=tableId%>" export="false"
                                                   pagesize="<%=new Integer(pageSize)%>" decorator="<%=decorator%>"
                                                   partialList="true" size="<%=tableSize%>"
                                                   cellspacing="1"  offset="0" cellpadding="2">

                                        <%
                                            for(FormSelectColumnDef displayColumnDef : formDef.getFormSelectColumnDefs())  { //#for displayColumnDef
                                        %>

                                        <%
                                            if(displayColumnDef.isSortable()) {//#if displayColumnDef.isSortable()
                                                String newOrder = sortOrder == null ? "asc" : sortOrder.equalsIgnoreCase("asc") ? "desc" : "asc";
                                                href = commonHref + displayColumnDef.getProperty() + "&" + sortOrderParam + "=" + newOrder +  "'>" + displayColumnDef.getTitle() + "</a>";
//                                    href = commonHref + displayColumnDef.getProperty() + "&" + sortOrderParam + "=" + newOrder +  "'>" + messageResources.getMessage(displayColumnDef.getTitleKey()) + "</a>";
                                                columnStyleClass = sortProperty.equals(displayColumnDef.getProperty()) ? sortedStyleClass : sortableStyleClass;

                                        %>

                                        <display:column style="width:8%" property="<%=displayColumnDef.getProperty()%>" headerClass="<%=columnStyleClass%>" title="<%=href%>" />

                                        <%
                                        } //#if displayColumnDef.isSortable()
                                        else {//#if !displayColumnDef.isSortable()
                                        %>

                                        <display:column style="width:8%" property="<%=displayColumnDef.getProperty()%>" title="<%=displayColumnDef.getTitle()%>" />

                                        <%
                                            }//#if !displayColumnDef.isSortable()
                                        %>

                                        <%
                                            }//#for displayColumnDef
                                        %>

                                    </display:table>
                                </td>
                            </tr>
                        </table>


                        <%
                            }//#if formDef
                        %>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
</table>

</body>
</html>
<%--<html:javascript formName="customerMessageForm" staticJavascript="false" cdata="false"/>--%>
<%--<script type="text/javascript" src="<c:url value="/scripts/validator.jsp"/>"></script>--%>