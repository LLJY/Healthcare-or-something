package org.lucas.models;

/*enum UserRole{
    PATIENT, DOCTOR
}*/

public abstract class User {
    private String userID;
    private String userName;
    private String password;
    private String email;
    private String gender;

    public User (String userID, String userName, String password, String email, String gender) {
        this.userID = userID;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.gender = gender;
    }

    public String getUserID() { return userID; }
    public String getUserName() { return userName; }
    public String getPassword() { return password; }
    public String getEmail() { return email; }
    public String getGender() { return gender; }

    public void displayUserInfo(){
        System.out.println("UserID: " + userID);
        System.out.println("Username: " + userName);
        System.out.println("Email: " + email);
    }
}

