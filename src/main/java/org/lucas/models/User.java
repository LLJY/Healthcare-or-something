package org.lucas.models;

/*enum UserRole{
    PATIENT, DOCTOR
}*/

import org.lucas.util.ObjectBase;

import java.util.Objects;

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

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", loginName='" + loginName + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", gender='" + gender + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(loginName, user.loginName) && Objects.equals(name, user.name) && Objects.equals(password, user.password) && Objects.equals(email, user.email) && Objects.equals(gender, user.gender);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, loginName, name, password, email, gender);
    }
}

