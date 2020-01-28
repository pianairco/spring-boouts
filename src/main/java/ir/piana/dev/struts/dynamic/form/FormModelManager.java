package ir.piana.dev.struts.dynamic.form;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;

import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by mj.rahmati on 12/4/2019.
 */
public class FormModelManager {
    private Map<String, FormPersistDef> formInsertDefMap;
    private Map<String, FormSelectDef> formSelectDefMap;

    public FormDef getFormDef(String formName) {
        if(formInsertDefMap.containsKey(formName))
            return formInsertDefMap.get(formName);
        else if(formSelectDefMap.containsKey(formName))
            return formSelectDefMap.get(formName);
        return null;
    }

    public FormPersistDef getFormPersistDef(String formInsertName) {
        return formInsertDefMap.get(formInsertName);
    }

    FormSelectDef getFormSelectDef(String formSelectName) {
        return formSelectDefMap.get(formSelectName);
    }

    private FormModelManager(Map<String, FormPersistDef> formInsertDefMap,
                             Map<String, FormSelectDef> formSelectDefMap) {
        this.formInsertDefMap = formInsertDefMap;
        this.formSelectDefMap = formSelectDefMap;
    }

    private static List<ElementInitialSelect> getElementInitialSelects(Object list) {
        List<ElementInitialSelect> elementInitialSelects = new ArrayList<>();
        for(Map<String, Object> map : (List<Map<String, Object>>)list) {
            Object name = map.get("name");
            Object queryName = map.get("query-name");
            Object mapper = map.get("mapper");
            elementInitialSelects.add(new ElementInitialSelect((String) name, (String)queryName, mapper == null ? new ArrayList<>() : (List<String>)mapper));
        }
        return elementInitialSelects;
//        return ((List<Map<String, String>>)list).stream()
//                .map(map -> new ElementInitialSelect(map.get("name"), map.get("query-name")))
//                .collect(Collectors.toList());
    }

    private static List<ElementControl> getElementControls(Object list) {
        return ((List<Map<String, String>>)list).stream().map(
                map -> {
                    String disabledString = map.get("disabled");
                    boolean disabled = disabledString != null && disabledString.equals("true") ? true : false;
                    return new ElementControl(map.get("name"), map.get("title"), disabled, map.get("type"), map.get("items"),
                            map.get("query-name"), map.get("option-value"), map.get("option-title"));
                })
                .collect(Collectors.toList());
    }

    private static List<ElementButton> getElementButtons(Object list) {
        return ((List<Map<String, Object>>)list).stream().map(
                map -> {
                    Object activitiesObject = map.get("activities");
                    List<String> activities = null;
                    if(activitiesObject != null && activitiesObject instanceof List)
                        activities = (List<String>)activitiesObject;
                    else
                        activities = new ArrayList<>();
                    String returnUrlObj = (String) map.get("return-url");
                    String returnUrl = "";
                    if(returnUrlObj != null && !returnUrlObj.isEmpty()) {
                        returnUrlObj = returnUrlObj.replaceAll("&", "&___");
                        returnUrl = returnUrlObj;
                    }

                    return new ElementButton((String)map.get("name"), (String)map.get("title"),
                            (String)map.get("type"), returnUrl, activities);
                })
                .collect(Collectors.toList());
    }

