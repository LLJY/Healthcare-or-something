package org.lucas.ui.framework.views;

import org.lucas.ui.framework.Canvas;
import org.lucas.ui.framework.Color;
import org.lucas.ui.framework.View;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

public class ListView extends View {
    private List<View> views = new ArrayList<>();

    public ListView(Canvas canvas, Color color) {
        super(canvas, "", color);
    }

    /**
     * Adds a list of views to the listview
     * @param views list of children
     */
    public void addItems(List<View> views){
        this.views.addAll(views);
    }

    /**
     * Adds a single item to the listview
     * @param view child item to add
     */
    public void addItem(View view){
        this.views.add(view);
    }

    /**
     * Removes a listview child at index
     * @param index index of child to remove
     */
    public void removeItemAt(int index){
        this.views.remove(index);
    }

    /**
     * Sets all children to the list of views. Overwrites everything already inside the list view.
     * @param views list of children to set
     */
    public void setItems(List<View> views){
        this.views.clear();
        this.views.addAll(views);
    }

    /**
     * Clears the ListView
     */
    public void clear(){
        this.views.clear();
    }

    /**
     * Listview overrides the getText method to provide the concatenated strings of all its children.
     * @return returns the strings that should be rendered
     */
    @Override
    public String getText() {
        StringBuilder sb = new StringBuilder();

        // oh cool java can do this now?
        for (View view : views) {
            sb.append(view.getText());
            sb.append("\n");
        }
        // escape the ascii colour
        return sb.toString();
    }

}
