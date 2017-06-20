package com.tin.chigua.imageshow.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.tin.chigua.imageshow.R;
import com.tin.chigua.imageshow.adapter.AlbumItemAdapter;
import com.tin.chigua.imageshow.bean.PhotoUpImageBucket;
import com.tin.chigua.imageshow.bean.PhotoUpImageItem;
import com.tin.chigua.imageshow.utils.DBUtils;

import java.util.ArrayList;

public class AlbumItemActivity extends Activity implements OnClickListener{

	private GridView gridView;
	private TextView back,ok;
	private PhotoUpImageBucket photoUpImageBucket;
	private ArrayList<PhotoUpImageItem> selectImages;
	private AlbumItemAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.album_item_images);
		init();
		setListener();
	}
	private void init(){
		gridView = (GridView) findViewById(R.id.album_item_gridv);
		back = (TextView) findViewById(R.id.back);
		ok = (TextView) findViewById(R.id.sure);
		selectImages = new ArrayList<PhotoUpImageItem>();
		
		Intent intent = getIntent();
		photoUpImageBucket = (PhotoUpImageBucket) intent.getSerializableExtra("imagelist");
		adapter = new AlbumItemAdapter(photoUpImageBucket.getImageList(), AlbumItemActivity.this);
		gridView.setAdapter(adapter);
	}
	private void setListener(){
		back.setOnClickListener(this);
		ok.setOnClickListener(this);
		gridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(AlbumItemActivity.this,FullPictureActivity.class);
				intent.putExtra("position",position);
				intent.putExtra("data",photoUpImageBucket.getImageList().get(position).getImagePath());

				startActivityForResult(intent, DBUtils.REQUEST_CODE);
			}
		});
		gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {

				CheckBox checkBox = (CheckBox) view.findViewById(R.id.check);
				photoUpImageBucket.getImageList().get(position).setSelected(
						!checkBox.isChecked());
				adapter.notifyDataSetChanged();

				Toast.makeText(AlbumItemActivity.this, "postion="+position,
						Toast.LENGTH_SHORT).show();
//				photoUpImageBucket.getImageList().get(position).setSelected(
//						!photoUpImageBucket.getImageList().get(position).isSelected());
//				adapter.notifyDataSetChanged();
				if (photoUpImageBucket.getImageList().get(position).isSelected()) {
					if (selectImages.contains(photoUpImageBucket.getImageList().get(position))) {

					}else {
						selectImages.add(photoUpImageBucket.getImageList().get(position));
					}
				}else {
					if (selectImages.contains(photoUpImageBucket.getImageList().get(position))) {
						selectImages.remove(photoUpImageBucket.getImageList().get(position));
					}else {

					}
				}

				return true;
			}
		});
}


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode == DBUtils.RESULT_CODE){
			if (requestCode == DBUtils.REQUEST_CODE){
				int position = data.getIntExtra("position",0);
//				photoUpImageBucket.getImageList().remove(position);
//				gridView.notify();
			}
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;
		case R.id.sure:
			Intent intent = new Intent(AlbumItemActivity.this,SelectedImagesActivity.class);
			intent.putExtra("selectIma", selectImages);
			startActivity(intent);
			break;

		default:
			break;
		}
	}


}
