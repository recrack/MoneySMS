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
	
	private String[] mMainCategory = { "�ĺ�", "�ְ�/���", "��Ȱ��ǰ", "�Ǻ�/�̿�", "�ǰ�/��ȭ", "����/����", "����/����",
			"������/ȸ��", "����/����", "�뵷/��Ÿ", "����/����", "ī����" };
	private String[][] mSubCategory = {
			{ "�ֽ�", "�ν�", "����", "�ܽ�", "Ŀ��/����", "��/����", "��Ÿ" },
			{ "������", "������", "�̵����", "���ͳ�", "����", "��Ÿ" },
			{ "����/����", "�ֹ�/���", "��ȭ�Ҹ�", "��Ÿ" },
			{ "�Ƿ���", "�м���ȭ", "���/��Ƽ", "��Ź������", "��Ÿ" },
			{ "�/����", "��ȭ��Ȱ", "����", "������", "���强����", "��Ÿ" },
			{ "��ϱ�", "�п�/�����", "���ƿ�ǰ", "��Ÿ" },
			{ "���߱����", "������", "�ڵ�������", "��Ÿ" },
			{ "�������", "����ȸ��", "����Ʈ", "����", "��Ÿ" },
			{ "����", "��������", "��Ÿ" },
			{ "�뵷/��Ÿ", "�뵷", "��Ÿ", "��ȯ" },
			{ "����", "����", "�ݵ�", "����", "����", "��Ÿ" },
			{ "ī����" }
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