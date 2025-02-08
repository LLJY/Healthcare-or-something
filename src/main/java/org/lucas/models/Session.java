package org.lucas.models;

import org.lucas.util.ObjectBase;
import java.util.Date;

import javax.print.Doc;
import java.time.LocalDateTime;
import java.util.*;

public class Session implements ObjectBase {
    private String id;
    private LocalDateTime startTime;
    private String remarks;
    private String connectivityIssues;
    private String zoomLink;

    //ongoing session start ongoing finish time

    public Session(LocalDateTime startTime,String zoomLink) {

        this.id = UUID.randomUUID().toString();
        this.startTime = startTime;
        this.zoomLink = zoomLink;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getConnectivityIssues() {
        return connectivityIssues;
    }

    public void setConnectivityIssues(String connectivityIssues) {
        this.connectivityIssues = connectivityIssues;
    }

    public String getZoomLink() {return zoomLink;}
    public void setZoomLink(String zoomLink) {this.zoomLink = zoomLink;}


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Session session = (Session) o;
        return Objects.equals(id, session.id) && Objects.equals(startTime, session.startTime)  && Objects.equals(remarks, session.remarks) && Objects.equals(connectivityIssues, session.connectivityIssues);
    //&& Objects.equals(medicines, session.medicines)
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, startTime, remarks, connectivityIssues);
        //add medicines back later
    }
    public void print() {
        System.out.println(this.toPrettyJsonString());
    }
}
