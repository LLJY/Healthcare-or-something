package org.lucas.models;

import java.util.ArrayList;
import java.util.List;

public class Medicine {   //fentanyl
    private String medName;
    private double medPrice;
    private int medQuantity;
    private String medDosage;
    public Medicine(String medName,double medPrice,int medQuantity,String medDosage){
        this.medName = medName;
        this.medPrice = medPrice;
        this.medQuantity = medQuantity;
        this.medDosage = medDosage;
    }

    public String getMedName() {return medName;}

    public double getMedPrice() {return medPrice;}

    public int getMedQuantity() {return medQuantity;}

    public String getMedDosage() {return medDosage;}

    public void setMedName(String medName) {this.medName = medName;}

    public void setMedPrice(float medPrice) {this.medPrice = medPrice;}

    public void setMedQuantity(int medQuantity) {this.medQuantity = medQuantity;}

    public void setMedDosage(String medDosage) {this.medDosage = medDosage;}

//    private static final List<Medicine> availableMedicines = new ArrayList<>();
//
//    static{
//        availableMedicines.add(new Medicine("fentanyl", 69, 10, "3/ hour"));
//    }

    public static final Medicine Fentanyl = new Medicine("Fentanyl", 2.50, 100, "500");
    public static final Medicine Meth = new Medicine("Meth", 5.00, 50, "200");
    public static final Medicine Cocaine = new Medicine("Cocaine", 50000.0, 50, "200");
    public static final Medicine CrystalMethamphetamine = new Medicine("Crystal Methamphetamine", 100000.0, 50, "200");

}

