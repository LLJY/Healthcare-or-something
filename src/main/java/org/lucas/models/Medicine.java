package org.lucas.models;

/**
 * Represents a type of medicine, including its name, price, and methods for managing available medicines.
 * This class supports creating medicine instances, finding medicines by name,
 * and managing a list of available medicines through file I/O.  It uses JSON
 * serialization for persistence.
 * <p>
 * The class maintains a static list of available medicines, which is loaded from and saved to
 * a file. This allows the application to retain medicine information across sessions.
*/
public class Medicine {
    private String medName;
    private double medPrice = 0.0;
    private int medQuantity;
    private String medDosage;

    /**
     * Constructor overload that does not specify price, this is used for unknown/new medicines.
     * There are impossibly many medicines to track, therefore this is important.
     * @param medicineName The name of the medicine
     * @param quantity The amount to prescribe
     * @param dosage The dosage instructions
     */
    public Medicine(String medicineName, int quantity, String dosage) {
        this.medName = medicineName;
        this.medQuantity = quantity;
        this.medDosage = dosage;
    }

    /**
     * Constructor overload that specifies the medicine price (for known medicines)
     * @param medicineName The name of the medicine
     * @param quantity The amount to prescribe
     * @param dosage The dosage instructions
     * @param medPrice The price of the medicine (optional)
     */
    public Medicine(String medicineName, int quantity, String dosage, double medPrice) {
        this.medPrice = medPrice;
        this.medName = medicineName;
        this.medQuantity = quantity;
        this.medDosage = dosage;
    }

    public String getMedName() {return medName;}
    public double getMedPrice() {return medPrice;}
    public int getMedQuantity() {return medQuantity;}
    public String getMedDosage() {return medDosage;}
    public void setMedName(String medName) {this.medName = medName;}
    public void setMedPrice(float medPrice) {this.medPrice = medPrice;}
    public void setMedQuantity(int medQuantity) {this.medQuantity = medQuantity;}
    public void setMedDosage(String medDosage) {this.medDosage = medDosage;}

}

