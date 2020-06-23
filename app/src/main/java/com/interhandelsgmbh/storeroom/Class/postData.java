package com.interhandelsgmbh.storeroom.Class;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;

/**
 * Created by parsa on 4/25/17.
 */
public class postData extends AsyncTask<String, Void, String> {

    String url = null;
    String data = null;
    String result = null;
    private boolean isCommunicationSucceed = false;
    private OnCompletedCallBack onCompleted = null;
    private Exception exception = null;
    private OnErrorCallBack onError = null;
    Context context;

    public postData(String _url, JSONObject _jsonObject, Context _context) {
        url = _url;
        data = _jsonObject.toString();
        context = _context;
        //this.onCompleted=onCompletedCallBack;
    }

    public postData(String _url, JSONArray _jsonArray) {
        url = _url;
        data = _jsonArray.toString();

        //this.onCompleted=onCompletedCallBack;
    }


    public void addOnCompletedCallBack(OnCompletedCallBack onCompletedCallBack) {
        this.onCompleted = onCompletedCallBack;
    }

    @Override
    protected String doInBackground(String... params) {
        HttpURLConnection httpcon;

        try {
            //Connect
            httpcon = (HttpURLConnection) ((new URL (url).openConnection()));
            httpcon.setDoOutput(true);
            httpcon.setRequestProperty("Content-Type", "application/json");
            httpcon.setRequestProperty("Accept", "application/json");
            httpcon.setRequestMethod("POST");
            httpcon.connect();

            //Write
            OutputStream os = httpcon.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(data);
            writer.close();
            os.close();

            //Read
            BufferedReader br = new BufferedReader(new InputStreamReader(httpcon.getInputStream(),"UTF-8"));

            String line = null;
            StringBuilder sb = new StringBuilder();

            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            br.close();
            result = sb.toString();

            return result;

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(String response) {

        //   Log.v("test:WebServiceResponse",response);
        if (onCompleted != null) {
            //  Gson gson = new Gson();
            try {
                //Log.w("WebService", response);
                //		JSONObject obj = new JSONObject(response);


                if(response.length()>0) {
                    JSONObject obj = new JSONObject();

                    obj = new JSONObject(response);

                    if (response != null) {
                        isCommunicationSucceed = true;
                        onCompleted.onCompletedCallBack(isCommunicationSucceed, obj);
                    }
                    else
                        onCompleted.onCompletedCallBack(isCommunicationSucceed, obj);
                }else{
                    onCompleted.onCompletedCallBack(false, null);
                }
            } catch (Exception e) {

                Toast.makeText(context, "error while posting data.. Please check your url", Toast.LENGTH_LONG).show();
                if (e.getMessage() != null)
                    Log.e(this.getClass().getName(), e.getMessage());
                Log.e(this.getClass().getName(), "Parsing json result from WebService("
                        + ") was not successfull. Result string : " + response);
            }
        }

        if (exception != null && onError != null) {
            onError.onError(exception);
        }
    }
    public interface OnCompletedCallBack {
        void onCompletedCallBack(boolean isSucceed, JSONObject returnedObject) throws JSONException, ParseException;
    }

    public interface OnErrorCallBack {
        void onError(Exception ex);
    }

}