package Model;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Sam on 4/17/15.
 */
public class School implements Serializable{

    //Dummmy data for testing purposes
    private static ArrayList<Post> posts = new ArrayList<Post>();
    private static ArrayList<Group> groups = new ArrayList<Group>();
    private String[] classes = {"CS 354", "CS 577", "CS 240", "CS 352", "CS 302", "CS 407", "CS 252",
        "ENG 200", "ENG 404", "ENG 501", "ENG 134", "ENG 234", "BUS 555", "BUS 654","BUS 444"};

    private String[] instructors = {"Mr. Andersen", "Mr. Roberts", "Ms. Christensen", "Mr. Drees",
            "Mr. Peterson", "Mr. Bridgewater", "Mrs. Andersen", "Mr. Andersen", "Mr. Roberts",
            "Ms. Christensen", "Mr. Drees", "Mr. Peterson", "Mr. Bridgewater", "Mrs. Andersen"};

    private String[] classroom = {"College Library Room ", "Steinbock Library Room ",
            "Memorial Library Room ", "Business Library Room ",
            "College Library Room ", "Steinbock Library Room ",
            "Memorial Library Room ", "Business Library Room ",
            "College Library Room ", "Steinbock Library Room ",
            "Memorial Library Room ", "Business Library Room ", "Memorial Library Room ",
            "Business Library Room "};
    private User user = new User("Sam", "Christensen", "sjchristens3@wisc.edu", "testpassword",
            this);


    //Create dummy content for testing the app
    public School(){
        for(int i = 0; i < 13; i++){
            posts.add(new Post(classes[i], instructors[i], classroom[i] + (100 + i),
                    13, 12, "", "SJCHRISTENS3"));
        }
        for(int i = 0; i < 13; i++){
            groups.add(new Group(classes[i], instructors[i], "sjchristens3"));
        }
    }

    public void addGroup(Group group){
        groups.add(group);
    }

    public ArrayList<Group> getGroups() {
        return groups;
    }

    public ArrayList<Post> getPosts() {
        return posts;
    }

    public void addPostsGroup(ArrayList<Post> posts){
        this.posts = posts;
    }

    public void addGroupGroup(ArrayList<Group> groups){
        this.groups = groups;
    }

    public void addPosts(Post post) {
         posts.add(post);
    }
}
