package org.lucas.ui.framework.views;

import org.lucas.ui.framework.Canvas;
import org.lucas.ui.framework.Color;
import org.lucas.ui.framework.TextStyle;
import org.lucas.ui.framework.View;

public class TextView extends View {
    public TextStyle textStyle;

    public TextView(Canvas canvas, String content, Color color) {
        super(canvas, content, color);
        this.textStyle = TextStyle.NONE;
    }
    public TextView(Canvas canvas, String content, Color color, TextStyle textStyle) {
        super(canvas, content, color);
        this.textStyle = textStyle;
    }

    @Override
    public String getText() {
        if(textStyle == TextStyle.NONE){
            return super.getText();
        }
        if(super.getText() == null) return null;
        return textStyle + super.getText() + "\u001b[0m";
    }
}
