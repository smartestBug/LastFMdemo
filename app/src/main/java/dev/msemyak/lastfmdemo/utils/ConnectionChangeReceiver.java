package dev.msemyak.lastfmdemo.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ConnectionChangeReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        RxBus.getInstance().post(new NetworkChangeEvent());
    }
}
