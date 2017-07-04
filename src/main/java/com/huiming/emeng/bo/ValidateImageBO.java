package com.huiming.emeng.bo;

import java.awt.image.BufferedImage;

/**
 * 验证码业务对象——ValidateImageBusinessObject
 * 
 * @author Jack
 * @date 2017年5月15日
 */
public class ValidateImageBO {

	/**
	 * 验证码对应的值
	 */
	private String value;
	
	/**
	 * 验证码图像
	 */
	private BufferedImage img;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public BufferedImage getImg() {
		return img;
	}

	public void setImg(BufferedImage img) {
		this.img = img;
	}

	
	
}
