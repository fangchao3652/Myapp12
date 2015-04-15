package com.example.Bean;

/**
 * 返回单个bean信息时使用
 * 
 * @author zhangxh
 * 
 */
public class ResultSingleBean extends ResultBean {
	/**
	 * 返回的单个数据元素
	 */
	private Object RetObj;

	public Object getRetObj() {
		return RetObj;
	}

	public void setRetObj(Object retObj) {
		RetObj = retObj;
	}

}
