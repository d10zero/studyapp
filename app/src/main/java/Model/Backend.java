package Model;

/**
 * Created by Sam on 4/29/15.
 */

import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.callumtaylor.asynchttp.AsyncHttpClient;
import net.callumtaylor.asynchttp.response.JsonResponseHandler;
import org.apache.http.Header;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Backend class used to connect with the server to get and post different JSON data
 * that is useful within the app
 *
 */
public class Backend {

    //Server urls and TAG for Log status
    private static final String TAG = "ConnectionManager";
    private static final String TAG2 = "JSONSetupError";
    private static final String SERVER_URL = "http://ec2-54-149-122-189.us-west-2.compute.amazonaws.com:8000/";

    //Callback interface for calling objects on Backend objects to
    //receive responses asynchronously
    public interface BackendCallback {
        void onRequestCompleted(Object result);
        void onRequestFailed(String message);
    }

    public static void login(final BackendCallback callback, final String username, final String password){

        //Set url for AsyncHttp request
        AsyncHttpClient client = new AsyncHttpClient(SERVER_URL);
        StringEntity jsonParams = null;

        //Set up HTTP Request headers to accept JSON response and Send JSON
        List<Header> headers = new ArrayList<Header>();
        headers.add(new BasicHeader("Accept", "application/json"));
        headers.add(new BasicHeader("Content-Type", "application/json"));

        try {
            JSONObject theuser = new JSONObject();
            theuser.put("username", username);
            theuser.put("password", password);
            jsonParams = new StringEntity(theuser.toString());
        }
        catch(Exception e){
            Log.d(TAG2, "error setting up new user");
        }
        //attempt to make connection and get JSON response
        client.post("users/login", jsonParams, headers, new JsonResponseHandler() {
            //if the connection/response succeeds
            @Override
            public void onSuccess() {
                JsonObject result = getContent().getAsJsonObject();
                String success = result.get("login").getAsString();

                if(success.equals("success")) {
                    //log the successful connection
                    Log.d(TAG, "Successfully logged in as " + username);
                    callback.onRequestCompleted(getContent());
                }
                else{
                    Log.d(TAG, "Failed to login as " + username);
                    callback.onRequestFailed("Connection Failed");
                }
            }

            //if the connection/response fails
            @Override
            public void onFailure() {
                Log.d(TAG, "Failed to login as " + username);
                callback.onRequestFailed("Connection Failed");
            }
        });

    }

    public static void createNewPost(final BackendCallback callback, Post post){

        //Set url for AsyncHttp request
        AsyncHttpClient client = new AsyncHttpClient(SERVER_URL);
        StringEntity jsonParams = null;

        //Set up HTTP Request headers to accept JSON response and Send JSON
        List<Header> headers = new ArrayList<Header>();
        headers.add(new BasicHeader("Accept", "application/json"));
        headers.add(new BasicHeader("Content-Type", "application/json"));

        try {
            JSONObject thepost = new JSONObject();
            thepost.put("classname", post.getClassName());
            thepost.put("instructor", post.getInstructor());
            thepost.put("location", post.getInstructor());
            thepost.put("creator", post.getCreatorUsername());
            thepost.put("date", post.getDate());
            thepost.put("starttime", post.getStartTime());
            thepost.put("endtime", post.getEndTime());
            jsonParams = new StringEntity(thepost.toString());
        }
        catch(Exception e){
            Log.d(TAG2, "error setting up new post");
        }

        //attempt to make connection and get JSON response
        client.post("posts", jsonParams, headers, new JsonResponseHandler() {
            //if the connection/response succeeds
            @Override
            public void onSuccess() {
                //log the successful connection
                Log.d(TAG, "Successfully created new post");
                callback.onRequestCompleted(getContent());
            }

            //if the connection/response fails
            @Override
            public void onFailure() {
                Log.d(TAG, "Failed trying to create a new group");
                callback.onRequestFailed("Connection Failed");
            }
        });

    }

