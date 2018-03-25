package com.danyy.tuling;

import java.io.IOException;
import java.net.URLEncoder;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

//import net.sf.json.JSONObject;

public class TulingApiProcess {
    /**
     * 调用图灵机器人平台接口
     */
    /**
     * 调用图灵机器人平台接口 需要导入的包：commons-logging-1.0.4.jar、 httpclient-4.3.1.jar、httpcore-4.3.jar
     * @throws JSONException
     */
    public static String getTulingMessage(String context) throws IOException, JSONException {

        String INFO = URLEncoder.encode(context, "utf-8");
        String requesturl = "http://www.tuling123.com/openapi/api?key=68fb7cc752d64360aac946873a07a72e&info=" + INFO;
        HttpGet request = new HttpGet(requesturl);
        HttpResponse response = HttpClients.createDefault().execute(request);

        String result = "";

        // 200即正确的返回码
        if (response.getStatusLine().getStatusCode() == 200) {
            result = EntityUtils.toString(response.getEntity());
            System.out.println("返回结果：" + result);
        }
        JSONObject json = new JSONObject(result);
        //以code=100000为例，参考图灵机器人api文档
        if("100000".equals(json.getString("code"))){
            result = json.getString("text");
//            System.out.println(result);
        } else{
            result = "对不起，你说的话真是太高深了……";
        }
        return result;
    }

}