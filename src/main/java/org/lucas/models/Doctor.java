package org.lucas.models;
import org.lucas.models.User;
import org.lucas.util.ObjectBase;

public class Doctor extends User implements ObjectBase {
    private String specialisation;
    private String licenseNumber;

    public Doctor(String userID, String userName, String password, String email, String gender, String specialisation, String licenseNumber) {
        super(userID,  userName, password, email, gender);

    }
}
