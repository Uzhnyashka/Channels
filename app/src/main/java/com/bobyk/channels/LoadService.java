package com.bobyk.channels;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.content.Context;
import android.util.Log;

import com.bobyk.channels.models.ChannelModel;
import com.bobyk.channels.models.ProgramModel;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import cz.msebera.android.httpclient.Header;

public class LoadService extends IntentService {

    private final String TAG = "IntentServiceLogs";


    public static final String URL_CHANNEL = "https://t2dev.firebaseio.com/CHANNEL.json";
    public static final String URL_PROGRAM = "https://t2dev.firebaseio.com/PROGRAM.json";

    private final AsyncHttpClient aClient = new SyncHttpClient();

    public LoadService(){
        super("LService");
    }

    @Override
    public void onCreate(){
        super.onCreate();
    }

    @Override
    protected void onHandleIntent(Intent intent){
        loadChannels();
        loadProgram(Calendar.getInstance().getTimeInMillis());
    }

    private void loadChannels(){
        aClient.get(URL_CHANNEL, new RequestParams(), new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                System.out.println(response);
                try {
                    getChannelsFromJson(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void loadProgram(final long time){
        aClient.get(URL_PROGRAM, new RequestParams(), new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.d("yeee", "DO THIS");
             //   System.out.println(response);
                try{
                    getProgramFromJson(response, time);
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        });
    }

    private void getChannelsFromJson(JSONObject jsonObject) throws JSONException {
        Set<String> categories = new HashSet<String>();
        Iterator it = jsonObject.keys();
        while (it.hasNext()){
            String key = (String) it.next();
            System.out.println(key);
            JSONObject jsonChannel = jsonObject.getJSONObject(key);
            jsonChannel.put("category", getCategory(jsonChannel));
            categories.add(jsonChannel.getString("category"));
            ChannelModel channel = new ChannelModel();
            channel.loadFromJson(jsonChannel);
            saveChannelToDb(channel);
            System.out.println(channel);
        }

        saveCategoriesToDb(categories);
    }

    private void getProgramFromJson(JSONObject jsonObject, long time) throws JSONException{
        Iterator it = jsonObject.keys();
        String currentDayKey = "";
        Calendar timeRequired = Calendar.getInstance();
        timeRequired.setTimeInMillis(time);
        while (it.hasNext()){
            String key = (String) it.next();
            JSONObject jsonDay = jsonObject.getJSONObject(key);
            Iterator iterator = jsonDay.keys();
            String k = (String) iterator.next();
            JSONObject jsonChannel = jsonDay.getJSONObject(k);
            long date = jsonChannel.getLong("date");
            Calendar dateTime = Calendar.getInstance();
            dateTime.setTimeInMillis(date);
            if (timeRequired.get(Calendar.DATE) == dateTime.get(Calendar.DATE)) {
                currentDayKey = key;
                break;}
            }
        JSONObject jsonCurrentDay = jsonObject.getJSONObject(currentDayKey);
        Iterator iterator = jsonCurrentDay.keys();
        int kol = 0;
        while (iterator.hasNext()){
            kol++;
            if (kol == 100) break;
            String k = (String) iterator.next();
            JSONObject jsonChannel = jsonCurrentDay.getJSONObject(k);
            ProgramModel programModel = new ProgramModel();
            programModel.loadFromJson(jsonChannel);
            System.out.println(programModel);
            saveProgramToDb(programModel);
        }
    }

    private void saveCategoriesToDb(Set<String> categories){
        Iterator<String> it = categories.iterator();
        while (it.hasNext()){
            ContentValues contentValues = new ContentValues();
            contentValues.put(ChannelContract.CategoryEntry.COLUMN_CATEGORY, it.next());
            System.out.println(contentValues.get(ChannelContract.CategoryEntry.COLUMN_CATEGORY));
            getContentResolver().insert(ChannelContract.CategoryEntry.CONTENT_URI, contentValues);
        }
    }

    private String getCategory(JSONObject jsonObject){
        Iterator it = jsonObject.keys();
        while (it.hasNext()){
            String key = (String) it.next();
            if (!key.equals("id") && !key.equals("name") && !key.equals("tvURL")) return key;
        }
        return null;
    }

    private void saveChannelToDb(ChannelModel channel){
        ContentValues contentValues = new ContentValues();
        contentValues.put(ChannelContract.ChannelEntry.COLUMN_ID_NAME, channel.getId());
        contentValues.put(ChannelContract.ChannelEntry.COLUMN_NAME, channel.getName());
        contentValues.put(ChannelContract.ChannelEntry.COLUMN_TV_URL, channel.getTvURL());
        contentValues.put(ChannelContract.ChannelEntry.COLUMN_CATEGORY, channel.getCategory());
        getContentResolver().insert(ChannelContract.ChannelEntry.CONTENT_URI, contentValues);
    }

    private void saveProgramToDb(ProgramModel program){
        ContentValues contentValues = new ContentValues();
        contentValues.put(ChannelContract.ProgramEntry.COLUMN_DATE, program.getDate());
        contentValues.put(ChannelContract.ProgramEntry.COLUMN_SHOW_ID, program.getShowID());
        contentValues.put(ChannelContract.ProgramEntry.COLUMN_TVSHOW_NAME, program.getTvShowName());
        getContentResolver().insert(ChannelContract.ProgramEntry.CONTENT_URI, contentValues);
    }
}
