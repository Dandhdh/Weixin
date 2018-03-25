package com.danyy.util;

import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class AuthUtil {

    public static final String APPID = "wx633e4285fbacd3df";
    public static final String APPSECRET = "537ca4bff2cadf1736fd8661ef14de4a";

    public static JSONObject doGet(String url) throws IOException{
        JSONObject jsonObject = null;
        DefaultHttpClient client = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet();
        HttpResponse response = client.execute(httpGet);
        HttpEntity entity = response.getEntity();
        if(entity!=null){
            String result = EntityUtils.toString(entity,"utf-8");
            jsonObject = JSONObject.fromObject(result);
        }
        httpGet.releaseConnection();
        return  jsonObject;
    }

}