    private static void fillFormActivities(Map map, FormDef formDef) {
        List<Map<String, Object>> activities = (List<Map<String, Object>>)map.get("activities");
        if(activities == null || activities.isEmpty()) {
            formDef.activityMap = new LinkedHashMap<>();
            formDef.activities = new ArrayList<>();
            return;
        }
        List<ElementActivity> elementActivities = new ArrayList<>();
        Map<String, ElementActivity> elementActivityMap = new LinkedHashMap<>();
        for(Map<String, Object> activity : activities) {
            String name = (String) activity.get("name");
            String type = (String) activity.get("type");
//            Object queriesObject = activity.get("queries");
//            List<String> queries = null;
//            if(queriesObject != null && queriesObject instanceof List) {
//                List<String> queryList = (List<String>) queriesObject;
//                if (queryList != null && !queryList.isEmpty())
//                    queries = queryList;
//                else
//                    queries = new ArrayList<>();
//            }

            Object permissionsObject = activity.get("permissions");
            List<String> permissions = null;
            if(permissionsObject != null && permissionsObject instanceof List) {
                permissions = (List<String>) permissionsObject;
            } else {
                permissions = new ArrayList<>();
            }

            List<ElementActivity.Operation> operationList = new ArrayList<>();
            Object operationsObject = activity.get("operations");
            if(operationsObject != null && operationsObject instanceof List) {
                List<Map<String, String>> operations = (List<Map<String, String>>) operationsObject;
                if(!operations.isEmpty()) {
                    for(Map<String, String> operationMap : operations) {
                        int order = Integer.valueOf(operationMap.get("order"));
                        OperationType operationType = OperationType.fromName(operationMap.get("type"));
                        String operationName = operationMap.get("name");
                        String resultAttributeName = operationMap.get("result-attribute-name");
                        if(resultAttributeName == null || resultAttributeName.isEmpty())
                            resultAttributeName = operationName;
                        operationList.add(new ElementActivity.Operation(order, operationType, operationName, resultAttributeName));
                    }
                }
            }
            Collections.sort(operationList);

            Object successMessageObject = activity.get("success-message");
            ElementActivity.MessageDef successMessageDef = new ElementActivity.MessageDef();
            if(successMessageObject != null && successMessageObject instanceof Map) {
                Map<String, Object> successMessage = (Map<String, Object>) successMessageObject;
                if(!successMessage.isEmpty()) {
                    successMessageDef.messageKey = (String)successMessage.get("message-key");
                    Object parametersObject = successMessage.get("parameters");
                    if (parametersObject != null && parametersObject instanceof List)
                        successMessageDef.parameters = (List<String>)parametersObject;
                    else
                        successMessageDef.parameters = new ArrayList<>();
                }
            }

            Object failureMessageObject = activity.get("failure-message");
            ElementActivity.MessageDef failurMessageDef = new ElementActivity.MessageDef();
            if(failureMessageObject != null && failureMessageObject instanceof Map) {
                Map<String, Object> failureMessageMap = (Map<String, Object>) failureMessageObject;
                if(!failureMessageMap.isEmpty()) {
                    failurMessageDef.messageKey = (String)failureMessageMap.get("message-key");
                    Object parametersObject = failureMessageMap.get("parameters");
                    if (parametersObject != null && parametersObject instanceof List)
                        failurMessageDef.parameters = (List<String>)parametersObject;
                    else
                        failurMessageDef.parameters = new ArrayList<>();
                }
            }

            ElementActivity elementActivity = new ElementActivity(name, type, permissions, operationList,
                    successMessageDef, failurMessageDef);
            elementActivities.add(elementActivity);
            elementActivityMap.put(name, elementActivity);
        }
        formDef.activities = elementActivities;
        formDef.activityMap = elementActivityMap;
    }

