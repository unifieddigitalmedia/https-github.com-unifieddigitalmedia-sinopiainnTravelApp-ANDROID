package com.example.home.sinopiainntravelapp;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Home on 07/11/16.
 */
public class ChatAdapter extends BaseAdapter {

    private static LayoutInflater inflater = null;

    ArrayList<ScaleBitMaps.ChatMessage> chatMessageList;

    public ChatAdapter(Activity activity, ArrayList<ScaleBitMaps.ChatMessage> list) {

        chatMessageList = list;

        inflater = (LayoutInflater) activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return chatMessageList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }






    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ScaleBitMaps.ChatMessage message = (ScaleBitMaps.ChatMessage) chatMessageList.get(position);

        View vi = convertView;

        if (convertView == null)
            vi = inflater.inflate(R.layout.chat_bubble, null);

        TextView msg = (TextView) vi.findViewById(R.id.message_text);
        msg.setText(message.body);
        LinearLayout layout = (LinearLayout) vi
                .findViewById(R.id.bubble_layout);
        LinearLayout parent_layout = (LinearLayout) vi
                .findViewById(R.id.bubble_layout_parent);

        // if message is mine then align to right
        if (message.isMine) {
            layout.setBackgroundResource(R.drawable.bubble2);
            parent_layout.setGravity(Gravity.RIGHT);
        }
        // If not mine then align to left
        else {
            layout.setBackgroundResource(R.drawable.bubble1);
            parent_layout.setGravity(Gravity.LEFT);
        }

        msg.setTextColor(Color.BLACK);

        return vi;
    }

    public void add(ScaleBitMaps.ChatMessage object) {
        chatMessageList.add(object);
    }

    public ArrayList<ScaleBitMaps.ChatMessage> getValues() {
        return chatMessageList;
    }


}