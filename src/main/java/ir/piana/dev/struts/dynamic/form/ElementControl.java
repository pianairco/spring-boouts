package ir.piana.dev.struts.dynamic.form;

/**
 * Created by mj.rahmati on 12/26/2019.
 */
public class ElementControl {
    String name;
    String title;
    boolean disabled;
    String type;
    String items;
    String queryName;
    String optionValue;
    String optionTitle;

    public ElementControl(String name, String title, boolean disabled, String type, String items, String queryName, String optionValue, String optionTitle) {
        this.name = name;
        this.title = title;
        this.disabled = disabled;
        this.type = type;
        this.items = items;
        this.queryName = queryName;
        this.optionValue = optionValue;
        this.optionTitle = optionTitle;
    }

    public ElementControl(String name, String title, String type, String queryName) {
        this.name = name;
        this.title = title;
        this.type = type;
        this.queryName = queryName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    void setTitle(String title) {
        this.title = title;
    }

    public boolean isDisabled() {
        return disabled;
    }

    void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public String getType() {
        return type;
    }

    void setType(String type) {
        this.type = type;
    }

    public String getItems() {
        return items;
    }

    public void setItems(String items) {
        this.items = items;
    }

    public String getQueryName() {
        return queryName;
    }

    public void setQueryName(String queryName) {
        this.queryName = queryName;
    }

    public String getOptionValue() {
        return optionValue;
    }

    void setOptionValue(String value) {
        this.optionValue = value;
    }

    public String getOptionTitle() {
        return optionTitle;
    }

    void setOptionTitle(String optionTitle) {
        this.optionTitle = optionTitle;
    }
}
