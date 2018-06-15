package com.gospell.aas.common.utils.ffmpeg;

/**
 * 转换视频格式为ts流
 * @author Administrator
 *
 */
public class ConvertVedio {
	
	
	/**
	 * 把视频先转换为MP4 再转换为ts
	 * @param inputPath
	 * @return
	 */
	public static ConvertDTO convertVedio(Boolean isSetRate,Integer minRate,String inputPath){
		int a = FfmpegUtil.checkContentType(inputPath);//判断视频格式类型
		ConvertDTO dto=null;
		String outputPath;
		if(a==0){//先转换成mp4  再转换成ts
			outputPath = getOutputPath(inputPath,".mp4");
			dto = FfmpegUtil.ffmpeg("",isSetRate,minRate, inputPath, outputPath);//先转换为MP4
			if(dto.getStatus()==0){
				dto = FfmpegUtil.ffmpeg("",isSetRate,minRate, outputPath, getOutputPath(outputPath,".ts"));//再转换成ts
			}	
		}else if(a==1){//先转换成flv 再转换成MP4  最后转成ts
			outputPath = getOutputPath(inputPath,".flv");
			dto = FfmpegUtil.ffmpeg("", isSetRate,minRate,inputPath, outputPath);//先转换为flv
			if(dto.getStatus()==0){
				dto = FfmpegUtil.ffmpeg("",isSetRate,minRate, outputPath, getOutputPath(outputPath,".mp4"));//再转换成mp4
				if(dto.getStatus()==0){
					dto = FfmpegUtil.ffmpeg("",isSetRate,minRate, getOutputPath(outputPath,".mp4"), getOutputPath(outputPath,".ts"));//再转换成ts
				}
			}	
		}else if(a==2){//mp4转成ts
			outputPath = getOutputPath(inputPath,".ts");
			dto = FfmpegUtil.ffmpeg("",isSetRate,minRate, inputPath, outputPath);//转换为ts
		}else if(a==3){
			dto = FfmpegUtil.ffmpeg("",isSetRate,minRate, inputPath, inputPath);//控制视频码率
		}else{
			System.out.println("不支持当前上传格式");
		}
	
		return dto;
	}
	
	/**
	 * 截取视频中的图片
	 * @param inputPath
	 * @return
	 */
	public static ConvertDTO ffmpegImageFormVedio(String inputPath){
//		String ffmpegPath = getFfmpegPath();
//		if(StringUtils.isBlank(ffmpegPath))
//			return null;
		String outputPath = getImagePath(inputPath);
		ConvertDTO dto = FfmpegUtil.ffmpegImageFormVedio("", inputPath, outputPath);
		return dto;
	}
	
	 /**
	  * 把ts转换成MP4
	  * @param inputPath
	  * @param isChangeCode 是否是转码转换
	  * @return
	  */
	public static ConvertDTO ffmpegMp4FormTs(Integer minRate,String inputPath,boolean isChangeCode){
 
		String outputPath = getMp4Path(inputPath);
		ConvertDTO dto = FfmpegUtil.ffmpegMp4FormTs("",minRate, inputPath, outputPath,isChangeCode);
		return dto;
	}
	
	
 
	
	/**
	 * 截取视频中的图片
	 * @param inputPath
	 * @return
	 */
	public static ConvertDTO ffmpegM2vFromImage(String inputPath){
//		String ffmpegPath = getFfmpegPath();
//		if(StringUtils.isBlank(ffmpegPath))
//			return null;
		String ffmpegPath="";
		String outputPath = getM2vPath(inputPath);
		ConvertDTO dto = FfmpegUtil.ffmpegM2vFormImage(ffmpegPath, inputPath, outputPath);
		return dto;
	}

//	/**
//	 * 获取ffmpeg执行文件的路径
//	 * 
//	 * @return
//	 */
//	private static String getFfmpegPath() {
//		List<AdExternalProgram> programList = UserUtils.getProgramList();
//		if(null != programList && programList.size() >0){
//			AdExternalProgram program = programList.get(0);
//			String url = program.getProgramPath();
//			//该路径是具体exe文件，正式在调用过程中，需要.exe上级目录
//			if(StringUtils.isNotBlank(url)){
//				if(url != null && url.contains("\\")){
//					url = url.replace("\\", "/");
//		    	}
//				String[] paths = url.split("/");
//		 
//				String[] realPath = new String[paths.length-1];
//				for (int i = 0; i < paths.length-1; i++) {
//					realPath[i] = paths[i];
//				}
//				String path = StringUtils.join(realPath, "/");
//				return FileUtils.getUploadFileRealPath()+path;
//			}
//
//		}
//		return "";
//		//return "C:\\Users\\Administrator\\Desktop\\ffmpeg\\bin";
//	}

	/**
	 * 获取输出文件名
	 * 
	 * @param inputPath
	 * @return
	 */
	private static String getOutputPath(String inputPath ,String type) {
		return inputPath.substring(0, inputPath.lastIndexOf(".")) + type;
	}
	
	/**
	 * 获取视频截图的输出路径
	 * 
	 * @param inputPath
	 * @return
	 */
	private static String getImagePath(String inputPath) {
		return inputPath.substring(0, inputPath.lastIndexOf(".")) + ".jpg";
	}
	
	/**
	 * 获取m2v的输出路径
	 * 
	 * @param inputPath
	 * @return
	 */
	private static String getM2vPath(String inputPath) {
		return inputPath.substring(0, inputPath.lastIndexOf(".")) + ".m2v";
	}
	
	/**
	 * 获取mp4的输出路径
	 * 
	 * @param inputPath
	 * @return
	 */
	private static String getMp4Path(String inputPath) {
		return inputPath.substring(0, inputPath.lastIndexOf(".")) + ".mp4";
	}
	
	
	
	public static void main(String[] args) {
	/*	String path="C:\\Users\\Administrator\\Desktop\\";
		String inputPath = path +"4.jpg";
		Date date = new Date();
//		convertVedio(inputPath);
//		ffmpegImageFormVedio(inputPath);
		ffmpegM2vFromImage(inputPath);
		Date date1 = new Date();
		long l = date1.getTime()-date.getTime();*/
		System.out.println(3*1000+"k");
	}
}
