package org.lucas;

import org.lucas.models.*;
import org.lucas.controllers.*;
import org.lucas.models.Doctor;
import org.lucas.models.Patient;
import org.lucas.ui.framework.ApplicationContext;
import org.lucas.ui.framework.Canvas;

import java.time.LocalDateTime;
import java.util.Scanner;


public class Main {
    private static ApplicationContext applicationContext;

    public static void main(String[] args) {
//        Globals.appointmentManager.generateRandomAppointmentData(6);
        // initialise medicines
        MedicinesController.getAvailableMedicines();
        //create a new canvas
        var canvas = new Canvas();
        applicationContext = new ApplicationContext(canvas);
        applicationContext.startApplication(Globals.loginPage);
    }
}