package com.wy.vo;

import java.io.Serializable;

public class User implements Serializable{

    private static final long serialVersionUID = 1L;
    
    private String name;
    
    private String id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "User [name=" + name + ", id=" + id + "]";
    }

    public User(String name, String id) {
        super();
        this.name = name;
        this.id = id;
    }
}
