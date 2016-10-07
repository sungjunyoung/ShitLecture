package com.new9.shitlecture.Java;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by SEGU on 2016-10-07.
 */

final public class User implements Serializable{

    static private String id;
    static private String pw;
    static private String selectedChannel =null;
    static private String name;
    static private String nick;
    static private List<Channel> channelList = new ArrayList<>();

    public static String getSelectedChannel() {
        return selectedChannel;
    }

    public static void setSelectedChannel(String selectedChannel) {
        User.selectedChannel = selectedChannel;
    }

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        User.name = name;
    }

    public static List<Channel> getChannelList() {
        return channelList;
    }

    public static void setChannelList(List<Channel> channelList) {
        User.channelList = channelList;
    }

    public static String getId() {
        return id;
    }
    public static void addChannel(Channel channel){
        User.channelList.add(channel);
    }
    public static boolean deleteChannel(Channel channel){
        int findChannel = -1;
        for(int i=0;i<User.channelList.size();i++){
            if(User.channelList.get(i).getChannelName().equals(channel.getChannelName()))
                findChannel = i;
        }
        if(findChannel<0) return false;
        else {
            User.channelList.remove(findChannel);
            return true;
        }
    }
    public static void setId(String id) {
        User.id = id;
    }

    public static String getPw() {
        return pw;
    }

    public static void setPw(String pw) {
        User.pw = pw;
    }

    public static String getNick() {
        return nick;
    }

    public static void setNick(String nick) {
        User.nick = nick;
    }
}
