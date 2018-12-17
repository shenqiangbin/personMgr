package com.sqber.personMgr.bll;

import java.io.IOException;

import com.sqber.personMgr.entity.ImageCaptchaDTO;

public interface ICodeService {
	/**
	 * @param codeLength
	 *            随机验证码的长度
	 * @return 由大小写字母和数字组成的字符串
	 */
	String getCode(int codeLength);

	boolean checkCode(String code, String time, String md5);

	ImageCaptchaDTO getImageCaptcha() throws IOException;
}