    public static void createNewGroupPost(final BackendCallback callback, final Post post, final int groupid){

        //Set url for AsyncHttp request
        AsyncHttpClient client = new AsyncHttpClient(SERVER_URL);
        StringEntity jsonParams = null;

        //Set up HTTP Request headers to accept JSON response and Send JSON
        List<Header> headers = new ArrayList<Header>();
        headers.add(new BasicHeader("Accept", "application/json"));
        headers.add(new BasicHeader("Content-Type", "application/json"));

        try {
            JSONObject thepost = new JSONObject();
            thepost.put("classname", post.getClassName());
            thepost.put("instructor", post.getInstructor());
            thepost.put("location", post.getInstructor());
            thepost.put("creator", post.getCreatorUsername());
            thepost.put("date", post.getDate());
            thepost.put("starttime", post.getStartTime());
            thepost.put("endtime", post.getEndTime());
            thepost.put("group_id", groupid);
            jsonParams = new StringEntity(thepost.toString());
        }
        catch(Exception e){
            Log.d(TAG2, "error setting up new post");
        }

        //attempt to make connection and get JSON response
        client.post("posts", jsonParams, headers, new JsonResponseHandler() {
            //if the connection/response succeeds
            @Override
            public void onSuccess() {
                //log the successful connection
                Log.d(TAG, "Successfully created new post");
                callback.onRequestCompleted(getContent());
            }

            //if the connection/response fails
            @Override
            public void onFailure() {
                Log.d(TAG, "Failed trying to create a new group");
                callback.onRequestFailed("Connection Failed");
            }
        });

    }

    public static void addNewGroupToUser(final BackendCallback callback, final int groupid,
                                         final String username){

        //Set url for AsyncHttp request
        AsyncHttpClient client = new AsyncHttpClient(SERVER_URL);
        StringEntity jsonParams = null;

        //Set up HTTP Request headers to accept JSON response and Send JSON
        List<Header> headers = new ArrayList<Header>();
        headers.add(new BasicHeader("Accept", "application/json"));
        headers.add(new BasicHeader("Content-Type", "application/json"));

        try {
            JSONObject group_id = new JSONObject();
            group_id.put("group_id", groupid);
            group_id.put("username", username);
            jsonParams = new StringEntity(group_id.toString());
        }
        catch(Exception e){
            Log.d(TAG2, "error setting up new group");
        }

        //attempt to make connection and get JSON response
        client.post("users/groups", jsonParams, headers, new JsonResponseHandler() {
            //if the connection/response succeeds
            @Override
            public void onSuccess() {
                JsonObject object = getContent().getAsJsonObject();
                //log the successful connection

                callback.onRequestCompleted(object);
            }

            //if the connection/response fails
            @Override
            public void onFailure() {
                Log.d(TAG, "Failed trying to create a new group");
                callback.onRequestFailed("Connection Failed");
            }
        });

    }

    public static void createNewGroup(final BackendCallback callback, final Group group){

        //Set url for AsyncHttp request
        AsyncHttpClient client = new AsyncHttpClient(SERVER_URL);
        StringEntity jsonParams = null;

        //Set up HTTP Request headers to accept JSON response and Send JSON
        List<Header> headers = new ArrayList<Header>();
        headers.add(new BasicHeader("Accept", "application/json"));
        headers.add(new BasicHeader("Content-Type", "application/json"));

        try {
            JSONObject thegroup = new JSONObject();
            thegroup.put("classname", group.getClassName());
            thegroup.put("instructor", group.getInstructor());
            thegroup.put("creator", group.getCreator());
            jsonParams = new StringEntity(thegroup.toString());
        }
        catch(Exception e){
            Log.d(TAG2, "error setting up new group");
        }

        //attempt to make connection and get JSON response
        client.post("groups", jsonParams, headers, new JsonResponseHandler() {
            //if the connection/response succeeds
            @Override
            public void onSuccess() {
                JsonObject object = getContent().getAsJsonObject();
                group.setGroupId(object.get("group_id").getAsInt());

                //log the successful connection
                Log.d(TAG, "Successfully created new group with id " + group.getGroupId());
                callback.onRequestCompleted(group);
            }

            //if the connection/response fails
            @Override
            public void onFailure() {
                Log.d(TAG, "Failed trying to create a new group");
                callback.onRequestFailed("Connection Failed");
            }
        });

    }

