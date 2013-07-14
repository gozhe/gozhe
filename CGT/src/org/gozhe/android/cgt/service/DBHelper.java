package org.gozhe.android.cgt.service;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class DBHelper {
	
	private static final String DATABASE_NAME = "cgt_db";
	private static final int DATABASE_VERSION = 1;

	private Context context;
	private DatabaseHelper dbHelper;
	private SQLiteDatabase db;

	public DBHelper(Context ctx) {
		this.context = ctx;
		dbHelper = new DatabaseHelper(context, DATABASE_NAME, null,
				DATABASE_VERSION);
	}

	public SQLiteDatabase getDb() throws SQLException {

		db = dbHelper.getReadableDatabase();// ��д��
		return db;
	}

	public void closeDb() {
		db.close();
	}

	private static class DatabaseHelper extends SQLiteOpenHelper {

		public DatabaseHelper(Context context, String name,
				CursorFactory factory, int version) {
			super(context, name, factory, version);
			// TODO Auto-generated constructor stub
		}

		// �����ݿ��һ�δ�����ʱ���������������һ�����������������ߴ������ݿ��
		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
		}

		// �������ݿ�,ɾ�����ؽ�
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			onCreate(db);
		}

	}
}
