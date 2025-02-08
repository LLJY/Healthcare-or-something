package org.lucas.pages;

import org.lucas.models.Doctor;
import org.lucas.ui.framework.Color;
import org.lucas.ui.framework.UiBase;
import org.lucas.ui.framework.View;
import org.lucas.ui.framework.views.ListView;
import org.lucas.ui.framework.views.TextView;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;




public class LoginPage extends UiBase { // This is the class that represents the login page
    private ArrayList<Doctor> doctors;
    public LoginPage() {
        this.doctors = new ArrayList<Doctor>();
    }

    @Override
    public View OnCreateView() { // This is the method that is called when the view is created
        ListView lv = new ListView(this.canvas, Color.GREEN); // Create a new list view with the canvas and color
        return lv; // Return the list view
    }

    @Override
    public void OnViewCreated(View parentView) { // This is the method that is called when the view is created


        ListView lv = (ListView) parentView; // Cast the parent view to a list view
        lv.setTitleHeader("Welcome to Telemedicine Integration System "); // Set the title header of the list view
        lv.addItem(new TextView(this.canvas, "To access our system, please kindly login by pressing ", Color.GREEN)); // Create a new text view with the message

        lv.attachUserInput("Login ", x -> { // Attach the user input to the list view
            Scanner scanner = new Scanner(System.in); // Create a new scanner object
            System.out.println("Enter your username: "); // Print the message to the console
            String username = scanner.nextLine(); // Get the username from the user
            System.out.println("Enter your password: "); // Print the message to the console
            String password = scanner.nextLine(); // Get the password from the user



            // Check Username and Password
            if (username.equals("admin") && password.equals("admin")) {
                System.out.println("Login successful!");
                ToPage(new MainPage());
            } else { System.out.println("Invalid username or password!"); }

        });
        canvas.setRequireRedraw(true);
    }

//    private ArrayList<Doctor> loadDoctorData(String filename) {
//
//        ArrayList<Doctor> doctors = new ArrayList<>();
//        File file = new File(filename);
//
//        try(Scanner fileScanner = new Scanner(file)) {
//            while (fileScanner.hasNextLine()) {
//                String line = fileScanner.nextLine();
//                String[] data = line.split(",");
//                if (data.length >=2) {
//                    String username = data[1];
//                    String password = data[2];
//                    doctors.add(new Doctor(username, password));
//                }
//            }
//        }
//
//    }
}
