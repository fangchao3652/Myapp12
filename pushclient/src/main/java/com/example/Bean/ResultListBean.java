package com.example.Bean;

import java.util.List;

/**
 * 返回bean集合时 使用
 * 
 * @author zhangxh
 * 
 */
public class ResultListBean extends ResultBean {
	/**
	 * 返回的列表集合
	 */
	private List<BaseBean> listBean;

	public List<BaseBean> getListBean() {
		return listBean;
	}

	public void setListBean(List<BaseBean> listBean) {
		this.listBean = listBean;
	}

}
