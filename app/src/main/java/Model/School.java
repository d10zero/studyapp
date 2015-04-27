package Model;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Sam on 4/17/15.
 */
public class School implements Serializable{

    private ArrayList<Post> posts = new ArrayList<Post>();
    private String[] classes = {"CS 354", "CS 577", "CS 240", "CS 352", "CS 302", "CS 407", "CS 252",
        "ENG 200", "ENG 404", "ENG 501", "ENG 134", "ENG 234", "BUS 555", "BUS 654","BUS 444"};
    private User user = new User("Sam", "Christensen", "sjchristens3", "sjchristens3@wisc.edu", "testpassword",
            this);

    public School(){

        for(int i = 0; i < 15; i++){
            posts.add(new Post(classes[i], "Instructor " + i, "College Library Room " + i,
                    13, false, user));
        }
    }

    public ArrayList<Post> getPosts() {
        return posts;
    }

    public void addPosts(Post post) {
         posts.add(post);
    }

}
