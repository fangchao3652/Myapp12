package com.example.common;

import android.app.Activity;
import android.content.Context;

import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners.SocializeClientListener;
import com.umeng.socialize.controller.listener.SocializeListeners.UMShareBoardListener;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.sso.SinaSsoHandler;
import com.umeng.socialize.sso.TencentWBSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;
import com.umeng.socialize.weixin.media.CircleShareContent;
import com.umeng.socialize.weixin.media.WeiXinShareContent;

/**
 * 友盟分享
 *
 * @author Zxh
 */
public class UmengShare {
    // 配置微信分享相关属性
    static String wxAppID = "wx7da209a001e4798a";
    static String wxAppSecret = "482fafa6d62e9b0330629d8b182ea204";

    public static UMSocialService mController = UMServiceFactory
            .getUMSocialService("com.umeng.share");
    static boolean isShow = false;

    /**
     * 打开分享面板
     *
     * @param context   当前activity
     * @param wxTitle   微信标题
     * @param wxContent 微信内容
     * @param url       跳转的url
     * @param content   分享的文字内容,新浪微博和腾讯微博需要直接包含url信息
     * @param imageUrl  展示的图片地址 url
     */
    public static void share(Activity context, String wxTitle,
                             String wxContent, String url, String content, String imageUrl) {
        // 配置需要分享的第三方平台
        mController.getConfig().setPlatforms(SHARE_MEDIA.WEIXIN,
                SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.SINA,
                SHARE_MEDIA.TENCENT);
        // 设置新浪SSO handler
        mController.getConfig().setSsoHandler(new SinaSsoHandler());
        // 设置腾讯微博SSO handler
        mController.getConfig().setSsoHandler(new TencentWBSsoHandler());

        // 添加微信平台
        UMWXHandler wxHandler = new UMWXHandler(context, wxAppID, wxAppSecret);
        wxHandler.addToSocialSDK();
        // 添加微信朋友圈
        UMWXHandler wxCircleHandler = new UMWXHandler(context, wxAppID,
                wxAppSecret);
        wxCircleHandler.setToCircle(true);
        wxCircleHandler.addToSocialSDK();

        // 设置微信好友分享内容
        WeiXinShareContent weixinContent = new WeiXinShareContent();
        // 设置分享文字
        weixinContent.setShareContent(wxContent);
        // 设置title
        weixinContent.setTitle(wxTitle);
        // 设置分享内容跳转URL
        weixinContent.setTargetUrl(url);
        // 设置分享图片
        weixinContent.setShareImage(new UMImage(context, imageUrl));
        mController.setShareMedia(weixinContent);
        // 设置微信朋友圈分享内容
        CircleShareContent circleMedia = new CircleShareContent();
        circleMedia.setShareContent(wxContent);
        // 设置朋友圈title
        circleMedia.setTitle(wxTitle);
        circleMedia.setShareImage(new UMImage(context, imageUrl));
        circleMedia.setTargetUrl(url);
        mController.setShareMedia(circleMedia);

        // 设置共同的分享内容
        mController.setShareContent(content);
        mController.setShareMedia(new UMImage(context, imageUrl));

        // 设置分享面板打开和关闭时的监听
        mController.setShareBoardListener(new UMShareBoardListener() {

            @Override
            public void onShow() {
                isShow = true;
            }

            @Override
            public void onDismiss() {
                isShow = false;
            }
        });
        // 打开分享选择面板
        mController.openShare(context, false);
    }

    public static void dismissShareBoard() {
        mController.dismissShareBoard();
    }

    public static boolean isShow() {
        return isShow;
    }

    public static UMSocialService getUMSocialService() {
        return mController;
    }

    private static boolean isSuccess;

    /**
     * 清除授权
     *
     * @param context
     * @param platform 标识
     * @return
     */
    public static boolean clearOauth(Context context, SHARE_MEDIA platform) {
        isSuccess = false;
        mController.deleteOauth(context, platform,
                new SocializeClientListener() {

                    public void onStart() {

                    }

                    public void onComplete(int status, SocializeEntity entity) {
                        if (status == 200) {
                            isSuccess = true;
                        } else {
                            isSuccess = false;
                        }
                    }
                });
        return isSuccess;
    }
}
