package ir.piana.dev.struts.servlet;

import ir.piana.dev.struts.data.dao.TestDao;
import org.apache.struts.action.ActionServlet;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;

@WebServlet(urlPatterns = "*.do", loadOnStartup = 1,
        initParams = {
        @WebInitParam(name="config", value = "/WEB-INF/struts-config.xml")
})
public class MyServlet extends ActionServlet {
    @Autowired
    private DataSource dataSource;

    @Autowired
    private TestDao testDao;

    @PostConstruct
    public void initModule() {
        System.out.println("init");
    }
}
