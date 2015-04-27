package Model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Sam on 2/20/15.
 */
public class User implements Serializable {
    private String firstName, lastName, userName;
    private String email, password;
    private ArrayList<Group> groups;
    private ArrayList<Post> posts;
    private School school;

    public User(String firstName, String lastName, String userName, String email, String password,
                   School school){
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.school = school;
        initialize();
    }

    public void addGroup(Group newGroup){
        groups.add(newGroup);
    }

    public ArrayList<Post> getPosts() {
        return posts;
    }

    public void addPost(Post post){
        posts.add(post);
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

    private void initialize(){

    }

}
