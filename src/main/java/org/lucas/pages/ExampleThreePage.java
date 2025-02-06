package org.lucas.pages;

import org.lucas.ui.framework.Color;
import org.lucas.ui.framework.UiBase;
import org.lucas.ui.framework.View;
import org.lucas.ui.framework.views.ListView;
import org.lucas.ui.framework.views.TextView;

import java.util.Scanner;

public class ExampleThreePage extends UiBase {
    public String username = "abcdefg";
    public String password = "";

    @Override
    public View OnCreateView() {
        ListView lv = new ListView(
                this.canvas,
                Color.MAGENTA
        );
        lv.addItem(new TextView(this.canvas, "Username: " + username, Color.MAGENTA));
        lv.addItem(new TextView(this.canvas, "Password: " + password, Color.MAGENTA));
        lv.setTitleHeader("Example Three Page");
        return lv;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public void OnViewCreated(View parentView) {
        ListView lv = (ListView) parentView;

        lv.attachUserInput("Enter Username", str->{
            System.out.println("Enter Username:");
            Scanner scanner = new Scanner(System.in);
            this.setUsername(scanner.next());
            lv.clear();
            lv.addItem(new TextView(this.canvas, "Username: " + username, Color.MAGENTA));
            lv.addItem(new TextView(this.canvas, "Password: " + password, Color.MAGENTA));
            canvas.setRequireRedraw(true);
        });
        parentView.attachUserInput("Press this if you are gay", str->{
            Scanner scanner = new Scanner(System.in);

        });
    }
}
