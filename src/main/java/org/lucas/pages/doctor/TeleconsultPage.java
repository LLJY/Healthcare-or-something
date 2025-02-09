package org.lucas.pages.doctor;

import org.lucas.controllers.MedicinesController;
import org.lucas.models.Appointment;
import org.lucas.models.MedicalCertificate;
import org.lucas.models.Medicine;
import org.lucas.models.Prescription;
import org.lucas.ui.framework.Color;
import org.lucas.ui.framework.TextStyle;
import org.lucas.ui.framework.UiBase;
import org.lucas.ui.framework.View;
import org.lucas.ui.framework.views.ListView;
import org.lucas.ui.framework.views.TextView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class TeleconsultPage extends UiBase {
    private String notes = "";
    private static Appointment appointment;
    private ListView listView;

    public static void setAppointment(Appointment appointment) {
        TeleconsultPage.appointment = appointment;
    }
// ViewAppointmentsPage -> static appointment -> ToPage Teleconsult.
    // Teleconsult execute the whole teleconsult,
    // 1. if zoomlink doesn't exist, create it
    // 2. display and manage doctor notes
//    3. display and manage patient information
    // 3a. display medicines information
    // 4. add medicines to prescription
    // 5. end consult
    // 6. (optional) add MC (shouldn't be too hard.)
    // 7. (end consult) -> go to billing page

    @Override
    public View OnCreateView() {
        listView = new ListView(
                this.canvas,
                Color.CYAN
        );
        listView.setTitleHeader("Teleconsult");
        return listView;
    }

    @Override
    public void OnViewCreated(View parentView) {
        ListView lv = (ListView) parentView;
        lv.attachUserInput("Set Doctor Notes", str -> {
            System.out.println("Enter Doctor Notes :");
            Scanner scanner = new Scanner(System.in);
            String newNotes = scanner.nextLine();
            appointment.setDoctorNotes(newNotes);
            refreshUi();
        });

        lv.attachUserInput("Add Prescription", str -> {
            // -1 sets the function to add mode
            setPrescribeMeds(-1);
            refreshUi();
        });

        lv.attachUserInput("Set Prescription", str -> {
            int selectedIndex = selectMedicineIndexPrompt();
            if(selectedIndex == -1){
                refreshUi();
                return;
            }
            setPrescribeMeds(selectedIndex);
            refreshUi();
        });

        lv.attachUserInput("Remove Prescription", str -> {
            int selectedIndex = selectMedicineIndexPrompt();
            if(selectedIndex == -1){
                refreshUi();
                return;
            }
            appointment.getBilling().getPrescription().removeMedicineAtIndex(selectedIndex);
            refreshUi();
        });

        lv.attachUserInput("Add Medical Certificate", str->{
            Scanner scanner = new Scanner(System.in);
            System.out.println("Medical Certificate No. Days * incl today: ");
            int days = scanner.nextInt();

            scanner = new Scanner(System.in);
            System.out.println("Medical Certificate Remarks: ");
            String remarks = scanner.nextLine();

            LocalDate today = LocalDate.now();
            LocalDate endDay = today.plusDays(days - 1);
            MedicalCertificate mc = new MedicalCertificate(today.atStartOfDay(), endDay.atTime(23, 59), remarks);
            appointment.setMedicalCertificate(mc);

            refreshUi();
        });
        lv.attachUserInput("Remove Medical Certificate", str->{
            if(appointment.getMc() == null){
                System.out.println("Medical Certificate not yet created. Returning...");
                refreshUi();
                return;
            }
            appointment.setMedicalCertificate(null);
        });

        lv.attachUserInput("Finish Consultation", str->{
            System.out.println("Finishing consultation...");
            System.out.println("Calculating Billing for patient...");
            appointment.finishAppointment(appointment.getDoctorNotes());
            canvas.previousPage();
            this.OnBackPressed();
        });
        refreshUi();
    }

    private int selectMedicineIndexPrompt(){
        List<Medicine> medicines = appointment.getBilling().getPrescription().getMedicines();
        if(medicines == null || medicines.isEmpty()){
            System.out.println("No medicines have been added yet, returning...");
            refreshUi();
            return -1;
        }
        int medicineSize = medicines.size();

        Scanner scanner = new Scanner(System.in);
        boolean validInput = false;
        int selectedIndex = -1;
        // common method to keep looping when selecting indexes from a list (until the input is valid)
        while (!validInput) {
            System.out.println("Select an index (0 - " +  + medicineSize + ")");
            selectedIndex = scanner.nextInt();
            if(selectedIndex >= 0 && selectedIndex < medicineSize) {
                validInput = true;
                break;
            }
        }
        return selectedIndex;
    }
    /**
     * Sets the medicine, either at the index or add a new medicine when index = -1
     * @param index -1 when adding, otherwise, specifying index will add a new medicine
     */
    private void setPrescribeMeds(int index){
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the drug name: ");
        // always use upper case
        String medicineName = scanner.nextLine().trim().toUpperCase();

        System.out.println("Enter the amount: ");
        int medicineAmount = scanner.nextInt();
        // somehow creating a new scanner prevents errors where it may just skip the scanner.
        scanner = new Scanner(System.in);
        Prescription prescription = appointment.getBilling().getPrescription();
        try {
            if (MedicinesController.findAvailableMedicineByName(medicineName) != null) {
                if (index == -1) {
                    prescription.addMedicine(medicineName, medicineAmount, "");
                } else {
                    prescription.setMedicineAtIndex(medicineName, medicineAmount, "", index);
                }
            } else {
                System.out.println("Enter the dosage/instructions: ");
                String dosage = scanner.nextLine();

                if (index == -1) {
                    prescription.addMedicine(medicineName, medicineAmount, dosage);
                } else {
                    prescription.setMedicineAtIndex(medicineName, medicineAmount, dosage, index);
                }
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        refreshUi();
    }

    private void refreshUi () {
        listView.clear();
        listView.addItem(new TextView(this.canvas, "Patient Name: " + appointment.getPatient().getName(), Color.GREEN, TextStyle.ITALIC));
        listView.addItem(new TextView(this.canvas, "Appointment Time: " + appointment.getAppointmentTime().format(DateTimeFormatter.ofPattern("HH:mm")), Color.GREEN, TextStyle.ITALIC));
        listView.addItem(new TextView(this.canvas, "Zoom link: " + appointment.getSession().getZoomLink(), Color.GREEN, TextStyle.ITALIC));
        listView.addItem(new TextView(this.canvas, "Reason: " + appointment.getReason() + "\n", Color.GREEN, TextStyle.ITALIC));
        String MCString = "Medical Certificate not yet given.";

        // separate the editable items with different colours
        if(appointment.getMc() != null){
            MCString = "Medical Certificate: from: " +
                    appointment.getMc().getStartDate().format(DateTimeFormatter.ofPattern("dd/MM")) +" to: " +
                    appointment.getMc().getEndDate().format(DateTimeFormatter.ofPattern("dd/MM"));
        }
        listView.addItem(new TextView(this.canvas,
                MCString,
                Color.CYAN,
                TextStyle.ITALIC));
        listView.addItem(new TextView(this.canvas, "Doctor Notes: " + appointment.getDoctorNotes(), Color.CYAN, TextStyle.ITALIC));

        // Add prescription details

        Prescription prescription = appointment.getBilling().getPrescription();
        if(prescription != null) {
            List<Medicine> medicines = prescription.getMedicines();
            if (medicines != null && !medicines.isEmpty()) {
                listView.addItem(new TextView(this.canvas, "Prescription:", Color.BLUE, TextStyle.BOLD));
                for (Medicine medicine : medicines) {
                    listView.addItem(new TextView(this.canvas, "  - " + medicine.getMedName() + " x " + medicine.getMedQuantity() + " | Dosage: " + medicine.getMedDosage(), Color.GREEN));
                }
            }
        }

        canvas.setRequireRedraw(true);
    }
}
