package ir.piana.dev.struts.dynamic.sql;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by mj.rahmati on 1/5/2020.
 */
public class ParameterProviderForStruts implements ParameterProvider {
    private HttpServletRequest request;
    private Map<String, Object> paramMap;

    public ParameterProviderForStruts(HttpServletRequest request, Map<String, Object> paramMap) {
        this.request = request;
        this.paramMap = paramMap;
    }

    @Override
    public <T> T get(String paramName) {
        Object obj = null;
        if(request != null) {
            obj = request.getParameter(paramName);
            if (obj == null)
                obj = request.getAttribute(paramName);
            if (obj == null)
                obj = request.getSession().getAttribute(paramName);
            request.getSession().setAttribute(paramName, obj);
        }
        if(paramMap != null && obj == null)
            obj = paramMap.get(paramName);
//        if(obj == null)
//            return "";
        return (T)obj;
    }

    @Override
    public <T> T getValue(String key, String type) {
        Object obj = null;
        if(request != null) {
            obj = request.getParameter(key);
            if (obj == null)
                obj = request.getAttribute(key);
            if (obj == null)
                obj = request.getSession().getAttribute(key);
            request.getSession().setAttribute(key, obj);
        }
        if(paramMap != null && obj == null)
            obj = paramMap.get(key);
        if(obj == null) {
            if(type.equals("string"))
                return (T) "";
            else
                return null;
        }
        return (T) obj;
    }
}
