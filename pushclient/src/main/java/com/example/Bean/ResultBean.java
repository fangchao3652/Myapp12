package com.example.Bean;

/**
 * 返回结果基bena
 * 
 * @author zhangxh
 * 
 */
public class ResultBean {
	/**
	 * 结果标识0成功1失败
	 */
	private int RetCode;
	/**
	 * 标识的文字信息
	 */
	private String RetMessage;

	public int getRetCode() {
		return RetCode;
	}

	public void setRetCode(int retCode) {
		RetCode = retCode;
	}

	public String getRetMessage() {
		return RetMessage;
	}

	public void setRetMessage(String retMessage) {
		RetMessage = retMessage;
	}
}
