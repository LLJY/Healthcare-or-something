package org.lucas.pages.doctor;

import org.lucas.Globals;
import org.lucas.controllers.UserController;
import org.lucas.models.Appointment;
import org.lucas.models.enums.AppointmentStatus;
import org.lucas.models.Billing;
import org.lucas.models.Session;
import org.lucas.models.enums.UserType;
import org.lucas.ui.framework.Color;
import org.lucas.ui.framework.TextStyle;
import org.lucas.ui.framework.UiBase;
import org.lucas.ui.framework.View;
import org.lucas.ui.framework.views.ListView;
import org.lucas.ui.framework.views.ListViewOrientation;
import org.lucas.ui.framework.views.TextView;
import org.lucas.util.ZoomCreator;
import org.lucas.util.ZoomOAuth;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import static org.lucas.Globals.gsonPrettyPrint;

public class ViewAppointmentsPage extends UiBase {
    ListView listView;
    List<Appointment> appointments;
    @Override
    public View OnCreateView() {
        ListView lv = new ListView(
                this.canvas,
                Color.GREEN
        );
        if(UserController.getActiveUserType() != UserType.DOCTOR){
            this.OnBackPressed();
            return lv;
        }
        lv.setTitleHeader(" View Appointments | " + UserController.getActiveDoctor().getName());
        this.listView = lv;
        return lv;
    }

    @Override
    public void OnViewCreated(View parentView) {
       ListView listView = (ListView) parentView;
        appointments = Globals.appointmentController.getAppointments()
                .stream()
                .filter(x->x.getAppointmentStatus() != AppointmentStatus.COMPLETED)
                .toList();

       // filter the appointments list and only show the appointments that are completed.
       listView.attachUserInput("Select Patient index", str->{
           selectAppointmentPrompt(appointments);
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
            int indexMax = 5;
            if(selectedAppointment.getAppointmentStatus() == AppointmentStatus.ACCEPTED){
                System.out.println("Select Options: 1. Approve appointment | 2. Reject appointment | 3. View Patient Info | 4. Start Appointment");
            }else{
                System.out.println("Select Options: 1. Approve appointment | 2. Reject appointment | 3. View Patient Info");
                indexMax -= 1;
            }
            selectedIndex1 = scanner.nextInt();
            if(selectedIndex1 >= 0 && selectedIndex1 < indexMax) {
                validInput = true;
                break;
            }
        }
        switch (selectedIndex1){
            case 1:
                selectedAppointment.setAppointmentStatus(AppointmentStatus.ACCEPTED);
                System.out.println("Accepted, generating zoomlink...");
                try {
                    // Get the OAuth access token
                    String accessToken = ZoomOAuth.getAccessToken();

                    // Call the method to create a Zoom meeting
                    String joinUrl = ZoomCreator.createZoomMeeting(
                            accessToken,
                            "Zoom Meeting",
                            1, // Duration in minutes
                            "UTC" // Timezone
                    );
                    selectedAppointment.setSession(new Session(joinUrl));
                    selectedAppointment.setBilling(new Billing());
                    Globals.appointmentController.saveAppointmentsToFile();
                }catch (IOException e){
                    System.out.println("Error generating zoom link");
                }
                break;
            case 2:
                selectedAppointment.setAppointmentStatus(AppointmentStatus.DECLINED);
                Globals.appointmentController.saveAppointmentsToFile();
                break;
            case 3:
                PatientInfoPage.patient = selectedAppointment.getPatient();
                ToPage(Globals.patientInfoPage);
                break;
            case 4:
                TeleconsultPage.setAppointment(selectedAppointment);
                ToPage(Globals.teleconsultPage);
                break;
        }

        refreshUi();
    }

    private void refreshUi() {
        listView.clear();
        // loop through the appointments and display them
        for (int i = 0; i < appointments.size(); i++) {
            Appointment appointment = appointments.get(i);
            String patientName = appointment.getPatient().getName();
            String consultReason = appointment.getReason();
            String formattedTime = appointment.getAppointmentTime().format(DateTimeFormatter.ofPattern("HH:mm"));
            String formattedDate = appointment.getAppointmentTime().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));

            // error handling for dirty json data
            if(appointment.getAppointmentStatus() == null){
                appointment.setAppointmentStatus(AppointmentStatus.PENDING);
            }

            String status = appointment.getAppointmentStatus().toString();

            String displayText = String.format("%d. %s | Consult Reason: %s | Date: %s | Time: %s |",
                    i, patientName, consultReason, formattedDate, formattedTime);

            Color itemColor = getItemColor(appointment.getAppointmentStatus());

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
