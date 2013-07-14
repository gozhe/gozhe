package org.gozhe.android.cgt.activity;

import org.gozhe.android.R;
import org.gozhe.android.cgt.activity.download.MyHistoryActivity;
import org.gozhe.android.cgt.activity.download.MyTaskActivity;
import org.gozhe.android.cgt.activity.gps.MapActivity;
import org.gozhe.android.cgt.activity.notice.NoticeActivity;
import org.gozhe.android.cgt.activity.upload.UploadActivity;
import org.gozhe.android.cgt.view.ImageAdapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

public class MainActivity extends Activity {

	private GridView gridView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		//requestWindowFeature(Window.FEATURE_NO_TITLE);   
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		
		setContentView(R.layout.main);
		
		//titlebarΪ�Լ��������Ĳ���
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.custom_title_b); 

		gridView = (GridView) findViewById(R.id.main_gridview);

		Integer[] images = { R.drawable.m1, R.drawable.m2, R.drawable.m3,
				R.drawable.m4, R.drawable.m5, R.drawable.m6, R.drawable.m7,
				R.drawable.m8, R.drawable.m9, R.drawable.m10, R.drawable.m11,
				R.drawable.m12 };

		String[] texts = { "�����ϱ�", "�ҵ�����", "��ʷ��¼", "������ʾ", "GPS��ͼ", "�����ɼ�",
				"��������", "����Ⱥ��", "����ͬ��", "ϵͳ����", "ʹ�ð���", "ϵͳǩ��" };

		gridView.setAdapter(new ImageAdapter(this, images, texts));

		gridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				switch (position) {
				case 0:
					gotoIntent(UploadActivity.class);
					break;
				case 1:
					gotoIntent(MyTaskActivity.class);
					break;
				case 2:
					gotoIntent(MyHistoryActivity.class);
					break;
				case 3:
					gotoIntent(NoticeActivity.class);
					break;
				case 4:
					gotoIntent(MapActivity.class);
					break;
				case 5:

					break;
				case 6:

					break;
				case 7:

					break;
				case 8:

					break;
				}
			}
		});
	}

	private void gotoIntent(Class<?> cs) {
		Intent intent = new Intent();
		intent.setClass(MainActivity.this, cs);
		startActivity(intent);
	}
}
