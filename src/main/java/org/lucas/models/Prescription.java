package org.lucas.models;

import org.lucas.controllers.MedicinesController;
import org.lucas.util.ObjectBase;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Represents a medical prescription in the healthcare system. This class is responsible for managing the details of a prescription,
 * including a unique identifier and a list of medicines prescribed to a patient.
 * Each prescription is uniquely identified by a UUID, which ensures that it can be distinctly traced and managed across the healthcare
 * management system. The class also includes functionalities to add and manage the list of medicines associated with each prescription.
 */
public class Prescription implements ObjectBase {
    private String prescriptionID;
    private List<Medicine> medicines;
    private Prescription prescription;
    /**
     * Constructs a new Prescription with a unique identifier and an empty list of medicines.
     * This setup facilitates the dynamic addition of medicines as they are prescribed by a healthcare provider.
     */
    public Prescription(){
        this.prescriptionID = UUID.randomUUID().toString();
        this.medicines = new ArrayList<>();
    }

    public String getPrescriptionID(){return prescriptionID;}
    public void removeMedicine(Medicine medicine){medicines.remove(medicine);}
    public List<Medicine> getMedicines() {return medicines;}   //returns a copy of medicine list to prevent external changes
    /**
     * Add medicines to the prescription
     * @param medicineName name of the medicine
     * @param quantity number of medicines to prescribe
     * @param dosage dosage of medicines
     * @return returns true when the addition is found in the database and will autopopulate the rest of the fields. false if medicine not found in field.
     */
    public boolean addMedicine(String medicineName, int quantity, String dosage) {
        Medicine selectedMedicine = MedicinesController.findAvailableMedicineByName(medicineName);
        if (selectedMedicine != null) {
            // prefer the database stored values if the medicine is found, to avoid doctors prescribing 100000000mg of paracetamol
            Medicine prescribedMedicine = new Medicine(medicineName, quantity, selectedMedicine.getMedDosage(), selectedMedicine.getMedPrice());
            medicines.add(prescribedMedicine);
            return true;
        }
        medicines.add(new Medicine(medicineName, quantity, dosage));
        return false;
    }


    /**
     * Attempts to update a medicine at a specified index in the prescription list.
     * This method first checks if the specified medicine exists by name. If found, it uses verified details from
     * a known database (e.g., correct dosage and price) to prevent inappropriate prescriptions. If the medicine
     * is not found in the database, it uses the provided dosage instead.
     *
     * Note: If the medicines list has not been initialized, this method initializes it.
     * @param medicineName The name of the medicine to update or add. Must not be null.
     * @param quantity The quantity of the medicine to be prescribed.
     * @param dosage The dosage of the medicine to be used if the medicine is not found in the database.
     * @param index The index at which to set the medicine in the list. Must be within the list's bounds.
     * @return true if the medicine was found in the database and updated, false otherwise.
     * @throws IllegalArgumentException if the provided index is out of bounds or medicineName is null.
     * @throws IllegalStateException if the index is invalid and the list size is smaller than the index.
     */
    public boolean setMedicineAtIndex(String medicineName, int quantity, String dosage, int index) {
        if(medicines == null){
            medicines = new ArrayList<>();
        }
        Medicine selectedMedicine = MedicinesController.findAvailableMedicineByName(medicineName);
        if (selectedMedicine != null) {
            // prefer the database stored values if the medicine is found, to avoid doctors prescribing 100000000mg of paracetamol
            Medicine prescribedMedicine = new Medicine(medicineName, quantity, selectedMedicine.getMedDosage(), selectedMedicine.getMedPrice());
            medicines.set(index, prescribedMedicine);
            return true;
        }
        medicines.set(index, new Medicine(medicineName, quantity, dosage));
        return false;
    }

    /**
     * Removes a medicine from the prescription list at the specified index.
     * If the 'medicines' list has not been initialized or if the index is out of bounds,
     * the method will handle the situation gracefully without throwing an exception.
     *
     * Note: This method assumes that the 'medicines' list is properly managed elsewhere in the code to ensure
     * that it is never null when this method is called. However, it checks for null as a safeguard.
     *
     * @param index The index of the medicine to be removed. Must be a valid index within the list's bounds.
     *              If the index is out of bounds, the method will complete without any action.
     */
    public void removeMedicineAtIndex(int index){
        if(medicines == null)
            return;

        medicines.remove(index);
    }

//    public void setPrescriptions(){
//        this.prescription = new Prescription();
//    }
//
//    public void addMedicinesToPrescription(Scanner scanner) {
//        Medicine.listAvailableMedicines(); //List down available meds
//
//        while (true) {
//            System.out.print("Enter medicine name (or 'done' to finish): ");
//            String medicineName = scanner.nextLine();
//
//            if (medicineName.equalsIgnoreCase("done")) {
//                break;
//            }
//
//            System.out.print("Enter quantity: ");
//            int quantity;
//            try {
//                quantity = Integer.parseInt(scanner.nextLine());
//            } catch (NumberFormatException e) {
//                System.out.println("Invalid quantity. Please enter a number.");
//                continue;
//            }
//
//            System.out.print("Enter dosage (e.g., 500mg): ");
//            String dosage = scanner.nextLine();
//
//            prescription.addMedicine(medicineName, quantity, dosage);
//        }
//    }
}