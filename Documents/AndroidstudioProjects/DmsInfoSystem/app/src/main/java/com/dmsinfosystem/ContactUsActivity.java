package com.dmsinfosystem;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by root on 12/12/14.
 */
public class ContactUsActivity extends Activity {
    private static Button contactUs,callUs;
    private EditText editText;
    private static final String TAG = "Contact Us";

    String NAME,MAIL,NUMBER,SUBJECT,BODY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_us);

        //Contact Us Button
        contactUs = (Button) findViewById(R.id.dropAMail);
        callUs = (Button) findViewById(R.id.makeACall);

        editText = (EditText) findViewById(R.id.contactName);
        NAME = editText.getText().toString();

        editText = (EditText) findViewById(R.id.contactMail);
        MAIL = editText.getText().toString();

        editText = (EditText) findViewById(R.id.contactNumber);
        NUMBER = editText.getText().toString();

        editText = (EditText) findViewById(R.id.contactSubject);
        SUBJECT = editText.getText().toString();

        BODY = "Hi\n, My Name is "+ NAME + "and my contact number is  " + NUMBER + ".I would Like to know the Details on\n ";

        contactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent contact = new Intent(Intent.ACTION_SEND);

                contact.setType("message/rfc822");
                contact.putExtra(Intent.EXTRA_EMAIL, new String[]{"contact@dmsinfosystem.com"});
                contact.putExtra(Intent.EXTRA_SUBJECT, SUBJECT);
                contact.putExtra(Intent.EXTRA_TEXT, BODY);


                try {
                    startActivity(Intent.createChooser(contact, "Send mail"));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(ContactUsActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        callUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String phone = "tel:+917583837766" ;
                Intent phoneCall = new Intent(Intent.ACTION_CALL, Uri.parse(phone));

                EndCallListener callListener = new EndCallListener();
                TelephonyManager mTM = (TelephonyManager) getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
                mTM.listen(callListener, PhoneStateListener.LISTEN_CALL_STATE);

                startActivity(phoneCall);


            }
        });

        getActionBar().setDisplayHomeAsUpEnabled(true);

    }

    private class EndCallListener extends PhoneStateListener {
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            if(TelephonyManager.CALL_STATE_RINGING == state) {
                Log.i(TAG, "RINGING, number: " + incomingNumber);
            }
            if(TelephonyManager.CALL_STATE_OFFHOOK == state) {
                //wait for phone to go offhook (probably set a boolean flag) so you know your app initiated the call.
                Log.i(TAG, "OFFHOOK");
            }
            if(TelephonyManager.CALL_STATE_IDLE == state) {
                //when this state occurs, and your flag is set, restart your app
                Log.i(TAG, "IDLE");
            }
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
