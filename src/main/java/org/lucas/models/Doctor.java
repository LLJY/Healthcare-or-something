package org.lucas.models;
import org.lucas.models.User;
import org.lucas.util.ObjectBase;

public class Doctor extends User implements ObjectBase {
    private String specialisation;
    private DoctorType type;
    private String licenseNumber;

    public Doctor(String id, String loginName, String password, String email, String gender, String specialisation, DoctorType type, String licenseNumber) {
        super(id, loginName, password, email, gender);
        this.specialisation = specialisation;
        this.type = type;
        this.licenseNumber = licenseNumber;
    }
}
