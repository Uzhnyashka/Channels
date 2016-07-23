package com.bobyk.channels;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.bobyk.channels.models.ChannelModel;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.Objects;

import cz.msebera.android.httpclient.Header;

public class LoadService extends Service {

    public static final String URL_CHANNEL = "https://t2dev.firebaseio.com/CHANNEL.json";
    public static final String URL_CATEGORY = "https://t2dev.firebaseio.com/CATEGORY.json";
    public static final String URL_PROGRAM = "https://t2dev.firebaseio.com/PROGRAM.json";

    public LoadService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        loadChannels();
        return super.onStartCommand(intent, flags, startId);
    }

    public void loadChannels(){
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(URL_CHANNEL, new RequestParams(), new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                System.out.println(response);
                bla(response);
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

    private void bla(JSONObject jsonObject) throws JSONException {
        Iterator it = jsonObject.keys();
        while (it.hasNext()){
            String key = (String) it.next();
            System.out.println(key);
            JSONObject jsonChannel = jsonObject.getJSONObject(key);
            ChannelModel channel = new ChannelModel();
            channel.loadFromJson(jsonChannel);
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

}
