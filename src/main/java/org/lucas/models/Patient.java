package org.lucas.models;

import org.lucas.util.ObjectBase;

import java.util.Objects;

public class Patient extends User implements ObjectBase {
    private String name;
    private String medicalHistory;
    private String allergies;
    private String dateOfBirth;
    private boolean patientConsent;
    private String address;
    private String nextOfKinName;


    public Patient(String userID, String userName, String password, String email, String gender, String name, String medicalHistory, String allergies, String dateOfBirth, boolean patientConsent, String address, String nextOfKinName) {
        super(userID, userName, password, email, gender);
        this.name = name;
        this.medicalHistory = medicalHistory;
        this.allergies = allergies;
        this.dateOfBirth = dateOfBirth;
        this.patientConsent = patientConsent;
        this.address = address;
        this.nextOfKinName = nextOfKinName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public boolean isPatientConsent() {
        return patientConsent;
    }

    public void setPatientConsent(boolean patientConsent) {
        this.patientConsent = patientConsent;
    }

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

}
