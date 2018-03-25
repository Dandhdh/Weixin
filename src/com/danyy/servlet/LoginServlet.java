package com.danyy.servlet;

import com.danyy.util.AuthUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

@WebServlet("/wxLogin")
public class LoginServlet extends HttpServlet {

    protected void deGet(HttpServletRequest req, HttpServletResponse resp)
         throws ServletException,IOException{

        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

         //回调地址
         String callBack = "http://17656f9g43.iok.la/Weixin/wx.do";
         String url = "https://open.weixin.qq.com/connect/oauth2/authorize?" +
                 "appid=APPID" +
                 "&redirect_uri=REDIRECT_URI" +
                 "&response_type=code" +
                 "&scope=SCOPE" +
                 "&state=STATE#wechat_redirect ";
         String url2 = url.replace("APPID",AuthUtil.APPID)
                          .replace("REDIRECT_URI", URLEncoder.encode(callBack))
                          .replace("SCOPE","snsapi_userinfo");
         //重定向
         resp.sendRedirect(url2);
     }

}
