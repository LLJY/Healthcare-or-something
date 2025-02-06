package org.lucas.models;
import org.lucas.util.ObjectBase;

public class Patient extends User implements ObjectBase {
    //private User user;
    private String name;
    private String medicalHistory;
    private String allergies;
    private String dateOfBirth;
    private boolean patientConsent;
    private String address;
    private String nextofKin;
    /*
    public Patient(.....){
    super(UserID, username, password){
        }

     */
    public Patient(String userID, String username, String password,String email,String gender, String name, String medicalHistory,String allergies,String dateOfBirth, boolean patientConsent, String address, String nextofKin) {
        super(userID, username, password, email, gender);
        this.name = this.name;
        this.medicalHistory = medicalHistory;
        this.allergies = allergies;
        this.dateOfBirth = dateOfBirth;
        this.patientConsent = patientConsent;
        this.address = address;
        this.nextofKin = nextofKin;
    }

    public void displayPatientInfo(){
        System.out.println(this.toPrettyJsonString());
    }
}
