package org.lucas.models;

/*enum UserRole{
    PATIENT, DOCTOR
}*/

import org.lucas.util.ObjectBase;

public abstract class User implements ObjectBase {
    private String id;
    private String loginName;
    private String name;
    private String password;
    private String email;
    private String gender;

    public User(String id, String loginName, String name, String password, String email, String gender) {
        this.id = id;
        this.loginName = loginName;
        this.name = name;
        this.password = password;
        this.email = email;
        this.gender = gender;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLoginName() {
        return loginName;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {this.name = name; }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public boolean checkPassword(String password) {
        // todo research password hashing
        return this.password.equals(password);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }


    /**
     * Prints user info as a pretty JSON string
     */
    public void displayUserInfo(){
        System.out.println(this.toPrettyJsonString());
    }
}

