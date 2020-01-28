package ir.piana.dev.struts.action;

import ir.piana.dev.struts.dynamic.CommonDispatchAction;
import org.apache.struts.action.ActionForm;
import org.apache.struts.util.MessageResources;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component("customerMessage")
public class CustomerMessageAction extends CommonDispatchAction {
    public void sendSms(ActionForm form, HttpServletRequest request) {
        Object o = request.getParameter("insert.fund-question.createCustomerId");
        if(o == null || String.valueOf(o).isEmpty()) {
            MessageResources resources = getResources(request);
        }
    }
}
