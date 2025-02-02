package org.lucas.ui.framework;

public class ApplicationContext {
    protected Canvas canvas;
    public ApplicationContext(Canvas canvas) {
        this.canvas = canvas;
    }

    /**
     * Sets the entry point of the application.
     * @param initialUi the UiBase class entry point
     */
    public void startApplication(UiBase initialUi) {
        initialUi.setCanvas(canvas);
        initialUi.ToPage(initialUi);
        canvas.mainLoop();
    }
}
