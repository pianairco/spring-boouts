package ir.piana.dev.struts.dynamic.form;

import java.util.List;

public class ElementInRowButton {
    String name;
    String title;
    String action;
    String method;
    String image;
    List<String> activities;
    List<Parameter> parameters;

    public ElementInRowButton() {
    }

    public ElementInRowButton(String name, String title, String action, String method, String image, List<String> activities, List<Parameter> parameters) {
        this.name = name;
        this.title = title;
        this.action = action;
        this.method = method;
        this.image = image;
        this.activities = activities;
        this.parameters = parameters;
    }

    public String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    void setTitle(String title) {
        this.title = title;
    }

    public String getAction() {
        return action;
    }

    void setAction(String action) {
        this.action = action;
    }

    public String getMethod() {
        return method;
    }

    void setMethod(String method) {
        this.method = method;
    }

    public String getImage() {
        return image;
    }

    void setImage(String image) {
        this.image = image;
    }

    public List<String> getActivities() {
        return activities;
    }

    public List<Parameter> getParameters() {
        return parameters;
    }

    void setParameters(List<Parameter> parameters) {
        this.parameters = parameters;
    }

    public static class Parameter {
        String name;
        String attribute;

        public Parameter() {
        }

        public Parameter(String name, String attribute) {
            this.name = name;
            this.attribute = attribute;
        }

        public String getName() {
            return name;
        }

        void setName(String name) {
            this.name = name;
        }

        public String getAttribute() {
            return attribute;
        }

        void setAttribute(String attribute) {
            this.attribute = attribute;
        }
    }
}

