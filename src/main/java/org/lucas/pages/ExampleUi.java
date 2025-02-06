package org.lucas.pages;

import org.lucas.Globals;
import org.lucas.controllers.ExampleUiController;
import org.lucas.ui.framework.Color;
import org.lucas.ui.framework.views.TextView;
import org.lucas.ui.framework.UiBase;
import org.lucas.ui.framework.View;
import org.lucas.ui.framework.views.ListView;

import java.util.ArrayList;
import java.util.List;

public class ExampleUi extends UiBase {
    List<String> randomExamples = new ArrayList<>();

    // define your controllers as static so other classes can always access it.
    // This is usually bad practice, but we do not intend for a DI framework, so let's just keep it pragmatic.
    public static ExampleUiController controller = new ExampleUiController();

    @Override
    public View OnCreateView() {

        ListView lv = new ListView(this.canvas, Color.MAGENTA);
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

        // good practice to separate all your user input interception here.
        parentView.attachUserInput("Navigate to the next page", str->{
            controller.nextPageCalled(str);
            ToPage(Globals.exampleUiSecondPage);
        });
        parentView.attachUserInput("Get and display example user", str->{
            var user = controller.getExampleUser();
            if(user != null){
                // remember to typecast your view to the correct type!
                String textView = String.format("Name: %s\nAge: %d\nEmail: %s", user.name(), user.age(), user.email());
                ((ListView) parentView).addItem(
                        new TextView(this.canvas, textView, Color.MAGENTA)
                );

                // MUST!! tell the ui to redraw
                canvas.setRequireRedraw(true);
            }
        });
    }

    // OPTIONAL OVERRIDES, YOU DON'T HAVE TO OVERRIDE THESE METHODS!!

    @Override
    public void OnBackPressed() {
        super.OnBackPressed();
        // just as a test. you may leave this override empty
        System.out.println("You pressed back!");
    }

    @Override
    public void OnApplicationExit() {
        System.out.println("You exited the application!");
    }
}
