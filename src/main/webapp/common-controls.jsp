<%@ page import="org.apache.struts.util.MessageResources" %>

<%@ page import="java.util.Map" %>
<%@ page import="java.util.stream.Collectors" %>
<%@ page import="ir.piana.dev.struts.dynamic.form.FormDef" %>
<%@ page import="ir.piana.dev.struts.dynamic.form.FormManager" %>
<%@ page import="ir.piana.dev.struts.dynamic.form.ElementControl" %>
<%@ page import="ir.piana.dev.struts.dynamic.form.ElementButton" %>
<%@ page import="ir.piana.dev.struts.util.FormControlUtils" %>
<%@ page import="ir.piana.dev.struts.util.DateUtils" %>
<%@ page import="static ir.piana.dev.struts.util.DateUtils.*" %>
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
    <script type='text/javascript' src='scripts/jquery-ui-1.8.2.custom.min.js'></script>
    <script type='text/javascript' src='scripts/jquery.validate.js'></script>
    <link type='text/css' href='css/jquery-ui-1.8.2.custom.css' rel='stylesheet' />
    <link type='text/css' href='styles/kamadatepicker.min.css?version=1.5.1' rel='stylesheet' />
    <script language="javascript" src='scripts/kamadatepicker.min.js?version=1.5.1' type="text/javascript"></script>
</head>
<body >

<style>
    .button-box {
        background-color: #fffff0;
    }

    .fieldLabelArea {
        vertical-align: top;
        background-color: #fffff0;
    }

    .fieldValueArea {
        vertical-align: top;
        background-color: #fffff0;
    }

    td.fieldLabelArea, td.fieldValueArea {
        padding: 3px;
    }
</style>

<script language="javascript">
    function resetSearchPanel()
    {
        <%="document.forms[0].elements['search.branch.branchCode'].value = '';"%>
    }
</script>

<%
    boolean forList = false;
    String forListString = request.getParameter("forList");
    if(forListString != null && !forListString.isEmpty() && forListString.equals("true"))
        forList = true;
    int rowPerPage = 30;
    if(forList) {
        Object pageSizeObject = request.getSession().getAttribute("common.search.pageSize");
        String pageSize = "30";
        if(pageSizeObject != null && pageSizeObject instanceof String && !((String)pageSizeObject).isEmpty())
            pageSize = (String) pageSizeObject;
        rowPerPage = Integer.parseInt(pageSize);
    }
    FormDef formDef = (FormDef) request.getAttribute("form-def");
    FormManager formManager = (FormManager) request.getAttribute("form-manager");
    String title = "";
    String[] split = formDef.getName().split("@");
    String formActionUrl = split[0] + "?method=" + split[1] + "&persist=true";
%>

<script language="javascript">
    function resetSearchPanel() {
        <%
        for(ElementControl SQLParamDef : formDef.getControls()) {
            String d = "document.forms[0].elements['" + SQLParamDef.getName() + "'].value = '';";
        %>
        <%=d%>
        <%
        }
        %>
    }

    var dpOptions = {
        buttonsColor: "red",
        forceFarsiDigits: true,
        gotoToday: true,
        markToday: true,
        sync: true,
        markHolidays: true,
        highlightSelectedDay: true,
        nextButtonIcon: "img/next.png",
        previousButtonIcon: "img/prev.png"
    };

    var dateFields = [
        <%
            for(ElementControl elementControl : formDef.getControls()) {
                if(elementControl.getType().equals("date")) {
        %>

        "<%=elementControl.getName()%>",

        <%
                }
            }
        %>
    ];

    $(document).ready(function () {
        $.each(dateFields, function (key, value) {
            kamaDatepicker(value.replace(/\./g, '_'), dpOptions);
        });
    });
</script>

