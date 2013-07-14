package org.gozhe.android.cgt.view;

import java.util.List;

import org.gozhe.android.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

public class GalleryImageAdapter extends BaseAdapter {

	private Context context;
	int mGalleryItemBackground;
	private List<String> list;

	public GalleryImageAdapter(Context context, List<String> list) {
		try {
			this.context = context;
			this.list = list;
			// ʹ��res/values/attr.xml�е�<declare-styleable>�����Gallery����
			TypedArray a = context.obtainStyledAttributes(R.styleable.Gallery);
			// //ȡ��Gallery���Ե�Index id
			mGalleryItemBackground = a.getResourceId(
					R.styleable.Gallery_android_galleryItemBackground,
					Color.GREEN);
			// �ö����styleable�����ܹ�����ʹ��
			a.recycle();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub

		return this.list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		if (list != null && list.size() > 0) {
			return this.list.get(position);
		}
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@SuppressWarnings("deprecation")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		try {
			ImageView v = new ImageView(this.context);
			// �趨ͼƬ��ImageView����
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inSampleSize = 10;
			Bitmap bm = BitmapFactory.decodeFile(list.get(position).toString(),
					options);
			v.setImageBitmap(bm);
			/* v.setImageResource(this.images[position]); */
			// �����趨ͼƬ�Ŀ��
			v.setScaleType(ImageView.ScaleType.FIT_XY);
			// �����趨layout�Ŀ��
			v.setLayoutParams(new Gallery.LayoutParams(256, 256));
			v.setPadding(2, 0, 0, 0);
			v.setBackgroundResource(mGalleryItemBackground);
			return v;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
