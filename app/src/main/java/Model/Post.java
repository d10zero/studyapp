package Model;

/**
 * Created by Sam on 4/17/15.
 */
public class Post {

    private String className, instructor,location;
    private User creator;
    private int time;
    private boolean undefined;

    public Post (String className, String instructor, String location, int time, boolean undefined, User creator){
        this.className = className;
        this.instructor = instructor;
        this.location = location;
        this.time = time;
        this.undefined = undefined;
        this.creator = creator;
    }

    public String getCreatorName() {
        return creator.getFirstName() + " " + creator.getLastName();
    }

    public String getCreatorUsername(){
        return creator.getUserName();
    }

    public String getCreatorEmail(){
        return creator.getEmail();
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

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public boolean getUndefined(){
        return undefined;
    }

}
