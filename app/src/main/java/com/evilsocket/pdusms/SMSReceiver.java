package com.evilsocket.pdusms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.util.Log;

import static android.provider.Telephony.Sms.Intents.WAP_PUSH_RECEIVED_ACTION;

/**
 * Created by evilsocket on 12/05/15.
 */
public class SMSReceiver extends BroadcastReceiver {
    private static final String TAG = "PDUSMS::SMSReceiver";

    final SmsManager _smsManager = SmsManager.getDefault();

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "onReceive( " + intent.getAction() + " )" );
        Log.i(TAG, "Expected : " + WAP_PUSH_RECEIVED_ACTION );
    }
}
