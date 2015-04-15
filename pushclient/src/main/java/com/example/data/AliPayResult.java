package com.example.data;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cc.android.supu.bean.AliPayResultBean;

/**
 * 支付宝回传数据处理
 * 
 * @author zhangxinhao
 * 
 */
public class AliPayResult {
	static String str = "resultStatus={9000};memo={};result={partner=\"2088201363678709\"&seller_id=\"2088201363678709\"&out_trade_no=\"7078994\"&subject=\"速普商城订单No:7078994\"&body=\"0105041067 MOONY 尤妮佳 原装进口婴儿纸尿裤 L54片 *1\"&total_fee=\"0.01\"&notify_url=\"http%3a%2f%2fwww.supuy.com%2fbank%2fAlipay_MobileNotify_V2.aspx\"&service=\"mobile.securitypay.pay\"&payment_type=\"1\"&_input_charset=\"utf-8\"&it_b_pay=\"60m\"&success=\"true\"&sign_type=\"RSA\"&sign=\"WxHHLaeqXcoyViBJyQV1a2+luhry9umGDLnjSX4HrcH6Q6Un75KJwIT1QAWnHcXrVOPcAT9xD8Sk923SjUkQRA0VDh29IPNA26FV+aaxV5yMyPP/kBF+oKiQy01LhQswTZDTF7k21SR2PNEdjXFnHzQJvzjthILbgJp7aEASwLM=\"}";

	public static void main(String[] args) {
		AliPayResult result = new AliPayResult(str);
		result.getResult();
	}

	private static final Map<String, String> sResultStatus;

	private String mResult;

	String resultStatus = null;
	String memo = null;
	String result = null;
	boolean isSignOk = false;

	public AliPayResult(String result) {
		this.mResult = result;
	}

	static {
		sResultStatus = new HashMap<String, String>();
		sResultStatus.put("{9000}", "支付成功");
		sResultStatus.put("{4000}", "系统异常");
		sResultStatus.put("{4001}", "订单参数错误");
		sResultStatus.put("{6001}", "您已取消了本次订单的支付");
		sResultStatus.put("{6002}", "网络连接异常");
	}

	/**
	 * 获取支付宝返回的支付状态信息
	 * 
	 * @return
	 */
	public String getResult() {
		AliPayResultBean bean = Json2Bean(string2JSON(mResult, ";"));
		return sResultStatus.get(bean.getResultStatus());
	}

	private AliPayResultBean Json2Bean(JSONObject json) {
		Gson gson = new Gson();
		return gson.fromJson(json.toString(), AliPayResultBean.class);
	}

	public JSONObject string2JSON(String src, String split) {
		JSONObject json = new JSONObject();

		try {
			String[] arr = src.split(split);
			for (int i = 0; i < arr.length; i++) {
				String[] arrKey = arr[i].split("=");
				json.put(arrKey[0], arr[i].substring(arrKey[0].length() + 1));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return json;
	}

	private String getContent(String src, String startTag, String endTag) {
		String content = src;
		int start = src.indexOf(startTag);
		start += startTag.length();

		try {
			if (endTag != null) {
				int end = src.indexOf(endTag);
				content = src.substring(start, end);
			} else {
				content = src.substring(start);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return content;
	}
}
