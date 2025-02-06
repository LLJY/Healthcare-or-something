package org.lucas.models;
// daryl Loves bbc 8=====D
import org.lucas.util.ObjectBase;

import java.time.LocalDateTime;
import java.util.*;

public class Session implements ObjectBase {
    private String id;
    private LocalDateTime startTime;
    private String patientId;
    private String doctorId;
    private String reason;
    private String remarks;
    private String connectivityIssues;
    private List<Medicine> medicines;

    public Session(List<Medicine> medicines){
        this.medicines = medicines;
    }
}
