package com.raegon.moneysms;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class DetailActivity extends Activity implements OnClickListener {
	private EditText mDetail;
	private Button mSubmit;
	private String mAmount;
	private String mCategory;
	private String mSubCategory;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        setContentView(R.layout.detail_activity);
        
		Intent intent = getIntent();
		mAmount = intent.getStringExtra("amount");
		mCategory = intent.getStringExtra("category");
		mSubCategory = intent.getStringExtra("subCategory");
		
		mDetail = (EditText) findViewById(R.id.detail);
        mSubmit = (Button) findViewById(R.id.button_submit);
        mSubmit.setOnClickListener(this);
        
    }

	@Override
	public void onClick(View v) {
		
		String phoneNumber = "#11117";
		String detail = mDetail.getText().toString();
		
		if(detail.equals(""))
			detail = mSubCategory;
		
		String message = detail+" "+mAmount+"¿ø "+"Çö±Ý "+mCategory+">"+mSubCategory;
        sendSMS(phoneNumber, message);  
        
	}
	
    private void sendSMS(String phoneNumber, final String message)
    {        
        String SENT = "SMS_SENT";
        String DELIVERED = "SMS_DELIVERED";
 
        PendingIntent sentPI = PendingIntent.getBroadcast(this, 0,
            new Intent(SENT), 0);
 
        PendingIntent deliveredPI = PendingIntent.getBroadcast(this, 0,
            new Intent(DELIVERED), 0);

        //---when the SMS has been sent---
        registerReceiver(new BroadcastReceiver(){
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                switch (getResultCode())
                {
                    case Activity.RESULT_OK:
                        Toast.makeText(getBaseContext(), message, 
                                Toast.LENGTH_SHORT).show();
                        finish();
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        Toast.makeText(getBaseContext(), "Generic failure", 
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        Toast.makeText(getBaseContext(), "No service", 
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        Toast.makeText(getBaseContext(), "Null PDU", 
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        Toast.makeText(getBaseContext(), "Radio off", 
                                Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter(SENT));

        //---when the SMS has been delivered---
        registerReceiver(new BroadcastReceiver(){
        	@Override
            public void onReceive(Context arg0, Intent arg1) {
                switch (getResultCode())
                {
                    case Activity.RESULT_OK:
                        Toast.makeText(getBaseContext(), "SMS delivered", 
                                Toast.LENGTH_SHORT).show();
                        break;
                    case Activity.RESULT_CANCELED:
                        Toast.makeText(getBaseContext(), "SMS not delivered", 
                                Toast.LENGTH_SHORT).show();
                        break;                        
                }
            }
        }, new IntentFilter(DELIVERED)); 
        
        SmsManager sms = SmsManager.getDefault(); 
        sms.sendTextMessage(phoneNumber, null, message, sentPI, deliveredPI);
    }
}
