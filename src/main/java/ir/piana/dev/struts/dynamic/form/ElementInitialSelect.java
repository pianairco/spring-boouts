package ir.piana.dev.struts.dynamic.form;

import java.util.List;

/**
 * Created by mj.rahmati on 12/26/2019.
 */
public class ElementInitialSelect {
    String name;
    String queryName;
    List<String> mapper;

    public ElementInitialSelect(String name, String queryName) {
        this.name = name;
        this.queryName = queryName;
    }

    public ElementInitialSelect(String name, String queryName, List<String> mapper) {
        this.name = name;
        this.queryName = queryName;
        this.mapper = mapper;
    }

    public String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }

    public String getQueryName() {
        return queryName;
    }

    void setQueryName(String queryName) {
        this.queryName = queryName;
    }

    public List<String> getMapper() {
        return mapper;
    }

    void setMapper(List<String> mapper) {
        this.mapper = mapper;
    }
}
