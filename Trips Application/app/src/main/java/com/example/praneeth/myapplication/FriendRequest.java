package com.example.praneeth.myapplication;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Praneeth on 4/21/2017.
 */

public class FriendRequest {
    String fromKey;
    String toKey;
    Boolean requestAction;

    public FriendRequest(String fromKey, String toKey, Boolean requestAction) {
        this.fromKey = fromKey;
        this.toKey = toKey;
        this.requestAction = requestAction;
    }

    public FriendRequest() {
    }

    @Override
    public String toString() {
        return "FriendRequest{" +
                "fromKey='" + fromKey + '\'' +
                ", toKey='" + toKey + '\'' +
                ", requestAction=" + requestAction +
                '}';
    }

    public String getFromKey() {
        return fromKey;
    }

    public void setFromKey(String fromKey) {
        this.fromKey = fromKey;
    }

    public String getToKey() {
        return toKey;
    }

    public void setToKey(String toKey) {
        this.toKey = toKey;
    }

    public Boolean getRequestAction() {
        return requestAction;
    }

    public void setRequestAction(Boolean requestAction) {
        this.requestAction = requestAction;
    }
    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("fromKey", fromKey);
        result.put("toKey", toKey);
        result.put("requestAction", requestAction);
        return result;
    }
}
