package com.uncc.prateek.jsonparserdemo;

import org.json.JSONException;
import org.json.JSONObject;

public class Person {
    String name, dept;
    int id, age;

    public Person(JSONObject personJSONObject) throws JSONException {
        this.age = personJSONObject.getInt("age");
        this.name = personJSONObject.getString("name");
        this.id = personJSONObject.getInt("id");
        this.dept = personJSONObject.getString("department");
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Person [name=" + name + ", dept=" + dept + ", id=" + id
                + ", age=" + age + "]";
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }
}


