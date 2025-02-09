package org.lucas.pages.doctor;

import org.lucas.controllers.UserController;
import org.lucas.models.Patient;
import org.lucas.ui.framework.Color;
import org.lucas.ui.framework.UiBase;
import org.lucas.ui.framework.View;
import org.lucas.ui.framework.views.ListView;
import org.lucas.ui.framework.views.TextView;

import java.util.List;
import java.util.Scanner;

/**
 * Dual purpose page, can ask user to select multiple patients, or display a single patient
 */
public class PatientInfoPage extends UiBase {
    public static UserController userController = new UserController();
    public static Patient patient;
    @Override
    public View OnCreateView() {
        ListView lv = new ListView(this.canvas, Color.GREEN);
        return lv;
    }

    @Override
    public void OnViewCreated(View parentView) {

        ListView lv = (ListView) parentView;

        if(patient != null){
            displayPatient(patient, lv);
            return;
        }
        lv.setTitleHeader("List of Patients");
        List<Patient> patients = userController.getPatients();
        int index = 1;

        // Show list of patient
        for (Patient patient : patients) {
            lv.addItem(new TextView(this.canvas, index + ". " + patient.getName(), Color.GREEN));
            index += 1;
        }
        
        // When selecting "Select Patient Index"
        lv.attachUserInput("Select Patient Index", str -> {
            Scanner scanner = new Scanner(System.in);
            boolean validInput = false;
            int selectedIndex = -1;
            while (!validInput) {
                System.out.println("Select an index (1 - " + patients.size() + ")");
                selectedIndex = scanner.nextInt();
                if (selectedIndex >= 0 && selectedIndex < patients.size()) {
                    validInput = true;
                    break;
                }
            }
            displayPatient(patient, lv);
        });
    }

    private void displayPatient(Patient patient, ListView lv) {
        lv.clear();
        lv.setTitleHeader("Patient Information");
        lv.addItem(new TextView(this.canvas, "Name: " + patient.getName(), Color.GREEN));
        lv.addItem(new TextView(this.canvas, "ID: " + patient.getId(), Color.GREEN));
        lv.addItem(new TextView(this.canvas, "Email: " + patient.getEmail(), Color.GREEN));
        lv.addItem(new TextView(this.canvas, "Gender: " + patient.getGender(), Color.GREEN));
        lv.addItem(new TextView(this.canvas, "Address: " + patient.getAddress(), Color.GREEN));
        lv.addItem(new TextView(this.canvas, "Date of Birth: " + patient.getDateOfBirth(), Color.GREEN));
        lv.addItem(new TextView(this.canvas, "Allergies: " + patient.getAllergies(), Color.GREEN));
        lv.addItem(new TextView(this.canvas, "Medical History: " + patient.getMedicalHistory(), Color.GREEN));
        lv.addItem(new TextView(this.canvas, "Next of Kin: " + patient.getNextOfKinName(), Color.GREEN));

        // Request UI redraw
        canvas.setRequireRedraw(true);
    }

    @Override
    public void OnBackPressed(){
        super.OnBackPressed();
        PatientInfoPage.patient = null;
    }

    public void refreshUi(){

    }
}