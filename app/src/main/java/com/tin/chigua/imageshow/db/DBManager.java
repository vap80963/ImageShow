package com.tin.chigua.imageshow.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.tin.chigua.imageshow.bean.PhotoUpImageBucket;
import com.tin.chigua.imageshow.bean.PhotoUpImageItem;
import com.tin.chigua.imageshow.utils.DBUtils;

import java.util.List;


/**
 * Created by hasee on 1/2/2017.
 */

public class DBManager {

	private static final String TAG = "DBManager";

	private DBHelper dbHelper;
	private Context mContext;
	private SQLiteDatabase db;


	public DBManager(Context context) {
		dbHelper = new DBHelper(context);
		this.mContext = context;
		db = dbHelper.getWritableDatabase();
	}
	/**
	 *  将文件夹信息插入到FOLDER表中
	 * 	将图片信息插入到Picture表
	 */
	public void insertToAlbum(List<PhotoUpImageBucket> list){
		for (int i = 0; i < list.size(); i++) {
			long bucketId = list.get(i).getBucketId();
			String bucketName = list.get(i).getBucketName();
			int count = list.get(i).getCount();
			String sql = "insert or ignore into " + DBUtils.FOLDER_TABLE + " values( ?, ?, ?)";
			db.execSQL(sql,new Object[]{bucketId,bucketName,count});
			Log.e(TAG, "insertToAlbum: 插入的  " + i + "  文件ID为" + bucketId);
			//将文件夹对应的图片数组插入到Picture表中
			insertToPicture(list.get(i).getImageList());
		}
	}
	/**
	 * 	将文件夹对应的图片数组插入到Picture表中
	 */
	public void insertToPicture(List<PhotoUpImageItem> list){
		for (int i = 0; i < list.size(); i++) {
			long _id = list.get(i).getImageId();
			long bucket_id = list.get(i).getBucketId();
			String data = list.get(i).getImagePath();
			int favor = 0;
			db.execSQL("insert or ignore into " + DBUtils.PICTURE_TABLE +
							" values(?,?,?,?)",new Object[]{_id,data,favor,bucket_id});
			Log.e(TAG, "insertToAlbum: 插入的  " + i + "  图片文件ID为" + _id);
		}
	}
	/**
	 * 将被收藏的图片ID存入ENJOY表中
	 */
	public void insertToEnjoy(String data){
		long PId = 0;
		PId = selectFromPicture(data);
		db.execSQL("insert into " + DBUtils.ENJOY_TABLE +
						" values(null,?)",new Object[]{PId});
	}
	/**
	 * 用于对picture表中图片的favor值进行修改
	 */
	public void update(String data,int enjoy){
		db.execSQL("update " + DBUtils.PICTURE_TABLE +
						" set " + DBUtils.PICTURE_ENJOY + " = " + enjoy + " where " +
						DBUtils.PICTURE_DATA + " =?",new Object[]{data});
		if(enjoy == 1){
			if(selectFromEnjoy(data) == 0){
				insertToEnjoy(data);
			}else {
				return;
			}
		}else {
			deleteFromFavor(data);
		}
	}

	/**
	 *
	 * @return 检索结果，返回收藏状态
	 */
	public int selectFromEnjoy(String data){
		//通过路径，检索图片ID
		long ID = selectFromPicture(data);
		//再通过图片ID，检索picture表中ID所对应图片的收藏状态
		Cursor c = db.rawQuery("select " + DBUtils.PICTURE_ENJOY + " from " + DBUtils.PICTURE_TABLE +
								" where " + DBUtils.PICTURE_ID + " =?",new String[]{String.valueOf(ID)});
		if(c.moveToNext()){
			int favor = c.getInt(c.getColumnIndex(DBUtils.PICTURE_ENJOY));
			return favor;
		}
		c.close();
		return 0;
	}
	/**
	 * 通过路径，检索图片ID
	 */
	public long selectFromPicture(String data){
		long ID = 0;
		Cursor c = db.rawQuery("select " + DBUtils.PICTURE_ID + " from " +  DBUtils.PICTURE_TABLE +
				" where " + DBUtils.PICTURE_DATA + "=?",new String[]{data});
		if(c.moveToNext()){
			ID = c.getLong(c.getColumnIndex(DBUtils.PICTURE_ID));
		}
		c.close();
		Log.e(TAG, "selectFromPicture: ID = "+ ID);
		return ID;
	}

	/**
	 * 将某张图片在图片表中的元组删除
	 * @param data
	 */
	public void deleteFromPicture(String data) {
		long PId = 0;
		PId = selectFromPicture(data);
		update(data,0);
		db.execSQL("delete from " + DBUtils.PICTURE_TABLE +
				" where " + DBUtils.PICTURE_ID + " =?",new Object[]{PId});
	}

	/**
	 * 将图片从ENJOY表中的元组删除
	 * @param data
	 */
	public void deleteFromFavor(String data) {
		long PId = selectFromPicture(data);
		db.execSQL("delete from " + DBUtils.ENJOY_TABLE +
						" where " + DBUtils.PICTURE_ID + " = ?",new Object[]{PId});
	}

	/**
	 * 关闭数据库
	 */
	public void closeDB(){
		db.close();
	}

}
