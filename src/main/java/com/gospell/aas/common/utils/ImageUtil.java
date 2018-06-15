package com.gospell.aas.common.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;

import com.gospell.aas.dto.adv.ImageInfo;

public class ImageUtil {

	/**
	 * 使用BufferedImage获取图片信息
	 * 
	 * @param src
	 *            源图片路径
	 */
	public static ImageInfo getImageInfoByBufferedImage(String src) {
		ImageInfo dto = null;
		if (!FileUtils.checkfile(src)) {
			return null;
		}
		File file = new File(src);
		FileInputStream is = null;
		try {
			is = new FileInputStream(file);
			BufferedImage sourceImg = null;
			sourceImg = javax.imageio.ImageIO.read(is);
			dto = new ImageInfo();
			dto.setFilePath(src);
			int width = sourceImg.getWidth();
			int height = sourceImg.getHeight();
			dto.setHeight(height);
			dto.setWidth(width);
			long size = file.length() / 1024;
			dto.setFileSize(String.valueOf(size));
	    	
	    	String format = src.substring(src.lastIndexOf(".")+1);
			dto.setFileFormat(format);
			dto.setFilePath(src);

		} catch (Exception e1) {
			e1.printStackTrace();
			return null;

		}
		return dto;

	}

	/**
	 * 使用BufferedImage获取图片大小
	 * 
	 * @param src
	 *            源图片路径
	 */
	public static Long getFileSize(String src) {
		String filePath = "";
		if (org.apache.commons.lang3.StringUtils.isNotBlank(src) && src.startsWith("/"))
			 
		{
			filePath = FileUtils.getUploadFileRealPath()+src;
		}
		if (!FileUtils.checkfile(filePath)) {
			return 0l;
		}
		File file = new File(filePath);
		return file.length() / 1024;
	}

	/**
	 * 获取文件的大小
	 * 
	 * @param path
	 * @return
	 */
	public static String getAllFileSize(String path) {
		if (org.apache.commons.lang3.StringUtils.isBlank(path))
			return "";
		String[] paths = path.split(",");
		String allSize = "";
		for (int i = 0; i < paths.length; i++) {
			String filePath = paths[i];
			if (org.apache.commons.lang3.StringUtils.isNotBlank(filePath)) {
				Long size = getFileSize(paths[i]);
				String fileSize = String.valueOf(size)+",";
				allSize += fileSize;
			}
		}
		return allSize;
	}

}
