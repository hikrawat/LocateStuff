package com.ritambhara.rememberstuff.model;

import android.graphics.Bitmap;

/**
 * Information about the stuff. 
 * @author karawat
 *
 */
public class StuffInfo 
{
	private int stuffId;
	private String title;
	private String description;
	private boolean imageExist; 
	private boolean audioExist;
	private Bitmap stuffImage;
	
	private String imageFilePath;
	private String audioFilePath;
	
	public StuffInfo()
	{
		imageExist = false;
		audioExist = false;
		title = "";
		description = "";
	}
	
//	public int getStuffId() {
//		return stuffId;
//	}
	public void setStuffId(int stuffId) {
		this.stuffId = stuffId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public boolean isImageExist() {
		return imageExist;
	}
	public void setImageExist(boolean imageExist) {
		this.imageExist = imageExist;
	}
	public boolean isAudioExist() {
		return audioExist;
	}
	public void setAudioExist(boolean audioExist) {
		this.audioExist = audioExist;
	}
	public Bitmap getStuffImage() {
		return stuffImage;
	}
	public void setStuffImage(Bitmap stuffImage) {
		this.stuffImage = stuffImage;
	}
	public String getImageFilePath() {
		return imageFilePath;
	}
	public void setImageFilePath(String imageFilePath) {
		this.imageFilePath = imageFilePath;
	}
	public String getAudioFilePath() {
		return audioFilePath;
	}
	public void setAudioFilePath(String audioFilePath) {
		this.audioFilePath = audioFilePath;
	}
	
}
