package com.shortcutads.retrofit;

import com.google.gson.annotations.SerializedName;

public class RestResponce {

    @SerializedName("appData")
    private String buff;

    @SerializedName("status")
    private boolean status;

    public String getMessage() {
        return buff;
    }

    public boolean isStatus() {
        return status;
    }
}