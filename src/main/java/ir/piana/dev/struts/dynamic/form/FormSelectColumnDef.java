package ir.piana.dev.struts.dynamic.form;

/**
 * Created by mj.rahmati on 12/14/2019.
 */
public class FormSelectColumnDef {
    String property;
    String title;
//    String titleKey;
    String styleClass;
    boolean sortable;

    public FormSelectColumnDef(String property, String title, String styleClass, boolean sortable) {
        this.property = property;
        this.title = title;
        this.styleClass = styleClass;
        this.sortable = sortable;
    }

    public String getProperty() {
        return property;
    }

    void setProperty(String property) {
        this.property = property;
    }

    public String getTitle() {
        return title;
    }

    void setTitle(String title) {
        this.title = title;
    }

    public String getStyleClass() {
        return styleClass;
    }

    void setStyleClass(String styleClass) {
        this.styleClass = styleClass;
    }

    public boolean isSortable() {
        return sortable;
    }

    void setSortable(boolean sortable) {
        this.sortable = sortable;
    }
}
