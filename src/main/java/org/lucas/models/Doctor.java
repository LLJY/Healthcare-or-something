package org.lucas.models;
import org.lucas.models.User;
import org.lucas.util.ObjectBase;

public class Doctor extends User implements ObjectBase {
    private String specialisation;
    private DoctorType type;
    private String licenseNumber;

    public Doctor(String id, String loginName, String name, String password, String email, String gender, String specialisation, DoctorType type, String licenseNumber) {
        super(id, loginName, name, password, email, gender);
        this.specialisation = specialisation;
        this.type = type;
        this.licenseNumber = licenseNumber;
    }

    /*public void displayDoctorInfo() {
            super.displayUserInfo();
            System.out.println("Specialisation: " + specialisation);
            System.out.println("Type: " + type);
            System.out.println("License Number: " + licenseNumber);
            System.out.println(this.toPrettyJsonString());
    }*/
    @Override
    public void displayUserInfo() {
        System.out.println(this.toPrettyJsonString());
    }

}