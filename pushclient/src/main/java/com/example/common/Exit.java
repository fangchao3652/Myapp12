package com.example.common;

import android.os.Handler;
import android.os.HandlerThread;

/**
 * 再按一次退出
 *
 * @author xiaohao
 */
public class Exit {
    private boolean isExit = false;

    private Runnable task = new Runnable() {

        @Override
        public void run() {

            isExit = false;

        }

    };

    public void doExitInOneSecond() {

        isExit = true;

        HandlerThread thread = new HandlerThread("doExit");

        thread.start();

        new Handler(thread.getLooper()).postDelayed(task, 2000);

    }

    public boolean isExit() {

        return isExit;

    }

    public void setExit(boolean isExit) {

        this.isExit = isExit;

    }

}
