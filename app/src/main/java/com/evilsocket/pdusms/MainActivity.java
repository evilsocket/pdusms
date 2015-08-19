package com.evilsocket.pdusms;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by evilsocket on 10/05/15.
 */
public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    private static final String SENT = "SMS_SENT";
    private static final String DELIVERED = "SMS_DELIVERED";

    private BroadcastReceiver _sentListener = null;
    private BroadcastReceiver _deliverListener = null;

    private PendingIntent _sentIntent = null;
    private PendingIntent _deliverIntent = null;

    private EditText _numberText;
    private Button _sendButton;
    private TextView _statusText;

    private void log( String msg ) {
        Log.i("PDUSMS", msg);
    }

    private void setStatus(String msg) {
        log( "STATUS: " + msg );
        _statusText.setText(msg);
    }

    private static String convertToHex(byte[] data) {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < data.length; i++) {
            int halfbyte = (data[i] >>> 4) & 0x0F;
            int two_halfs = 0;
            do {
                if ((0 <= halfbyte) && (halfbyte <= 9))
                    buf.append((char) ('0' + halfbyte));
                else
                    buf.append((char) ('A' + (halfbyte - 10)));
                halfbyte = data[i] & 0x0F;
            } while (two_halfs++ < 1);
        }
        return buf.toString();
    }

    public void onClick(View v) {
        /*
         * Expected "logcat -b radio" :
         *
         * D/WAP PUSH( 1287): Rx: 0a0603...
         * D/RILC    (  185): SOCKET RIL_SOCKET_1 REQUEST: SMS_ACKNOWLEDGE length:20
         * D/RILC    (  185): RequestComplete, RIL_SOCKET_1
         * E/RILC    (  185): Send Response to RIL_SOCKET_1
         * D/RILJ    ( 1287): [9277]< SMS_ACKNOWLEDGE  [SUB0]
         * V/WAP PUSH( 1287): appid found: 2:application/vnd.wap.slc
         * W/WAP PUSH( 1287): wap push manager not found!
         * V/WAP PUSH( 1287): fall back to existing handler
         * V/WAP PUSH( 1287): Delivering MMS to: com.google.android.talk com.google.android.apps.hangouts.sms.MmsWapPushDeliverReceiver
         */
        String destinationAddress = _numberText.getText().toString();

        setStatus("Sending to " + destinationAddress + " ...");

        try {
            WspPDU push = new WspPDU();

            push.setPduType(WspPDU.PDU_TYPE_PUSH);
            push.setContentType("application/vnd.wap.slc");
            push.setCharset("utf-8");
            push.setUrl("www.evilsocket.net");

            SmsManager.getDefault().sendDataMessage(
                    destinationAddress,
                    null,
                    WspPDU.WAP_PUSH_PORT,
                    push.getBytes(),
                    _sentIntent,
                    _deliverIntent
            );
        }
        catch( Exception e ){
            setStatus( "Error while sending data message: " + e );
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _numberText = (EditText)findViewById(R.id.numberText);
        _sendButton = (Button)findViewById(R.id.sendButton);
        _statusText = (TextView)findViewById(R.id.statusText);

        _statusText.setText("Input the number ...");
        _sendButton.setOnClickListener(this);

        _sentIntent    = PendingIntent.getBroadcast(this,0,new Intent(SENT),0);
        _deliverIntent = PendingIntent.getBroadcast(this,0,new Intent(DELIVERED),0);

        _sentListener = new BroadcastReceiver(){
            @Override public void onReceive( Context context, Intent intent){
                if (getResultCode() != Activity.RESULT_OK) {
                    setStatus( "Error while sending: " + getResultCode());
                }
                else {
                    setStatus( "Sent, waiting ..." );
                }
            }
        };

        _deliverListener = new BroadcastReceiver(){
            @Override public void onReceive( Context context, Intent intent){
                if (getResultCode() == Activity.RESULT_OK) {
                    Object intent_pdu = intent.getExtras().get("pdu");
                    StringBuilder sb = new StringBuilder();
                    for (byte b : (byte[])intent_pdu) {
                        sb.append(String.format("%02X", b));
                    }
                    Log.i("PDUSMS::deliverList", "Intent: " + sb.toString());
                    if(sb.substring(sb.length() - 2).equals("00")){
                        setStatus("SMS Delivered, phone is online!");
                    }
                    else{
                        setStatus( "Error while delivering... Phone is offline?\nPDU: " + sb.toString() );
                        Log.i("PDUSMS::deliverList", "Intent: " + sb.substring(sb.length() - 2));
                    }
                }
                else {
                    setStatus( "Error while delivering... Phone is offline?\n" +
                            "ResultCode: " + getResultCode() );
                }
            }
        };

        registerReceiver( _sentListener, new IntentFilter(SENT) );
        registerReceiver( _deliverListener, new IntentFilter(DELIVERED) );
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
