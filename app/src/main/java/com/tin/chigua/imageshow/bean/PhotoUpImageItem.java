package com.tin.chigua.imageshow.bean;

import java.io.Serializable;

public class PhotoUpImageItem implements Serializable {

	//图片ID
	private long imageId;
	//原图路径
	private String imagePath;
	//是否被选择
	private boolean isSelected = false;
	public long bucketId;

	public long getBucketId() {
		return bucketId;
	}
	public void setBucketId(long bucketId) {
		this.bucketId = bucketId;
	}
	
	public long getImageId() {
		return imageId;
	}
	public void setImageId(long imageId) {
		this.imageId = imageId;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	public boolean isSelected() {
		return isSelected;
	}
	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}
	
	
}
