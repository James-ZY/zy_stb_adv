
package com.gospell.aas.common.utils;



import com.gospell.aas.dto.adv.VedioInfo;
import com.xuggle.xuggler.ICodec;
import com.xuggle.xuggler.IContainer;
import com.xuggle.xuggler.IStream;
import com.xuggle.xuggler.IStreamCoder;

/**
 * 视频操作类
 * @author Administrator
 *
 */
public class VedioInfoUtil {

	/**
	 * 获取视频的时长，分辨率、大小
	 * @param filename
	 * @return
	 */
    public static VedioInfo getVedioInfo(String filename){
    	VedioInfo info = null;
 
    	if(FileUtils.checkfile(filename)){
            // first we create a Xuggler container object
            IContainer container = IContainer.make();
            // we attempt to open up the container
            int result = container.open(filename, IContainer.Type.READ, null);

            // check if the operation was successful
            if (result<0)
                return null;
            else
            	info = new VedioInfo();
            // query how many streams the call to open found
            int numStreams = container.getNumStreams();
            // query for the total duration
            long duration = container.getDuration();
            // query for the file size
            long fileSize = container.getFileSize();
            long secondDuration = duration/1000000;
            int bitRate = container.getBitRate();
            info.setBitRate(bitRate);
            info.setDuration(secondDuration);
            info.setFileSize(fileSize);
           String vedio_code = null;
            boolean isVedio = false;
            for (int i=0; i<numStreams; i++) {
                IStream stream = container.getStream(i);
                IStreamCoder coder = stream.getStreamCoder();
                if (coder.getCodecType() == ICodec.Type.CODEC_TYPE_VIDEO) {
                	isVedio = true;
                    info.setHeight(coder.getHeight());
                    info.setWidth(coder.getWidth());
                    String id  = coder.getCodecID().name();
                    if(!id.equals(VedioInfo.H264)&& !id.equals(VedioInfo.MPEG2TS)&&!id.equals(VedioInfo.MPEG2VIDEO)){
                    	vedio_code = null;
                    	break;
                    }else{
                    	vedio_code = coder.getCodecID().name();
                    }
                }
                if (coder.getCodecType() == ICodec.Type.CODEC_TYPE_AUDIO) {
                	 info.setAudioCodec(coder.getCodecID().name());
                }
   
            }
            info.setIsVedio(isVedio);
            info.setVedioCodec(vedio_code);
   
    	}
    	
    	String format = filename.substring(filename.lastIndexOf(".")+1);
    	info.setFormat(format);
    	return info;
    }
    public static void main(String[] args) {
    	VedioInfo info = getVedioInfo("C:\\Users\\Administrator\\Desktop\\video\\admin_20170323100050020.mp4");
    	//	VedioInfo info = getVedioInfo("D:\\apache-tomcat-7.0.62\\webapps\\upload\\advs\\admin\\vedio\\admin_20170323100050020.mp4");

		System.out.println("视频高度"+info.getHeight());
		System.out.println("视频宽度"+info.getWidth());
		System.out.println("视频大小"+info.getFileSize());
		System.out.println("视频时长(秒)"+info.getDuration());
		System.out.println("视频格式"+info.getFormat());
		System.out.println("码流"+info.getBitRate());
		System.out.println("视频编码"+info.getVedioCodec());
		System.out.println("音频编码"+info.getAudioCodec());
		System.out.println("H264"+info.getIsVedio());
		String vedio_code = info.getVedioCodec();
    	if(vedio_code == null){//为null，表示视频的编码格式不是mpeg2或者h.264
    		 
    	}
    	String audio_code = info.getAudioCodec();
    	//当视频编码为H264,音频编码为aac的时候，ffmpeg只需要把ts封装成MP4，vedio就可以展示了，其他情况，都需要进行转码
    	if(vedio_code.equals(VedioInfo.H264) ){
    		if( audio_code!= null && audio_code.equals(VedioInfo.AAC)){
    			System.out.println(true);
    		}else{
    			System.out.println(false);    			
    		}
    	}
	}
    
    
 

}
