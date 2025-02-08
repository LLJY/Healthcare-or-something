package org.lucas.controllers;
import com.google.gson.reflect.TypeToken;
import org.lucas.Globals;
import org.lucas.models.*;
import org.lucas.util.Util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class AppointmentManager {
    private static List<Appointment> appointments = new ArrayList<>();
    private static final String fileName = "appointments.txt";

    public AppointmentManager(){
        //me thinks should add the read file logic here
        // no- never never put error prone code inside the constructor... - Lucas
    }

    public void generateRandomAppointmentData(int numberOfAppointments){
        Random random = new Random();

        for (int i = 0; i < numberOfAppointments; i++) {
            // Generate random data for the patient
            String patientID = "P" + (i + 1);
            String patientUserName = "patient" + (i + 1);
            String patientName = "Patient " + (i + 1);
            String patientPassword = "password";
            String patientEmail = "patient" + (i + 1) + "@example.com";
            String patientGender = random.nextBoolean() ? "Male" : "Female";
            String medicalHistory = "History " + (i + 1);
            String allergies = "Allergies " + (i + 1);
            LocalDateTime dateOfBirth = LocalDateTime.now().minusYears(random.nextInt(80) + 1);
            boolean patientConsent = random.nextBoolean();
            String address = "Address " + (i + 1);
            String nextOfKinName = "Next of Kin " + (i + 1);
            Patient patient = new Patient(patientID, patientUserName, patientName, patientPassword, patientEmail, patientGender, medicalHistory, allergies, dateOfBirth, patientConsent, address, nextOfKinName);

            // Generate random data for the doctor
            String doctorID = "D" + (random.nextInt(10) + 1);
            String doctorUserName = "doctor" + (random.nextInt(10) + 1);
            String doctorName = "Doctor " + (random.nextInt(10) + 1);
            String doctorPassword = "password";
            String doctorEmail = "doctor" + (random.nextInt(10) + 1) + "@example.com";
            String doctorGender = random.nextBoolean() ? "Male" : "Female";
            String specialisation = "Specialisation " + (random.nextInt(10) + 1);
            DoctorType doctorType = DoctorType.values()[random.nextInt(DoctorType.values().length)];
            String licenseNumber = "License " + (random.nextInt(1000) + 1);
            Doctor doctor = new Doctor(doctorID, doctorUserName, doctorName, doctorPassword, doctorEmail, doctorGender, specialisation, doctorType, licenseNumber);

            // Generate random data for the appointment
            String reason = "Reason " + (i + 1);
            LocalDateTime appointmentTime = LocalDateTime.now().plusDays(random.nextInt(365)).plusMinutes(random.nextInt(1440));

            AppointmentStatus appointmentStatus = AppointmentStatus.values()[random.nextInt(AppointmentStatus.values().length)];

            String doctorNotes = "Notes " + (i + 1);
            Appointment appointment = new Appointment(patient, reason, appointmentTime, appointmentStatus, doctorNotes);
            appointment.setDoctor(doctor);

            // Add the appointment to the list
            appointments.add(appointment);
        }
    }

    public void addAppointment(Appointment appointment){
        appointments.add(appointment);
        sortAppointment();
    }

    public void sortAppointment(){
        appointments.sort(Comparator.comparing(Appointment::getAppointmentTime));
    }

    public void displayAppointments(){
        for(Appointment appt: appointments){
            appt.TRYING();
        }
    }

    public void displayPendingAppointments(){
        for(Appointment appt: appointments){
            if(appt.getAppointmentStatus()==AppointmentStatus.PENDING) {
                appt.TRYING();
            };
        }
    }

    public void displayDoctorsAppointment(Doctor doctor){
        for(Appointment appt: appointments){
            if(appt.getDoctor()== doctor){
                appt.TRYING();
            }
        }
    }

    public void displayPatientsAppointment(Patient patient){
        for(Appointment appt: appointments){
            if(appt.getPatient()== patient){
                appt.TRYING();
            }
        }
    }

    public void setAppointments(List<Appointment> appointments){
        AppointmentManager.appointments = appointments;
    }

    public List<Appointment> getAppointments(){
        if(appointments.isEmpty()){
            loadAppointmentFromFile();
        }
        return appointments;
    }

    private void loadAppointmentFromFile(){
        appointments.clear();
        StringBuilder sb = new StringBuilder();
        try(BufferedReader br = Files.newBufferedReader(Paths.get(fileName))){
            String line;
            while((line = br.readLine()) != null){
                sb.append(line);
            }
            Type listType = new TypeToken<List<Appointment>>() {}.getType();
            appointments = Util.fromJsonString(sb.toString(), listType);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void saveAppointmentsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            String json = Globals.gsonPrettyPrint.toJson(appointments);
            writer.write(json);
            System.out.println("Appointments saved to " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