    private static FormPersistDef getFormPersistDef(Map<String, Object> formPersistDefMap) throws RayanFormException {
        if(formPersistDefMap == null || formPersistDefMap.isEmpty())
            throw new RayanFormException("map is null or empty");
        FormPersistDef formPersistDef = new FormPersistDef();
        String name = (String) formPersistDefMap.get("name");
        String queryName = (String)formPersistDefMap.get("query-name");
        String title = (String)formPersistDefMap.get("title");
        String queryUpdateName = (String) formPersistDefMap.get("query-update-name");
        String queryInsertName = (String) formPersistDefMap.get("query-insert-name");
        Object controlInRowObject = formPersistDefMap.get("control-in-row");
        int controlInRow = controlInRowObject == null ? 1 : (int) controlInRowObject;
        List<ElementInitialSelect> selects = getElementInitialSelects(formPersistDefMap.get("initial-select"));
        List<ElementControl> controls = getElementControls(formPersistDefMap.get("control"));
        List<ElementButton> buttons = getElementButtons(formPersistDefMap.get("button"));
        formPersistDef.name = name;
        formPersistDef.queryName = queryName;
        formPersistDef.title = title;
        formPersistDef.queryUpdateName = queryUpdateName;
        formPersistDef.queryInsertName = queryInsertName;
        formPersistDef.controlInRow = controlInRow;
        formPersistDef.selects = selects != null ? selects : new ArrayList<>();
        formPersistDef.controls = controls != null ? controls : new ArrayList<>();
        formPersistDef.buttons = buttons != null ? buttons : new ArrayList<>();
        fillFormActivities(formPersistDefMap, formPersistDef);
        return formPersistDef;
//        fillPersistenceColumns(formInsertDefMap, formPersistDef);
    }

    private static void fillFormSelectInRowButtons(Map map, FormSelectDef formSelectDef) {
        List<Map<String, Object>> inRowButtons = (List<Map<String, Object>>)map.get("in-row-buttons");
        if(inRowButtons == null || inRowButtons.isEmpty()) {
            formSelectDef.inRowButtonMap = new LinkedHashMap<>();
            formSelectDef.inRowButtons = new ArrayList<>();
            return;
        }
        List<ElementInRowButton> elementInRowButtons = new ArrayList<>();
        Map<String, ElementInRowButton> elementInRowButtonMap = new LinkedHashMap<>();
        for(Map<String, Object> inRowButton : inRowButtons) {
            String name = (String) inRowButton.get("name");
            String title = (String) inRowButton.get("title");
            String action = (String) inRowButton.get("action");
            String method = (String) inRowButton.get("method");
            String image = (String) inRowButton.get("image");
            Object activitiesObject = inRowButton.get("activities");
            List<String> activities = null;
            if(activitiesObject != null && activitiesObject instanceof List)
                activities = (List<String>)activitiesObject;
            else
                activities = new ArrayList<>();
            Object parametersObject = inRowButton.get("parameters");
            List<ElementInRowButton.Parameter> parameterList = new ArrayList<>();
            if(parametersObject != null && parametersObject instanceof List) {
                List<Map<String, String>> parameters = (List<Map<String, String>>) parametersObject;
                if(!parameters.isEmpty()) {
                    for(Map<String, String> paramMap : parameters) {
                        String paramName = paramMap.get("name");
                        String attribute = paramMap.get("attribute");
                        parameterList.add(new ElementInRowButton.Parameter(paramName, attribute));
                    }
                }
            }
            ElementInRowButton elementInRowButton = new ElementInRowButton(name, title, action, method, image, activities, parameterList);
            elementInRowButtonMap.put(name, elementInRowButton);
            elementInRowButtons.add(elementInRowButton);
        }
        formSelectDef.inRowButtonMap = elementInRowButtonMap;
        formSelectDef.inRowButtons = elementInRowButtons;
    }

//    private static void fillFormSelectActivities(Map map, FormSelectDef formSelectDef) {
//        List<Map<String, Object>> activities = (List<Map<String, Object>>)map.get("activities");
//        if(activities == null || activities.isEmpty()) {
//            formSelectDef.activityMap = new LinkedHashMap<>();
//            formSelectDef.activities = new ArrayList<>();
//            return;
//        }
//        List<ElementActivity> elementActivities = new ArrayList<>();
//        Map<String, ElementActivity> elementActivityMap = new LinkedHashMap<>();
//        for(Map<String, Object> activity : activities) {
//            String name = (String) activity.get("name");
//            String type = (String) activity.get("type");
////            Object queriesObject = activity.get("queries");
////            List<String> queries = null;
////            if(queriesObject != null && queriesObject instanceof List) {
////                List<String> queryList = (List<String>) queriesObject;
////                if (queryList != null && !queryList.isEmpty())
////                    queries = queryList;
////                else
////                    queries = new ArrayList<>();
////            }
//
//            Object permissionsObject = activity.get("permissions");
//            List<String> permissions = null;
//            if(permissionsObject != null && permissionsObject instanceof List) {
//                List<String> permissionList = (List<String>) permissionsObject;
//                if (permissionList != null && !permissionList.isEmpty())
//                    permissions = permissionList;
//                else
//                    permissions = new ArrayList<>();
//            }
//
//            List<ElementActivity.Operation> operationList = new ArrayList<>();
//            Object operationsObject = activity.get("operations");
//            if(operationsObject != null && operationsObject instanceof List) {
//                List<Map<String, String>> operations = (List<Map<String, String>>) operationsObject;
//                if(!operations.isEmpty()) {
//                    for(Map<String, String> operationMap : operations) {
//                        int order = Integer.valueOf(operationMap.get("order"));
//                        OperationType operationType = OperationType.fromName(operationMap.get("type"));
//                        String operationName = operationMap.get("name");
//                        String resultAttributeName = operationMap.get("result-attribute-name");
//                        if(resultAttributeName == null || resultAttributeName.isEmpty())
//                            resultAttributeName = operationName;
//                        operationList.add(new ElementActivity.Operation(order, operationType, operationName, resultAttributeName));
//                    }
//                }
//            }
//            Collections.sort(operationList);
//
//            ElementActivity elementActivity = new ElementActivity(name, type, permissions, operationList);
//            elementActivities.add(elementActivity);
//            elementActivityMap.put(name, elementActivity);
//        }
//        formSelectDef.activities = elementActivities;
//        formSelectDef.activityMap = elementActivityMap;
//    }

