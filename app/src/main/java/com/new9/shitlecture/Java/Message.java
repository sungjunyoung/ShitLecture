package com.new9.shitlecture.Java;

/**
 * Created by SEGU on 2016-10-07.
 */

public class Message {
    private String name;

    public Message() {
    }

    private String content;

    public Message(String name, String content) {
        this.name = name;
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
