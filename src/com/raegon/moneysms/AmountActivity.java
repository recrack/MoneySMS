package com.raegon.moneysms;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class AmountActivity extends Activity {
	private Button[] mNumbers;
	private Button mBack;
	private Button mClear;
	private Button mNext;
	private EditText mAmount;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND,
//                WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
         
        setContentView(R.layout.amount_activity);
        
        mNumbers = new Button[10];
        for(int i=0; i<10; i++) {
        	int resID = getResources().getIdentifier("numpad"+i, "id", this.getPackageName());  
        	mNumbers[i] = (Button) findViewById(resID);
        	mNumbers[i].setOnClickListener(mNumberListener);
        }
        
        mAmount = (EditText) findViewById(R.id.amount);
        mBack	= (Button) findViewById(R.id.button_backspace);
        mClear	= (Button) findViewById(R.id.button_clear);
        mNext	= (Button) findViewById(R.id.button_amount_next);
        
        mAmount.setFocusable(false);
        mBack.setOnClickListener(mNumberListener);
        mClear.setOnClickListener(mNumberListener);
        
        mNext.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
            	Intent intent = new Intent(AmountActivity.this, CategoryActivity.class);
            	intent.putExtra("amount", mAmount.getText().toString()); 
            	startActivity(intent);
            	
            	finish();
            	overridePendingTransition(0,0);
            }
        });
    }
    
    OnClickListener mNumberListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Button btn = (Button) v;
			String text = mAmount.getText().toString();
			String amount = "0";
			
			if (btn == mBack && text.length() > 1) {
				amount = text.substring(0, text.length()-1);
			} else {
				for(int i=0; i<10; i++) {
					if(btn == mNumbers[i])
						amount = text + i;
				}
			}
			
			if(amount.startsWith("0") && amount.length() > 1)
				amount = amount.substring(1);
			mAmount.setText(amount);
		}   	
    };
}