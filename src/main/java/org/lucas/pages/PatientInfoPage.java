package org.lucas.pages;

import org.lucas.controllers.PatientInfoController;
import org.lucas.models.Patient;
import org.lucas.ui.framework.Color;
import org.lucas.ui.framework.UiBase;
import org.lucas.ui.framework.View;
import org.lucas.ui.framework.views.ListView;

import java.time.LocalDateTime;

public class PatientInfoPage extends UiBase {
    public static PatientInfoController controller = new PatientInfoController();
    @Override
    public View OnCreateView() {
        ListView lv = new ListView(this.canvas, Color.GREEN);
        lv.setTitleHeader("Patient Info");
        return lv;
    }

    @Override
    public void OnViewCreated(View parentView) {
        ListView lv = (ListView) parentView;

        lv.attachUserInput("Get Patient Info", str -> {
            Patient daryl = new Patient(
                    "000123",
                    "daryl",
                    "123123",
                    "daryllikedick@gmail.com",
                    "NIGER",
                    "small cock",
                    "Gay",
                    "gay",
                    LocalDateTime.of(2002, 1, 1, 10, 10, 10),
                    true,
                    "500123",
                    "your mother");
            Patient Tzekai = new Patient(
                    "000124",
                    "tzekai",
                    "123123",
                    "tzekaigay@gmail.com",
                    "NIGER",
                    "small cock",
                    "Gay",
                    "gay",
                    LocalDateTime.of(2002, 1, 1, 10, 10, 10),
                    true,
                    "500123",
                    "your mother");

            canvas.setRequireRedraw(true);
        });
    }
}