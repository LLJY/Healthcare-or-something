package org.lucas;

import org.lucas.controllers.*;
import org.lucas.ui.framework.ApplicationContext;
import org.lucas.ui.framework.Canvas;


public class Main {

    public static void main(String[] args) {
         // Globals.appointmentController.generateRandomAppointmentData(6);
        // initialise medicines
        MedicinesController.getAvailableMedicines();
        //create a new canvas
        var canvas = new Canvas();
        ApplicationContext applicationContext = new ApplicationContext(canvas);
        applicationContext.startApplication(Globals.loginPage);
    }
}