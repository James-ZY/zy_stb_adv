package com.gospell.aas.common.utils.gif;

import java.io.File;
import java.io.FileInputStream;

public class GifFrameUtil {

	/**
	 * 获取gif图片的帧数
	 * 
	 * @param path
	 *            文件路径
	 * @return
	 */
	public static Integer getGitFrame(File file) {
		Integer frame = -1;// -1表示未获取到图片的帧数或者遇到异常或者文件格式不对
		try {
			GifDecoder gd = new GifDecoder();
			int status = gd.read(new FileInputStream(file));
			if (status != GifDecoder.STATUS_OK) {
				return frame;
			}
			frame = gd.getFrameCount();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return frame;
	}
}
