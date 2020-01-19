<%@ page language="java" errorPage="/error.jsp" contentType="text/html;charset=UTF-8" %>
<%@ include file="/taglibs.jsp" %>

<table width="100%" border="0" cellspacing="0">
  <tr width="100%" height="10">
    <td width="5%" vAlign=center align=middle>
      <a href="index.do">
        <img src="images/company.gif" border=0 height="60">
      </a>
    </td>
    <td width="50%" vAlign=top align="right" style="height: 95px;">
      <%--<img src="images/system_name.gif" border=0>--%>
        <style>
          @font-face { font-family: IranNastaliq; src: url('fonts/IranNastaliq.ttf');}
        </style>
        <div style="font-family: IranNastaliq;font-size: x-large"></div>
    </td>
    <td width="40%" Valign="bottom" align="left">&nbsp;
      <a href="changePass.do?method=list">
        <bean:message key="general.changePass"/>
      </a>&nbsp;
      <a href="logout.do?id=<%=System.currentTimeMillis()%>">
        <bean:message key="general.exit"/>
      </a>
    </td>
  </tr>
</table>