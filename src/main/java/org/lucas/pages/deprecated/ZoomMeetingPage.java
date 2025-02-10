package org.lucas.pages.deprecated;
import org.lucas.util.ZoomCreator;
import org.lucas.util.ZoomOAuth;
import org.lucas.ui.framework.Color;
import org.lucas.ui.framework.UiBase;
import org.lucas.ui.framework.View;
import org.lucas.ui.framework.views.ListView;
import org.lucas.ui.framework.views.TextView;

import java.io.IOException;

public class ZoomMeetingPage extends UiBase {


    @Override
    public View OnCreateView() {
        ListView lv = new ListView(
                this.canvas,
                Color.GREEN
        );
        lv.setTitleHeader("Zoom Meeting Generator");
        lv.addItem(new TextView(this.canvas, "Do you want to create a Zoom Meeting with your patient? ", Color.GREEN));
        return lv;
    }

    @Override
    public void OnViewCreated(View parentView) {
        ListView lv = (ListView) parentView;

        lv.attachUserInput("Create Zoom Meeting", str->{
            try {
                // Get the OAuth access token
                String accessToken = ZoomOAuth.getAccessToken();

                // Call the method to create a Zoom meeting
                String joinUrl = ZoomCreator.createZoomMeeting(
                        accessToken,
                        "Zoom Meeting",
                        30, // Duration in minutes
                        "UTC" // Timezone
                );
                System.out.println("Your teleconsultation is ready! Click the link below to join the session with your patient. ");
                // Output the join URL
                System.out.println("Zoom Meeting URL: " + joinUrl);
                // Kaiser Page
//                ToPage(Globals.teleconsultPage);
            } catch (IOException e) {
                e.printStackTrace();
            }
            canvas.setRequireRedraw(true);
        });
    }
}
