package com.new9.shitlecture.Java;

import android.content.Context;

import android.util.Log;
import android.widget.Toast;


import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;
import com.new9.shitlecture.Activity.LoginActivity;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;

import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;

import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.IOException;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * Created by SEGU on 2016-10-07.
 */

public class Client implements Serializable {

    public Client() {
    }


    final private String loginURL = "http://52.78.190.59/index.php/server/login";


    public boolean postLoginData(String id, String pw) {

        User.setId(id);
        User.setPw(pw);
        try {
            HttpClient httpClient = new DefaultHttpClient();
            List<NameValuePair> qparams = new ArrayList<NameValuePair>();
            qparams.add(new BasicNameValuePair("USER_ID", id));
            qparams.add(new BasicNameValuePair("PASSWORD", pw));
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(qparams, "UTF-8");
            HttpPost httpPost = new HttpPost(loginURL);
            httpPost.setEntity(entity);

            HttpResponse response = httpClient.execute(httpPost);

            Log.d("tag", response.toString());

            if (response != null) {

                String temp = EntityUtils.toString(response.getEntity());
                setCurUser(temp);
                //Map<String,Object> tempMap= jsonToMap(tempObject);
                //System.out.print(temp.get());
                return true;
            } else return false;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static Map<String, Object> jsonToMap(JSONObject json) throws JSONException {
        Map<String, Object> retMap = new HashMap<String, Object>();

        if (json != JSONObject.NULL) {
            retMap = toMap(json);
        }

        return retMap;
    }

    public static Map<String, Object> toMap(JSONObject object) throws JSONException {
        Map<String, Object> map = new HashMap<String, Object>();

        Iterator<String> keysItr = object.keys();
        while (keysItr.hasNext()) {
            String key = keysItr.next();
            Object value = object.get(key);

            if (value instanceof JSONObject) {
                value = toMap((JSONObject) value);
            }
            map.put(key, value);
        }
        return map;
    }

    public void setCurUser(String temp) throws JSONException { //EntityUtils.toString(response.getEntity());
        JSONObject tempObject = new JSONObject(temp);

        Map<String, Object> tempMap = new Gson().fromJson(temp, new TypeToken<HashMap<String, Object>>() {
        }.getType());
        String name = tempMap.get("name").toString();
        User.setName(name);
        ArrayList<LinkedTreeMap<String, String>> tempArray = (ArrayList<LinkedTreeMap<String, String>>) tempMap.get("lectures");
        for (int i = 0; i < tempArray.size(); i++) {
            LinkedTreeMap<String, String> tempLinkedTreeMap = tempArray.get(i);
            Channel tempChannel = new Channel(tempLinkedTreeMap.get("lecture"), tempLinkedTreeMap.get("professor"));
            User.addChannel(tempChannel);
        }
    }
}