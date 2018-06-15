package com.gospell.aas.common.utils;

/**
 * @title 读取图片文件将其转为二进制,再生成新的图片
 * 
 */

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageToBinaryUtil {

	/**
	 * 转换byte数组为Image
	 * 
	 * @param bytes
	 *            Image的bytes数据数组
	 * @param filename
	 *            为要生成新的文件名
	 * @return boolean
	 */
	public static boolean ByteToImage(byte[] b, String filename) {
		boolean bl = false;
		File binaryFile = new File("C:\\Users\\Administrator\\Desktop\\"
				+ filename + ".png");
		try {
			FileOutputStream fileOutStream = new FileOutputStream(binaryFile);
			for (int i = 0; i < b.length; i++) {
				fileOutStream.write(b[i]);
			}
			fileOutStream.flush();
			bl = true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}// 创建文件输出流。
		catch (IOException e) {
			e.printStackTrace();
		}
		return bl;
	}
	/**
	 * rootpath 结果为D:\\apache-tomcat-7.0.62\\webapps\\advs，
	 * 因为图片路径为/adv/...，所以必须截取rootpath字符串
	 * @return
	 */
	private static String getRootPath(){
		String rootPath = ApplicationContextHelper.getRootRealPath();
		String s1[] = rootPath.split("\\\\");
		String path = "";
		for (int i = 0; i < s1.length-1; i++) {
			if(i == (s1.length -2)){
				path = path +s1[i];
			}else{
				path = path +s1[i]+"\\";
			}
		
		}
		 return path;
		  
	}
	/*
	 * 转换Image数据为byte数组
	 */
	public static byte[] ImageToBytes(String path) {
		String rootpath = getRootPath();
		path = rootpath+path;
		File file = new File(path);
		FileInputStream fin = null;
		try {
			fin = new FileInputStream(file);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			int i = fin.read();
			while (i != -1) {
				out.write(i);
				i = fin.read();
			}
			return out.toByteArray();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return null;
	}
}