<html:form target="" action="<%=formActionUrl%>">
    <table border="0px" width="100%" cellpadding="0px" cellspacing="0" style="border-collapse: collapse;" class="content">

        <%
            for(ElementButton elementButton : formDef.getButtons()) {
                if(elementButton.getType().equals("submit") && elementButton.getActivities() != null &&!elementButton.getActivities().isEmpty()) {
        %>

        <input type="hidden" name="activity" value="<%=elementButton.getActivities().stream().collect(Collectors.joining(","))%>" />

        <%
                }
            }
        %>

        <%
            int i = formDef.getControlInRow();
            for(ElementControl elementControl : formDef.getControls()) {
        %>
        <%
            if(elementControl.getType().equals("hidden")) {
        %>

        <%=FormControlUtils.getTextHidden(elementControl.getName(), request, false)%>

        <%
                continue;
            }
        %>

        <%
            if(i == formDef.getControlInRow()) {
        %>
        <tr>
            <%
                }
            %>

            <td width="<%=35/formDef.getControlInRow()%>%" class="fieldLabelArea">
                <label>
                    <%=elementControl.getTitle()%>:
                </label>
            </td>
            <td width="<%=65/formDef.getControlInRow()%>%" class="fieldValueArea">
                <%
                    if(elementControl.getType().equals("select")) {
                        Map<String, String> listMap = formManager.getListMap(
                                request.getAttribute(elementControl.getItems()),
                                elementControl.getOptionTitle(), elementControl.getOptionValue());
                %>
                <%=FormControlUtils.getSelect(elementControl.getName(), request, false, listMap, false, true, false)%>
                <%
                } else if(elementControl.getType().equals("date")) {
                %>

                <%=FormControlUtils.getInput(elementControl.getName(), request, false, "10", "maxlength=\"10\" onblur=\"setEndDate()\" onkeypress=\"return autoMask(this,event,'"+ DATE_MASK_STRING + "');\"", elementControl.isDisabled())%>

                <%
                } else if(elementControl.getType().equals("number")) {
                %>

                <%=FormControlUtils.getInput(elementControl.getName(), request, false, "10", "onblur=\"setRemainderTo()\" onkeypress=\"return autoMask(this,event,'###############');\"", elementControl.isDisabled())%>

                <%
                } else if(elementControl.getType().equals("string")) {
                %>

                <%=FormControlUtils.getInput(elementControl.getName(), request, false, "12", elementControl.isDisabled())%>

                <%
                } else if(elementControl.getType().equals("text")) {
                %>

                <%=FormControlUtils.getTextBox(elementControl.getName(), request, false, "5", "40", elementControl.isDisabled())%>

                <%
                    }
                %>
            </td>

            <%
                if(--i <= 0) {
                    i = formDef.getControlInRow();
            %>
        </tr>
        <%
            }
        %>

        <%
            }
        %>

        <%
            if(i != formDef.getControlInRow() && i > 0) {
        %>
        <td colspan="<%=2 * i%>" width="<%=100/formDef.getControlInRow()*i%>">&nbsp;</td>
        </tr>
        <%
            }
        %>
        <tr class="button-box">
            <td colspan="<%=2 * formDef.getControlInRow()%>" align=left>
                <%
                    for(ElementButton elementButton : formDef.getButtons()) {
                %>

                <%
                    if(elementButton.getType().equals("submit")) {
                        String activity = "";
                        if(elementButton.getActivities() != null && !elementButton.getActivities().isEmpty()) {
                            activity = elementButton.getActivities().stream().collect(Collectors.joining(","));
                        }
                %>
                <html:submit styleClass="button" property="<%=activity%>">
                    <%=elementButton.getTitle()%>
                </html:submit>&nbsp;
                <%
                } else if(elementButton.getType().equals("reset")) {
                %>
                <button onclick="resetSearchPanel()" type="button">
                    <%=elementButton.getTitle()%>
                </button>&nbsp;
                <%
                } else if(elementButton.getType().equals("redirect")) {
                %>
                <button onclick="location = 'forward.jsp?forward=<%=elementButton.getReturnUrl()%>'" type="button" onblur="setNextFocus('return')">
                    <%=elementButton.getTitle()%>
                </button>&nbsp;
                <%
                    }
                %>

                <%
                    }
                %>
                    <%--<button onclick="resetSearchPanel()" type="button"><bean:message key="button.reset"/></button>&nbsp;--%>
                    <%--<html:submit styleClass="button" ><bean:message key="button.save"/></html:submit>&nbsp;--%>
            </td>
        </tr>
        <%
            if(forList) {
        %>
        <tr>
            <td width="100%" colspan="2" class="fieldLabelArea">
                <bean:message key="general.pageNumber"/>:<input type="text"  onkeypress="return autoMask(this,event,'####');" size="3" name="common.search.pageSize" value="<%=rowPerPage%>" maxLength="4">&nbsp;
            </td>
        </tr>
        <%
            }
        %>
    </table>
</html:form>

</body>
</html>
<%--<html:javascript formName="customerMessageForm" staticJavascript="false" cdata="false"/>--%>
<%--<script type="text/javascript" src="<c:url value="/scripts/validator.jsp"/>"></script>--%>