package org.lucas.ui.framework;

import java.util.Stack;

/**
 * Defines the application start point. And gracefully starts the application.
 */
public class ApplicationContext {
    protected Canvas canvas;
    protected Stack<UiBase> backStack = new Stack<>();
    public ApplicationContext(Canvas canvas) {
        this.canvas = canvas;
    }

    /**
     * Sets the entry point of the application.
     * @param initialUi the UiBase class entry point
     */
    public void startApplication(UiBase initialUi) {
        initialUi.setCanvas(canvas);
        initialUi.setApplicationContext(this);
        initialUi.ToPage(initialUi);
        canvas.mainLoop();
    }
}
