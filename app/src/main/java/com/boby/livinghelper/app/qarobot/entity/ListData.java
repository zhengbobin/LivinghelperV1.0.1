package com.boby.livinghelper.app.qarobot.entity;


/**
 * 聊天列表实体类
 *
 * @author zbobin.com
 */
public class ListData {
	
	public static final int SEND = 1;
	public static final int RECEIVER = 2;
	private String content;
	private int flag;
	
	public ListData(String content,int flag) {
		setContent(content);
		setFlag(flag);
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}
	
	
	
}
