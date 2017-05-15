package com.huiming.emeng.common;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import com.huiming.emeng.bo.ValidateImageBO;

/**
 * 验证码工具类
 * 
 * @author Jack
 * @date 2017年5月15日
 */
public class ValidateCodeUtil {

	/**
	 * 生成验证码
	 * @return 验证码业务对象
	 */
	public static ValidateImageBO getValidateBO() {
		ValidateImageBO result = new ValidateImageBO();
		//默认生成的图像宽度为68px，高度为32px
		int width=68, height=32;
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);		
		Graphics g = image.getGraphics();			
		Random random = new Random(new java.util.Date().getTime());		
		g.setColor(getRandColor(200,250));
		g.fillRect(0, 0, width, height);			
		g.setFont(new Font("Times New Roman",Font.PLAIN,18));			
		g.setColor(getRandColor(160,200));
		for (int i=0;i<50;i++) {
			int x = random.nextInt(width);
			int y = random.nextInt(height);
		  int xl = random.nextInt(width);
		  int yl = random.nextInt(height);
			g.drawLine(x,y,x+xl,y+yl);
		}		

		int n1 = random.nextInt(50);
		int n2 = random.nextInt(50);
		int code = -1;
		
	    g.setColor(new Color(20+random.nextInt(110),20+random.nextInt(110),20+random.nextInt(110)));
	    g.drawString(String.valueOf(n1),14*0+4,16);
	    
    	g.setColor(new Color(20+random.nextInt(110),20+random.nextInt(110),20+random.nextInt(110)));
	    if(random.nextInt(10)>5) {
		    g.drawString(" + ",14*1+4,16);
		    g.setColor(new Color(20+random.nextInt(110),20+random.nextInt(110),20+random.nextInt(110)));
		    g.drawString(String.valueOf(n2)+"=?",14*2+4,16);
		    code = n1+n2;
	    }
	    else {
	    	g.drawString(" - ",14*1+4,16);
	    	while(n1<n2) {
	    		n2 = random.nextInt(50);
	    	}
	    	g.setColor(new Color(20+random.nextInt(110),20+random.nextInt(110),20+random.nextInt(110)));
		    g.drawString(String.valueOf(n2)+"=?",14*2+4,16);
		    code = n1-n2;
	    }
	    
		g.dispose();
		
		result.setValue(String.valueOf(code));
		result.setImg(image);
		
		return result;
	}
	
	
	
	/**
	 * 获取随机的颜色
	 * @param fc 基础色值
	 * @param bc 最大色值
	 * @return 随机颜色对象
	 */
	private static Color getRandColor(int fc,int bc) {
	    Random random = new Random(new java.util.Date().getTime());
	    if(fc>255) {
	    	fc=255;
	    }
	    if(bc>255) {
	    	bc=255;
	    }
	    int r=fc+random.nextInt(bc-fc);
	    int g=fc+random.nextInt(bc-fc);
	    int b=fc+random.nextInt(bc-fc);
	    return new Color(r,g,b);
    }
}
