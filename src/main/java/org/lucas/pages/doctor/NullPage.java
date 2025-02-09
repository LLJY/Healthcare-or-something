package org.lucas.pages.doctor;

import org.lucas.ui.framework.Color;
import org.lucas.ui.framework.UiBase;
import org.lucas.ui.framework.View;
import org.lucas.ui.framework.views.TextView;

/**
 * Null page that implements the null object pattern
 * This avoids cases where the backstack is empty.
 */
public class NullPage extends UiBase {

    @Override
    public View OnCreateView() {
        return new TextView(this.canvas, "If you see this screen, this should not happen, exit the app immediately", Color.RED);
    }

    @Override
    public void OnViewCreated(View parentView) {

    }
}
