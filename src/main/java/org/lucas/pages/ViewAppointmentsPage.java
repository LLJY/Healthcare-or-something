package org.lucas.pages;

import org.lucas.Globals;
import org.lucas.controllers.LoginController;
import org.lucas.models.Appointment;
import org.lucas.models.AppointmentStatus;
import org.lucas.models.enums.UserType;
import org.lucas.ui.framework.Color;
import org.lucas.ui.framework.TextStyle;
import org.lucas.ui.framework.UiBase;
import org.lucas.ui.framework.View;
import org.lucas.ui.framework.views.ListView;
import org.lucas.ui.framework.views.ListViewOrientation;
import org.lucas.ui.framework.views.TextView;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class ViewAppointmentsPage extends UiBase {
    ListView listView;
    @Override
    public View OnCreateView() {
        ListView lv = new ListView(
                this.canvas,
                Color.GREEN
        );
        if(LoginController.getActiveUserType() != UserType.DOCTOR){
            this.OnBackPressed();
            return lv;
        }
        lv.setTitleHeader(" View Appointments | Welcome back! " + LoginController.getActiveDoctor().getName());
        this.listView = lv;
        return lv;
    }

    @Override
    public void OnViewCreated(View parentView) {
       ListView listView = (ListView) parentView;
       listView.attachUserInput("Select Patient index", str->{
           List<Appointment> appointments = Globals.appointmentManager.getAppointments();
           selectAppointmentPrompt(appointments);
           Appointment appointment = appointments.get(Integer.parseInt(str));
       });
       refreshUi();
    }

    private void selectAppointmentPrompt(List<Appointment> appointments) {
        Scanner scanner = new Scanner(System.in);
        boolean validInput = false;
        int selectedIndex = -1;
        while (!validInput) {
            System.out.println("Select an index (0 - " + appointments.size() + ")");
            selectedIndex = scanner.nextInt();
            if(selectedIndex >= 0 && selectedIndex < appointments.size()) {
                validInput = true;
                break;
            }
        }
        Appointment selectedAppointment = appointments.get(selectedIndex);
        boolean validInput1 = false;
        int selectedIndex1 = -1;
        while (!validInput1) {
            System.out.println("Select Options: 1. Approve appointment | 2. Reject appointment | 3. Start Appointment | 4. View Patient Info");
            selectedIndex1 = scanner.nextInt();
            if(selectedIndex1 >= 0 && selectedIndex1 < 4) {
                validInput = true;
                break;
            }
        }
        switch (selectedIndex1){
            case 1:
                selectedAppointment.setAppointmentStatus(AppointmentStatus.ACCEPTED);
                break;
            case 2:
                selectedAppointment.setAppointmentStatus(AppointmentStatus.DECLINED);
                break;
            case 3:
                // TODO, go to appt page
            case 4:
                // TODO go to view patient page
        }

        refreshUi();
    }

    private void refreshUi() {
        listView.clear();
        List<Appointment> appointments = Globals.appointmentManager.getAppointments();
        for (int i = 0; i < appointments.size(); i++) {
            Appointment appointment = appointments.get(i);
            String patientName = appointment.getPatient().getName();
            String consultReason = appointment.getReason();
            String formattedTime = appointment.getAppointmentTime().format(DateTimeFormatter.ofPattern("HH:mm"));
            if(appointment.getAppointmentStatus() == null){
                appointment.setAppointmentStatus(AppointmentStatus.PENDING);
            }
            String status = appointment.getAppointmentStatus().toString();

            String displayText = String.format("%d. %s | Consult Reason: %s | Time: %s |",
                    i, patientName, consultReason, formattedTime);

            Color itemColor = getItemColor(appointment.getAppointmentStatus()); // Extract to a separate method

            // add row view for horizontal separation, use two text views for two different statuses.
            ListView rowView = new ListView(this.canvas, itemColor, ListViewOrientation.HORIZONTAL);
            rowView.addItem(new TextView(this.canvas, displayText, itemColor));
            rowView.addItem(new TextView(this.canvas, status, itemColor, TextStyle.BOLD));
            listView.addItem(rowView);
        }
        this.canvas.setRequireRedraw(true);
    }

    private Color getItemColor(AppointmentStatus status) {
        switch (status) {
            case DECLINED:
                return Color.RED;
            case PENDING:
                return Color.CYAN;
            default:
                return Color.GREEN;
        }
    }
}
