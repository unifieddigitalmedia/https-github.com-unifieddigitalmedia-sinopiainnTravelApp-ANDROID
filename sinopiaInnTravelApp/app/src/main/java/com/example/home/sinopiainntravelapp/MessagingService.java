package com.example.home.sinopiainntravelapp;

import java.io.Serializable;

/**
 * Created by Home on 07/11/16.
 */
public class MessagingService implements Serializable {

    String id, message, createdAt, receipient;



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getreceipient() {
        return receipient;
    }

    public void setreceipient(String receipient) {
        this.receipient = receipient;
    }


}