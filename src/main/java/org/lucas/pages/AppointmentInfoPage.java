package org.lucas.pages;

import org.lucas.Globals;
import org.lucas.models.Appointment;
import org.lucas.models.AppointmentStatus;
import org.lucas.models.Patient;
import org.lucas.ui.framework.Color;
import org.lucas.ui.framework.TextStyle;
import org.lucas.ui.framework.UiBase;
import org.lucas.ui.framework.View;
import org.lucas.ui.framework.views.ListView;
import org.lucas.ui.framework.views.TextView;

import java.time.LocalDateTime;
import java.util.Scanner;

public class AppointmentInfoPage extends UiBase {
    private String notes = "";

    private static Appointment appointment = new Appointment(
            new Patient(
                    "sdfsdf",
                    "AnnaqiKun",
                    "Annaqi",
                    "abcdef",
                    "annawi@sdfsdf",
                    "Male",
                    "noneee",
                    "aaaaaa",
                    LocalDateTime.of(2002, 10, 10, 10, 10),
                    false,
                    "Some address",
                    "somenok"),
            "aaaaaa",
            LocalDateTime.now(),
            AppointmentStatus.ACCEPTED,
            ""
    );


    private boolean followUpNeeded = false;

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public boolean isFollowUpNeeded() {
        return followUpNeeded;
    }

    public void setFollowUpNeeded(boolean followUpNeeded) {
        this.followUpNeeded = followUpNeeded;
    }

    private String followUpNeededToString() {
        return followUpNeeded ? "Yes" : "No";
    }

    @Override
    public View OnCreateView() {
        ListView lv = new ListView(
                this.canvas,
                Color.BLUE
        );
        lv.addItem(new TextView(this.canvas, "Please Fill up all the pages ", Color.BLUE, TextStyle.ITALIC));
        lv.addItem(new TextView(this.canvas, "1. Enter Documentation " , Color.BLUE, TextStyle.ITALIC));
        lv.addItem(new TextView(this.canvas, "2. Select follow-up for patient " , Color.BLUE, TextStyle.ITALIC));
        lv.addItem(new TextView(this.canvas, "3. Confirm only when ready to submit " + notes, Color.BLUE, TextStyle.ITALIC));
        lv.setTitleHeader("Doctor's Notes");
        return lv;
    }

    //

    @Override
    public void OnViewCreated(View parentView) {
        ListView lv = (ListView) parentView;

        lv.attachUserInput("Documentation", str -> {
            System.out.println("Enter Documentation :");
            Scanner scanner = new Scanner(System.in);
            this.setNotes(scanner.nextLine());
            lv.clear();
            lv.addItem(new TextView(this.canvas, "Note: " + notes, Color.WHITE));
            lv.addItem(new TextView(this.canvas, "Follow up needed:" + followUpNeededToString(), Color.WHITE));
            canvas.setRequireRedraw(true);

        });

        lv.attachUserInput("Follow-Up", str -> {
            System.out.println("Does the patient need a follow-up? (Y/N)");
            System.out.println("Enter Y or N :");
            Scanner scanner = new Scanner(System.in);
            String followUpInput = scanner.nextLine().trim().toUpperCase();
            this.setFollowUpNeeded(followUpInput.equals("Y"));
            lv.clear();
            lv.addItem(new TextView(this.canvas, "Note: " + notes, Color.WHITE));
            lv.addItem(new TextView(this.canvas, "Follow up needed:" + followUpNeededToString(), Color.WHITE));
            canvas.setRequireRedraw(true);

        });

        lv.attachUserInput("Confirm", str -> {
            System.out.println("Please press confirm to proceed.");
            // Call the set method of ScheduleNext page!
            Globals.scheduleNext.setFollowUpNeeded(isFollowUpNeeded());
            ToPage(Globals.scheduleNext);
        });
    }

    @Override
    public void OnBackPressed() {
        super.OnBackPressed();
        ToPage(Globals.zoomMeetingPage);

    }
}
