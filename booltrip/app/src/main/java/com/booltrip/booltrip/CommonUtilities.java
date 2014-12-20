package com.booltrip.booltrip;
import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public final class CommonUtilities {


    private static String serverUri = "http://booltrip.com/api";
    /**
     * HTTP client
     */
    private static AsyncHttpClient client = new AsyncHttpClient();
    private static AsyncHttpClient clientPost = new AsyncHttpClient();
    public static void get(String url, AsyncHttpResponseHandler responseHandler) {
        try {
            client.get(serverUri+url, responseHandler);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }
    public static void post(String url, JSONObject json, AsyncHttpResponseHandler responseHandler, Context context) {
        Header[] headers = {
                new BasicHeader("Accept", "application/json")
                ,new BasicHeader("Content-type", "application/json")
        };
        try {
            StringEntity se = new StringEntity(json.toString());
            clientPost.post(context, serverUri+url, headers, se, "application/json", responseHandler);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return;
        }
    }
}
