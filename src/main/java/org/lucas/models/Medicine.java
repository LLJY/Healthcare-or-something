package org.lucas.models;

public class Medicine {   //fentanyl
    private String medName;
    private double medPrice;
    private int medQuantity;
    private int medDosage;
    public Medicine(String medName,double medPrice,int medQuantity,int medDosage){
        this.medName = medName;
        this.medPrice = medPrice;
        this.medQuantity = medQuantity;
        this.medDosage = medDosage;
    }

    public String getMedName() { return medName; }
    public double getMedPrice() { return medPrice;}
    public int getMedQuantity() { return medQuantity;}
    public int getMedDosage() {return medDosage;}

    public void setMedName(String medName) { this.medName = medName;}
    public void setMedPrice(float medPrice) { this.medPrice = medPrice;}
    public void setMedQuantity(int medQuantity) { this.medQuantity = medQuantity;}
    public void setMedDosage(int medDosage) {this.medDosage = medDosage;}
}
