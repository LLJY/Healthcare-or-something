package org.lucas.models;

import org.lucas.util.ObjectBase;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Prescription implements ObjectBase {
    private String prescriptionID;
    private List<Medicine> medicines;

    public Prescription(){
        this.prescriptionID = UUID.randomUUID().toString();
        this.medicines = new ArrayList<>();
    }

    public String getPrescriptionID(){return prescriptionID;}
    public void addMedicine(Medicine medicine){medicines.add(medicine);}
    public void removeMedicine(Medicine medicine){medicines.remove(medicine);}
    //returns a copy of medicine list to prevent external changes
    public List<Medicine> getMedicines(){return new ArrayList<>(medicines);}
    //function that select medicine based on string
    //calls this function pass string -> List check which string = medicine name -> return object
}
