package org.gozhe.android.cgt.utils;

import java.io.File;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.os.StatFs;
import android.telephony.TelephonyManager;

public class Utility {

	/**
	 * ��ȡ�ֻ�IMEI��
	 * 
	 * @param context
	 * @return
	 */
	public static String getIMEI(Context context) {
		TelephonyManager telephonyManager = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		return telephonyManager.getDeviceId();
	}

	/**
	 * �ж�SD���Ƿ���ڲ��ɶ�д
	 * 
	 * @return
	 */
	public static boolean isSdcardAvailable() {
		boolean result = false;
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state)) {
			if (Environment.getExternalStorageDirectory().canWrite()) {
				result = true;
			}
		}
		return result;
	}

	/**
	 * SD���ܿռ�(MIB��λ)
	 * 
	 * @return
	 */
	public static long getSdcardAllSize() {
		if (isSdcardAvailable()) {
			File path = Environment.getExternalStorageDirectory();
			StatFs statfs = new StatFs(path.getPath());
			long blockSize = statfs.getBlockSize();// ��ȡblock��SIZE
			long totalBlocks = statfs.getBlockCount();// ��ȡBLOCK����
			long availableBlock = statfs.getAvailableBlocks();// ���е�Block������

			return totalBlocks * blockSize / (1024 * 1024);// MiB
			// (availableBlocks * blockSize)/1024 KIB ��λ
			// (availableBlocks * blockSize)/1024 /1024 MIB��λ
		} else {
			return 0;
		}
	}

	/**
	 * SD�����ÿռ�(MIB��λ)
	 * 
	 * @return
	 */
	public static long getSdcardAvaiableSize() {

		if (isSdcardAvailable()) {
			File path = Environment.getExternalStorageDirectory();
			StatFs statfs = new StatFs(path.getPath());
			long blockSize = statfs.getBlockSize();// ��ȡblock��SIZE
			long totalBlocks = statfs.getBlockCount();// ��ȡBLOCK����
			long availableBlock = statfs.getAvailableBlocks();// ���е�Block������

			return availableBlock * blockSize / (1024 * 1024);// MiB
			// (availableBlocks * blockSize)/1024 KIB ��λ
			// (availableBlocks * blockSize)/1024 /1024 MIB��λ
		} else {
			return 0;
		}
	}

	/**
	 * ��ȡӦ�ó���·��
	 * 
	 * @param app
	 * @return
	 */
	public static String getAppPath() {

		if (isSdcardAvailable()) {
			File path = Environment.getExternalStorageDirectory();
			String sdcardPath = path.getPath();
			return sdcardPath + "/CGT";
		} else {
			return "@";
		}
	}

	/**
	 * ��ȡӦ�ó��������Ƭ¼���ļ�·��
	 * 
	 * @param app
	 * @return
	 */
	public static String getUpDataPath() {

		File files = new File(getAppPath() + "/UpData");
		if (!files.exists()) {
			files.mkdirs();
		}
		return getAppPath() + "/UpData";
	}

	/**
	 * �ж������Ƿ����
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isNetworkAvaiable(Context context) {

		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = cm.getActiveNetworkInfo();
		NetworkInfo.State state = info.getState();
		if (state == NetworkInfo.State.CONNECTED)
			return true;
		else
			return false;
	}
}
