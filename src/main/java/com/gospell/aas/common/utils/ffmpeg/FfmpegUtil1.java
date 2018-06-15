package com.gospell.aas.common.utils.ffmpeg;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gospell.aas.common.config.Global;

/**
 * 将视频格式转换为ts流
 * 
 * @author Administrator
 *
 */
public class FfmpegUtil1 {
	
	/**
	 * 把图片转换为m2v格式的
	 */
	protected static Logger logger = LoggerFactory.getLogger(FfmpegUtil1.class);
	public static ConvertDTO ffmpegM2vFormImage(String ffmpegPath, String inputPath, String outputPath){
		ConvertDTO dto = new ConvertDTO();
		if (!checkfile(inputPath)) {
			System.out.println(inputPath + " is not file");
			dto.setStatus(1);
			dto.setMessage("输入文件错误");
			return dto;
		}

		List<String> command_image = getFfmpegCommandM2v(ffmpegPath, inputPath,outputPath);
		if (null != command_image && command_image.size() > 0) {
			process(command_image, dto);// 截取视频图片
			if (dto.getStatus() == 0) {
				dto.setOutput(outputPath);
				dto.setInput(inputPath);
			}
		}
		return dto;
	}

	/**
	 * 截取视频中的图片
	 * 
	 * @param ffmpegPath
	 * @param inputPath
	 * @param outputPath
	 * @return
	 */
	public static ConvertDTO ffmpegImageFormVedio(String ffmpegPath, String inputPath, String outputPath) {
		ConvertDTO dto = new ConvertDTO();
		if (!checkfile(inputPath)) {
			System.out.println(inputPath + " is not file");
			dto.setStatus(1);
			dto.setMessage("输入文件错误");
			return dto;
		}

		List<String> command_image = getFfmpegCommandImage(ffmpegPath, inputPath,outputPath);
		if (null != command_image && command_image.size() > 0) {
			process(command_image, dto);// 截取视频图片
			if (dto.getStatus() == 0) {
				dto.setOutput(outputPath);
				dto.setInput(inputPath);
			}
		}
		return dto;
	}
	
	/**
	 * 把ts转换成MP4
	 * 
	 * @param ffmpegPath
	 * @param inputPath
	 * @param outputPath
	 * @return
	 */
	public static ConvertDTO ffmpegMp4FormTs(String ffmpegPath, String inputPath, String outputPath,boolean isChange) {
		ConvertDTO dto = new ConvertDTO();
		if (!checkfile(inputPath)) {
	 
			dto.setStatus(1);
			dto.setMessage("输入文件错误");
			return dto;
		}

		List<String> command_image = getFfmpegTsToMP4(ffmpegPath, inputPath,outputPath,isChange);
		if (null != command_image && command_image.size() > 0) {
			process(command_image, dto); 
			if (dto.getStatus() == 0) {
				dto.setOutput(outputPath);
				dto.setInput(inputPath);
			}
		}
		return dto;
	}

	/**
	 * 将视频转换为ts流
	 * 
	 * @param ffmpegPath
	 *            ffmpegPath bin路径
	 * @param inputPath
	 *            源文件路径
	 * @param outputPath
	 *            输出文件路径
	 * @return
	 */
	public static ConvertDTO ffmpeg(String ffmpegPath, String inputPath, String outputPath) {
		ConvertDTO dto = new ConvertDTO();
		if (!checkfile(inputPath)) {
			System.out.println(inputPath + " is not file");
			dto.setStatus(1);
			dto.setMessage("输入文件错误");
			return dto;
		}

		int type = checkContentType(inputPath);
		List<String> command = getFfmpegCommand(type, ffmpegPath, inputPath, outputPath, dto);
		if (null != command && command.size() > 0) {
			process(command, dto);// 转换文件为ts流
			if (dto.getStatus() == 0) {
				dto.setOutput(outputPath);
				dto.setInput(inputPath);
			}
		}
		return dto;
	}
	
