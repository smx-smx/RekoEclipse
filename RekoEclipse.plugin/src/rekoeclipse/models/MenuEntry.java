
package rekoeclipse.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MenuEntry {

    @SerializedName("items")
    @Expose
    private List<MenuItem> items = null;
    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("commandId")
    @Expose
    private Object commandId;
    @SerializedName("commandData")
    @Expose
    private Object commandData;

    public List<MenuItem> getItems() {
        return items;
    }

    public void setItems(List<MenuItem> items) {
        this.items = items;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
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
