package org.lucas.controllers;
import com.google.gson.reflect.TypeToken;
import org.lucas.Globals;
import org.lucas.models.Doctor;
import org.lucas.models.Patient;
import org.lucas.models.User;
import org.lucas.models.enums.DoctorType;
import org.lucas.models.enums.UserType;
import org.lucas.util.Pair;
import org.lucas.util.Util;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller class that stores the logged in user. Can be either a {@link Doctor} or {@link Patient}.
 * As well as login logic and stores/loads the users to a JSON file for persistence.
 */
public class UserController {
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

    /**
     * Loads users (Patients and Doctors) from files specified by patientFileName and doctorFileName.
     * This method first clears the current list of users, then loads patients from the patientFileName
     * and subsequently loads doctors from the doctorFileName, adding them to the users list.
     * The files are expected to contain JSON representations of lists of Patient and Doctor objects, respectively.
     * If a file does not exist or an error occurs during reading or parsing, an error message is logged, and loading from that file is skipped.
     * Note: This method replaces the entire existing list of users.*/

    private void loadUsersFromFile(){
        users.clear();
        //first load the patients
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


    /**
     * Retrieves a list of patients from the loaded users.
     * If the users list is empty, it first attempts to load users from the file using {@link #loadUsersFromFile()}.
     * Then, it filters the users to include only instances of {@link Patient}, casts them to {@link Patient},
     * and collects them into a new list.
     *
     * @return A list of {@link Patient} objects. Returns an empty list if no patients are found or if an error occurs during loading.
     * @see #loadUsersFromFile()
     * @see Patient
     */
    public List<Patient> getPatients(){
        if(users.isEmpty()){
            loadUsersFromFile();
        }

        // get the users which are instanceof patient, then map and cast them all to Patients
        return users.stream().filter(usr->usr instanceof Patient).map(usr->(Patient)usr).toList();
    }
    // Same as getPatients
    public List<Doctor> getDoctors(){
        if(users.isEmpty()){
            loadUsersFromFile();
        }
        // get the users which are instanceof patient, then map and cast them all to Patients
        return users.stream().filter(usr->usr instanceof Doctor).map(usr->(Doctor)usr).toList();
    }


    /**
     * Saves a list of users to a JSON file.
     * This method serializes the given list of users to JSON format using a pretty-printing Gson instance
     * and writes the JSON string to the file specified by the fileName.
     * The type T must extend {@link User}.
     * If an error occurs during file writing, it logs an error message.
     * @param <T>      The type of User (e.g., Patient, Doctor). Must extend {@link User}.
     * @param users    The list of users to save.
     * @param fileName The name of the file to save the users to.
     * @see User
     * @throws IllegalArgumentException if the file name is null or empty.
     */
    private <T extends User> void saveUsersToFile(List<T> users, String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            String json = Globals.gsonPrettyPrint.toJson(users);
            writer.write(json);
            System.out.println("Users saved to " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Authenticates a user based on the provided username and password.
     * This method searches the loaded users for a user with a matching login name and password.
     * If the user is found and the credentials are valid, the method returns the {@link UserType}
     * (DOCTOR or PATIENT). It also sets the activeDoctor or activePatient based on the user type.
     * If the user is not found or the credentials are invalid, it returns {@link UserType#ERROR}.
     * If the users list is empty, it first attempts to load users from the file using {@link #loadUsersFromFile()}.
     * @param username The username to authenticate.
     * @param password The password to authenticate.
     * @return The {@link UserType} of the authenticated user (DOCTOR, PATIENT, or ERROR).
     * @see User
     * @see UserType
     * @see #loadUsersFromFile()*/

    public UserType authenticate(String username, String password){
        if(users.isEmpty()){
            loadUsersFromFile();
        }

        List<User> authenticated = users.stream().filter(usr->
            usr.getLoginName().equals(username) && usr.checkPassword(password)
        ).toList();
        //System.out.println(authenticated);

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

    public static boolean comparePasswordHash(Pair<byte[], byte[]> hashSalt, String password){
        return hashPassword(password, hashSalt.second).equals(hashSalt);
    }

    /**
     * Static method that hashes the password, and automatically generates the hash
     * Reference : <a href="https://www.baeldung.com/java-password-hashing">...</a>
     * @param password The plaintext password
     * @return a Pair object (first is password hash) (second is password salt)
     */
    public static Pair<byte[], byte[]> hashPassword(String password, byte[] salt){
        SecureRandom random = new SecureRandom();
        byte[] hash = null;
        random.nextBytes(salt);

        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
        SecretKeyFactory factory = null;
        try {
            factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            hash = factory.generateSecret(spec).getEncoded();
        }catch (Exception e){
            e.printStackTrace();
        }
        return new Pair<>(hash, salt);
    }

    public static byte[] generateRandomPasswordSalt(){
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }

    /**Gets the active user type.
     * @return the {@link UserType} of the currently active user, or null if no user is active.*/
    public static UserType getActiveUserType() {
        return activeUserType;
    }

    /** Gets the active doctor.
     * @return the active {@link Doctor}, or null if no doctor is active.*/
    public static Doctor getActiveDoctor() {
        return activeDoctor;
    }

    /**Gets the active patient.
     * @return the active {@link Patient}, or null if no patient is active.*/
    public static Patient getActivePatient() {
        return activePatient;
    }




    /// method used for debugging only.
    public Pair<List<Patient>, List<Doctor>> generateDummyUsers() {
        List<Patient> dummyPatients = new ArrayList<>();
        dummyPatients.add(new Patient(
                "P001", "lucaslee", "Lucas Lee", "password123", "lucas.lee@example.com", "Male",
                "No significant medical history.", "", LocalDateTime.now().minusYears(28).minusMonths(5),
                "Blk 123, Ang Mo Kio Ave 1, #01-1234, Singapore 560123", "Mary Tan", "8123 4567"
        ));
        dummyPatients.add(new Patient(
                "P002", "kaiseryee", "Kaiser Yee", "securepass", "kaiser.yee@example.com", "Female",
                "History of asthma.", "Penicillin", LocalDateTime.now().minusYears(32).minusMonths(4).minusDays(12),
                "Blk 456, Jurong West St 42, #02-5678, Singapore 640456", "Peter Yee", "9012 3456"
        ));
        dummyPatients.add(new Patient(
                "P003", "annaqi", "Annaqi Kun", "strongpass", "annaqi.kun@example.com", "Female",
                "Allergic to peanuts.", "Peanuts", LocalDateTime.now().minusYears(24).minusMonths(2).minusDays(15),
                "Blk 789, Tampines St 81, #03-9012, Singapore 520789", "Siti Kun", "8765 4321"
        ));

        List<Doctor> dummyDoctors = new ArrayList<>();
        dummyDoctors.add(new Doctor(
                "D001", "limtzekai", "Dr. Lim Tze Kai", "docpass", "limtzekai@clinic.com", "Male",
                "GP", DoctorType.GENERAL_PRACTICE, "L12345", "9876 5432"
        ));
        dummyDoctors.add(new Doctor(
                "D002", "kieransim", "Dr. Kieran Sim", "securedoc", "kieransim@clinic.com", "Male",
                "Cardiology", DoctorType.SPECIALIST, "L67890", "8345 6789"
        ));

        // Save dummy users to files
        saveUsersToFile(dummyPatients, patientFileName);
        saveUsersToFile(dummyDoctors, doctorFileName);

        return new Pair<>(dummyPatients, dummyDoctors);
    }
}
