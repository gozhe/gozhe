package org.gozhe.android.cgt.activity.upload;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.gozhe.android.R;
import org.gozhe.android.cgt.utils.ImageCacheUtil;
import org.gozhe.android.cgt.utils.Utility;
import org.gozhe.android.cgt.view.CameraAdapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.GridView;

public class AddQuestionBActivity extends Activity{

	
	private GridView gridview;
	private Button btn_takephoto,btn_openalbum;
	
	private List<String> photos;
	private CameraAdapter adapter;
	
	String photoPath,savePath;
	  
    /** 
     * �򿪱�������requestcode. 
     */  
    public static final int OPEN_PHOTO_REQUESTCODE =  0x1; 
    
    /** 
     * ���յ�requestcode. 
     */  
    public static final int TAKE_PHOTO_REQUESTCODE =  0x2; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.add_question_b);
		
		photos = new ArrayList<String>();
	    photoPath= Utility.getUpDataPath();
		
		gridview=(GridView)findViewById(R.id.question_photo_gridview);
		btn_takephoto=(Button)findViewById(R.id.question_btn_takephoto);
		btn_openalbum=(Button)findViewById(R.id.question_btn_openalbum);

		loadData();
		
		adapter = new CameraAdapter(this, photos);
		if (photos != null) {
			gridview.setAdapter(adapter);
		}
		
		btn_takephoto.setOnClickListener(this.take_photoListener);
		btn_openalbum.setOnClickListener(this.open_albumListener);
	}
	
	private OnClickListener open_albumListener= new OnClickListener(){

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			openPhotos();
		}
		
	};
	
	private OnClickListener take_photoListener= new OnClickListener(){

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(!Utility.isSdcardAvailable()){
				//TODO
			}
			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			// ĳЩ�ֻ�ֱ�ӷ���,Uri uri = data.getData()��Ϊnull,ԭ�򣬵�������ͷǰ��ָ����Ƭ·��
			savePath = buildPhotoName();
			
			// ָ��·��
			intent.putExtra(MediaStore.EXTRA_OUTPUT,
								Uri.fromFile(new File(savePath)));
			// ָ������
			// intent.putExtra("outputFormat",Bitmap.CompressFormat.PNG);
			// ������
			// intent.putExtra(MediaStore.EXTRA_SCREEN_ORIENTATION,
			// Configuration.ORIENTATION_LANDSCAPE);
			startActivityForResult(intent, TAKE_PHOTO_REQUESTCODE);
		}
		
	};
	

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		switch(requestCode){
		case OPEN_PHOTO_REQUESTCODE:  
			
			//��������������OptionsΪnullʱ�򣬾���Ĭ��decode�����oomŶ.   
            //Bitmap bm = ImageCacheUtil.decode(null, null,ImageCacheDemoActivity.this,data.getData(), null);   
            
			//���������������Ͳ���oom.��˿�Ǿ������������.   
//            Bitmap bm = ImageCacheUtil.getResizedBitmap(null, null,   
//                    AddQuestionBActivity.this, data.getData(), target, false);  
            
			//��ȡͼƬ��·��
			Uri originalUri = data.getData(); //���ͼƬ��uri
			
            String[] proj = {MediaStore.Images.Media.DATA};
            Cursor cursor = managedQuery(originalUri, proj, null, null, null);
            
            //���Ҹ������ ����ǻ���û�ѡ���ͼƬ������ֵ
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            
            //�����������ͷ ���������Ҫ����С�ĺ���������Խ��
            cursor.moveToFirst();
            
            //����������ֵ��ȡͼƬ·��www.2cto.com
            String filePath = cursor.getString(column_index);
            
            photos.add(filePath);
            
            adapter.notifyDataSetChanged();
            
            //mImageView.setImageBitmap(bm);  
			
			break;
		case TAKE_PHOTO_REQUESTCODE:
			
			photos.add(savePath);
			//OOM����
			adapter.notifyDataSetChanged();
			break;
			
		}
		super.onActivityResult(requestCode, resultCode, data);
	}


	
	private void openPhotos(){
		 Intent intent = new Intent(Intent.ACTION_PICK, null);  
	     intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");  
	     startActivityForResult(intent, OPEN_PHOTO_REQUESTCODE); 
	}
	
	private void loadData(){
		File path= new File(photoPath);
		if(path.isDirectory()){
			File[] files = path.listFiles();
			for(int i=0;i<files.length;i++){
				String filepath = files[i].getAbsolutePath();
				
				photos.add(filepath);
			}
		}
	}
	
	@SuppressLint("SimpleDateFormat")
	private String buildPhotoName() {
		Date now = new Date();
		SimpleDateFormat formater = new SimpleDateFormat("yyyyMMdd_HHmm");
		return photoPath + "/"+formater.format(now) + ".jpg";
	}
	
}
