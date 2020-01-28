package ir.piana.dev.struts.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Iterator;
import java.util.Map;

public class FormControlUtils {

    private static String getValue(String name, HttpServletRequest request, boolean noSession) {
        String value = request.getParameter(name);
        if(value == null)
            value = (String) request.getAttribute(name);
        if(value == null) {
            if (noSession)
                value = "";
            else
                value = request.getSession().getAttribute(name) != null ? request.getSession().getAttribute(name) + "" : "";

        }
        return value;
    }

    public static String getTextHidden(String name, HttpServletRequest request, boolean noSession) {
        String value = getValue(name, request, noSession);
        String input = "<input type='hidden' name='" + name + "' value='" + value + "' />";
        return input;
    }

    public static String getSelect(String name,
                                   HttpServletRequest request,
                                   boolean noSession,
                                   Map items,
                                   boolean hasNothing,
                                   boolean hasAll,
                                   boolean multiSelect) {
        return getSelect(name, request, noSession, items,hasNothing,hasAll,multiSelect,"","");

    }

    public static String getSelect(String name,
                                   HttpServletRequest request,
                                   boolean noSession,
                                   Map items,
                                   boolean hasNothing,
                                   boolean hasAll,
                                   boolean multiSelect,
                                   String width,String height){
        return getSelect(name, request, noSession, items, hasNothing, hasAll, multiSelect, width, height,"");
    }

    public static String getSelect(String name,
                                   HttpServletRequest request,
                                   boolean noSession,
                                   Map items,
                                   boolean hasNothing,
                                   boolean hasAll,
                                   boolean multiSelect,
                                   String width,
                                   String height,
                                   String extended) {
        HttpSession session = request.getSession();
        String value = getValue(name, request, noSession);

        StringBuffer select = new StringBuffer();
        if (multiSelect)
            select.append("<select multiple=\"true\" name=\"").append(name).append("\"") ;
        else
            select.append("<select name=\"").append(name).append("\"") ;
        if (!width.equals("")){
            select.append(" STYLE=\"width:" );
            select.append(width );
            select.append(";\"");
        }
        if (!height.equals("")){
            select.append(" STYLE=\"height:" );
            select.append(height);
            select.append(";\"");
        }
        select.append(" " + extended + " ");
        select.append(">");
        if (hasAll)
            select.append("<option value=''>همه</option>") ;
        if (hasNothing)
            select.append("<option value=''>هیچکدام</option>") ;

        for (Iterator iterator = items.keySet().iterator(); iterator.hasNext();) {
            String propertyValue = (String) iterator.next();
            try {
                String lablePropertyValue = (String) items.get(propertyValue);
                String selected = "";
                if (propertyValue!=null && propertyValue.equals(value)) {
                    selected = "selected";
                }
                select. append("<option value=\"").
                        append(propertyValue).
                        append("\" ").
                        append(selected).
                        append(">").
                        append(lablePropertyValue).
                        append("</option>");

            } catch (Exception e) {
            }
        }
        select.append("</select>") ;
        return select.toString() ;
    }

    public static String getInput(String name, HttpServletRequest request, boolean noSession, String size, String Extended, boolean isDisable) {
        String value = getValue(name, request, noSession);
        String disabled = isDisable ? "disabled='true'" : "";
        String input = "<input type='text' name='" + name + "' id='" + name.replace(".", "_") + "' size='" + size + "' value='" + value + "' " + Extended + disabled + " />";
        return input;
    }

    public static String getInput(String name, HttpServletRequest request, boolean noSession, String size, boolean disabled) {
        String value = getValue(name, request, noSession);
        String input = "<input type='text' name='" + name +"' size='" + size +  "' value='" + value + "'" ;
        if (disabled)
            input += " disabled='true'" ;
        input += " />";
        return input;
    }

    public static String getTextBox(String name, HttpServletRequest request, boolean noSession, String rows, String cols, boolean disabled) {
        String value = getValue(name, request, noSession);
        String input = "<textarea rows=' " + rows + "' name='" + name +"' cols='" + cols +  "' ";
        if (disabled)
            input += " disabled='true'" ;
        input += " >" + value + "</textarea>";
        return input;
    }
}
