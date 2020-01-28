package ir.piana.dev.struts.dynamic.form;

import java.util.List;

/**
 * Created by mj.rahmati on 12/26/2019.
 */
public class ElementButton {
    String name;
    String title;
    String type;
    List<String> activities;
    String returnUrl;

    public ElementButton(String name, String title, String type, String returnUrl, List<String> activities) {
        this.name = name;
        this.title = title;
        this.type = type;
        this.returnUrl = returnUrl;
        this.activities = activities;
    }

    public String getName() {
        return name;
    }

    public String getTitle() {
        return title;
    }

    public String getType() {
        return type;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public List<String> getActivities() {
        return activities;
    }
}
