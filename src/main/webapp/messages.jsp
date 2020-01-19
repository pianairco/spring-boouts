<%
String noPointMessage = request.getAttribute("noPointMessage") != null ? request.getAttribute("noPointMessage").toString() : "";
%>
<%-- Error Messages --%>
<logic:messagesPresent>
    <!--<div class="error">-->
        <html:messages id="error">
            <font color="red">${error}.&nbsp;</font>
        </html:messages>
    <!--</div>-->
</logic:messagesPresent>

<%-- Success Messages --%>
<logic:messagesPresent message="true">
    <!--<div class="message">-->
        <html:messages id="message" message="true">
            <font color="green">${message}<%if (!message.equals(noPointMessage)){%>.<%}%>&nbsp;</font>
        </html:messages>
    <!--</div>-->
</logic:messagesPresent>

<%
    request.setAttribute("messageRead", true);
%>
