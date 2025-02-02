package org.lucas;

import org.lucas.ui.framework.ApplicationContext;
import org.lucas.ui.framework.Canvas;


//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        ExampleRecord exampleRecord = new ExampleRecord("Lucas Lee", 12, "aaaaa@aaaa.com");
        System.out.println(exampleRecord.toPrettyJsonString());

        // create a new canvas
        var canvas = new Canvas();

        ApplicationContext applicationContext = new ApplicationContext(canvas);

        applicationContext.start(Globals.exampleUi);

        // call this to execute the ui framework.
        canvas.mainLoop();
    }
}