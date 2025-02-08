package org.lucas.models;

import org.lucas.util.ObjectBase;
import java.time.LocalDateTime;

public class Appointment implements ObjectBase {
    private Patient patient;
    private String reason;
    public LocalDateTime appointmentTime;
    private Doctor doctor;
    private AppointmentStatus appointmentStatus;
    private Session session;
    private String doctorNotes;
    // function ( session() -> String(doctornote)-> billing()
    /*
    * private void endAppointment(String doctornote){
    *       //session.end();
    *        this.doctornote= doctornote
    *          //billing.billingclass()
    * }
    * */

    public Appointment(Patient patient, String reason, LocalDateTime appointmentTime, AppointmentStatus appointmentStatus, String doctorNotes) {
        this.patient = patient;
        this.reason = reason;
        this.appointmentTime = appointmentTime;
        this.appointmentStatus = appointmentStatus;
        this.doctorNotes = doctorNotes;
    }

    public Patient getPatient() {
        return patient;
    }
    public void setPatient(Patient patient) {
        this.patient = patient;
    }
    public String getReason() {
        return reason;
    }
    public void setReason(String reason) {
        this.reason = reason;
    }
    public LocalDateTime getAppointmentTime() {
        return appointmentTime;
    }
    public void setAppointmentTime(LocalDateTime appointmentTime) {
        this.appointmentTime = appointmentTime;
    }
    public Session getSession() {return session;};
    public void setSession(Session session) {this.session = session;};
    public AppointmentStatus getAppointmentStatus() {return this.appointmentStatus;}
    public void setAppointmentStatus(AppointmentStatus appointmentStatus) {this.appointmentStatus= appointmentStatus;};
    public Doctor getDoctor(){return doctor;}
    public String getDoctorNotes(){return doctorNotes;}
    public void setDoctorNotes(String doctorNotes){this.doctorNotes= doctorNotes;}
    public void setDoctor(Doctor doctor){
        this.doctor = doctor;
    }


    public void Approved(Doctor doctor, Session session) {
        this.doctor = doctor;
        this.appointmentStatus = AppointmentStatus.ACCEPTED;
        this.session = session;

    }

    public void TRYING(){
        System.out.println("Patient Name: "+patient.getName());
        System.out.println("Appointment Reason: "+reason);
        System.out.println("Appointment time: "+appointmentTime);
        System.out.println("Appointment Status:"+ appointmentStatus);
        if(doctor!=null){
            System.out.println("Doctor Name: "+doctor.getName());
        }
    }

     public void print() {
         System.out.println(this.toPrettyJsonString());
     }

    @Override
    public String toString() {
        return "Appointment{" +
                "patient=" + patient +
                ", reason='" + reason + '\'' +
                ", appointmentTime=" + appointmentTime +
                ", doctor=" + doctor +
                ", appointmentStatus=" + appointmentStatus +
                ", session=" + session +
                ", doctorNotes='" + doctorNotes + '\'' +
                '}';
    }
}




// Patient -> Appointment -> waiting for the doctor to approve -> Assign doctor in the appointment
// Pass patient as an object
// Doctor as an Object
// Status : pending or approved
// approved ->appointment -> session date / time
// if there's no appointment wants a doctor, current time
// if time less than/eq the current time wants asap
// if more means appointment
// able to sort the queue by using date time

// approved -> appointment -> session date / time
// Zoom link -> Doc Notes -> Back to [appointment -> session date / time ]
// once the session is  created deleted objected
