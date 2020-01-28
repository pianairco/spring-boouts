package ir.piana.dev.struts.dynamic;

import ir.piana.dev.struts.dynamic.form.FormManager;
import ir.piana.dev.struts.dynamic.form.FormModelManager;
import ir.piana.dev.struts.dynamic.sql.SQLModelManager;
import ir.piana.dev.struts.dynamic.sql.SQLQueryManager;
import org.springframework.stereotype.Component;

import java.io.InputStream;

/**
 * Created by mj.rahmati on 12/15/2019.
 */
@Component
public class FormManagerProvider {
    private String[] jsonResources;
    private SQLQueryManagerProvider sqlManagerProvider;
    private FormManager formManager;
    private boolean debug;

    public void setJsonResources(String[] jsonResources) {
        this.jsonResources = jsonResources;
    }

    public void setSqlQueryManagerProvider(SQLQueryManagerProvider sqlManagerProvider) {
        this.sqlManagerProvider = sqlManagerProvider;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public void init() throws Exception {
        formManager = loadFormManager();
    }

    public FormModelManager loadFormModelManager(String[] jsonResources, SQLModelManager sqlModelManager) throws Exception {
        InputStream[] inputStreams = new InputStream[jsonResources.length];
        for (int i = 0; i < jsonResources.length; i++) {
            inputStreams[i] = FormManagerProvider.class.getResourceAsStream(jsonResources[i]);
        }
        FormModelManager formModelManager = FormModelManager.getNewInstance(inputStreams);
        return formModelManager;
    }

    public FormManager loadFormManager() throws Exception {
        SQLQueryManager sqlQueryManager = sqlManagerProvider.getSqlQueryManager();
        FormManager formManager = FormManager.createFormManager(
                loadFormModelManager(jsonResources, sqlQueryManager.getSQLModelManager()), debug);
        return formManager;
    }

    public FormManager getFormManager() {
        if(debug) {
            try {
                FormManager formManager = loadFormManager();
                return formManager;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return formManager;
    }
}
