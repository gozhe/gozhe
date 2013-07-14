package org.gozhe.android.cgt.activity;

import org.gozhe.android.R;
import org.gozhe.android.cgt.utils.Utility;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.DialogInterface.OnCancelListener;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {

	
	private Button loginBtn,resetBtn;
	private ProgressDialog pd;
	private EditText usernameText,passwordText;
	private CheckBox cb;
	private String result;
	
	private Handler handler = new Handler() {
		@Override
		// ������Ϣ���ͳ�����ʱ���ִ��Handler���������
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			// ֻҪִ�е�����͹ر�progressdialog
			if (pd != null) {
				pd.dismiss();
			}
			switch (msg.what) {
			case 1:
				Toast.makeText(LoginActivity.this, "��ǰ���粻���ã�",Toast.LENGTH_SHORT).show();
				break;
			case 2:
				Toast.makeText(LoginActivity.this, result, Toast.LENGTH_LONG).show();
				break;
			case 3:
				Toast.makeText(LoginActivity.this, "���������ļ����ִ�������SD���Ƿ���ã�",Toast.LENGTH_SHORT).show();
				break;
			case 4:
				Toast.makeText(LoginActivity.this, "�û��������벻��Ϊ��",Toast.LENGTH_SHORT).show();
				break;
			default:
				break;
			}
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);   
		
		setContentView(R.layout.login);
		
		usernameText = (EditText) findViewById(R.id.login_edit_account);
		passwordText = (EditText) findViewById(R.id.login_edit_pwd);
		
		loginBtn = (Button) findViewById(R.id.login_btn_login);
		loginBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				loginBtn.setClickable(false);
				pd = ProgressDialog.show(LoginActivity.this, "���ڵ�¼", "���Ժ򡭡�");
				pd.setCancelable(true);
				final Thread thread = new Thread() {
					@Override
					public void run() {
						loginMethod();
					}
				};
				thread.start();
				pd.setOnCancelListener(new OnCancelListener() {
					@Override
					public void onCancel(DialogInterface dialog) {
						// TODO Auto-generated method stub
						loginBtn.setClickable(true);
					}
				});
			}
		});
		
		resetBtn = (Button) findViewById(R.id.login_btn_reset);
		resetBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				usernameText.setText("");
				passwordText.setText("");
			}
		});
		
		cb = (CheckBox) findViewById(R.id.login_cb_savepwd);
		
		SharedPreferences loginSave = getSharedPreferences("LoginSave", 0);
		usernameText.setText(loginSave.getString("UserName", ""));
		passwordText.setText(loginSave.getString("Password", ""));
		cb.setChecked(loginSave.getBoolean("CheckBox", true));
	}
	
	private void loginMethod() {
		String username = usernameText.getText().toString().trim();
		String password = passwordText.getText().toString().trim();
		
		if (Utility.isSdcardAvailable()) {
			if (username.equals("") || password.equals("")) {
				loginBtn.setClickable(true);
				handler.sendEmptyMessage(4);
			} else if (username.equals("root") && password.endsWith("root")) {
				// �����û������������֤
				Intent main_newIntent = new Intent();
				main_newIntent.setClass(LoginActivity.this, MainActivity.class);
				handler.sendEmptyMessage(0);
				loginBtn.setClickable(true);
				startActivity(main_newIntent);
			} else {
				//result = Utility.Login(this, username, password);
				// result="��¼�ɹ�";
				if ("��¼�ɹ�".equals(result)) {
					Editor savePwd = getSharedPreferences("LoginSave", 0).edit();
					if (cb.isChecked()) {
						savePwd.putString("UserName", username);
						savePwd.putString("Password", password);
						savePwd.putBoolean("CheckBox", true);
						savePwd.commit();
					} else {
						savePwd.putString("UserName", username);
						savePwd.putString("Password", "");
						savePwd.putBoolean("CheckBox", false);
						savePwd.commit();
					}
					Intent main_newIntent = new Intent();
					main_newIntent.setClass(LoginActivity.this,MainActivity.class);

					handler.sendEmptyMessage(0);
					loginBtn.setClickable(true);
					startActivity(main_newIntent);
				} else if (result.equals("java.net.SocketException: Network unreachable")) {
					loginBtn.setClickable(true);
					handler.sendEmptyMessage(1);
				} else {
					loginBtn.setClickable(true);
					handler.sendEmptyMessage(2);
				}
			}
		}
	}

}
