package org.lucas.models;

import org.lucas.util.ObjectBase;

import java.util.ArrayList;
import java.util.UUID;
import java.util.List;

public class Billing implements ObjectBase {
    private double billAmount;
    private int billingID;
    private boolean isPaid;
    private Prescription prescription;

    public Billing(Prescription prescription) {
        this.billAmount = 0.0;
        this.billingID = UUID.randomUUID().hashCode();
        this.isPaid = false;
        this.prescription = prescription;
        calculateBillAmount();
    }

    public int getBillingID() {
        return billingID;
    }

    public boolean isPaid() {
        return isPaid;
    }

    //set payment status
    public void setPaid(boolean isPaid) {
        this.isPaid = isPaid;
    }

    /**
     * loops through every medicine in list of medicines
     * from prescription class
     * to calculate total medicinal bill
     */
    private void calculateBillAmount() {
        billAmount = 0.0;
        for (Medicine medicine : prescription.getMedicines()) {
            billAmount += medicine.getMedPrice() * medicine.getMedQuantity();
        }
    }

    public String generateBill() {
        StringBuilder billDetails = new StringBuilder();
        billDetails.append("Bill ID: ").append(billingID).append("\n");
        billDetails.append("----------------------------------------\n");
        billDetails.append("Medicines:\n");
        for (Medicine medicine : prescription.getMedicines()) {
            billDetails.append("  - ").append(medicine.getMedName())
                    .append(" (Qty: ").append(medicine.getMedQuantity())
                    .append(", Price: $").append(String.format("%.2f", medicine.getMedPrice()))
                    .append(", Dosage: ").append(medicine.getMedDosage()).append("mg")
                    .append(") - Subtotal: $").append(String.format("%.2f", medicine.getMedPrice() * medicine.getMedQuantity()))
                    .append("\n");
        }
        billDetails.append("----------------------------------------\n");
        billDetails.append("Total Amount: $").append(String.format("%.2f", billAmount)).append("\n");
        billDetails.append("Payment Status: ").append(isPaid ? "Paid" : "Unpaid").append("\n");
        return billDetails.toString();
    }
    // Method to get bill details (for display or printing)
    @Override
    public String toString() {
        return generateBill(); // Call the helper method
    }
}
