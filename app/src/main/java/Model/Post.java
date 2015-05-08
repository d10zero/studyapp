package Model;

import com.samchristensen.studyapp.HomePageActivity;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Sam on 4/17/15.
 */
public class Post implements Serializable{

    //Instance variables for each Post that is created
    private String className, instructor,location;
    private String creator;
    private double starttime, endtime;
    private String date;


    //Post constructor
    public Post (String className, String instructor, String location,
                 double starttime, double endtime, String date, String creator){
        this.className = className;
        this.instructor = instructor;
        this.location = location;
        this.starttime = starttime;
        this.endtime = endtime;
        this.creator = creator;
        this.date = date;
    }


    //All of the setting and getting methods for different instance variables
    public String getCreatorUsername(){
        return creator;
    }

    public String getCreatorEmail(){
        return creator + "@wisc.edu";
    }

    public String getClassName() {
        return className;
    }

    public String getInstructor() {
        return instructor;
    }

    public String getLocation() {
        return location;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getStartTime() {
        return starttime;
    }

    public double getEndTime() {
        return endtime;
    }

    public void setStartTime(int starttime) {
        this.starttime = starttime;
    }

    public void setEndTime(int endtime) {
        this.endtime = endtime;
    }

    public String getDate() {
        return date;
    }
}