	/**
	 * 获取视频信息
	 * 
	 * @param ffmpegPath
	 * @param inputPath
	 * @param outputPath
	 * @return
	 */
	public static ConvertDTO getVedioInfo(String ffmpegPath, String inputPath) {
		ConvertDTO dto = new ConvertDTO();
		if (!checkfile(inputPath)) {
			System.out.println(inputPath + " is not file");
			dto.setStatus(1);
			dto.setMessage("error");
			return dto;
		}

		List<String> command_image = getVedionInfoCommand(ffmpegPath, inputPath);
		if (null != command_image && command_image.size() > 0) {
			processVedioInfo(command_image, dto); 
		 
		}
		return dto;
	}

	/**
	 * 检查视频的格式
	 * 
	 * @param inputPath
	 *            源文件
	 * @return
	 */
	private static int checkContentType(String inputPath) {
		String type = inputPath.substring(inputPath.lastIndexOf(".") + 1, inputPath.length()).toLowerCase();
		// ffmpeg能解析的格式：（asf，mpg，wmv，3gp，mkv，mp4，mov，avi，flv，rm，rmvb等）
        if (type.equals("avi")) {
            return 0;
        } else if (type.equals("mpg")) {
            return 0;
        } else if (type.equals("wmv")) {
            return 0;
        } else if (type.equals("3gp")) {
            return 0;
        } else if (type.equals("mov")) {
            return 0;
        } else if (type.equals("mp4")) {
            return 0;
        } else if (type.equals("asf")) {
            return 0;
        } else if (type.equals("asx")) {
            return 0;
        } else if (type.equals("flv")) {
            return 0;
        }
        // 对ffmpeg无法解析的文件格式(wmv9，rm，rmvb等),
        // 可以先用别的工具（mencoder）转换为avi(ffmpeg能解析的)格式.
        else if (type.equals("wmv9")) {
            return 1;
        } else if (type.equals("rm")) {
            return 1;
        } else if (type.equals("rmvb")) {
            return 1;
        } else if (type.equals("ts")) {
            return 2;
        }
        return 9;
	}

	/**
	 * 检查文件的合法性
	 * 
	 * @param path
	 * @return
	 */
	private static boolean checkfile(String path) {
		File file = new File(path);
		if (!file.isFile()) {
			return false;
		}
		return true;
	}
	/**
	 * 通过进程获取视频信息
	 * 
	 * @param command
	 * @param dto
	 */
	private static void processVedioInfo(List<String> command, ConvertDTO dto) {
		Process videoProcess = null;
		StringBuffer commandLog= new StringBuffer();
		try {

			if (null == command || command.size() == 0)
				return;
			
			for (int i = 0; i < command.size(); i++) {
				commandLog.append(command.get(i));
			}
			logger.info(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"ffmepg执行的命令:"+commandLog.toString());
			  videoProcess = new ProcessBuilder(command).redirectErrorStream(true).start();
				 BufferedReader br = new BufferedReader(new InputStreamReader(videoProcess.getInputStream()));  
				  StringBuffer sb = new StringBuffer();  
		            String line = "";  
		            while ((line = br.readLine()) != null) {  
		                sb.append(line);  
		            }  
		    dto.setMessage(sb.toString());
			new PrintStream1(videoProcess.getInputStream()).start();
		    int exitcode = videoProcess.waitFor();
			dto.setStatus(exitcode);
			 
	 
		} catch (Exception e) {
            logger.error("上传出错", e);
			 
            if(null != videoProcess){
            	try{
            	videoProcess.getErrorStream().close();
				videoProcess.getInputStream().close();
				videoProcess.getOutputStream().close();
				videoProcess.destroy();
            	}catch(Exception ee){
            		ee.printStackTrace();
            	}
            }
			e.printStackTrace();
			dto.setStatus(1);
			dto.setMessage("error");
		}

	}
	/**
	 * ffmpeg能解析的格式：（asx，asf，mpg，wmv，3gp，mp4，mov，avi，flv等）
	 * 
	 * @param command
	 * @param dto
	 */
	private static void process(List<String> command, ConvertDTO dto) {
		Process videoProcess = null;
		StringBuffer commandLog= new StringBuffer();
		try {

			if (null == command || command.size() == 0)
				return;
			
			for (int i = 0; i < command.size(); i++) {
				commandLog.append(command.get(i));
			}
			logger.info(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"ffmepg执行的命令:"+commandLog.toString());
			  videoProcess = new ProcessBuilder(command).redirectErrorStream(true).start();
			  new PrintStream1(videoProcess.getErrorStream()).start();
			new PrintStream1(videoProcess.getInputStream()).start();

			int exitcode = videoProcess.waitFor();
			dto.setStatus(exitcode);
			if (exitcode == 1) {
				videoProcess.getErrorStream().close();
				videoProcess.getInputStream().close();
				videoProcess.getOutputStream().close();
				videoProcess.destroy();
				dto.setMessage("转换文件失败，请重新上传");
				logger.error("执行转换命令失败，请检查命令");
				 
			}
	 
		} catch (Exception e) {
            logger.error("上传出错", e);
			 
            if(null != videoProcess){
            	try{
            	videoProcess.getErrorStream().close();
				videoProcess.getInputStream().close();
				videoProcess.getOutputStream().close();
				videoProcess.destroy();
            	}catch(Exception ee){
            		ee.printStackTrace();
            	}
            }
			e.printStackTrace();
			dto.setStatus(1);
			dto.setMessage("上传出错");
		}

	}

