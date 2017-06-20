package com.tin.chigua.imageshow.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.tin.chigua.imageshow.R;
import com.tin.chigua.imageshow.db.DBManager;
import com.tin.chigua.imageshow.utils.DBUtils;

public class FullPictureActivity extends Activity {

	private ImageView mImgV;
	private TextView deleteTv;
	private TextView enjoyTv;
	private	int enjoy = 0;
	private int position;
	private String data;

	private DBManager dbManager;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.album_full_picture);

		init();

	}

	private void init() {
		mImgV = (ImageView) findViewById(R.id.picture_full);
		deleteTv = (Button) findViewById(R.id.picture_delete);
		enjoyTv = (Button) findViewById(R.id.picture_enjoy);

		dbManager = new DBManager(FullPictureActivity.this);
		Intent intent = getIntent();
		position = intent.getIntExtra("position",0);
		data = intent.getStringExtra("data");
		enjoy = dbManager.selectFromEnjoy(data);
		checkEnjoy();
		mImgV.setImageBitmap(BitmapFactory.decodeFile(data));
	}

	public void onClick(View v){
		switch (v.getId()){
			case R.id.picture_full:
				finish();
				break;
			case R.id.picture_delete:
				//从数据库中将记录删除
				dbManager.deleteFromPicture(data);
//				dbManager.selectFromPicture(data);
				Intent intent = new Intent();
				intent.putExtra("position",position);
				setResult(DBUtils.RESULT_CODE,intent);
				finish();
				break;
			case R.id.picture_enjoy:
				//标记为enjoy
				enjoy = (enjoy + 1) % 2;
				dbManager.update(data,enjoy);
				checkEnjoy();
				break;
			default:
				break;
		}
	}

	//判断Enjoy的状态，并将其修改
	private void checkEnjoy() {

		if(enjoy == 1){
			enjoyTv.setText("已收藏");
		}else {
			enjoyTv.setText("收藏");
		}
	}



	@Override
	protected void onDestroy() {
		super.onDestroy();
		dbManager.closeDB();
	}
}








