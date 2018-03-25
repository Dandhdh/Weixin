package com.danyy.servlet;

import com.danyy.util.AuthUtil;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/callBack")
public class CallBackServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException,IOException {
        String code = req.getParameter("code");
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?" +
                "appid=APPID" +
                "&secret=SECRET" +
                "&code=CODE" +
                "&grant_type=authorization_code";
        String url2 = url.replace("APPID", AuthUtil.APPID)
                .replace("SECRET", AuthUtil.APPSECRET)
                .replace("code", code);


        JSONObject jsonObject = AuthUtil.doGet(url2);
        String openid = jsonObject.getString("openid");
        String token = jsonObject.getString("access_token");

        String infoUrl = "https://api.weixin.qq.com/sns/userinfo?" +
                "access_token=ACCESS_TOKEN" +
                "&openid=OPENID" +
                "&lang=zh_CN";
        String infoUrl2 = infoUrl.replace("ACCESS_TOKEN", token)
                .replace("OPENID", openid);
        JSONObject userInfo = AuthUtil.doGet(infoUrl2);
        System.out.println(userInfo);
    }
}
