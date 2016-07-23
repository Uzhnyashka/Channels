package com.bobyk.channels;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.content.Context;
import android.util.Log;

import com.bobyk.channels.models.ChannelModel;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import cz.msebera.android.httpclient.Header;

public class TryLoadService extends IntentService {

    private final String TAG = "IntentServiceLogs";


    public static final String URL_CHANNEL = "https://t2dev.firebaseio.com/CHANNEL.json";

    private final AsyncHttpClient aClient = new SyncHttpClient();

    public TryLoadService(){
        super("LService");
    }

    @Override
    public void onCreate(){
        super.onCreate();
    }

    @Override
    protected void onHandleIntent(Intent intent){
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

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });
    }

    public void loadChannels(){
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(URL_CHANNEL, new RequestParams(), new JsonHttpResponseHandler(){
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

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });
    }

    private void getChannelsFromJson(JSONObject jsonObject) throws JSONException {
        Iterator it = jsonObject.keys();
        while (it.hasNext()){
            String key = (String) it.next();
            System.out.println(key);
            JSONObject jsonChannel = jsonObject.getJSONObject(key);
            ChannelModel channel = new ChannelModel();
            channel.loadFromJson(jsonChannel);
            saveChannelToDb(channel);
            System.out.println(channel);
           /* ChannelModel channel = null;
            try {
                obj = jsonObject.getJSONObject(key);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            System.out.println(obj);
            try {
                if (!key.equals("id") && !key.equals("name") && !key.equals("tvURL")) obj.put("category", key);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            System.out.println(obj);*/
        }
    }

    private void saveChannelToDb(ChannelModel channel){
        ContentValues contentValues = new ContentValues();
        contentValues.put(ChannelContract.ChannelEntry.COLUMN_ID_NAME, channel.getId());
        contentValues.put(ChannelContract.ChannelEntry.COLUMN_NAME, channel.getName());
        contentValues.put(ChannelContract.ChannelEntry.COLUMN_TV_URL, channel.getTvURL());
        getContentResolver().insert(ChannelContract.ChannelEntry.CONTENT_URI, contentValues);
    }

}
