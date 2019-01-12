package com.sqber.personMgr.bll.impl;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.Random;

import javax.imageio.ImageIO;

import com.sqber.personMgr.bll.ICodeService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

import com.sqber.personMgr.entity.ImageCaptchaDTO;

@Service
public class CodeService implements ICodeService {

//	@Override
//	public String getCode(int codeLength) {
//
//		Random randomer = new Random();
//		StringBuilder builder = new StringBuilder();
//
//		char[] elements = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R',
//				'S', 'T', 'V', 'U', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
//				'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '1', '2', '3', '4', '5', '6', '7', '8',
//				'9' };
//
//		for (int i = 0; i < codeLength; i++) {
//			int index = randomer.nextInt(elements.length);
//			builder.append(elements[index]);
//		}
//
//		return builder.toString();
//	}


	private int calResult;

	@Override
	public String getCode(int codeLength) {

		Random randomer = new Random();
		StringBuilder builder = new StringBuilder();

		int[] elements = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
		char[] operates = { '+', '-', 'x' };

		int index = randomer.nextInt(elements.length);
		int oneNum = elements[index];

		index = randomer.nextInt(elements.length);
		int anotherNum = elements[index];

		index = randomer.nextInt(operates.length);
		char operate = operates[index];

		calResult = calResult(oneNum,anotherNum,operate);

		builder.append(oneNum).append(operate).append(anotherNum).append("=?");

		return builder.toString();
	}

	private int calResult(int oneNum,int anotherNum,char operate){
		if(operate == '+'){
			return  oneNum + anotherNum;
		}
		if(operate == '-'){
			return  oneNum - anotherNum;
		}
		if(operate == 'x'){
			return  oneNum * anotherNum;
		}
		return 0;
	}

	public ImageCaptchaDTO getImageCaptcha() throws IOException {
		String code = getCode(4);
		String img = darwImg(code, 113, 45);
		String time = new SimpleDateFormat("yyyyMMdd HH:mm:ss").format(new Date());

		//String md5 = md5Cal(code, time);
		String md5 = md5Cal(String.valueOf(calResult), time);

		return new ImageCaptchaDTO(img, md5, time);
	}

	public boolean checkCode(String code, String time, String md5) {
		if (md5Cal(code, time).equals(md5))
			return true;
		else
			return false;
	}

	private String md5Cal(String code, String time) {
		return DigestUtils.md5Hex(code.toLowerCase() + time + "540de77bc32847828b85c84217ca4c32");
	}

	private String darwImg(String content, int imgWidth, int imgHeight) throws IOException {
		// 创建一个画布
		BufferedImage bufferedImage = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_RGB);
		// 创建一个画笔，要开始画东西了
		Graphics2D g = bufferedImage.createGraphics();
		// 画布背景色
		g.setBackground(Color.WHITE);
		g.clearRect(0, 0, imgWidth, imgHeight);

		g.setPaint(new GradientPaint(0, 0, new Color(28, 64, 124), imgWidth, imgHeight, new Color(46, 109, 163)));
		// g.setPaint(Color.BLUE);
		g.fillRect(0, 0, imgWidth, imgHeight);

		// 画笔颜色
		g.setColor(Color.white);
		// 画笔字体
		g.setFont(new Font("Arial", Font.PLAIN, imgHeight - 10));

		String text = content;
		FontMetrics metrics = g.getFontMetrics(g.getFont());
		int xPos = (imgWidth - metrics.stringWidth(text)) / 2;
		int yPos = ((imgHeight - metrics.getHeight()) / 2) + metrics.getAscent();
		g.drawString(text, xPos, yPos);

		// 干扰
		addLine(g, imgWidth, imgHeight);
		//addOtherText(g, imgWidth, imgHeight, text);

		g.dispose();

		return saveToBase64Img(bufferedImage);
	}

	private void addOtherText(Graphics2D g, int imgWidth, int imgHeight, String text) {
		Random randomer = new Random();

		for (int i = 0; i < text.length(); i++) {
			char item = text.charAt(i);

			int pointX = randomer.nextInt(imgWidth);
			int pointY = randomer.nextInt(imgHeight);
			g.setFont(new Font("Arial", Font.PLAIN, imgHeight - 25));
			g.drawString(Character.toString(item), pointX, pointY);
		}
	}

	private void addLine(Graphics2D g, int imgWidth, int imgHeight) {
		int pointNum = 10;
		Random randomer = new Random();

		for (int i = 0; i < pointNum; i++) {
			int pointX1 = randomer.nextInt(imgWidth);
			int pointY1 = randomer.nextInt(imgHeight);
			int pointX2 = randomer.nextInt(imgWidth);
			int pointY2 = randomer.nextInt(imgHeight);
			g.drawLine(pointX1, pointY1, pointX2, pointY2);
		}
	}

	private String saveToBase64Img(BufferedImage bufferedImage) throws IOException {

		ByteArrayOutputStream output = new ByteArrayOutputStream();

		ImageIO.write(bufferedImage, "png", output);
		output.flush();
		output.close();

		byte[] byteArr = output.toByteArray();

		String str = Base64.getEncoder().encodeToString(byteArr);
		String imgStr = new StringBuilder().append("data:image/png;base64,").append(str).toString();
		return imgStr;
	}

}
