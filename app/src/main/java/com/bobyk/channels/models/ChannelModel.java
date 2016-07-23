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

    public ChannelModel(){

    }

    public void loadFromJson(JSONObject jsonObject) throws JSONException{
        setId(jsonObject.getString("id"));
        setName(jsonObject.getString("name"));
        setTvURL(jsonObject.getString("tvURL"));
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

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTvURL(String tvURL) {
        this.tvURL = tvURL;
    }

    @Override
    public String toString() {
        return "Channel[ {id:" + this.id + "}, " +
                "{name:" + this.name + "}, " +
                "{tvURL:" + this.tvURL + "}\n";

    }
}
