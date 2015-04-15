package com.dtr.zxing.activity;

import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;

import com.dtr.zxing.camera.CameraManager;
import com.dtr.zxing.utils.CaptureActivityHandler;
import com.google.zxing.Result;

/**
 * 二维码扫描
 * @author xuzj
 */
public class CaptureBaseActivity extends ActionBarActivity {

	protected ActionBar mActionBar;

	protected Rect mCropRect = null;
	protected CameraManager cameraManager;
	protected CaptureActivityHandler handler;

	public Rect getCropRect() {
		return mCropRect;
	}

	public Handler getHandler() {
		return handler;
	}

	public CameraManager getCameraManager() {
		return cameraManager;
	}

	public void handleDecode(Result rawResult, Bundle bundle) {
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initActionBar();
	}

	/**
	 * 初始化actionBar
	 */
	void initActionBar() {
		mActionBar = getSupportActionBar();
		mActionBar.setTitle("");// 设置页面标题
		// 设置是否显示logo
		mActionBar.setDisplayShowHomeEnabled(true);
		// 设置是否显示返回按钮
		mActionBar.setDisplayHomeAsUpEnabled(true);
		// 设置是否显示title
		mActionBar.setDisplayShowTitleEnabled(true);
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
