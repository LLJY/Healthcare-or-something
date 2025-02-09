package org.lucas.models;

import org.lucas.util.ObjectBase;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Represents a patient in the healthcare system, extending the User class with medical-specific details.
 * This class includes fields for managing a patient's medical history, allergies, date of birth, consent status,
 * address, and next of kin information. It is used to maintain comprehensive patient records necessary for
 * medical treatments and administrative purposes.
 */
public class Patient extends User implements ObjectBase {
    private String medicalHistory;
    private String allergies;
    private LocalDateTime dateOfBirth;
    private PatientConsent patientConsent; // Use PatientConsent class
    private String address;
    private String nextOfKinName;


    /**
     * Constructs a new Patient with specified personal and medical details.
     * @param userID Unique identifier for the patient.
     * @param userName Patient's username for system access.
     * @param name Patient's full legal name.
     * @param password Patient's password for system access.
     * @param email Patient's email address.
     * @param gender Patient's gender.
     * @param medicalHistory Description of the patient's past medical conditions and treatments.
     * @param allergies Any known allergies that the patient has.
     * @param dateOfBirth The patient's date of birth.
     * @param address The residential address of the patient.
     * @param nextOfKinName The name of the patient's next of kin.
     */

    public Patient(String userID, String userName, String name, String password, String email, String gender, String medicalHistory, String allergies, LocalDateTime dateOfBirth, String address, String nextOfKinName) {
        super(userID, userName, name, password, email, gender);
        this.medicalHistory = medicalHistory;
        this.allergies = allergies;
        this.dateOfBirth = dateOfBirth;
        this.patientConsent = patientConsent;
        this.address = address;
        this.nextOfKinName = nextOfKinName;
        this.patientConsent = new PatientConsent(false, "");
    }

    // Getters and Setters
    /**Returns the patient's medical history.
     * @return the medicalHistory A string containing the patient's medical history.*/
    public String getMedicalHistory() {
        return medicalHistory;
    }

    /** Sets the patient's medical history.
     * @param medicalHistory The medical history to set for the patient.*/
    public void setMedicalHistory(String medicalHistory) {
        this.medicalHistory = medicalHistory;
    }

    /** Returns the patient's allergies.
     * @return the allergies A string containing the patient's allergies.*/
    public String getAllergies() {
        return allergies;
    }

    /** Sets the patient's allergies.
     * @param allergies The allergies to set for the patient.*/
    public void setAllergies(String allergies) {
        this.allergies = allergies;
    }

    /** Returns the patient's date of birth.
     * @return the dateOfBirth A LocalDateTime object representing the patient's date of birth.*/
    public LocalDateTime getDateOfBirth() {
        return dateOfBirth;
    }

    /** Sets the patient's date of birth.
     * @param dateOfBirth The date of birth to set for the patient.*/
    public void setDateOfBirth(LocalDateTime dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    /** Returns the patient's consent information.
     * @return the patientConsent A PatientConsent object representing the patient's consent.*/
    public PatientConsent getPatientConsent() {
        return patientConsent;
    }

    /** Sets the patient's consent information.
     * @param patientConsent The PatientConsent object to set for the patient.*/
    public void setPatientConsent(PatientConsent patientConsent) {
        this.patientConsent = patientConsent;
    }

    /** Returns the patient's address.
     * @return the address A string containing the patient's address.*/
    public String getAddress() {
        return address;
    }

    /** Sets the patient's address.
     * @param address The address to set for the patient.*/
    public void setAddress(String address) {
        this.address = address;
    }

    /** Returns the patient's next of kin name.
     * @return the nextOfKinName A string containing the patient's next of kin name.*/
    public String getNextOfKinName() {
        return nextOfKinName;
    }

    /** Sets the patient's next of kin name.
     * @param nextOfKinName The next of kin name to set for the patient.*/
    public void setNextOfKinName(String nextOfKinName) {
        this.nextOfKinName = nextOfKinName;
    }

    /** Displays the patient's user information in a pretty JSON format.
     * Demonstrates encapsulation by accessing and displaying patient data
     * through getter methods.*/
    public void displayUserInfo() {
        System.out.println(this.toPrettyJsonString());
    }
}