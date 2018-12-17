package com.sqber.personMgr.entity;

/**
 * @author admin
 * 图像验证码dto
 */
public class ImageCaptchaDTO {
	
	private String image;
	private String md5;
	private String time;
	
	public ImageCaptchaDTO(String image, String md5, String time) {
		super();
		this.image = image;
		this.md5 = md5;
		this.time = time;
	}
	
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getMd5() {
		return md5;
	}
	public void setMd5(String md5) {
		this.md5 = md5;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
}
