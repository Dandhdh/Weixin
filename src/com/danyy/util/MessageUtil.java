package com.danyy.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

import com.danyy.po.*;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.thoughtworks.xstream.XStream;

public class MessageUtil {

    /**
     *
     */
    public static final String MESSAGE_TEXT = "text";
    public static final String MESSAGE_NEWS = "news";
    public static final String MESSAGE_IMAGE = "image";
    public static final String MESSAGE_VOICE = "voice";
    public static final String MESSAGE_MUSIC = "music";
    public static final String MESSAGE_VIDEO = "video";
    public static final String MESSAGE_LINK = "link";
    public static final String MESSAGE_LOCATION = "location";     //地理位置信息
    public static final String MESSAGE_EVNET = "event";
    public static final String MESSAGE_SUBSCRIBE = "subscribe";      //事件类型：关注
    public static final String MESSAGE_UNSUBSCRIBE = "unsubscribe";  //事件类型：取消关注
    public static final String MESSAGE_CLICK = "CLICK";
    public static final String MESSAGE_VIEW = "VIEW";
    public static final String MESSAGE_SCANCODE= "scancode_push";

    /**
     * xml转为Map集合
     * @param request
     * @return
     * @throws IOException
     * @throws DocumentException
     */
    public static Map<String, String> xmlToMap(HttpServletRequest request) throws IOException, DocumentException {
        Map<String, String> map = new HashMap<String, String>();
        SAXReader reader = new SAXReader();

        InputStream ins = request.getInputStream();
        Document doc = reader.read(ins);

        Element root = doc.getRootElement();

        List<Element> list = root.elements();

        for (Element e:list){
            map.put(e.getName(), e.getText());
        }
        ins.close();
        return map;
    }
    /**
     * 将文本消息对象转化为xml
     * @return
     */
    public static String textMessageToXml(TextMessage textMessage) {
        XStream xStream = new XStream();
        xStream.alias("xml", textMessage.getClass());
        return xStream.toXML(textMessage);
    }

    /*
    拼接文本消息
     */
    public static String initText(String toUserName,String fromUserName,String content){
        TextMessage text = new TextMessage();
        text.setFromUserName(toUserName);
        text.setToUserName(fromUserName);
        text.setMsgType(MessageUtil.MESSAGE_TEXT);
        //text.setCreateTime((new Date()).getTime());
        text.setContent(content);
        return  textMessageToXml(text);
    }

    /*
    主菜单
     */
    public static String menuText(){
        StringBuffer sb = new StringBuffer();
        sb.append("终于等到你，还好没放弃！！！\n\n");
        sb.append("回复：\n");
        sb.append("菜单、调出菜单\n");
        sb.append("1、 我是谁？\n");
        sb.append("2、 介绍\n");
        sb.append("3、 图文信息\n");
        sb.append("4、 超跑图片\n");
        sb.append("5、 音乐\n");
        sb.append("回复其他的话呢,就会有图灵小机器人陪你聊天\n");
        return sb.toString();
    }

    /*
    回复“1”
     */
    public static String firstMenu(){
        StringBuffer sb = new StringBuffer();
        sb.append("");
        return sb.toString();
    }

    public static String secondMenu(){
        StringBuffer sb = new StringBuffer();
        sb.append("在实践中学习，相信功能会日渐丰富，敬请期待");
        return sb.toString();
    }

    /*
    图文消息转换成xml类型
     */
    public static String newsMessageToXml(NewsMessage newsMessage){
        XStream xs = new XStream();
        xs.alias("xml",newsMessage.getClass());
        xs.alias("item",new News().getClass());
        return xs.toXML(newsMessage);
    }

    /*
    图片信息转成xml
     */
    public static  String imageMessageToXml(ImageMessage imageMessage){
        XStream xs = new XStream();
        xs.alias("xml",imageMessage.getClass());
        return xs.toXML(imageMessage);
    }

    /*
    音乐信息转成xml
     */
    public static String musicMessageToXml(MusicMessage musicMessage){
        XStream xs = new XStream();
        xs.alias("xml",musicMessage.getClass());
        return xs.toXML(musicMessage);
    }

    /*
    图文信息组装，即推文
     */
    public static String initNewsMessage(String toUserName,String fromUserName){
        String message = null;
        List<News> newsList = new ArrayList<News>();
        NewsMessage newsMessage = new NewsMessage();

        News news = new News();
        news.setTitle("hexo搭建博客");
        news.setDescription("打一波广告，这是我的博客啦啦啦，虽然没什么，哈哈");
        news.setPicUrl("http://www.imooc.com/static/img/landp_banner.jpg");
        news.setUrl("https://dengyaoyuan.github.io");

        newsList.add(news);

        newsMessage.setToUserName(fromUserName);
        newsMessage.setFromUserName(toUserName);
        newsMessage.setCreateTime(new Date().getTime());
        newsMessage.setMsgType(MESSAGE_NEWS);
        newsMessage.setArticles(newsList);
        newsMessage.setArticleCount(newsList.size());

        message = newsMessageToXml(newsMessage);
        return message;
    }

    /*
    图片信息的组装
     */
    public static String initImageMessage(String toUserName,String fromUserName){
        String message = null;
        Image image = new Image();
        image.setMediaId("MOOD5mgQnvPRyiIcb6x-JQdeAKfiOYzAj8mM6KiW0RaSnH_E8PwP6xMKaJvuLXAf");
        ImageMessage imageMessage = new ImageMessage();
        imageMessage.setFromUserName(toUserName);
        imageMessage.setToUserName(fromUserName);
        imageMessage.setMsgType(MESSAGE_IMAGE);
        imageMessage.setCreateTime(new Date().getTime());
        imageMessage.setImage(image);

        message = imageMessageToXml(imageMessage);
        return message;
    }

    /*
    音乐消息的组装
     */
    public static String initMusicMessage(String toUserName,String fromUserName){
        String message = null;
        Music music = new Music();
        String path = MessageUtil.class.getResource("See You Again.mp3").getPath();
        music.setThumbMediaId("OoDqEEn8dZsOJjlaRBU1i7lTDSQQLN4IuXmoXfXThoTE6S53IYlrDEDI6vAvlxgX");
        music.setTitle("This is Our Paradise");
        music.setDescription("喜欢的小众歌曲");
        music.setMusicUrl("http://music.163.com/#/song?id=510027147");
        music.setHQMusicUrl("http://music.163.com/#/song?id=510027147");

        MusicMessage musicMessage = new MusicMessage();
        musicMessage.setFromUserName(toUserName);
        musicMessage.setToUserName(fromUserName);
        musicMessage.setMsgType(MESSAGE_MUSIC);
        musicMessage.setCreateTime(new Date().getTime());
        musicMessage.setMusic(music);

        message = musicMessageToXml(musicMessage);
        return message;
    }
}