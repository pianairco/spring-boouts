package ir.piana.dev.struts.dynamic;

import ir.piana.dev.struts.dynamic.form.*;
import ir.piana.dev.struts.dynamic.sql.*;
import ir.piana.dev.struts.util.CommonUtils;
import org.apache.struts.action.*;
import org.apache.struts.actions.DispatchAction;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by mj.rahmati on 12/15/2019.
 */
@SuppressWarnings("Duplicates")
public class CommonDispatchAction extends DispatchAction {
    @Autowired
    private DataSource dataSource;

//    public GenericManager getManager() {
//        return manager;
//    }

    private SQLQueryManagerProvider sqlManagerProvider;
    private SQLQueryManager sqlQueryManager;
    private FormManagerProvider formManagerProvider;

    public void setSQLQueryManagerProvider(SQLQueryManagerProvider sqlManagerProvider) {
        this.sqlManagerProvider = sqlManagerProvider;
        this.sqlQueryManager = sqlManagerProvider.getSqlQueryManager();
    }

    public void setFormManagerProvider(FormManagerProvider formManagerProvider) {
        this.formManagerProvider = formManagerProvider;
    }

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        if(this.isCancelled(request)) {
            ActionForward af = this.cancelled(mapping, form, request, response);
            if(af != null) {
                return af;
            }
        }

