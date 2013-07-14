package org.gozhe.android.cgt.activity;


import org.gozhe.android.R;
import org.gozhe.android.cgt.utils.Utility;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.widget.Toast;

public class StartingActivity  extends Activity{

	//�ӳ�3�� 
    private static final long SPLASH_DELAY_MILLIS = 5000;
	// ʹ��Handler��postDelayed������3���ִ����ת��	

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);   
		
		setContentView(R.layout.starting);
				
		new Handler().postDelayed(new Runnable(){
			@Override
			public void run() {
				// TODO Auto-generated method stub
				checkState();
				
				goLoginPage();
			}
		},SPLASH_DELAY_MILLIS);
	}
	
	private void checkState(){
		boolean network = Utility.isNetworkAvaiable(StartingActivity.this);
		boolean sdcard = Utility.isSdcardAvailable();
		if(!network){
			showInfo("������񲻿���Ŷ!");
		}
		if(!sdcard){
			showInfo("SD������쳣!Ϊ��֤�������ʹ��,�����sdcard!");
		}
	}
	
	private void showInfo(String infos){
		Toast.makeText(this, infos, Toast.LENGTH_SHORT).show();
	}
	
	private void goLoginPage() {
        Intent intent = new Intent(StartingActivity.this, LoginActivity.class);
        StartingActivity.this.startActivity(intent);
        StartingActivity.this.finish();
    }
}
