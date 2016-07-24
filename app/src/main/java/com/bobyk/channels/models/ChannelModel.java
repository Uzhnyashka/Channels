package com.bobyk.channels.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by bobyk on 23/07/16.
 */
public class ChannelModel implements Serializable{
    private String id;
    private String name;
    private String tvURL;
    private String category;

    public ChannelModel(){

    }

    public void loadFromJson(JSONObject jsonObject) throws JSONException{
        setId(jsonObject.getString("id"));
        setName(jsonObject.getString("name"));
        setTvURL(jsonObject.getString("tvURL"));
        setCategory(jsonObject.getString("category"));
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getTvURL() {
        return tvURL;
    }

    public String getCategory() {
        return category;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTvURL(String tvURL) {
        this.tvURL = tvURL;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Channel[ {id:" + this.id + "}, " +
                "{name:" + this.name + "}, " +
                "{tvURL:" + this.tvURL + "}, " +
                "{category:" + this.category + "}\n";

    }
}
