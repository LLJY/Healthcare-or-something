package org.lucas;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.lucas.controllers.AppointmentController;
import org.lucas.controllers.UserController;
import org.lucas.pages.LoginPage;
import org.lucas.pages.doctor.*;

/**
 * Our project is far too small to warrant any complex Dependency Injection.
 * So compromise by just using static instances.
 */
public class Globals {

    // add your pages here
    public static PatientInfoPage patientInfoPage = new PatientInfoPage();
    public static ZoomMeetingPage zoomMeetingPage = new ZoomMeetingPage();
    public static DoctorMainPage mainPage = new DoctorMainPage();
    public static LoginPage loginPage = new LoginPage();
    public static TeleconsultPage teleconsultPage = new TeleconsultPage();
    public static ViewScheduleSessionPage viewScheduleSessionPage = new ViewScheduleSessionPage();
    public static ScheduleNext scheduleNext = new ScheduleNext();
    public static ViewAppointmentsPage viewAppointmentsPage = new ViewAppointmentsPage();

    // Controllers
    public static AppointmentController appointmentController = new AppointmentController();
    public static UserController userController = new UserController();

    public static Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
    // used strictly for debugging, beats printOutputData for POJO classes, lol
    public static Gson gsonPrettyPrint = new GsonBuilder().setPrettyPrinting().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();

}