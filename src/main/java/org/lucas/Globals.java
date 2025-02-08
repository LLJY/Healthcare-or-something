package org.lucas;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.lucas.controllers.AppointmentController;
import org.lucas.controllers.LoginController;
import org.lucas.pages.*;

/**
 * Our project is far too small to warrant any complex Dependency Injection.
 * So compromise by just using static instances.
 */
public class Globals {

    // add your pages here
    public static ExampleUiSecondPage exampleUiSecondPage = new ExampleUiSecondPage();
    public static PatientInfoPage patientInfoPage = new PatientInfoPage();
    public static ZoomMeetingPage zoomMeetingPage = new ZoomMeetingPage();
    public static LoginPage loginPage = new LoginPage();
    public static MainPage mainPage = new MainPage();
    public static AppointmentInfoPage appointmentInfoPage = new AppointmentInfoPage();
    public static ViewScheduleSessionPage viewScheduleSessionPage = new ViewScheduleSessionPage();
    public static ScheduleNext scheduleNext = new ScheduleNext();
    public static ViewAppointmentsPage viewAppointmentsPage = new ViewAppointmentsPage();

    // Controllers
    public static AppointmentController appointmentManager = new AppointmentController();
    public static LoginController loginController = new LoginController();

    public static Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
    // used strictly for debugging, beats printOutputData for POJO classes, lol
    public static Gson gsonPrettyPrint = new GsonBuilder().setPrettyPrinting().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();

}