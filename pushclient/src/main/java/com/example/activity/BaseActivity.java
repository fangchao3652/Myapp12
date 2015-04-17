package com.example.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.inputmethod.InputMethodManager;

import com.example.R;


/**
 * Activity基类没有actionbar
 *
 * @author fc
 */
public class BaseActivity extends FragmentActivity {

    /**
     * 当前activity的全路径标识
     */
    protected String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.base_slide_right_in, R.anim.base_slide_right_out);
    }

    public void onResume() {
        super.onResume();
    }

    public void onPause() {
        super.onPause();
    }

    /**
     * 收起软键盘
     */
    public void collapseSoftInputMethod() {
        InputMethodManager imm = (InputMethodManager) getSystemService(this.INPUT_METHOD_SERVICE);
        if (imm != null) {
            if (this.getCurrentFocus() != null
                    && this.getCurrentFocus().getWindowToken() != null) {
                imm.hideSoftInputFromWindow(this.getCurrentFocus()
                        .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }
}
