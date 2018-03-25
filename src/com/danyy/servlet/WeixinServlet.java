
package com.danyy.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.DocumentException;
import org.json.JSONException;

import com.danyy.po.TextMessage;
import com.danyy.tuling.TulingApiProcess;
import com.danyy.util.CheckUtil;
import com.danyy.util.MessageUtil;

public class WeixinServlet extends HttpServlet {

     /**
     * 接收微信平台发送的post请求
     */
    private static final long serialVersionUID = 7922920577832311647L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        //微信解密签名，结合了开发者填写的token参数和请求中的timestamp参数、nonce参数。
        String signature = request.getParameter("signature");
        //时间戳
        String timestamp = request.getParameter("timestamp");
        //随机数
        String nonce = request.getParameter("nonce");
        //随机字符串
        String echostr = request.getParameter("echostr");

        /**
         *通过检验 signature 对请求进行校验， 若校验成功则原样返回 echostr， 表示接入
         *成功，否则接入失败
         */
        PrintWriter out = response.getWriter();
        if (CheckUtil.checkSignature(signature, timestamp, nonce)) {
            out.print(echostr);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();
        try {
            //接收到的数据是xml的格式，需要转化为map，方便获取
            Map<String, String> map = MessageUtil.xmlToMap(request);
            //获取自身的name信息
            String toUserName = map.get("ToUserName");
            //获取用户name信息
            String fromUserName = map.get("FromUserName");
            String msgType = map.get("MsgType");
            String content = map.get("Content");

            System.out.println("消息类型"+msgType);
            String message = null;
                //当接收的消息内容是文本类型的时候
                if (MessageUtil.MESSAGE_TEXT.equals(msgType)){
                    if ("1".equals(content)){
                        message = MessageUtil.initText(toUserName,fromUserName,MessageUtil.firstMenu());

                    }else if ("3".equals(content)){
                        message = MessageUtil.initNewsMessage(toUserName,fromUserName);

                    }else if("菜单".equals(content)){
                        message = MessageUtil.initText(toUserName,fromUserName,MessageUtil.menuText());

                    }else if("2".equals(content)){
                        message = MessageUtil.initText(toUserName,fromUserName,MessageUtil.secondMenu());

                    }else if("5".equals(content)){
                        message = MessageUtil.initMusicMessage(toUserName,fromUserName);

                    }else if("4".equals(content)){
                        message = MessageUtil.initImageMessage(toUserName,fromUserName);

                    } else {

                        //
                        TextMessage text = new TextMessage();
                        //需要将信息返回给用户，所以toUserName与fromUserName反过来
                        text.setFromUserName(toUserName);
                        text.setToUserName(fromUserName);
                        text.setMsgType(MessageUtil.MESSAGE_TEXT);

                        //调用第三方图灵机器人说话
                        String reMsg = "";
                        try {
                            reMsg = TulingApiProcess.getTulingMessage(content);
                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        text.setContent("阿狸：" + reMsg);
                        //需要将String类型转会微信服务器所接受的类型
                        message = MessageUtil.textMessageToXml(text);
                    }


                }
                //事件类型
               else if(MessageUtil.MESSAGE_EVNET.equals(msgType)){
                   String eventType = map.get("Event");
                    System.out.println("具体的事件类型"+eventType);
                    if(MessageUtil.MESSAGE_SUBSCRIBE.equals(eventType)){
                        message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.menuText());
                    }else if(MessageUtil.MESSAGE_CLICK.equals(eventType)){
                        message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.menuText());
                    }else if(MessageUtil.MESSAGE_VIEW.equals(eventType)){
                        String url = map.get("EventKey");
                        message = MessageUtil.initText(toUserName, fromUserName, url);
                    }else if(MessageUtil.MESSAGE_SCANCODE.equals(eventType)){
                        String key = map.get("EventKey");
                        message = MessageUtil.initText(toUserName, fromUserName, key);
                    }
                }else if(MessageUtil.MESSAGE_LOCATION.equals(msgType)){
                    String label = map.get("Label");
                    message = MessageUtil.initText(toUserName, fromUserName, label);
                }else if(MessageUtil.MESSAGE_IMAGE.equals(msgType)){
                    String msg = "什么图片来的？我不看不看，就是不看";
                    message = MessageUtil.initText(toUserName, fromUserName, msg);
                }else if(MessageUtil.MESSAGE_VIDEO.equals(msgType)){
                    String msg = "我才不看这种小视频呢";
                    message = MessageUtil.initText(toUserName, fromUserName, msg);
                }
                else if(MessageUtil.MESSAGE_VOICE.equals(msgType)){
                    String msg = "说什么？大声点，我听不见";
                    message = MessageUtil.initText(toUserName, fromUserName, msg);
                }


            System.out.println(message);
            out.println(message);
            response.getWriter().write(message);

        } catch (DocumentException e) {
            e.printStackTrace();
        } finally{
            out.close();
        }
    }
}