        String parameter = this.getParameter(mapping, form, request, response);
        String name = this.getMethodName(mapping, form, request, response, parameter);
        if(!"execute".equals(name) && !"perform".equals(name)) {
            return this.dispatchMethod(mapping, form, request, response, name);
        } else {
            String message = messages.getMessage("dispatch.recursive", mapping.getPath());
            log.error(message);
            throw new ServletException(message);
        }
    }

    public ActionForward dispatchMethod(
            ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response,
            String name)
            throws Exception {
        if (name == null)
            return this.unspecified(mapping, form, request, response);

        Method method = null;
        String message = null;
        try {
            method = this.getMethod(name);
        } catch (NoSuchMethodException var13) {
            ActionForward common = common(mapping, form, request, response, mapping.getPath() + "@" + name);
            return common;
        }
        try {
            Object[] args = new Object[]{mapping, form, request, response};
            ActionForward forward = (ActionForward) method.invoke(this, args);
            return forward;
        } catch (ClassCastException var14) {
            message = messages.getMessage("dispatch.return", mapping.getPath(), name);
            log.error(message, var14);
            throw var14;
        } catch (IllegalAccessException var15) {
            message = messages.getMessage("dispatch.error", mapping.getPath(), name);
            log.error(message, var15);
            throw var15;
        } catch (InvocationTargetException var16) {
            Throwable t = var16.getTargetException();
            if (t instanceof Exception) {
                throw (Exception) t;
            } else {
                message = messages.getMessage("dispatch.error", mapping.getPath(), name);
                log.error(message, var16);
                throw new ServletException(t);
            }
        }
    }

    public ActionForward common(ActionMapping mapping, ActionForm form,
                                HttpServletRequest request,
                                HttpServletResponse response, String formName) throws SQLException, RayanSQLException {
//         FormPersistDef formInsertDef = formManagerProvider.getFormManager().getFormPersistDef(formName);

        FormDef formDef = formManagerProvider.getFormManager().getFormDef(formName);

        request.setAttribute("form-def", formDef);
        request.setAttribute("form-manager", formManagerProvider.getFormManager());

        manageActivity(form, request, formDef);

        if(!formDef.getInitialSelects().isEmpty()) {
            for(ElementInitialSelect elementInitialSelect : formDef.getInitialSelects()) {
                try {
                    sqlQueryManager.query(elementInitialSelect.getQueryName(),
                            dataSource, request,
                            new ParameterProviderForStruts(request, new LinkedHashMap<>()), elementInitialSelect.getName());
                    if(elementInitialSelect.getMapper() != null && !elementInitialSelect.getMapper().isEmpty()) {
                        Object attribute = request.getAttribute(elementInitialSelect.getName());
                        if(attribute != null && attribute instanceof List && ((List)attribute).size() == 1) {
                            Map map = (Map)((List) attribute).get(0);
                            for(String mapped : elementInitialSelect.getMapper()) {
                                String[] split = mapped.split(":");
                                request.getSession().setAttribute(split[1], map.get(split[0]));
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

//        if(formDef instanceof FormPersistDef) {
//            executeCommonPersist(request, response, (FormPersistDef) formDef, messages);
//        } else if(formDef instanceof FormSelectDef)
//            executeCommonSelect(request, response, (FormSelectDef) formDef);

        return mapping.findForward("common");
//        return mapping.findForward("common-jsp");
    }

    private String getValue(HttpServletRequest request, String key) {
        return getValue(request, key, false);
    }

    private String getValue(HttpServletRequest request, String key, boolean saveToSession) {
        Object obj = null;
        if(request != null) {
            obj = request.getParameter(key);
            if (obj == null)
                obj = request.getAttribute(key);
            if (obj == null)
                obj = request.getSession().getAttribute(key);
            if(saveToSession)
                request.getSession().setAttribute(key, obj);
        }
        return (String)obj;
    }

//    public void executeCommonPersist(
//            HttpServletRequest request, HttpServletResponse response,
//            FormPersistDef formPersistDef, ActionMessages messages)
//            throws SQLException, RayanSQLException {
//        request.setAttribute("include", "edit");
//        String persist = request.getParameter("persist");
//        if(persist != null && persist.equals("true")) {
//            if(request.getParameter(formPersistDef.getIdParam()) != null &&
//                    !request.getParameter(formPersistDef.getIdParam()).isEmpty()) {
//                executeCommonUpdate(request, response, formPersistDef);
//                messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
//                        "message.saved", getValue(request, formPersistDef.getIdParam())));
//                saveMessages(request.getSession(), messages);
//            } else {
//                executeCommonInsert(request, response, formPersistDef);
//                messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
//                        "message.saved", getValue(request, formPersistDef.getIdParam())));
//                saveMessages(request.getSession(), messages);
//            }
//        }
//    }

//    public void executeCommonInsert(HttpServletRequest request, HttpServletResponse response, FormPersistDef formPersistDef)
//            throws SQLException, RayanSQLException {
//        sqlQueryManager.query(formPersistDef.getQueryInsertName(), manager.getGenericJdbcDAO().getDataSource(), request, new StrutsParameterProvider(request, new LinkedHashMap<>()));
//    }
//
//    public void executeCommonUpdate(HttpServletRequest request, HttpServletResponse response, FormPersistDef formPersistDef)
//            throws SQLException, RayanSQLException {
//        sqlQueryManager.query(formPersistDef.getQueryUpdateName(), manager.getGenericJdbcDAO().getDataSource(), request, new StrutsParameterProvider(request, new LinkedHashMap<>()));
//    }

    public void executeCommonSelect(HttpServletRequest request, HttpServletResponse response, FormSelectDef formSelectDef)
            throws SQLException {
        String pageSize = getValue(request, "common.search.pageSize", true);
        String firstLoad = getValue(request, "new_search", false);
        request.setAttribute("include", "list");
        if(firstLoad == null || firstLoad.isEmpty() || firstLoad.equals("false")) {
            String query = sqlQueryManager.createQuery(formSelectDef.getQueryName(), request, new ParameterProviderForStruts(request, new LinkedHashMap<>()));
            request.setAttribute(formSelectDef.getQueryName(), query);
        }
//        sqlQueryManager.query(formSelectDef.getQueryName(), getManager().getGenericJdbcDAO().getDataSource(), request, null);

//        if(!formSelectDef.getInitialSelects().isEmpty()) {
//            for(ElementInitialSelect elementSelect : formSelectDef.getInitialSelects()) {
//                try {
//                    sqlQueryManager.query(elementSelect.getQueryName(),
//                            getManager().getGenericJdbcDAO().getDataSource(),
//                            request, new LinkedHashMap<>(), elementSelect.getName());
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }
    }

    private void manageActivity(ActionForm form, HttpServletRequest request, FormDef formDef)
            throws SQLException, RayanSQLException {
        String activities = request.getParameter("activity");
        if(activities == null || activities.isEmpty())
            return;
        for(String activity : activities.split(",")) {
            if (activity != null && formDef.getActivityMap().containsKey(activity)) {
                ElementActivity elementActivity = formDef.getActivityMap().get(activity);
                for (String permission : elementActivity.getPermissions()) {
                    if (!CommonUtils.hasPermission(request.getSession(), permission)) {
                        ActionMessages messages = new ActionMessages();
                        messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("general.not-permission"));
                        saveErrors(request.getSession(), messages);
                        return;
                    }
                }

                SQLExecuter sqlExecuter = SQLExecuter.getInstance();
                List<ElementActivity.Operation> operations = elementActivity.getOperations();
                Connection connection = null;
                Statement statement = null;
                try {
                    connection = dataSource.getConnection();
                    connection.setAutoCommit(false);
                    statement = connection.createStatement();
                    for (ElementActivity.Operation operation : operations) {
                        if (operation.getOperationType() == OperationType.QUERY) {
                            SQLType sqlType = sqlQueryManager.getSQLType(operation.getName());
                            String query = sqlQueryManager.createQuery(operation.getName(), request,
                                    new ParameterProviderForStruts(request, new LinkedHashMap<>()));
                            sqlExecuter.query(query, sqlType, statement, request,
                                    new ParameterProviderForStruts(request, new LinkedHashMap<>()), operation.getResultAttributeName());
                        } else if (operation.getOperationType() == OperationType.QUERY_STRING) {
                            getValue(request, "common.search.pageSize", true);
                            SQLType sqlType = sqlQueryManager.getSQLType(operation.getName());
                            String query = sqlQueryManager.createQuery(operation.getName(), request,
                                    new ParameterProviderForStruts(request, new LinkedHashMap<>()));
                            request.setAttribute(operation.getResultAttributeName(), query);
                        } else if (operation.getOperationType() == OperationType.FUNCTION) {
                            Method declaredMethod = this.getClass().getDeclaredMethod(operation.getName(), ActionForm.class, HttpServletRequest.class);
                            declaredMethod.invoke(this, form, request);
//                            error prone
//                            declaredMethod.invoke(form, request);
                        }
                    }
                    connection.commit();
                    if (elementActivity.getSuccessMessage().getMessageKey() != null && !elementActivity.getSuccessMessage().getMessageKey().isEmpty()) {
                        ActionMessages messages = new ActionMessages();
                        List<String> parameters = elementActivity.getSuccessMessage().getParameters();
                        Object[] params = parameters.stream().map(key -> getValue(request, key)).collect(Collectors.toList()).toArray(new Object[0]);
                        messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
                                elementActivity.getSuccessMessage().getMessageKey(), params));
                        saveMessages(request.getSession(), messages);
                    }
                } catch (Exception ex) {
                    connection.rollback();
                    if (elementActivity.getFailureMessage().getMessageKey() != null && !elementActivity.getFailureMessage().getMessageKey().isEmpty()) {
                        ActionMessages messages = new ActionMessages();
                        List<String> parameters = elementActivity.getFailureMessage().getParameters();
                        Object[] params = parameters.stream().map(key -> getValue(request, key)).collect(Collectors.toList()).toArray(new Object[0]);
                        messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
                                elementActivity.getFailureMessage().getMessageKey(), params));
                        saveMessages(request.getSession(), messages);
                    }
//                ex.printStackTrace();
                } finally {
                    statement.close();
                    connection.setAutoCommit(true);
                    connection.close();
                }
            }
        }
    }

//    public ActionForward unspecified(ActionMapping mapping, ActionForm form,
//                                     HttpServletRequest request,
//                                     HttpServletResponse response)
//            throws Exception {
//        return common(mapping, form, request, response);
//    }
}
