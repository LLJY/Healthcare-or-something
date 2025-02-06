package org.lucas.ui.framework;


public abstract class UiBase {
    protected Canvas canvas;

    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
    }

    /**
     * Base method for OnCreateView, here you will define your views, must return compatible View object
     *
     * @return returns a view for the framework to utilize
     */
    public abstract View OnCreateView();

    /**
     * Any other pre-execution you may want to perform before the view is executed. Such as initializing objects.
     * @param parentView the Parent view of the UiBase class, provided in OnCreateView
     */
    public abstract void OnViewCreated(View parentView);

    /**
     * Called when the back button is pressed.
     */
    protected void OnBackPressed(){}

    /**
     * Called when the application is exited.
     */
    protected void OnApplicationExit(){}

    /**
     * Navigates to the next page
     * @param page
     */
    public void ToPage(UiBase page) {
        page.setCanvas(canvas);
        var view = page.OnCreateView();
        page.OnViewCreated(view);
        canvas.pushPage(view);
        canvas.addOnBackPressedCallback(page::OnBackPressed);
        canvas.addApplicationStopCallback(page::OnApplicationExit);
    }

}
