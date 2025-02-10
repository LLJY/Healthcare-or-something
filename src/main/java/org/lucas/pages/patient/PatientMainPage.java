package org.lucas.pages.patient;

import org.lucas.Globals;
import org.lucas.controllers.AppointmentController;
import org.lucas.controllers.UserController;
import org.lucas.models.Appointment;
import org.lucas.models.enums.AppointmentStatus;
import org.lucas.pages.doctor.ViewAppointmentsPage;
import org.lucas.ui.framework.Color;
import org.lucas.ui.framework.UiBase;
import org.lucas.ui.framework.View;
import org.lucas.ui.framework.views.ListView;
import org.lucas.ui.framework.views.TextView;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

import static org.lucas.Globals.appointmentController;


/** Represents the main page of the Telemedicine Integration System.
 * This page displays a menu of options for the user to navigate to different sections of the application.
 * It extends {@link UiBase} and uses a {@link ListView} to present the menu items.*/
public class PatientMainPage extends UiBase {

    /** Called when the main page's view is created.
     * Creates a {@link ListView} to hold the main menu options.
     * Sets the title header to "Main".
     * @return A new {@link ListView} instance representing the main page's view.
     * @Override*/

    @Override
    public View OnCreateView() {
        ListView lv = new ListView(this.canvas, Color.GREEN);
        return lv;
    }

    /**Called after the view has been created and attached to the UI.
     * Populates the view with the main menu options, such as "View List of Patient", and "View Appointment".
     * Attaches user input handlers to each menu option to navigate to the corresponding pages.
     * @param parentView The parent {@link View} to which the main page's UI elements are added. This should be a ListView.
     * @Override */
    @Override
    public void OnViewCreated(View parentView) {
        ListView lv = (ListView) parentView; // Cast the parent view to a list view
        lv.setTitleHeader(" Teleconsultation | Welcome back! " + UserController.getActivePatient().getName());
        lv.addItem(new TextView(this.canvas, "1. Book Appointment - To schedule appointment to see doctors ", Color.GREEN));
        lv.addItem(new TextView(this.canvas, "2. View Appointment - To view scheduled appointments for teleconsultation ", Color.GREEN));

        lv.attachUserInput("Book Appointment", str -> {
            bookAppointmentPrompt();
        });

        lv.attachUserInput("View Billing", str -> {
            BillingPage.appointments = appointmentController.getAppointments();
            ToPage(new BillingPage());
        });


        canvas.setRequireRedraw(true);
    }

    private void bookAppointmentPrompt(){
        appointmentController.getAppointments();
        Scanner scanner = new Scanner(System.in); // Create a new scanner object
        System.out.println("Enter reason to consult: ");
        String reason = scanner.nextLine(); //
        System.out.println("Do you have any Medical History?: ");
        String history = scanner.nextLine(); //

        System.out.println("Select your appointment date in this format (DD-MM-YYYY): ");
        LocalDate date = null; // safe to initialise as null, as it will never be null after the prompt.
        boolean validDate = false;
        String appointmentDate;
        while (!validDate) {
            appointmentDate = scanner.nextLine();
            try {
                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                date = LocalDate.parse(appointmentDate, dateFormatter);

                System.out.println(appointmentDate);

                // Check if the date is in the future, don't want past appointments.
                if (date.isBefore(LocalDate.now())) {
                    System.out.println("The appointment date must be in the future. Please enter a valid date (DD-MM-YYYY):");
                    continue;
                }
                validDate = true;
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please enter a date in the format (DD-MM-YYYY):");
            }
        }

        // tze kai I changed your timeslots to a dictionary instead.
        List<String> timeSlots = Arrays.asList("1. 8:00", "2. 9:00", "3. 10:00", "4. 11:00", "5. 12:00", "6. 13:00", "7. 14:00", "8. 15:00", "9. 16:00");

        Dictionary<Integer, LocalDateTime> dateTimeDictionary = new Hashtable<>();
        // start the timeslot at 8:00am
        LocalDateTime startDate = date.atStartOfDay().withHour(8).withMinute(0).withSecond(0).withNano(0);

        // Define the time slots and their corresponding integer keys
        dateTimeDictionary.put(1, startDate);
        dateTimeDictionary.put(2, startDate.plusHours(1));
        dateTimeDictionary.put(3, startDate.plusHours(2));
        dateTimeDictionary.put(4, startDate.plusHours(3));
        dateTimeDictionary.put(5, startDate.plusHours(4));
        dateTimeDictionary.put(6, startDate.plusHours(5));
        dateTimeDictionary.put(7, startDate.plusHours(6));
        dateTimeDictionary.put(8, startDate.plusHours(7));
        dateTimeDictionary.put(9, startDate.plusHours(8));

        // Display available time slots (using StringBuilder)
        StringBuilder sb = new StringBuilder("Available timeslots: [");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        for (int i = 1; i <= dateTimeDictionary.size(); i++) {
            // display for the user in a nice fashion "1. 8:00 AM
            LocalDateTime time = dateTimeDictionary.get(i);
            String formattedTime = formatter.format(time);
            sb.append(i).append(". ").append(formattedTime);
            if (i < dateTimeDictionary.size()) {
                sb.append(", ");
            }
        }
        sb.append("]");
        System.out.println(sb); // Convert StringBuilder to String and print
        System.out.print("Select your appointment timeslot (1-" + dateTimeDictionary.size() + "): ");
        scanner = new Scanner(System.in);

        int selectedSlot = -1; // Initialize to an invalid value
        while (selectedSlot < 1 || selectedSlot > dateTimeDictionary.size()) {
            if (scanner.hasNextInt()) {
                selectedSlot = scanner.nextInt();
                if (selectedSlot < 1 || selectedSlot > dateTimeDictionary.size()) {
                    System.out.println("Invalid timeslot. Please enter a number between 1 and " + dateTimeDictionary.size() + ":");
                }
            } else {
                System.out.println("Invalid input. Please enter a number:");
                scanner.next(); // Clear the invalid input
            }
        }

        LocalDateTime selectedDateTime = dateTimeDictionary.get(selectedSlot);

        System.out.println("You have requested for an appointment on " + formatter.format(selectedDateTime) + " at index " + selectedSlot);
        // TODO maybe a y/N prompt, but it is 5am and i am TIRED

        Appointment appointment = new Appointment(UserController.getActivePatient(), reason, selectedDateTime, AppointmentStatus.PENDING);
        appointment.setHistory(history);
        appointmentController.addAppointment(appointment);
        appointmentController.saveAppointmentsToFile();
        canvas.setRequireRedraw(true);
    }
}

