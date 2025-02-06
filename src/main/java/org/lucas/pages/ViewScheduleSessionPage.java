package org.lucas.pages;

import org.lucas.ui.framework.Color;
import org.lucas.ui.framework.UiBase;
import org.lucas.ui.framework.View;
import org.lucas.ui.framework.views.ListView;

public class ViewScheduleSessionPage extends UiBase{

    @Override
    public View OnCreateView() {
        ListView lv = new ListView(this.canvas, Color.BLUE);
        return lv;
    }

    @Override
    public void OnViewCreated(View parentView) {

    }
}
