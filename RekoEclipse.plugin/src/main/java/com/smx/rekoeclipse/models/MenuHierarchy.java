
package com.smx.rekoeclipse.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MenuHierarchy {

    @SerializedName("items")
    @Expose
    private List<MenuEntry> items = null;
    @SerializedName("text")
    @Expose
    private Object text;
    @SerializedName("commandId")
    @Expose
    private Object commandId;
    @SerializedName("commandData")
    @Expose
    private Object commandData;

    public List<MenuEntry> getItems() {
        return items;
    }

    public void setItems(List<MenuEntry> items) {
        this.items = items;
    }

    public Object getText() {
        return text;
    }

    public void setText(Object text) {
        this.text = text;
    }

    public Object getCommandId() {
        return commandId;
    }

    public void setCommandId(Object commandId) {
        this.commandId = commandId;
    }

    public Object getCommandData() {
        return commandData;
    }

    public void setCommandData(Object commandData) {
        this.commandData = commandData;
    }

}
