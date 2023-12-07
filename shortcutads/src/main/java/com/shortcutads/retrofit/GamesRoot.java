package com.shortcutads.retrofit;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GamesRoot {

    @SerializedName("game")
    private List<GameItem> game;

    @SerializedName("message")
    private String message;

    @SerializedName("status")
    private boolean status;

    public List<GameItem> getGame() {
        return game;
    }

    public String getMessage() {
        return message;
    }

    public boolean isStatus() {
        return status;
    }

    public static class GameItem {

        @SerializedName("image")
        private String image;

        @SerializedName("createdAt")
        private String createdAt;

        @SerializedName("name")
        private String name;

        @SerializedName("link")
        private String link;

        @SerializedName("rating")
        private double rating;

        @SerializedName("_id")
        private String id;

        @SerializedName("updatedAt")
        private String updatedAt;

        public String getImage() {
            return image;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public String getName() {
            return name;
        }

        public String getLink() {
            return link;
        }

        public double getRating() {
            return rating;
        }

        public String getId() {
            return id;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }
    }
}