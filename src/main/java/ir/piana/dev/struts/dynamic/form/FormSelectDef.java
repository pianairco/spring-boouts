package ir.piana.dev.struts.dynamic.form;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by mj.rahmati on 12/14/2019.
 */
public class FormSelectDef extends FormDef {
//    SelectDef selectDef;
    String decorator;
    String tableActivity;
    String sortProperty;
    String sortOrder;
    String actionURL;
    String actionMethod;
    String rowPerPage;
    List<String> searchParams;
//    List<SQLParamDef> searchSQLParamDefs;
    Map<String, FormSelectColumnDef> formSelectColumnDefMap;
    List<FormSelectColumnDef> formSelectColumnDefs;



    public FormSelectDef() {
    }

    public FormSelectDef(String queryName) {
        this.queryName = queryName;
    }

//    public FormSelectDef(SelectDef selectDef) {
//        this.queryName = selectDef.getName();
//        this.selectDef = selectDef;
//    }

    public String getDecorator() {
        return decorator;
    }

    public String getTableActivity() {
        return tableActivity;
    }

    public Map<String, FormSelectColumnDef> getFormSelectColumnDefMap() {
        return Collections.unmodifiableMap(formSelectColumnDefMap);
    }

    public List<FormSelectColumnDef> getFormSelectColumnDefs() {
        return Collections.unmodifiableList(formSelectColumnDefs);
    }

    public String getSortProperty() {
        return "";
//        return this.selectDef.getParamMap().get(sortProperty).getKey();
    }

    public String getSortOrder() {
        return "";
//        return this.selectDef.getParamMap().get(sortOrder).getKey();
    }

    public String getActionURL() {
        return actionURL;
    }

    public String getActionMethod() {
        return actionMethod;
    }

    public String getRowPerPage() {
        return "30";
//        return this.selectDef.getParamMap().get(rowPerPage).getKey();
    }

//    public List<SQLParamDef> getSearchParams() {
//        if(searchSQLParamDefs == null) {
//            List<SQLParamDef> searchParams = new ArrayList<>();
//            for (String searchParam : this.searchParams) {
//                if (selectDef.getParamMap().containsKey(searchParam))
//                    searchParams.add(selectDef.getParamMap().get(searchParam));
//            }
//            searchSQLParamDefs = Collections.unmodifiableList(searchParams);
//        }
//        return searchSQLParamDefs;
//    }
}