    public static void createNewUser(final BackendCallback callback, User user){

        //Set url for AsyncHttp request
        AsyncHttpClient client = new AsyncHttpClient(SERVER_URL);
        StringEntity jsonParams = null;

        //Set up HTTP Request headers to accept JSON response and Send JSON
        List<Header> headers = new ArrayList<Header>();
        headers.add(new BasicHeader("Accept", "application/json"));
        headers.add(new BasicHeader("Content-Type", "application/json"));

        try {
            JSONObject theuser = new JSONObject();
            theuser.put("username", user.getUserName());
            theuser.put("password", user.getPassword());
            theuser.put("name", user.getFirstName() + " " + user.getLastName());
            theuser.put("groups", user.getGroups());
            theuser.put("email", user.getEmail());
            jsonParams = new StringEntity(theuser.toString());
        }
        catch(Exception e){
            Log.d(TAG2, "error setting up new user");
        }
        //attempt to make connection and get JSON response
        client.post("users", jsonParams, headers, new JsonResponseHandler() {
            //if the connection/response succeeds
            @Override
            public void onSuccess() {
                //log the successful connection
                Log.d(TAG, "Successfully created new user");
                callback.onRequestCompleted(getContent());
            }

            //if the connection/response fails
            @Override
            public void onFailure() {
                Log.d(TAG, "Failed trying to create a new user");
                callback.onRequestFailed("Connection Failed");
            }
        });

    }

    public static void getUser(final BackendCallback callback, final String username){

        //Set url for AsyncHttp request
        AsyncHttpClient client = new AsyncHttpClient(SERVER_URL);

        //Set up HTTP Request headers to accept JSON response and Send JSON
        List<Header> headers = new ArrayList<Header>();
        headers.add(new BasicHeader("Accept", "application/json"));

        String location = "users/" + username;

        //attempt to make connection and get JSON response
        client.get(location, null, headers, new JsonResponseHandler() {
            //if the connection/response succeeds
            @Override
            public void onSuccess() {
                JsonObject object = getContent().getAsJsonObject();

                String namer, passwordr, emailr, firstName, lastName;
                ArrayList<Post> posts = new ArrayList<Post>();
                ArrayList<Group> groups = new ArrayList<Group>();
                emailr = object.get("email").getAsString();
                passwordr = object.get("password").getAsString();
                namer = object.get("name").getAsString();

                String[] temp = namer.split(" ");

                firstName = temp[0];
                lastName = temp[1];

                //log the successful connection
                Log.d(TAG, "Successful server connection getting user " + username);
                callback.onRequestCompleted(new User(firstName, lastName, emailr, passwordr, null));
            }

            //if the connection/response fails
            @Override
            public void onFailure() {
                Log.d(TAG, "Failed server connection getting user " + username);
                callback.onRequestFailed("Connection Failed");
            }
        });

    }

