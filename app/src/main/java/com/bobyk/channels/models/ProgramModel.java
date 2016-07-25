package com.bobyk.channels.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by bobyk on 25/07/16.
 */
public class ProgramModel implements Serializable{
    private long date;
    private String showID;
    private String tvShowName;

    public ProgramModel(){

    }

    public void loadFromJson(JSONObject jsonObject) throws JSONException{
        setDate(jsonObject.getLong("date"));
        setShowID(jsonObject.getString("showID"));
        setTvShowName(jsonObject.getString("tvShowName"));
    }

    public long getDate() {
        return date;
    }

    public String getShowID() {
        return showID;
    }

    public String getTvShowName() {
        return tvShowName;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public void setShowID(String showID) {
        this.showID = showID;
    }

    public void setTvShowName(String tvShowName) {
        this.tvShowName = tvShowName;
    }

    @Override
    public String toString() {
        return "ProgramModel[ {date:" + this.date + "}, " +
                "{showID:" + this.showID + "}, " +
                "{tvShowName:" + this.tvShowName + "}]";
    }
}
