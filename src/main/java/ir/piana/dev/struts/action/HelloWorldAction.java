package ir.piana.dev.struts.action;

import ir.piana.dev.struts.data.dao.TestDao;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component("helloWorld")
public class HelloWorldAction extends DispatchAction {
    @Autowired
    private TestDao testDao;

    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request, HttpServletResponse response) throws Exception {

        HelloWorldForm helloWorldForm = (HelloWorldForm) form;
        helloWorldForm.setMessage("Hello World! Struts");

        String appParam = testDao.getAppParam();
        System.out.println(appParam);

        return mapping.findForward("success");
    }
}
