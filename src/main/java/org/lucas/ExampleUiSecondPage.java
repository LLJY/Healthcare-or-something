package org.lucas;

import org.lucas.ui.framework.Color;
import org.lucas.ui.framework.views.TextView;
import org.lucas.ui.framework.UiBase;
import org.lucas.ui.framework.View;
import org.lucas.ui.framework.views.ListView;

import java.util.ArrayList;
import java.util.List;

public class ExampleUiSecondPage extends UiBase {
    List<String> randomExamples = new ArrayList<>();
    @Override
    public View OnCreateView() {
        randomExamples.addAll(List.of(new String[]{"ABC222", "DEF222", "HIJK222", "MNO222", "PQR222", "STU222", "V222", "WXYZ222"}));
        ListView lv = new ListView(this.canvas, Color.CYAN);

        List<TextView> views = new ArrayList<>();
        for (int i = 0; i < randomExamples.size(); i++) {
            lv.addItem(
                    new TextView(this.canvas, randomExamples.get(i), Color.MAGENTA)
            );
        }

        // must return a view
        return lv;
    }

    @Override
    public void OnViewCreated(View parentView) {
        // this is for you to do stuff right before the view is pushed to the canvas.
    }

    @Override
    public void OnBackPressed() {
        // just as a test.
        System.out.println("SDFSDFDSFSDFDSFDSFDSDFDSFSDFDSFDSF");
    }
}
