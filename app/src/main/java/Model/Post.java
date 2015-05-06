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
    private User creator;
    private double time;
    private String date;


    //Post constructor
    public Post (String className, String instructor, String location,
                 double time, String date, String creator){
        this.className = className;
        this.instructor = instructor;
        this.location = location;
        this.time = time;
        //this.creator = creator;
        this.date = date;
    }


    //All of the setting and getting methods for different instance variables
    public String getCreatorName() {
        return creator.getFirstName() + " " + creator.getLastName();
    }

    public String getCreatorUsername(){
        return "sjchristens3";
    }

    public String getCreatorEmail(){
        return "sjchristens3@wisc.edu";
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

    public double getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }
}
