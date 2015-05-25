package com.example.view;

import android.content.Context;
import android.widget.SeekBar;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.R;
import com.example.common.SharedPreferencesUtils;

/**测试用
 * Created by supumall on 2015/5/23.
 */
public class Fc_DialogWithWheel {

    public  static void showCustomView(Context context) {



        MaterialDialog dialog = new MaterialDialog.Builder(context)
                .title("安静时段")
                .customView(R.layout.dialog_wheelselecter, true)
                .positiveText(R.string.dialog_ok)
                .negativeText(android.R.string.cancel)
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        }

                    @Override
                    public void onNegative(MaterialDialog dialog) {
                    }
                }).build();

      //  tv_time = (TextView) dialog.getCustomView().findViewById(R.id.time_txt);


       // startseekbar = (SeekBar) dialog.getCustomView().findViewById(R.id.seekbar_starttime);//开始时间seekbar

       /* startseekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                timestart = progress / 10;
                starttime.setText(String.format(getResources().getString(R.string.starttime), timestart));

                SetTime(tv_time, timestart, timelast);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });*/


        dialog.show();

    }
}
