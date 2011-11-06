package com.raegon.moneysms;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

public class CategoryActivity extends Activity implements OnClickListener{
	private String mAmount;
	private String mCategory;
	
	TableLayout mTable;
	FrameLayout.LayoutParams mTableParams;
	TableLayout.LayoutParams mRowParams;
	TableRow.LayoutParams	 mBtnParams;
	
	private String[] mMainCategory = { "식비", "주거/통신", "생활용품", "의복/미용", "건강/문화", "교육/육아", "교통/차량",
			"경조사/회비", "세금/이자", "용돈/기타", "저축/보험", "카드대금" };
	private String[][] mSubCategory = {
			{ "주식", "부식", "간식", "외식", "커피/음료", "술/유흥", "기타" },
			{ "관리비", "공과금", "이동통신", "인터넷", "월세", "기타" },
			{ "가구/가전", "주방/욕실", "잡화소모", "기타" },
			{ "의류비", "패션잡화", "헤어/뷰티", "세탁수선비", "기타" },
			{ "운동/레져", "문화생활", "여행", "병원비", "보장성보험", "기타" },
			{ "등록금", "학원/교재비", "육아용품", "기타" },
			{ "대중교통비", "주유비", "자동차보험", "기타" },
			{ "경조사비", "모임회비", "데이트", "선물", "기타" },
			{ "세금", "대출이자", "기타" },
			{ "용돈/기타", "용돈", "기타", "상환" },
			{ "예금", "적금", "펀드", "보험", "투자", "기타" },
			{ "카드대금" }
	};
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// Remove title bar
		requestWindowFeature(Window.FEATURE_NO_TITLE);
	
		setContentView(R.layout.category_activity);
		
		mTable = (TableLayout) findViewById(R.id.tableLayout); 

		mRowParams = new TableLayout.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT, 0.0f);
		mBtnParams = new TableRow.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.FILL_PARENT, 0.0f);
		
		mTable.setStretchAllColumns(true);
		
		// Get Amount
		Intent intent = getIntent();
		mAmount = intent.getStringExtra("amount");
		mCategory = intent.getStringExtra("category");
		
		// Create Buttons
		if(mCategory == null) {
			createButtons(mMainCategory);
		} else {
			for(int i=0; i<mMainCategory.length; i++) {
				if(mMainCategory[i].equals(mCategory)) {
					createButtons(mSubCategory[i]);
					break;
				}
			}
		}
	}

	private void createButtons(String[] array) {
		int rowCount = (array.length / 2) + 1;
		int index = 0;

		for (int i = 0; i < rowCount; i++) {
			TableRow row = new TableRow(this);
			for (int j = 0; j < 2; j++) {
				if (index < array.length) {
					String text = array[index++];
					Button btn = new Button(this);
					btn.setWidth(150);
					btn.setText(text);
					btn.setOnClickListener(this);
					row.addView(btn, mBtnParams);
				}
			}
			mTable.addView(row, mRowParams);
		}
	}

	@Override
	public void onClick(View v) {
		Button btn = (Button) v; 
		if(mCategory == null) {
			Intent intent = new Intent(CategoryActivity.this, CategoryActivity.class);
        	intent.putExtra("amount", mAmount);
        	intent.putExtra("category", btn.getText());
        	startActivity(intent);
        	finish();
		} else {
			Intent intent = new Intent(CategoryActivity.this, DetailActivity.class);
        	intent.putExtra("amount", mAmount);
        	intent.putExtra("category", mCategory);
        	intent.putExtra("subCategory", btn.getText());
        	startActivity(intent);
        	finish();
		}
	}
}