package org.lucas;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.lucas.pages.ExampleUi;
import org.lucas.pages.ExampleUiSecondPage;

/**
 * Our project is far too small to warrant any complex Dependency Injection.
 * So compromise by just using static instances.
 */
public class Globals {

    // add your pages here
    public static ExampleUi exampleUi = new ExampleUi();
    public static ExampleUiSecondPage exampleUiSecondPage = new ExampleUiSecondPage();

    public static Gson gson = new Gson();
    // used strictly for debugging, beats printOutputData for POJO classes, lol
    public static Gson gsonPrettyPrint = new GsonBuilder().setPrettyPrinting().create();
}