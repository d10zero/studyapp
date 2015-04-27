package Model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Sam on 2/20/15.
 */
public class Group implements Serializable {
    private String className, instructor;
    private ArrayList<User> members;
    private ArrayList<String> chatboard;

    public Group(String className, String instructor){
        this.className = className;
        this.instructor = instructor;
    }

    public String getInstructor() {
        return instructor;
    }

    public String getClassName() {
        return className;
    }

    public ArrayList<String> getChatboard() {
        return chatboard;
    }

    public ArrayList<User> getMembers() {
        return members;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
