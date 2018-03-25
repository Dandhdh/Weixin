package com.danyy.po;
/**
 * ÏûÏ¢¸¸Àà
 * @author Stephen
 *
 */
public class BaseMessage {
	//½ÓÊÕ·½Î¢ÐÅºÅ
	private String ToUserName;
	//·¢ËÍ·½Î¢ÐÅºÅ
	private String FromUserName;
	//´´½¨Ê±¼ä
	private long CreateTime;
	//ÏûÏ¢ÀàÐÍ
	private String MsgType;
	public String getToUserName() {
		return ToUserName;
	}
	public void setToUserName(String toUserName) {
		ToUserName = toUserName;
	}
	public String getFromUserName() {
		return FromUserName;
	}
	public void setFromUserName(String fromUserName) {
		FromUserName = fromUserName;
	}
	public long getCreateTime() {
		return CreateTime;
	}
	public void setCreateTime(long createTime) {
		CreateTime = createTime;
	}
	public String getMsgType() {
		return MsgType;
	}
	public void setMsgType(String msgType) {
		MsgType = msgType;
	}
}
