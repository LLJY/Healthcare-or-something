package org.lucas.controllers;

import org.lucas.models.PatientInfo;

import java.util.ArrayList;
import java.util.List;

public class PatientInfoController {
    private List<PatientInfo> patientInfos;

    public List<PatientInfo> getPatientInfos(){
        if(patientInfos == null){
            // get patient info
            patientInfos = new ArrayList<PatientInfo>();
            patientInfos.add(
                    new PatientInfo("Ligma", "Kieran Sim", "kieranlikesgaymen@gmail.com")
            );
            patientInfos.add(
                    new PatientInfo("Balls", "Annaqi", "annaqiisasmellymalay@gmail.com")
            );
        }
        return patientInfos;
    }
}