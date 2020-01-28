package ir.piana.dev.struts.dynamic.form;

import java.util.List;
import java.util.Map;

/**
 * Created by mj.rahmati on 12/28/2019.
 */
public abstract class FormDef {
    String name;
    String queryName;
    String title;
    List<ElementInitialSelect> selects;
    int controlInRow;
    List<ElementControl> controls;
    List<ElementButton> buttons;
    List<ElementInRowButton> inRowButtons;
    Map<String, ElementInRowButton> inRowButtonMap;
    Map<String, ElementActivity> activityMap;
    List<ElementActivity> activities;

    public String getName() {
        return name;
    }

    public String getQueryName() {
        return queryName;
    }

    public String getTitle(){
        return title;
    }

    public List<ElementInitialSelect> getInitialSelects(){
        return selects;
    }

    public int getControlInRow(){
        return controlInRow;
    }

    public List<ElementControl> getControls(){
        return controls;
    }

    public List<ElementButton> getButtons(){
        return buttons;
    }

    public List<ElementActivity> getActivities() {
        return activities;
    }

    public Map<String, ElementActivity> getActivityMap() {
        return activityMap;
    }

    public List<ElementInRowButton> getInRowButtons() {
        return inRowButtons;
    }

    public Map<String, ElementInRowButton> getInRowButtonMap() {
        return inRowButtonMap;
    }
}