	/**
	 * 根据文件类型设置ffmpeg命令
	 * 
	 * @param type
	 * @param oldfilepath
	 * @param outputPath
	 * @param dto
	 * @return
	 */
	private static List<String> getFfmpegCommand(int type, String ffmpegPath, String oldfilepath, String outputPath,
			ConvertDTO dto) {
		List<String> command = new ArrayList<String>();
 
		if (type == 0) {
			command.add("ffmpeg");
			command.add("-y");
			command.add("-i");
			command.add(oldfilepath);
			command.add("-ab");
			command.add("56");
			command.add("-ar");
			command.add("22050");
			command.add("-qscale");
			command.add("8");
			command.add("-r");
			command.add("15");
			command.add(outputPath);
		} else if (type == 1) {
			command.add("ffmpeg");
			command.add("mencoder");
			command.add("-y");
			command.add("-i");
			command.add(oldfilepath);
			command.add("-oac");
	        command.add("lavc");
	        command.add("-lavcopts");
	        command.add("acodec=mp3:abitrate=64");
	        command.add("-ovc");
	        command.add("xvid");
	        command.add("-xvidencopts");
	        command.add("bitrate=600");
	        command.add("-of");
	        command.add("avi");
	        command.add("-o");
			command.add(outputPath);
		} else if (type == 2) {
			command.add("ffmpeg");
			command.add("-y");
			command.add("-i");
			command.add(oldfilepath);
			command.add("-b:v");
			command.add("1000k"); 
			command.add("-bufsize");
			command.add("-1000k");
			command.add("-maxrate");
			command.add("-1200k");
			command.add(outputPath);
		
		} else {
			dto.setStatus(1);
			dto.setMessage("不支持当前上传格式");
			   logger.info("不支持当前上传格式");
		}
		return command;
	}

	/**
	 * 根据文件类型设置ffmpeg命令
	 * 
	 * @param type
	 * @param oldfilepath
	 * @param outputPath
	 * @param dto
	 * @return
	 */
	private static List<String> getFfmpegCommandImage(String ffmpegPath, String oldfilepath, String outputPath) {
		List<String> command = new ArrayList<String>();
	
		command.add("ffmpeg");

		command.add("-y");
		command.add("-i");
		command.add(oldfilepath);
		command.add("-f");
		command.add("image2");
		command.add("-ss");
		command.add("1");
		command.add("-vframes");
		command.add("1");
		command.add("-s");
		int width=720;
		int height=576;
		try{
		String w = Global.getConfig("vedio_demo_width");
			width = Integer.parseInt(w);
			String h= Global.getConfig("vedio_demo_height");
			height = Integer.parseInt(h);
		}catch(Exception e){
			logger.info("在调用视频截图 的过程中，遇到字符串转换为数字异常", e);
		}
		String px = String.valueOf(width) + "*" + String.valueOf(height);// 根据视频的大小设置图片的大小
		command.add(px);
		command.add(outputPath);
		return command;
}
	
