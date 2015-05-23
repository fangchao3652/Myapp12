package com.example.Bean;

import java.util.List;

/**
 * 用户信息bean
 *
 * @author fc
 */
public class UserBean extends BaseBean {
    private String MemberId;
    private String SName;
    private String MemberPwd;
    private String ImageUrl;

    public List<String> getTopicList() {
        return TopicList;
    }

    public void setTopicList(List<String> topicList) {
        TopicList = topicList;
    }

    private List<String> TopicList;
    public String getMemberPwd() {
        return MemberPwd;
    }

    public void setMemberPwd(String memberPwd) {
        MemberPwd = memberPwd;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public String getMemberId() {
        return MemberId;
    }

    public void setMemberId(String memberId) {
        MemberId = memberId;
    }

    public String getSName() {
        return SName;
    }

    public void setSName(String SName) {
        this.SName = SName;
    }
    /*	*//**
     * 用户编号
     *//*
    private String MemberId;
	*//**
     * 用户账号
     *//*
    private String Account;
	*//**
     * 用户等级
     *//*
	private String Level;
	*//**
     * 用户账户余额
     *//*
	private String Price;
	*//**
     * 用户积分
     *//*
	private String Scores;

	private String passWord;
	*//**
     * 头像地址
     *//*
	private String ImageUrl;
	*//**
     * 邮箱是否验证
     *//*
	private String Emailverify;
	*//**
     * 手机是否验证
     *//*
	private String Mobileverify;
	*//**
     * 未读消息数
     *//*
	private String Issee;
	*//**
     * 待评价商品
     *//*
	private String Eaid;
	*//**
     * 已回复咨询数
     *//*
	private String Consultcount;
	*//**
     * 待支付订单数
     *//*
	private String Paycount;
	*//**
     * 待发货数量
     *//*
	private String NoDelivery;
	*//**
     * 待收货数量
     *//*
	private String NoReceipt;
	*//**
     * 优惠券数量
     *//*
	private String Tickcount;

	*//**
     * 购物车数量
     *//*
	private String CartCount;

	public String getCartCount() {
		return CartCount;
	}

	public void setCartCount(String cartCount) {
		CartCount = cartCount;
	}

	public String getMemberId() {
		return MemberId;
	}

	public void setMemberId(String memberId) {
		MemberId = memberId;
	}

	public String getAccount() {
		return Account;
	}

	public void setAccount(String account) {
		Account = account;
	}

	public String getLevel() {
		return Level;
	}

	public void setLevel(String level) {
		Level = level;
	}

	public String getPrice() {
		return Price;
	}

	public void setPrice(String price) {
		Price = price;
	}

	public String getScores() {
		return Scores;
	}

	public void setScores(String scores) {
		Scores = scores;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public String getImageUrl() {
		return ImageUrl;
	}

	public void setImageUrl(String imageUrl) {
		ImageUrl = imageUrl;
	}

	public String getEmailverify() {
		return Emailverify;
	}

	public void setEmailverify(String emailverify) {
		Emailverify = emailverify;
	}

	public String getMobileverify() {
		return Mobileverify;
	}

	public void setMobileverify(String mobileverify) {
		Mobileverify = mobileverify;
	}

	public String getIssee() {
		return Issee;
	}

	public void setIssee(String issee) {
		Issee = issee;
	}

	public String getEaid() {
		return Eaid;
	}

	public void setEaid(String eaid) {
		Eaid = eaid;
	}

	public String getConsultcount() {
		return Consultcount;
	}

	public void setConsultcount(String consultcount) {
		Consultcount = consultcount;
	}

	public String getPaycount() {
		return Paycount;
	}

	public void setPaycount(String paycount) {
		Paycount = paycount;
	}

	public String getNoDelivery() {
		return NoDelivery;
	}

	public void setNoDelivery(String noDelivery) {
		NoDelivery = noDelivery;
	}

	public String getNoReceipt() {
		return NoReceipt;
	}

	public void setNoReceipt(String noReceipt) {
		NoReceipt = noReceipt;
	}

	public String getTickcount() {
		return Tickcount;
	}

	public void setTickcount(String tickcount) {
		Tickcount = tickcount;
	}*/

}
