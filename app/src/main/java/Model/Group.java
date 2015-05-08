package Model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Sam on 2/20/15.
 */
public class Group implements Serializable {

    //Instance variables for each Group created
    private int groupId;
    private String className, instructor, creator;
    private ArrayList<String> memberEmails = new ArrayList<String>();
    private ArrayList<Post> chatboard = new ArrayList<Post>();

    //Group constructor
    public Group(String className, String instructor, String creator){
        this.className = className;
        this.instructor = instructor;
        this.creator = creator;
    }

    //Settors and Getteors for each of the variables in a group
    public String getInstructor() {
        return instructor;
    }

    public String getClassName() {
        return className;
    }

    public ArrayList<Post> getChatboard() {
        return chatboard;
    }

    public void addPost(Post post){
        chatboard.add(post);
    }

    public ArrayList<String> getMembers() {
        return memberEmails;
    }

    public void addMembers(String membername){
        memberEmails.add(membername);
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public int getGroupId() {
        return groupId;
    }

    public void addMembersGroup(ArrayList<String> members){
        this.memberEmails = members;
    }

    public void addChatGroup(ArrayList<String> chat){
        //this.chatboard = chat;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getCreator() {
        return creator;
    }
}
