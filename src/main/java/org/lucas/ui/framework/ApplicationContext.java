package org.lucas.ui.framework;

public class ApplicationContext {
    protected Canvas canvas;
    public ApplicationContext(Canvas canvas) {
        this.canvas = canvas;
    }

    public void start(UiBase initialUi) {
        initialUi.setCanvas(canvas);
        initialUi.ToPage(initialUi);
    }
}
