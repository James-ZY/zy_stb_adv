
package com.gospell.aas.common.utils;



import java.io.File;
import java.io.IOException;

import net.sf.json.JSONObject;

import com.gospell.aas.dto.adv.FfprobeVedioInfo;

/**
 * 视频操作类
 * @author Administrator
 *
 */
public class FfprobeVedioInfoUtil {

	/**
	 * 获取视频的时长，分辨率、大小
	 * @param filename
	 * @return
	 */
	public static FfprobeVedioInfo getVedioInfo(String filename,String type){
		FfprobeVedioInfo info = null;
		System.out.println(filename);
		if(FileUtils.checkfile(filename)){
			File file=new File(filename);
			String content;
			try {
				info = new FfprobeVedioInfo();
				content = org.apache.commons.io.FileUtils.readFileToString(file,"UTF-8");
				JSONObject jsonObject=JSONObject.fromObject(content).getJSONObject("format");
				JSONObject vdjsonObject = null;
				JSONObject acjsonObject = null;
				if(type.equals("video")){
					vdjsonObject=(JSONObject) JSONObject.fromObject(content).getJSONArray("streams").get(0);
					if(vdjsonObject.getString("codec_type").equals("audio")){
						vdjsonObject = (JSONObject) JSONObject.fromObject(content).getJSONArray("streams").get(1);
						acjsonObject=(JSONObject) JSONObject.fromObject(content).getJSONArray("streams").get(0);
					}else{
						acjsonObject=(JSONObject) JSONObject.fromObject(content).getJSONArray("streams").get(1);
					}
					info.setAudioCodec(acjsonObject.getString("codec_name"));
					info.setBitRate(jsonObject.getInt("bit_rate"));
					info.setDuration(jsonObject.getLong("duration"));
				}else{
					vdjsonObject=(JSONObject) JSONObject.fromObject(content).getJSONArray("streams").get(0);
				}
				info.setHeight(vdjsonObject.getInt("height"));
				info.setWidth(vdjsonObject.getInt("width"));
				info.setFileSize(jsonObject.getLong("size"));
				info.setFormat(jsonObject.getString("filename").substring(filename.lastIndexOf(".")+1));
				info.setVedioCodec(vdjsonObject.getString("codec_name"));
				info.setIsVedio(vdjsonObject.getString("codec_type").equals("video")?true:false);
				com.gospell.aas.common.utils.FileUtils.delFile(filename);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return info;
	}
}
