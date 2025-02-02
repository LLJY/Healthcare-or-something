package org.lucas.ui.framework;

import org.lucas.ui.framework.views.UserInput;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

// handles page rendering, pass it a view and it should render.
public class Canvas {
    private Terminal terminal;
    private Stack<View> pages;
    private String systemMessage = "";
    private boolean requireRedraw = false;
    protected List<IGenericCallbackInterface> backPressedCallback = new ArrayList<>();
    protected List<IGenericCallbackInterface> applicationStopCallback = new ArrayList<>();
    protected boolean stopCanvas = false;

    public Canvas() {
        this.terminal = new Terminal(); // Handles terminal interaction (see notes below)
        this.pages = new Stack<>();
        // null object pattern, avoid null object reference when hitting the root null page.
        pages.push(new NullView(this));
    }

    public void drawText(String text, Color color, int x, int y) {
        terminal.setCursorPosition(x, y);
        terminal.setTextColor(color); // Assuming Terminal handles ANSI color codes
        terminal.write(text);
        terminal.resetColor(); // Reset to default color after printing
    }

    public void drawText(String text, Color color) {
        terminal.setTextColor(color); // Assuming Terminal handles ANSI color codes
        terminal.write(text);
        terminal.resetColor(); // Reset to default color after printing
    }

    public View getCurrentPage() {
        return this.pages.peek();
    }

    /**
     * Adds an on back press callback for the current page
     * @param callback callback to add
     */
    public void addOnBackPressedCallback(IGenericCallbackInterface callback) {
        this.backPressedCallback.add(callback);
    }

    /**
     * Adds an app exit callback for the current page
     * @param callback callback to add
     */
    public void addApplicationStopCallback(IGenericCallbackInterface callback) {
        this.applicationStopCallback.add(callback);
    }

    protected void clearCallbacks(){
        this.backPressedCallback.clear();
        this.applicationStopCallback.clear();
    }

    public void pushPage(View page){
        this.pages.push(page);
        View v = pages.peek();
        if(v.getInputOptions().get(0) == null){
            // manually add input 0 to always be the back button
            v.inputOptions.put(0, new UserInput("Go Back", str->{
                v.OnBackPressed();
                this.previousPage();
            }));
        }
        // clear the back pressed callbacks when changing pages
        this.clearCallbacks();
        this.addOnBackPressedCallback(page::OnBackPressed);
        this.addApplicationStopCallback(page::OnApplicationExit);
        this.setRequireRedraw(true);
    }

    /**
     * Simple utility function that tells the mainloop to redraw the current page.
     * Call this when updates are required.
     * @param requireRedraw boolean value to set the requireRedraw flag
     */
    public void setRequireRedraw(boolean requireRedraw) {
        this.requireRedraw = requireRedraw;
    }

    public void previousPage() {
        View curPage = pages.pop();

        // NEVER allow the initial NullView to be shown
        if(pages.peek() instanceof NullView){
            pages.push(curPage);
        }
        // call all the callbacks
        for (IGenericCallbackInterface callback : backPressedCallback) {
            callback.callback();
        }
        setRequireRedraw(true);
    }

    public void mainLoop(){
        while(!stopCanvas) {
            View currentPage = this.pages.peek();
            if (currentPage instanceof NullView) {
                continue;
            }
            if(requireRedraw){
                renderView(currentPage);
            }
            terminal.flush();
            // start the scanner
            String response = terminal.getUserInput();
            Integer responseInt = -1;
            try{
                responseInt = Integer.parseInt(response);
            }catch (Exception e){
                if(!response.isEmpty() && response.toLowerCase().charAt(0) == 'q'){
                    stopCanvas = true;
                    break;
                }
                // invalid response
            }
            if (currentPage.getInputOptions() != null
                    && (responseInt == -1 || currentPage.getInputOptions().get(responseInt) == null)) {
                systemMessage = "Invalid input, please try again.";
                setRequireRedraw(true);
                continue;
            }
            // if the input was valid, it should pass through here, so clear the system message
            systemMessage = "";

            // call the lambda method
            try {
                UserInput inputOptions = currentPage.getInputOptions().get(responseInt);
                inputOptions.lambda().onInput(response);
            }catch (Exception e){
                // TODO handle logging with log4j to file
            }
        }

        for (IGenericCallbackInterface callback: applicationStopCallback) {
            callback.callback();
        }
        System.out.println("Bye! Have a Nice Day!");
    }

    public void renderView(View v) {
        if(v instanceof NullView){
            //drawText("The end of the backstack has been reached, this should NOT happen.", Color.WHITE);
            return;
        }

        Color pageColor = this.pages.peek().color;
        terminal.clearScreen();

        // draw the header first
        if(v.getTitleHeader() != null){
            drawText(v.getTitleHeader()+"\n", pageColor);
        }

        String text = v.getText();
        if(text != null){
            drawText(text, pageColor);
        }

        // draw the footer last
        String footer = v.getFooter();
        if(footer != null){
            footer = footer + "| q: Quit App";
            drawText(footer, pageColor);
        }

        // system messages
        if(!systemMessage.isBlank()){
            System.out.println();
            drawText(systemMessage, Color.RED);
        }
        terminal.flush();
        this.setRequireRedraw(false);
    }

    public Terminal getTerminal() {
        return terminal;
    }
}
