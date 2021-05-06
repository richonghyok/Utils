package kp.chonghyok.listener;

import android.view.View;

public abstract class OnDelayClickListener implements View.OnClickListener {
    final static int MIN_DELAY = 1000;
    private long lastClickTime;

    public abstract void onDelayClick(View v);

    @Override
    public void onClick(View v) {
        long curClickTime = System.currentTimeMillis();
        if (curClickTime - lastClickTime >= MIN_DELAY) {
            lastClickTime = curClickTime;
            onDelayClick(v);
        }
    }
}