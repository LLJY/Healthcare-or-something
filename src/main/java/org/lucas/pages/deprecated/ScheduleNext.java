package org.lucas.pages.deprecated;

import org.lucas.Globals;
import org.lucas.ui.framework.Color;
import org.lucas.ui.framework.TextStyle;
import org.lucas.ui.framework.UiBase;
import org.lucas.ui.framework.View;
import org.lucas.ui.framework.views.ListView;
import org.lucas.ui.framework.views.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;

public class ScheduleNext extends UiBase {
    private int numberOfDays = 0;  // Use private field
    private String futureDate = ""; // Store the calculated future date
    private boolean followUpNeeded = false; // Default to false


    public void setFollowUpNeeded(boolean followUpNeeded) {
        this.followUpNeeded = followUpNeeded;
    }

    @Override
    public View OnCreateView() {
        ListView lv = new ListView(this.canvas, Color.CYAN);

        // Conditionally add instruction based on followUpNeeded
        if (followUpNeeded) {
            lv.addItem(new TextView(this.canvas, "Enter days until next visit:", Color.CYAN));
            lv.addItem(new TextView(this.canvas, "Ex. 30 days (1 month)", Color.CYAN, TextStyle.ITALIC));
        } else {
            lv.addItem(new TextView(this.canvas, "Follow-up not needed. Click Submit to end the session.", Color.CYAN));
        }
        lv.setTitleHeader("Next Scheduled Visit");
        return lv;
    }

    @Override
    public void OnViewCreated(View parentView) {
        ListView lv = (ListView) parentView;

        if (followUpNeeded) {
            lv.attachUserInput("Enter Days", str -> {
                try {
                    System.out.println("Enter the number of days: ");
                    Scanner scanner = new Scanner(System.in);
                    int days = scanner.nextInt();

                    setNumberOfDays(days);  // Use setter
                    calculateFutureDate(); // call function here
                    lv.clear(); //clear the list so we can see the new data

                    lv.addItem(new TextView(this.canvas, "This Session Date: " + getCurrentDate(), Color.WHITE));
                    lv.addItem(new TextView(this.canvas, "Next Session Date: " + getFutureDate(), Color.WHITE));   // Use getter
                    canvas.setRequireRedraw(true);
                } catch (java.util.InputMismatchException e) {
                    System.out.println("Invalid input. Please enter a valid number.");
                    lv.clear();
                    lv.addItem(new TextView(this.canvas, "Invalid Input", Color.RED));
                    canvas.setRequireRedraw(true);
                }

            });

            lv.attachUserInput("Submit", str -> {
                System.out.println("Session has ended. ");
                //ToPage();
            });
        }

        else {
            lv.attachUserInput("Submit", str -> {
                System.out.println("Session has ended. ");
                //ToPage();
            });
        }
    }

    //get next Session Date
    private void calculateFutureDate() {
        Calendar now = Calendar.getInstance();
        now.add(Calendar.DAY_OF_MONTH, getNumberOfDays());
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        setFutureDate(sdf.format(now.getTime())); //Use setter
    }

    //get current Session Date
    private String getCurrentDate() {
        Calendar now = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(now.getTime());
    }

    public int getNumberOfDays() {
        return numberOfDays;
    }

    public void setNumberOfDays(int numberOfDays) {
        this.numberOfDays = numberOfDays;
    }

    public String getFutureDate() {
        return futureDate;
    }

    public void setFutureDate(String futureDate) {
        this.futureDate = futureDate;
    }

    @Override
    public void OnBackPressed() {
        super.OnBackPressed();
        ToPage(Globals.teleconsultPage); // Or navigate back to wherever is appropriate
    }
}