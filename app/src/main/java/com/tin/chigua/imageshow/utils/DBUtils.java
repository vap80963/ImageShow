package com.tin.chigua.imageshow.utils;

/**
 * Created by hasee on 1/2/2017.
 */

public class DBUtils {

	//在这个工具类中，放置数据库中数据库名、版本号、所有表的表名及表中对应队列的属性名
	//数据库名
	public static final String DATABASE_NAME = "pic_manager.db";
	//数据库版本号
	public static final int DATABASE_VERSION = 1;
	//数据库表名
	public static final String PICTURE_TABLE = "PICTURE";
	public static final String FOLDER_TABLE = "FOLDER";
	public static final String ENJOY_TABLE = "ENJOY";
	//数据库属性列名
	public static final String PICTURE_ID = "PId";
	public static final String PICTURE_NAME = "PName";
	public static final String PICTURE_DATA = "PData";
	public static final String PICTURE_ENJOY = "PEnjoy";
	public static final String FOLDER_ID = "FId";
	public static final String FOLDER_NAME = "FName";
	public static final String FOLDER_COUNT = "FCount";
	public static final String ENJOY_ID = "EId";

	public static final int REQUEST_CODE = 02131;
	public static final int RESULT_CODE = 5221;


}