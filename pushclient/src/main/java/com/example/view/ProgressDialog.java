package com.example.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.widget.TextView;

import com.example.R;


/**
 * 加载对话框
 *
 * @author Xuzj
 */
public class ProgressDialog extends Dialog {

    private Context context = null;
    private static ProgressDialog customProgressDialog = null;

    public ProgressDialog(Context context) {
        super(context);
        this.context = context;
    }

    public ProgressDialog(Context context, int theme) {
        super(context, theme);
    }

    public static ProgressDialog createDialog(Context context) {
        customProgressDialog = new ProgressDialog(context,
                R.style.CustomProgressDialog);
        customProgressDialog.setContentView(R.layout.dialog_customview);
        customProgressDialog.getWindow().getAttributes().gravity = Gravity.CENTER;
        customProgressDialog.setCanceledOnTouchOutside(false);
        return customProgressDialog;
    }

    public void onWindowFocusChanged(boolean hasFocus) {
        if (customProgressDialog == null) {
            return;
        }
    }

    /**
     *
     * [Summary] setTitile 标题
     *
     * @param strTitle
     * @return
     *
     */
    public ProgressDialog setTitile(String strTitle) {
        customProgressDialog.setTitle(strTitle);
        return customProgressDialog;
    }

    /**
     *
     * [Summary] setMessage 提示内容
     *
     * @param strMessage
     * @return
     *
     */
    public ProgressDialog setMessage(String strMessage) {
        TextView tvMsg = (TextView) customProgressDialog
                .findViewById(R.id.dialog_text);
        if (tvMsg != null) {
            tvMsg.setText(strMessage);
        }
        return customProgressDialog;
    }
}
