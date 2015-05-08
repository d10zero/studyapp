package Model;

import com.orm.SugarRecord;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Sam on 2/20/15.
 */
public class User extends SugarRecord<User> implements Serializable  {

    //Instance variables for each User created
    private String firstName, lastName, userName, email, password;
    private ArrayList<Group> groups = new ArrayList<Group>();
    private School school;

    //User constructor
    public User(String firstName, String lastName, String email, String password,
                   School school){
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = email.substring(0, email.length());
        this.email = email;
        this.password = password;
        this.school = school;
    }


    //Settor and Gettors of each of the instance variables for the user
    public void addGroup(Group newGroup){
        groups.add(newGroup);
    }

    public ArrayList<Group> getGroups() {
        return groups;
    }

    public String getFirstName(){
        return firstName;
    }

    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

    public String getLastName(){
        return lastName;
    }

    public void setLastName(String lastName){
        this.lastName = lastName;
    }

    public String getUserName(){
        return userName;
    }

    public String getEmail(){
        return email;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String newPassword){
        password = newPassword;
    }

    public void setSchool(School school) {
        this.school = school;
    }
}
