package org.lucas.pages.patient;

import org.lucas.Globals;
import org.lucas.controllers.UserController;
import org.lucas.models.Appointment;
import org.lucas.models.enums.AppointmentStatus;
import org.lucas.ui.framework.Color;
import org.lucas.ui.framework.TextStyle;
import org.lucas.ui.framework.UiBase;
import org.lucas.ui.framework.View;
import org.lucas.ui.framework.views.ListView;
import org.lucas.ui.framework.views.ListViewOrientation;
import org.lucas.ui.framework.views.TextView;

import java.util.List;

public class BillingPage extends UiBase {
    public static List<Appointment> appointments;
    @Override
    public View OnCreateView() {
        return new ListView(this.canvas, Color.BLUE);
    }

    @Override
    public void OnViewCreated(View parentView) {
        ListView listView = (ListView) parentView;
        if (appointments == null) {
            // If appointments is null, retrieve the concluded appointments for the active patient
            appointments = Globals.appointmentController.getAppointments()
                    .stream()
                    .filter(appointment -> appointment.getPatient().getId().equals(UserController.getActivePatient().getId()))
                    .filter(appointment -> appointment.getAppointmentStatus() == AppointmentStatus.COMPLETED)
                    .toList();
        }

        if (appointments.isEmpty()) {
            listView.addItem(new TextView(this.canvas, "No concluded appointments found for this patient.", Color.RED, TextStyle.BOLD));
        } else {
            listView.setTitleHeader("Billing Details");
            ListView rowView = new ListView(this.canvas, Color.BLUE, ListViewOrientation.HORIZONTAL);
            for (Appointment appointment: appointments) {
                if(appointment.getBilling() == null){
                    continue;
                }
                Color color = getItemColor(appointment.getBilling().isPaid());

                // Display billing details for each appointment
                rowView.addItem(new TextView(this.canvas,
                        " | Appointment Date: " + appointment.getAppointmentTime().toLocalDate(),
                        color));

                rowView.addItem(new TextView(this.canvas,
                        " | Amount Due: $" + String.format("%.2f",
                                appointment.getBilling().getBillAmount()),
                        color, TextStyle.BOLD));

                rowView.addItem(new TextView(this.canvas,
                        " | Payment Status: " + (appointment.getBilling().isPaid()? "Paid": "Unpaid"),
                        color, TextStyle.BOLD));
            }
            listView.addItem(rowView);
        }
        canvas.setRequireRedraw(true);
    }

    private Color getItemColor(boolean status) {
        return status ? Color.GREEN : Color.RED;
    }

    @Override
    public void OnBackPressed() {
        super.OnBackPressed();
        appointments = null;
    }
}
