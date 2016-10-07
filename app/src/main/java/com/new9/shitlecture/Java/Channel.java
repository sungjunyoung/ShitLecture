package com.new9.shitlecture.Java;

import android.widget.Button;

/**
 * Created by SEGU on 2016-10-07.
 */
public class Channel {
    private String channelName; //보통 과목이름으로 설정


    private String professorName;
    private int numOfPersonInChannel;


    private Button enterChannel;


    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public Button getEnterChannel() {
        return enterChannel;
    }

    public String getProfessorName() {
        return professorName;
    }

    public void setProfessorName(String professorName) {
        this.professorName = professorName;
    }
    public void setEnterChannel(Button enterChannel) {
        this.enterChannel = enterChannel;
    }
    public int getNumOfPersonInChannel() {
        return numOfPersonInChannel;
    }

    public void setNumOfPersonInChannel(int numOfPersonInChannel) {
        this.numOfPersonInChannel = numOfPersonInChannel;
    }

    public Channel() {
    }

    public Channel(String channelName, String professorName, int numOfPersonInChannel) {
        setChannelName(channelName);
        setProfessorName(professorName);
        setNumOfPersonInChannel(numOfPersonInChannel);
    }
}
