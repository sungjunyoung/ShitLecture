package com.new9.shitlecture.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.new9.shitlecture.Activity.ChannelActivity;
import com.new9.shitlecture.Java.Channel;
import com.new9.shitlecture.Java.User;
import com.new9.shitlecture.R;

import java.util.List;
/**
 * Created by SEGU on 2016-10-07.
 */
public class ChannelAdapter extends RecyclerView.Adapter<ChannelAdapter.ViewHolder> {

    private List<Channel> channelList;

    public ChannelAdapter(List<Channel> channelList) {
        this.channelList = channelList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView channelName, numOfPersonInChannel, nameOfProfessor;
        public Button enterButton;

        public ViewHolder(View itemView) {
            super(itemView);
            channelName = (TextView) itemView.findViewById(R.id.channelName);
            numOfPersonInChannel = (TextView) itemView.findViewById(R.id.numOfUserInChannel);
            nameOfProfessor = (TextView) itemView.findViewById(R.id.nameOfProfessor);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.channel_row,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Channel channel = channelList.get(position);
        holder.numOfPersonInChannel.setText(holder.numOfPersonInChannel.getText() +" "+ new Integer(channel.getNumOfPersonInChannel()).toString());
        holder.channelName.setText(channel.getChannelName());
        holder.nameOfProfessor.setText(channel.getProfessorName());
    }

    @Override
    public int getItemCount() {
        return channelList.size();
    }

}
