package org.lucas.models;
// daryl Loves bbc 8=====D
import org.lucas.util.ObjectBase;

import javax.print.Doc;
import java.time.LocalDateTime;
import java.util.*;

public class Session implements ObjectBase {
    private String id;
    private LocalDateTime startTime;
    private Patient patient;
    private Doctor doctor;
    private String reason;
    private String remarks;
    private String connectivityIssues;
    private List<Medicine> medicines;

    public Session(LocalDateTime startTime, Patient patient, String reason, String remarks, String connectivityIssues, List<Medicine> medicines) {
        this.id = UUID.randomUUID().toString();
        this.startTime = startTime;
        this.patient = patient;
        this.reason = reason;
        this.remarks = remarks;
        this.connectivityIssues = connectivityIssues;
        this.medicines = medicines;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getConnectivityIssues() {
        return connectivityIssues;
    }

    public void setConnectivityIssues(String connectivityIssues) {
        this.connectivityIssues = connectivityIssues;
    }

    public List<Medicine> getMedicines() {
        return medicines;
    }

    public void setMedicines(List<Medicine> medicines) {
        this.medicines = medicines;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Session session = (Session) o;
        return Objects.equals(id, session.id) && Objects.equals(startTime, session.startTime) && Objects.equals(patient, session.patient) && Objects.equals(doctor, session.doctor) && Objects.equals(reason, session.reason) && Objects.equals(remarks, session.remarks) && Objects.equals(connectivityIssues, session.connectivityIssues) && Objects.equals(medicines, session.medicines);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, startTime, patient, doctor, reason, remarks, connectivityIssues, medicines);
    }
}
