package com.example.home.sinopiainntravelapp;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.google.firebase.iid.FirebaseInstanceId;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import cz.msebera.android.httpclient.Header;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Chats extends Fragment implements View.OnClickListener {

    private EditText msg_edittext;
    private String user2 = "";
    public Random random;
    public static ArrayList<ScaleBitMaps.ChatMessage> chatlist;
    public static ChatAdapter chatAdapter;
    ListView msgListView;
    Handler mainHandler;

    public  static boolean active = false;


    @Override
    public void onStart() {
        super.onStart();
        active = true;
    }

    @Override
    public void onStop() {
        super.onStop();
        active = false;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.chat_layout, container, false);

        setHasOptionsMenu(true);
         mainHandler = new Handler(getContext().getMainLooper());


        random = new Random();

        msg_edittext = (EditText) view.findViewById(R.id.messageEditText);
        msgListView = (ListView) view.findViewById(R.id.msgListView);
        ImageButton sendButton = (ImageButton) view
                .findViewById(R.id.sendMessageButton);
        sendButton.setOnClickListener(this);


        msgListView.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        msgListView.setStackFromBottom(true);


        chatlist = new ArrayList<ScaleBitMaps.ChatMessage>();

       if (savedInstanceState != null) {

            ArrayList<ScaleBitMaps.ChatMessage> log = (ArrayList<ScaleBitMaps.ChatMessage>) savedInstanceState.getSerializable("chatAdapter");

            if (log != null) {


                chatAdapter = new ChatAdapter(getActivity(), log);

                String newdate;

                StringBuilder builder = new StringBuilder();

                newdate = builder.append(CommonMethods.getCurrentDate()).append(" ").append(CommonMethods.getCurrentTime()).toString();

                final ScaleBitMaps.ChatMessage chatMessage = new ScaleBitMaps.ChatMessage("", FirebaseInstanceId.getInstance().getToken(),
                        getArguments().getString("message")   , "" + random.nextInt(1000), false,newdate, "Sinopia Inn");

                chatMessage.setMsgID();

                chatMessage.body =  getArguments().getString("message");

                chatMessage.Date = CommonMethods.getCurrentDate();

                chatMessage.Time = CommonMethods.getCurrentTime();

                chatAdapter.add(chatMessage);

            }else{

                chatAdapter = new ChatAdapter(getActivity(), chatlist);



            }


        }



        msgListView.setAdapter(chatAdapter);

        getActivity().registerReceiver(this.appendChatScreenMsgReceiver, new IntentFilter("appendChatScreenMsg"));

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {


        ArrayList<ScaleBitMaps.ChatMessage> log = chatAdapter.getValues();

        savedInstanceState.putSerializable("chatAdapter",log);

        super.onSaveInstanceState(savedInstanceState);


    }

    public void sendTextMessage(View v) {

        String message = msg_edittext.getEditableText().toString();

        if (!message.equalsIgnoreCase("")) {


            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            String newdate = sdf.format(new Date());


            final ScaleBitMaps.ChatMessage chatMessage = new ScaleBitMaps.ChatMessage(FirebaseInstanceId.getInstance().getToken(), user2,
                    message, "" + random.nextInt(1000), true,newdate, ((Activity_CheckIn) getActivity()).name);

            chatMessage.setMsgID();

            chatMessage.body = message;

            chatMessage.Date = CommonMethods.getCurrentDate();

            chatMessage.Time = CommonMethods.getCurrentTime();

            msg_edittext.setText("");

            if(chatAdapter == null){

                chatAdapter = new ChatAdapter(getActivity(), chatlist);
            }
            chatAdapter.add(chatMessage);

            chatAdapter.notifyDataSetChanged();

            /*if (chatAdapter.getCount() > 1) {

                msgListView.smoothScrollToPosition(chatAdapter.getCount() - 1);

            }*/



            sendMessage(chatMessage);


        }

    }


    private void sendMessage(ScaleBitMaps.ChatMessage chatMessage) {

        AsyncHttpClient client = new AsyncHttpClient();

        RequestParams params = new RequestParams();

        params.put("body", chatMessage.getMessage());

        params.put("isMine","true");

        params.put("sender", chatMessage.getSender());

        params.put("msgid", chatMessage.getId());

        params.put("receiver", chatMessage.getReceiver());

        params.put("senderName", chatMessage.getSenderName());

        params.put("createdAt", chatMessage.getCreatedAt());


        client.post("http://www.sinopiainn.com/api/newmessage/", new JsonHttpResponseHandler() {

            @Override
            public void onStart() {
                // called before request is started
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject json) {

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable t, JSONObject e) {



            }

            @Override
            public void onRetry(int retryNo) {
                // called when request is retried
            }
        });




    }

        @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sendMessageButton:
                sendTextMessage(v);

        }
    }



    BroadcastReceiver appendChatScreenMsgReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle b = intent.getExtras();
            if (b != null) {

                String newdate;

                StringBuilder builder = new StringBuilder();

                newdate = builder.append(CommonMethods.getCurrentDate()).append(" ").append(CommonMethods.getCurrentTime()).toString();

                final ScaleBitMaps.ChatMessage chatMessage = new ScaleBitMaps.ChatMessage("", FirebaseInstanceId.getInstance().getToken(),
                        intent.getExtras().getString("message")   , "" + random.nextInt(1000), false,newdate, "Sinopia Inn");

                chatMessage.setMsgID();

                chatMessage.body = intent.getExtras().getString("message");

                chatMessage.Date = CommonMethods.getCurrentDate();

                chatMessage.Time = CommonMethods.getCurrentTime();

                chatAdapter.add(chatMessage);

                chatAdapter.notifyDataSetChanged();


            }


        }
    };


    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(appendChatScreenMsgReceiver);
    }


}