    public static void getPosts(final BackendCallback callback){

        //Set url for AsyncHttp request
        AsyncHttpClient client = new AsyncHttpClient(SERVER_URL);

        //Set up HTTP Request headers to accept JSON response and Send JSON
        List<Header> headers = new ArrayList<Header>();
        headers.add(new BasicHeader("Accept", "application/json"));

        //attempt to make connection and get JSON response
        client.get("posts", null, headers, new JsonResponseHandler() {
            //if the connection/response succeeds
            @Override
            public void onSuccess() {
                JsonArray jsonObject = getContent().getAsJsonArray();

                ArrayList<Post> posts = new ArrayList<Post>();
                String creator, classname, location, date, instructor;
                double starttime, endtime;

                for(int i = 0; i < jsonObject.size(); i++){
                    JsonObject object = jsonObject.get(i).getAsJsonObject();
                    creator = object.get("creator").getAsString();
                    classname = object.get("classname").getAsString();
                    location = object.get("location").getAsString();
                    starttime = object.get("starttime").getAsDouble();
                    endtime = object.get("endtime").getAsDouble();
                    date = object.get("date").getAsString();
                    instructor = object.get("instructor").getAsString();
                    posts.add(new Post(classname, instructor, location, starttime, endtime, date, creator));
                }
                Log.d(TAG, "Successful server connection getting all posts");

                //log the successful connection
                callback.onRequestCompleted(posts);
            }

            //if the connection/response fails
            @Override
            public void onFailure() {
                Log.d(TAG, "Failed server connection getting all posts");
                callback.onRequestFailed("Connection Failed");
            }
        });

    }

    public static void getGroups(final BackendCallback callback){

        //Set url for AsyncHttp request
        AsyncHttpClient client = new AsyncHttpClient(SERVER_URL);

        //Set up HTTP Request headers to accept JSON response and Send JSON
        List<Header> headers = new ArrayList<Header>();
        headers.add(new BasicHeader("Accept", "application/json"));

        //attempt to make connection and get JSON response
        client.get("groups", null, headers, new JsonResponseHandler() {
            //if the connection/response succeeds
            @Override
            public void onSuccess() {
                Log.d(TAG, "Successful server connection getting all groups");
                JsonArray jsonObject = getContent().getAsJsonArray();

                ArrayList<Group> groups = new ArrayList<Group>();
                for(int i = 0; i < jsonObject.size(); i++)
                    groups.add(unpackGroup(jsonObject.get(i).getAsJsonObject()));

                //log the successful connection
                callback.onRequestCompleted(groups);
            }

            //if the connection/response fails
            @Override
            public void onFailure() {
                Log.d(TAG, "Failed server connection getting all groups");
                callback.onRequestFailed("Connection Failed");
            }
        });

    }

    public static void getGroup(final BackendCallback callback, final int id){
        //Set url for AsyncHttp request
        AsyncHttpClient client = new AsyncHttpClient(SERVER_URL);

        //Set up HTTP Request headers to accept JSON response and Send JSON
        List<Header> headers = new ArrayList<Header>();
        headers.add(new BasicHeader("Accept", "application/json"));

        String location = "groups/" + id;

        //attempt to make connection and get JSON response
        client.get(location, null, headers, new JsonResponseHandler() {

            //if the connection/response succeeds
            @Override
            public void onSuccess() {
                //log the successful connection
                Log.d(TAG, "Successful server connection getting group " + id);
                callback.onRequestCompleted(unpackGroup(getContent().getAsJsonObject()));
            }

            //if the connection/response fails
            @Override
            public void onFailure() {
                Log.d(TAG, "Failed server connection getting group " + id);
                callback.onRequestFailed("Connection Failed");
            }
        });

    }

    private static Group unpackGroup(JsonObject object){

        ArrayList<String> members = new ArrayList<String>();
        ArrayList<String> chats = new ArrayList<String>();
        String creator, classname, instructor;
        int id;

        id = object.get("group_id").getAsInt();
        JsonArray objects = object.get("members").getAsJsonArray();
        for(int j = 0; j < objects.size(); j++)
            members.add(objects.get(j).getAsString());
        creator = object.get("creator").getAsString();
        classname = object.get("classname").getAsString();
        JsonArray chat = object.get("chat").getAsJsonArray();
        for(int j = 0; j < chat.size(); j++)
            chats.add(chat.get(j).getAsString());

        instructor = object.get("instructor").getAsString();

        Group group = new Group(classname, instructor, creator);
        group.addMembersGroup(members);
        group.addChatGroup(chats);
        group.setGroupId(id);

        return group;
    }
}