	/**
	 * 设置ts转换为MP4的命令
	 * 
	 * @param type
	 * @param oldfilepath
	 * @param outputPath
	 * @param dto
	 * @return
	 */
	private static List<String> getFfmpegTsToMP4(String ffmpegPath, String oldfilepath, String outputPath,boolean isChange) {
		 
		List<String> command = new ArrayList<String>();
	
		command.add("ffmpeg");
		command.add("-y");
		command.add("-i");
		command.add(oldfilepath);
		if(isChange){
			command.add("-c:v");
			command.add("libx264");
			command.add("-strict");
			command.add("-2");
		}else{
			command.add("-c:a");
			command.add("copy");
			command.add("-c:v");
			command.add("copy");
			command.add("-bsf:a");
			command.add("aac_adtstoasc");
		}
		//ffmpeg -y -i /usr/local/tomcat-7.0.70-advs-test/webapps/upload/advs/admin/vedio/admin_20170418173307236.ts -c:v  libx264 -strict -2 /usr/local/tomcat-7.0.70-advs-test/webapps/upload/advs/admin/vedio/admin_20170418173307236.mp4 
//		command.add("ffmpeg");
//		command.add("-y");
//		command.add("-i");
//		command.add(oldfilepath);
//		command.add("-c:v");
//		command.add("libx264");
//		command.add("-strict");
//		command.add("-2");
		command.add(outputPath);
		String s = "";
		for (int i = 0; i < command.size(); i++) {
			s =s + command.get(i)+" "; 
		}
	 
        logger.info("当前的命令是:", s);
		return command;
}
	
	/**
	 * 获取视频信息的命令
	 * 
	 * @param type
	 * @param oldfilepath
 
	 */
	private static List<String> getVedionInfoCommand(String ffmpegPath, String oldfilepath) {
		 
		List<String> command = new ArrayList<String>();
	
		command.add("ffmpeg");
		 
		command.add("-i");
		command.add(oldfilepath);
		 
		String s = "";
		for (int i = 0; i < command.size(); i++) {
			s =s + command.get(i)+" "; 
		}
        logger.info("当前的命令是:", s);
		return command;
}

/**
 * 根据文件类型设置ffmpeg命令
 * 
 * @param type
 * @param oldfilepath
 * @param outputPath
 * @param dto
 * @return
 */
private static List<String> getFfmpegCommandM2v(String ffmpegPath, String oldfilepath, String outputPath) {
	 
	List<String> command = new ArrayList<String>();
 
	command.add("ffmpeg");
	command.add("-y");
	command.add("-f");
	command.add("image2");
	command.add("-r");
	command.add("1");
	command.add("-i");
	command.add(oldfilepath);
	command.add("-b:v");
	command.add("200k");
	command.add("-r");
	command.add("10");
	command.add("-s");
	//ffmpeg -u -f image2 -r 1 -i /1.jpg -b:v 200k -r 10 -s 720*560 /1.m2v
	int width=720;
	int height=576;
	String px = String.valueOf(width) + "*" + String.valueOf(height);
	command.add(px);
	command.add(outputPath);
	return command;
}
}

class PrintStream1 extends Thread {
	java.io.InputStream __is = null;

	public PrintStream1(java.io.InputStream is) {
		__is = is;
	}

	@Override
	public void run() {
		try {
			while (this != null) {
				int _ch = __is.read();
				if (_ch == -1) {
					break;
				} else {
					//System.out.print((char) _ch);
				}

			}
		} catch (Exception e) {
		
			e.printStackTrace();
		}
	}
}
