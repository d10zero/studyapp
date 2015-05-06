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
        public void onRequestCompleted(Object result);
        public void onRequestFailed(String message);
    }

    public static void testJSON(final BackendCallback callback){

        //Set url for AsyncHttp request
        AsyncHttpClient client = new AsyncHttpClient(SERVER_URL);

        //Set up HTTP Request headers to accept JSON response and Send JSON
        List<Header> headers = new ArrayList<Header>();
        headers.add(new BasicHeader("Accept", "application/json"));
        //headers.add(new BasicHeader("Content-Type", "application/json"));

        //attempt to make connection and get JSON response
        client.get("users", null, headers, new JsonResponseHandler() {

            //if the connection/response succeeds
            @Override
            public void onSuccess() {

                //log the successful connection
                Log.d(TAG, "Parsing JSON response");

                //create variables to store response in
                String username, password, name, email;
                double[] time;
                ArrayList<Post> posts = new ArrayList<Post>();

                //get response into JSONArray
                JsonArray result = getContent().getAsJsonArray();
                for (int i = 0; i < result.size(); i++) {

                    //turn each JSON object into Post object
                    JsonObject object = result.get(i).getAsJsonObject();
                    username = object.get("username").getAsString();
                    password = object.get("password").getAsString();
                    name = object.get("name").getAsString();
                    email = object.get("email").getAsString();
                    Log.d(TAG, "USERNAME = " + username);
                    Log.d(TAG, "PASSWORD = " + password);
                    Log.d(TAG, "NAME = " + name);
                    Log.d(TAG, "EMAIL = " + email);
                }

                Log.d(TAG, "Load returned: " + result);
                callback.onRequestCompleted(posts);
            }

            //if the connection/response fails
            @Override
            public void onFailure() {
                Log.d(TAG, "Connection to server failed");
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
            thepost.put("time", post.getTime());
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
                JsonObject result = getContent().getAsJsonObject();

                //log the successful connection
                Log.d(TAG, "Successfully created new group");
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

    public static void createNewGroup(final BackendCallback callback, Group group){

        //Set url for AsyncHttp request
        AsyncHttpClient client = new AsyncHttpClient(SERVER_URL);
        StringEntity jsonParams = null;

        //Set up HTTP Request headers to accept JSON response and Send JSON
        List<Header> headers = new ArrayList<Header>();
        headers.add(new BasicHeader("Accept", "application/json"));
        headers.add(new BasicHeader("Content-Type", "application/json"));

        try {
            JSONObject thegroup = new JSONObject();
            thegroup.put("group_id", group.getGroupId());
            thegroup.put("classname", group.getClassName());
            thegroup.put("instuctor", group.getInstructor());
            thegroup.put("members", group.getMembers());
            thegroup.put("chat", group.getChatboard());
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
                JsonObject result = getContent().getAsJsonObject();

                //log the successful connection
                Log.d(TAG, "Successfully created new group");
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

                //log the successful connection
                Log.d(TAG, "Successfully logged in as " + username);
                callback.onRequestCompleted(getContent());
            }

            //if the connection/response fails
            @Override
            public void onFailure() {
                Log.d(TAG, "Failed to login as " + username);
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
                //log the successful connection
                Log.d(TAG, "Successful server connection getting user " + username);
                callback.onRequestCompleted(getContent());
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
                int id;
                double time;
                for(int i = 0; i < jsonObject.size(); i++){
                    JsonObject object = jsonObject.get(i).getAsJsonObject();
                    creator = object.get("creator").getAsString();
                    classname = object.get("classname").getAsString();
                    id = object.get("post_id").getAsInt();
                    location = object.get("location").getAsString();
                    time = object.get("time").getAsDouble();
                    date = object.get("date").getAsString();
                    instructor = object.get("instructor").getAsString();
                    posts.add(new Post(classname, instructor, location, time, date, creator));

                }

                //log the successful connection
                Log.d(TAG, "Successful server connection getting all posts");
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
                JsonArray jsonObject = getContent().getAsJsonArray();

                ArrayList<Group> groups = new ArrayList<Group>();
                ArrayList<String> members = new ArrayList<String>();
                ArrayList<String> chats = new ArrayList<String>();
                String creator, classname, instructor;
                int id;

                for(int i = 0; i < jsonObject.size(); i++){
                    JsonObject object = jsonObject.get(i).getAsJsonObject();
                    id = object.get("group_id").getAsInt();
                    JsonArray objects = object.get("members").getAsJsonArray();
                    for(int j = 0; j < objects.size(); j++)
                        members.add(objects.get(i).getAsString());
                    creator = object.get("creator").getAsString();
                    classname = object.get("classname").getAsString();
                    JsonArray chat = object.get("chat").getAsJsonArray();
                    for(int j = 0; j < objects.size(); j++)
                        chats.add(chat.get(i).getAsString());
                    instructor = object.get("instructor").getAsString();

                    groups.add(new Group(classname, instructor));
                    groups.get(groups.size()-1).addMembersGroup(members);
                    groups.get(groups.size()-1).addChatGroup(chats);
                    groups.get(groups.size()-1).setGroupId(id);
                }
                //log the successful connection
                Log.d(TAG, "Successful server connection getting all groups");
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
                callback.onRequestCompleted(getContent());
            }

            //if the connection/response fails
            @Override
            public void onFailure() {
                Log.d(TAG, "Failed server connection getting group " + id);
                callback.onRequestFailed("Connection Failed");
            }
        });

    }
}
