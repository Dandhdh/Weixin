package com.danyy.test;

import com.danyy.po.AccessToken;
import com.danyy.util.WeixinUtil;
import net.sf.json.JSONObject;

public class WeixinTest {
    public static void main(String args[]){
        try {
            AccessToken token = WeixinUtil.getAccessToken();
            System.out.println("凭证："+token.getToken());
            System.out.println("有效时间："+token.getExpiresIn());

            /*
            创建菜单
             */
            String path = "C:\\Users\\Administrator\\Desktop\\56427c97b7238036537cba90d9b18ff3.jpg";
            String media_ID = WeixinUtil.upload(path,token.getToken(),"thumb");
            System.out.println(media_ID);

            String menu = JSONObject.fromObject(WeixinUtil.initMenu()).toString();
            int result = WeixinUtil.createMenu(token.getToken(),menu);
            if(result==0){
                System.out.println("创建菜单成功");
            }else {
                System.out.println("错误码："+result);
            }

            /*
            查询菜单
             */
            JSONObject jsonObject = WeixinUtil.queryMenu(token.getToken());
            System.out.println(jsonObject);

            /*
            删除菜单
             */
            int result2 = WeixinUtil.deleteMenu(token.getToken());
            if(result2==0){
                System.out.println("菜单删除成功");
            }else {
                System.out.println("菜单删除失败");
            }

            /*
            翻译
             */
            String result3 = WeixinUtil.translate("dim");
            System.out.println(result3);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
