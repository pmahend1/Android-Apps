package com.example.praneeth.myapplication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by Praneeth on 4/20/2017.
 */

public class User {
    String email, firstname, lastname, profilepicUri, userKey, gender = "Male", status = "no status";
    Map<String, ArrayList<Chat>> chatRoomConveMap = new HashMap<String, ArrayList<Chat>>();
    ArrayList<String> friends = new ArrayList<>();
    Set<String> userTrips =  new HashSet<>();

    public User(String email, String firstname, String lastname, String profilepicUri, String userKey, String gender, String status) {
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.profilepicUri = profilepicUri;
        this.userKey = userKey;
        this.gender = gender;
        this.status = status;
    }

    public User() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getProfilepicUri() {
        return profilepicUri;
    }

    public void setProfilepicUri(String profilepicUri) {
        this.profilepicUri = profilepicUri;
    }

    public String getUserKey() {
        return userKey;
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", profilepicUri='" + profilepicUri + '\'' +
                ", userKey='" + userKey + '\'' +
                ", gender='" + gender + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("firstname", firstname);
        result.put("lastname", lastname);
        result.put("email", email);
        result.put("profilepicUri", profilepicUri);
        result.put("userKey", userKey);
        result.put("status", status);
        result.put("gender", gender);
        return result;
    }
}
