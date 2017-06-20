package com.tin.chigua.imageshow.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.tin.chigua.imageshow.utils.DBUtils;

import static android.content.ContentValues.TAG;

/**
 * Created by hasee on 1/2/2017.
 */

public class DBHelper extends SQLiteOpenHelper {


	public DBHelper(Context context){
		super(context, DBUtils.DATABASE_NAME,null,DBUtils.DATABASE_VERSION);
	}

	public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		db.execSQL("create table if not exists " + DBUtils.PICTURE_TABLE + " (" +
				DBUtils.PICTURE_ID + " real primary key unique," +
				DBUtils.PICTURE_DATA + " varchar(50)," +
				DBUtils.PICTURE_ENJOY + " integer, " +
				DBUtils.FOLDER_ID + " real)");
		db.execSQL("create table if not exists " + DBUtils.FOLDER_TABLE + " (" +
				DBUtils.FOLDER_ID + " real primary key unique," +
				DBUtils.FOLDER_NAME + " varchar(30)," +
				DBUtils.FOLDER_COUNT + " integer)");
		db.execSQL("create table if not exists " + DBUtils.ENJOY_TABLE + " (" +
				DBUtils.ENJOY_ID + " integer primary key autoincrement," +
				DBUtils.PICTURE_ID + " integer)");
//		db.execSQL("create view folder_pic as " +
//				"select from " + DBUtils.PICTURE_TABLE +" group by " +
//				DBUtils.FOLDER_ID );
		Log.e(TAG, "onCreate: 创建数据库表成功》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》");

	}

	@Override
	public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

	}

	@Override
	public void onOpen(SQLiteDatabase db) {
		super.onOpen(db);
		Log.e(TAG, "onOpen:》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》");
	}
}
