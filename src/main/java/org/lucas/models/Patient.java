package org.lucas.models;

import org.lucas.util.ObjectBase;

import java.time.LocalDateTime;
import java.util.Objects;

public class Patient extends User implements ObjectBase {
    private String medicalHistory;
    private String allergies;
    private LocalDateTime dateOfBirth;
    private boolean patientConsent;
    private String address;
    private String nextOfKinName;


    public Patient(String userID, String userName, String name, String password, String email, String gender, String medicalHistory, String allergies, LocalDateTime dateOfBirth, boolean patientConsent, String address, String nextOfKinName) {
        super(userID, userName, name, password, email, gender);
        this.medicalHistory = medicalHistory;
        this.allergies = allergies;
        this.dateOfBirth = dateOfBirth;
        this.patientConsent = patientConsent;
        this.address = address;
        this.nextOfKinName = nextOfKinName;
    }

//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }

    public String getMedicalHistory() {
        return medicalHistory;
    }

    public void setMedicalHistory(String medicalHistory) {
        this.medicalHistory = medicalHistory;
    }

    public String getAllergies() {
        return allergies;
    }

    public void setAllergies(String allergies) {
        this.allergies = allergies;
    }

    public LocalDateTime getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDateTime dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public boolean isPatientConsent() {
        return patientConsent;
    }

    public void setPatientConsent(boolean patientConsent) {
        this.patientConsent = patientConsent;
    }
//kk sorry im done
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNextOfKinName() {
        return nextOfKinName;
    }

    public void setNextOfKinName(String nextOfKinName) {
        this.nextOfKinName = nextOfKinName;
    }
    public void displayUserInfo() {
        System.out.println(this.toPrettyJsonString());
    }

}
