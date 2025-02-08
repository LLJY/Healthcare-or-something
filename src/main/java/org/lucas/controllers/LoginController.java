package org.lucas.controllers;
import com.google.gson.reflect.TypeToken;
import org.lucas.Globals;
import org.lucas.models.Appointment;
import org.lucas.models.Doctor;
import org.lucas.models.Patient;
import org.lucas.models.User;
import org.lucas.models.enums.DoctorType;
import org.lucas.models.enums.UserType;
import org.lucas.util.Util;

import javax.print.Doc;
import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
public class LoginController {
    private static List<User> users = new ArrayList<>();
    private static final String doctorFileName = "doctor_data.txt";
    private static final String patientFileName = "patient_data.txt";
    /**
     * the active doctor that is currently logged in.
     */
    private static Doctor activeDoctor;
    /**
     * Placeholder patient, for the scope of this project, not required. Handle this case gracefully
     */
    private static Patient activePatient;
    /**
     * Specifies the user type to avoid excessive instanceof or null checks
     */
    private static UserType activeUserType;

    public void loadUsersFromFile(){
        users.clear();
        // first load the patients
        StringBuilder sb = new StringBuilder();
        try(BufferedReader br = Files.newBufferedReader(Paths.get(patientFileName))){
            String line;
            while((line = br.readLine()) != null){
                sb.append(line);
            }
            Type listType = new TypeToken<List<Patient>>() {}.getType();
            users.addAll(Util.fromJsonString(sb.toString(), listType));
        }catch (IOException e){
            e.printStackTrace();
        }

        // then load the doctors
        sb = new StringBuilder();
        try(BufferedReader br = Files.newBufferedReader(Paths.get(doctorFileName))){
            String line;
            while((line = br.readLine()) != null){
                sb.append(line);
            }
            Type listType = new TypeToken<List<Doctor>>() {}.getType();
            users.addAll(Util.fromJsonString(sb.toString(), listType));
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    /// method used for debugging only.
    public void generateDummyUsers() {
        List<Patient> dummyPatients = new ArrayList<>();
        dummyPatients.add(new Patient(
                "P001", "patient1", "John Doe", "password123", "john.doe@example.com", "Male",
                "No significant medical history.", "None", LocalDateTime.now().minusYears(30),
                true, "123 Main St", "Jane Doe"
        ));
        dummyPatients.add(new Patient(
                "P002", "patient2", "Alice Smith", "securepass", "alice.smith@example.com", "Female",
                "History of asthma.", "Penicillin", LocalDateTime.now().minusYears(25),
                false, "456 Oak Ave", "Bob Smith"
        ));
        //... add more dummy patients...

        List<Doctor> dummyDoctors = new ArrayList<>();
        dummyDoctors.add(new Doctor(
                "D001", "doctor1", "Dr. Jane Smith", "docpass", "jane.smith@clinic.com", "Female",
                "GP", DoctorType.GENERAL_PRACTICE, "L12345"
        ));
        dummyDoctors.add(new Doctor(
                "D002", "doctor2", "Dr. David Lee", "securedoc", "david.lee@clinic.com", "Male",
                "Pediatrics", DoctorType.SPECIALIST, "L67890"
        ));
        //... add more dummy doctors...

        // Save dummy users to files
        saveUsersToFile(dummyPatients, patientFileName);
        saveUsersToFile(dummyDoctors, doctorFileName);
    }

    ///  Method used for debugging only.
    private <T extends User> void saveUsersToFile(List<T> users, String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            String json = Globals.gsonPrettyPrint.toJson(users);
            writer.write(json);
            System.out.println("Users saved to " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public UserType authenticate(String username, String password){
        if(users.isEmpty()){
            loadUsersFromFile();
        }

        List<User> authenticated = users.stream().filter(usr->
            usr.getEmail().equals(username) && usr.checkPassword(password)
        ).toList();

        System.out.println(authenticated);

        if(authenticated.isEmpty()){
            return UserType.ERROR;
        }
        if(authenticated.getFirst() instanceof Doctor){
            activeDoctor = (Doctor)authenticated.getFirst();
            activeUserType = UserType.DOCTOR;
            return UserType.DOCTOR;
        }
        if(authenticated.getFirst() instanceof Patient){
            activePatient = (Patient)authenticated.getFirst();
            activeUserType = UserType.PATIENT;
            return UserType.PATIENT;
        }
        return UserType.ERROR;
    }

    public static UserType getActiveUserType() {
        return activeUserType;
    }

    public static Doctor getActiveDoctor() {
        return activeDoctor;
    }

    public static Patient getActivePatient() {
        return activePatient;
    }
}
