package org.lucas.pages;

import org.lucas.ui.framework.Color;
import org.lucas.ui.framework.UiBase;
import org.lucas.ui.framework.View;
import org.lucas.ui.framework.views.ListView;
import org.lucas.ui.framework.views.TextView;

public class MainPage extends UiBase {
    @Override
    public View OnCreateView() {
        ListView lv = new ListView(this.canvas, Color.GREEN);
        lv.setTitleHeader("Main");
        return lv;
    }

    @Override
    public void OnViewCreated(View parentView) {
        ListView lv = (ListView) parentView; // Cast the parent view to a list view
        lv.setTitleHeader("Welcome to Telemedicine Integration System "); // Set the title header of the list view
        lv.addItem(new TextView(this.canvas, "1. Patient Info - To view patient information ", Color.GREEN));
        lv.addItem(new TextView(this.canvas, "2. View Appointment - To view new / scheduled appointments for teleconsultation ", Color.GREEN));
        lv.addItem(new TextView(this.canvas, "3. View Past Session / Billing - To view all past consultation session and billing details ", Color.GREEN));
        lv.attachUserInput("Patient Info", str -> {
            ToPage(new PatientInfoPage());
                });
        lv.attachUserInput("View Appointment", str -> {
            ToPage(new ViewScheduleSessionPage());
        });
        lv.attachUserInput("View Past Session / Billing ", str -> {
            ToPage(new AppointmentInfoPage());
        });

        canvas.setRequireRedraw(true);
    }
}