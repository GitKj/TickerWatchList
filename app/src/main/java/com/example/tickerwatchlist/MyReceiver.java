package com.example.tickerwatchlist;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {

    public static String SMS;
    TickerListFragment tickerListFragment;

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.

        Intent myIntent = new Intent(context, MainActivity.class);
        //Toast.makeText(context, "Outside", Toast.LENGTH_SHORT).show();
        Bundle bundle = intent.getExtras();
        if (intent.getAction().equals(Telephony.Sms.Intents.SMS_RECEIVED_ACTION))
        {
            if (bundle != null) {

                //Toast.makeText(context, "Inside", Toast.LENGTH_SHORT).show();
                Object[] objects = (Object[]) bundle.get("pdus");
                String format = bundle.getString("format").toString();

                SmsMessage[] messages = new SmsMessage[objects.length];
                for (int i = 0; i < objects.length; i++) {
                    messages[i] = SmsMessage.createFromPdu((byte[]) objects[i], format);

                }
                SMS = messages[0].getMessageBody();
                myIntent.putExtra("sms", SMS);
                //Toast.makeText(context, messages[0].getMessageBody(), Toast.LENGTH_SHORT).show();
            }
        }

        myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(myIntent);
    }
}