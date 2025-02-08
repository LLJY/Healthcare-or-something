package org.lucas.models;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.lucas.Globals;
import org.lucas.util.ObjectBase;
import org.lucas.util.Util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
public class AppointmentManager {
    private static List<Appointment> appointments = new ArrayList<>();
    private static final String fileName = "appointments.txt";

    public AppointmentManager(){
        //me thinks should add the read file logic here
        // no- never never put error prone code inside the constructor... - Lucas
    }

    public void generateRandomAppointmentData(){

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

    public void setAppointments(List<Appointment> appointments){
        AppointmentManager.appointments = appointments;
    }

    public List<Appointment> getAppointments(){
        if(appointments.isEmpty()){
            loadAppointmentFromFile();
        }
        return appointments;
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