    private static void fillFormSelectColumns(Map map, FormSelectDef formSelectDef) {
        List<Map<String, String>> columnDefs = (List<Map<String, String>>)map.get("columns");
        if(columnDefs == null || columnDefs.isEmpty()) {
            formSelectDef.formSelectColumnDefMap = new LinkedHashMap<>();
            formSelectDef.formSelectColumnDefs = new ArrayList<>();
            return;
        }
        List<FormSelectColumnDef> columns = new ArrayList<>();
        Map<String, FormSelectColumnDef> columnMap = new LinkedHashMap<>();
        for(Map<String, String> columnDef : columnDefs) {
            String property = columnDef.get("property");
            String title = columnDef.get("title");
            String styleClass = columnDef.get("style-class");
            String sortable = columnDef.get("sortable");
            FormSelectColumnDef formSelectColumnDef = new FormSelectColumnDef(property, title, styleClass, sortable != null && sortable.equalsIgnoreCase("true"));
            columnMap.put(property, formSelectColumnDef);
            columns.add(formSelectColumnDef);
        }
        formSelectDef.formSelectColumnDefMap = columnMap;
        formSelectDef.formSelectColumnDefs = columns;
    }

    private static FormSelectDef getFormSelectDef(Map<String, Object> formSelectDefMap) {
        FormSelectDef formSelectDef = new FormSelectDef();
        String name = (String) formSelectDefMap.get("name");
        String queryName = (String) formSelectDefMap.get("query-name");
        String title = (String) formSelectDefMap.get("title");
        String decorator = (String) formSelectDefMap.get("decorator");
        String tableActivity = (String) formSelectDefMap.get("table-activity");
        String sortProperty = (String) formSelectDefMap.get("sortPropertyParamName");
        if(sortProperty == null || sortProperty.isEmpty())
            sortProperty = "sortProperty";
        String sortOrder = (String) formSelectDefMap.get("sortOrderParamName");
        if(sortOrder == null || sortOrder.isEmpty())
            sortOrder = "sortOrder";
        String rowPerPage = (String) formSelectDefMap.get("rowPerPageParamName");
        String actionURL = (String) formSelectDefMap.get("actionURL");
        String actionMethod = (String) formSelectDefMap.get("actionMethod");
        Object searchParams = formSelectDefMap.get("search-params");
        List<ElementInitialSelect> selects = getElementInitialSelects(formSelectDefMap.get("initial-select"));
        Object controlInRowObject = formSelectDefMap.get("control-in-row");
        int controlInRow = controlInRowObject == null ? 1 : (int) controlInRowObject;
        List<ElementControl> controls = getElementControls(formSelectDefMap.get("control"));
        List<ElementButton> buttons = getElementButtons(formSelectDefMap.get("button"));
        if(searchParams == null || !(searchParams instanceof List))
            formSelectDef.searchParams = new ArrayList<>();
        else
            formSelectDef.searchParams = (List<String>) searchParams;
        formSelectDef.name = name;
        formSelectDef.queryName = queryName;
        formSelectDef.title = title;
        formSelectDef.decorator = decorator;
        formSelectDef.tableActivity = tableActivity;
        formSelectDef.sortProperty = sortProperty;
        formSelectDef.sortOrder = sortOrder;
        formSelectDef.actionURL = actionURL;
        formSelectDef.actionMethod = actionMethod;
        formSelectDef.rowPerPage = rowPerPage;
        formSelectDef.controlInRow = controlInRow;
        formSelectDef.selects = selects;
        formSelectDef.controls = controls;
        formSelectDef.buttons = buttons;
//        formSelectDef.selectDef = sqlModelManager.getSelectDef(queryName);
        fillFormSelectColumns(formSelectDefMap, formSelectDef);
        fillFormSelectInRowButtons(formSelectDefMap, formSelectDef);
        fillFormActivities(formSelectDefMap, formSelectDef);
        return formSelectDef;
    }

//    private static void fillPersistenceColumns(Map map, PersistenceDef persistenceDef) {
//        List<Map<String, String>> columnDefs = (List<Map<String, String>>)map.get("columns");
//        if(columnDefs == null || columnDefs.isEmpty()) {
//            persistenceDef.persistenceColumnDefMap = new LinkedHashMap<>();
//            persistenceDef.persistenceColumnDefs = new ArrayList<>();
//            return;
//        }
//        List<PersistenceColumnDef> columns = new ArrayList<>();
//        Map<String, PersistenceColumnDef> columnMap = new LinkedHashMap<>();
//        for(Map<String, String> columnDef : columnDefs) {
//            String property = columnDef.get("property");
//            String titleKey = columnDef.get("title-key");
//            String styleClass = columnDef.get("style-class");
//            String sortable = columnDef.get("sortable");
//            PersistenceColumnDef persistenceColumnDef = new PersistenceColumnDef(property, titleKey, styleClass, sortable != null && sortable.equalsIgnoreCase("true"));
//            columnMap.put(property, persistenceColumnDef);
//            columns.add(persistenceColumnDef);
//        }
//        persistenceDef.persistenceColumnDefMap = columnMap;
//        persistenceDef.persistenceColumnDefs = columns;
//    }

    public static FormModelManager getNewInstance(InputStream ...formInputStreams)
            throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, FormPersistDef> formInsertDefMap = new LinkedHashMap<>();
        Map<String, FormSelectDef> formSelectDefMap = new LinkedHashMap<>();
        for(InputStream is : formInputStreams) {
            String string = IOUtils.toString(is);
            List<Map> modelList = objectMapper.readValue(string, List.class);
            for(Map map : modelList) {
                if(String.valueOf(map.get("type")).equals("persist")) {
                    FormPersistDef formPersistDef = getFormPersistDef(map);
                    formInsertDefMap.put(formPersistDef.name, formPersistDef);
                } else if(String.valueOf(map.get("type")).equals("list")) {
//                    FormSelectDef formSelectDef = getFormSelectDef(sqlModelManager, map);
//                    formSelectDefMap.put(formSelectDef.name, formSelectDef);
                }
            }
        }
        FormModelManager formModelManager = new FormModelManager(formInsertDefMap, formSelectDefMap);
        return formModelManager;
    }
}
