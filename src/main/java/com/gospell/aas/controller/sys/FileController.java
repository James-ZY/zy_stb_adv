/**
 * 
 */
package com.gospell.aas.controller.sys;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.POST;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.Maps;
import com.gospell.aas.common.config.Global;
import com.gospell.aas.common.persistence.BaseEntity;
import com.gospell.aas.common.utils.FileUtils;
import com.gospell.aas.common.utils.UserUtils;
import com.gospell.aas.common.utils.ffmpeg.ConvertDTO;
import com.gospell.aas.common.utils.ffmpeg.ConvertVedio;
import com.gospell.aas.common.utils.ffmpeg.FfmpegUtil;
import com.gospell.aas.common.utils.gif.GifFrameUtil;
import com.gospell.aas.controller.BaseController;
import com.gospell.aas.dto.adv.FfprobeVedioInfo;
import com.gospell.aas.entity.sys.SysParam;
import com.gospell.aas.service.sys.SysParamService;
import com.gospell.aas.webservice.rest.Result;

/**
 * <p> Title: 文件上传、下载 controller</p>
 * 
 * <p> Description: 包括图像上传、显示等</p>
 * 
 * <p> Copyright: Copyright (c) 2015 by Free Lancer </p>
 * 
 * <p> Company: Free Lancer </p>
 * 
 * @author: free lance
 * @Email: free.lance@Gmail.com
 * @version: 1.0
 * @date: 2015-1-13 上午11:10:25
 * 
 */
@Controller
@RequestMapping(value = "/file")
public class FileController extends BaseController {
 
	private static final Integer M2V_MAX_SIZE = Integer.parseInt(Global.getConfig("M2V_MAX_SIZE"));//单位kb
	private static final int GIF_MAX_FRAME_SIZE = Integer.parseInt(Global.getConfig("GIF_MAX_FRAME_SIZE"));
	
	@Autowired
	private SysParamService paramService;
    /**
     * 这里这里用的是MultipartFile file参数,所以前台就要用<input type="file" name="file"/>
     * 上传文件完毕后返回给前台[0`filepath],0表示上传成功(后跟上传后的文件路径),1表示失败(后跟失败描述)
     * 
     * @param file
     * @param request
     * @param response
     * @param type(主要用于判断图片大小)
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/upload")
    @POST
    public String uploadHeadPortrait(@RequestParam("file") MultipartFile file, HttpServletRequest request,
            HttpServletResponse response, Model model) throws IOException {
        // 如果用的是Tomcat服务器，则文件会上传到\\%TOMCAT_HOME%\\webapps\\YourWebProject\\upload\\文件夹中
        // 这里实现文件上传操作用的是commons.io.FileUtils类,它会自动判断/upload是否存在,不存在会自动创建
        String realPath = request.getSession().getServletContext().getRealPath("/upload");
        // 设置响应给前台内容的数据格式,在springmvc配置文件实现
        response.setContentType("text/plain; charset=UTF-8");     //这句话的意思，是让浏览器用utf8来解析返回的数据
        response.setCharacterEncoding("UTF-8");//这句话的意思，是告诉servlet用UTF-8转码，而不是用默认的ISO8859
        // 设置响应给前台内容的PrintWriter对象
        PrintWriter out = response.getWriter();
        // 上传文件的原名(即上传前的文件名字)
        String originalFilename = null;
        String newFilename = null;
        // 如果只是上传一个文件,则只需要MultipartFile类型接收文件即可,而且无需显式指定@RequestParam注解
        // 如果想上传多个文件,那么这里就要用MultipartFile[]类型来接收文件,并且要指定@RequestParam注解
        // 上传多个文件时,前台表单中的所有<input type="file"/>的name都应该是file,否则参数里的file无法获取到所有上传的文件
        if (file.isEmpty()) {
            out.print("1`" +getMessage("please.select.file"));
            out.flush();
            return null;
        } else {
            originalFilename = file.getOriginalFilename();
            newFilename = getUploadCurrentTime() + originalFilename; // 重新命名上传头像名称

            logger.info("文件原名: " + originalFilename);
            logger.info("文件名称: " + file.getName());
            logger.info("文件长度: " + file.getSize());
            logger.info("文件类型: " + file.getContentType());
            logger.info("新文件名: " + newFilename);
            logger.info("========================================");
            
        
            try {
                // 这里不必处理IO流关闭的问题,因为FileUtils.copyInputStreamToFile()方法内部会自动把用到的IO流关掉
                // 此处也可以使用Spring提供的MultipartFile.transferTo(File dest)方法实现文件的上传
                org.apache.commons.io.FileUtils.copyInputStreamToFile(file.getInputStream(), new File(realPath, newFilename));
            } catch (IOException e) {
                logger.error("文件[" + originalFilename + "]上传失败,堆栈轨迹如下", e);
                //e.printStackTrace();
                out.print("1`"+getMessage("file.upload.fail"));
                out.flush();
                return null;
            }
        }
        // 此时在Windows下输出的是[D:\\OpenSource\\apache-tomcat-7.0.57\\webapps\\mserver\\upload\愤怒的小鸟.jpg]
        // logger.info(realPath + "\\" + originalFilename);
        // 此时在Windows下输出的是[/AjaxFileUpload/upload/愤怒的小鸟.jpg]
        // logger.info(request.getContextPath() + "/upload/" + originalFilename);
        // 不推荐返回[realPath + "\\" + originalFilename]的值
        // 因为在Windows下<img src="file:///D:/aa.jpg">能被firefox显示,而<img src="D:/aa.jpg">firefox是不认的
        // out.print("0`" + "/upload/" + newFilename);
        out.print("0`" + request.getContextPath() + "/upload/" + newFilename);
        out.flush();
        return null;
    }
    
    /**
     * 这里这里用的是MultipartFile file参数,所以前台就要用<input type="file" name="file"/>
     * 上传文件完毕后返回给前台[0`filepath],0表示上传成功(后跟上传后的文件路径),1表示失败(后跟失败描述)
     * 
     * 
     * @param file
     * @param request
     * @param response
     * @param type(主要用于判断图片大小)
     * @return 返回图片路径，返回的名称按照用户名+时间格式来处理
     * @throws IOException
     */
    @RequestMapping(value = "/uploadImage")
    @POST
    public String upload(@RequestParam("file") MultipartFile file, HttpServletRequest request,
            HttpServletResponse response, Model model) throws IOException {
        // 如果用的是Tomcat服务器，则文件会上传到\\%TOMCAT_HOME%\\webapps\\YourWebProject\\upload\\文件夹中
        // 这里实现文件上传操作用的是commons.io.FileUtils类,它会自动判断/upload是否存在,不存在会自动创建
       // String realPath = request.getSession().getServletContext().getRealPath("/upload");
        // 设置响应给前台内容的数据格式,在springmvc配置文件实现
        response.setContentType("text/plain; charset=UTF-8");     //这句话的意思，是让浏览器用utf8来解析返回的数据
        response.setCharacterEncoding("UTF-8");//这句话的意思，是告诉servlet用UTF-8转码，而不是用默认的ISO8859
        // 设置响应给前台内容的PrintWriter对象
        PrintWriter out = response.getWriter();
        // 上传文件的原名(即上传前的文件名字)
        String originalFilename = null;
        String newFilename = null;
        String[] root = com.gospell.aas.common.utils.FileUtils.fileUploadAdr(request.getSession()
				.getServletContext().getRealPath(""));
		String r = root[0];
        if (file.isEmpty()) {
            out.print("1`" +getMessage("please.select.file"));
            out.flush();
            return null;
 
        } else {
        	
			String realPath = r + "/roll/backimage";
					 
            originalFilename = file.getOriginalFilename();
            newFilename = UserUtils.getUser().getLoginName()+"_"+getUploadCurrentTime()+"."+originalFilename.substring(originalFilename.lastIndexOf(".")+1); // 重新命名上传头像名称

            logger.info("文件原名: " + originalFilename);
            logger.info("文件名称: " + file.getName());
            logger.info("文件长度: " + file.getSize());
            logger.info("文件类型: " + file.getContentType());
            logger.info("新文件名: " + newFilename);
            logger.info("========================================");
            
        
            try {
                // 这里不必处理IO流关闭的问题,因为FileUtils.copyInputStreamToFile()方法内部会自动把用到的IO流关掉
                // 此处也可以使用Spring提供的MultipartFile.transferTo(File dest)方法实现文件的上传
                org.apache.commons.io.FileUtils.copyInputStreamToFile(file.getInputStream(), new File(realPath, newFilename));
            } catch (IOException e) {
                logger.error("文件[" + originalFilename + "]上传失败,堆栈轨迹如下", e);
                //e.printStackTrace();
                out.print("1`"+getMessage("file.upload.fail"));
                out.flush();
                return null;
            }
        }
        
        out.print("0`" +"/upload/" + root[1] + "/roll/backimage/"+newFilename);
        out.flush();
        return null;
    }

    /**
     * 将图片读到页面上
     * 
     * @param request
     *            内置对象
     * @param response
     *            内置对象
     * @param throws Exception 抛出异常
     * @return
     * 
     */
    @RequestMapping("/dumpImage")
    public void dumpImage(HttpServletRequest request, HttpServletResponse response) {
    	request.getSession().getServletContext().getRealPath("/upload");
        String paramPath = request.getParameter("path");
        String path = "";
        if (paramPath == null) {
            path = (String) request.getAttribute("path");
        } else {
            try {
                path = new String(paramPath.getBytes("ISO8859-1"), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                //e.printStackTrace();
                logger.error(e.getMessage(), e);
            }
        }
        String picPath = request.getSession().getServletContext().getRealPath("/") + File.separator + path;
        InputStream in = null;
        BufferedInputStream bis = null;
        OutputStream out = null;
        BufferedOutputStream bos = null;

        // 判断文件是否存在
        File file = new File(picPath);
        if (!file.exists() || file.isDirectory()) {
            return;
        }
        try {
            in = new FileInputStream(picPath);
            bis = new BufferedInputStream(in);

            byte[] data = new byte[1024];
            int bytes = 0;
            out = response.getOutputStream();
            bos = new BufferedOutputStream(out);
            while ((bytes = bis.read(data, 0, data.length)) != -1) {
                bos.write(data, 0, bytes);
            }
            bos.flush();
        } catch (Exception e) {
            //e.printStackTrace();
            logger.error(e.getMessage(), e);
        } finally {
            try {
                if (bos != null)
                    bos.close();
                if (out != null)
                    out.close();
                if (bis != null)
                    bis.close();
                if (in != null)
                    in.close();
            } catch (IOException e) {
              //e.printStackTrace();
              logger.error(e.getMessage(), e);
            }
        }
    }

    // 获取头上上传的当前时间
    private String getUploadCurrentTime() {
    	 Random random = new Random(); 
    	 int rannum = (int) (random.nextDouble() * (99999 - 10000 + 1)) + 10000;// 获取5位随机数  
         return new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date())+rannum;
    }
    /**
     * 普通文件上传
     * 
     * @param file
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/upload2")
    @ResponseBody
    public Map<String, Object> uploadFile(@RequestParam(value = "file") MultipartFile file, HttpServletRequest request,
            HttpServletResponse response) {
        Map<String, Object> resMap = Maps.newHashMap();
        if (file != null) {
            // 获取保存的路径，
            String realPath = request.getSession().getServletContext().getRealPath("/upload/apk");
            if (file.isEmpty()) {
                // 未选择文件
                resMap.put("status", Result.Status.NOT_EXIST_ERROR);
            } else {
                // 文件原名称
                String originFileName = file.getOriginalFilename();
                try {
                    // 这里使用Apache的FileUtils方法来进行保存
                    org.apache.commons.io.FileUtils.copyInputStreamToFile(file.getInputStream(), new File(realPath, originFileName));
                    resMap.put("status", Result.Status.OK);
                } catch (IOException e) {
                    logger.error("文件上传失败:" + e.getMessage(), e);
                    resMap.put("status", Result.Status.INTERNAL_SERVER_ERROR);
                    //e.printStackTrace();
                }
            }

        }
        return resMap;
    }
 
    
    /**
     * 这里这里用的是MultipartFile file参数,所以前台就要用<input type="file" name="file"/>
     * 上传文件完毕后返回给前台[0`filepath],0表示上传成功(后跟上传后的文件路径),1表示失败(后跟失败描述)
     * 
     * @param file
     * @param request
     * @param response
     * @param type(主要用于判断图片大小)
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/fileUpload")
    @POST
    public String uploadFile(@RequestParam("file") MultipartFile file,HttpServletRequest request,
            HttpServletResponse response, Model model) throws IOException{
    	/*
    	 *现在最新的文件上传路径是位于tomcat的webapps下的upload，为了防止重复，upload下的路劲为advs，在下就分为用户（比如admin），
    	 *admin下又分为vedio和image:例子：
    	 */
    	 request.getSession().getServletContext().getRealPath("/upload");
    	String[] root = com.gospell.aas.common.utils.FileUtils.fileUploadAdr(request.getSession().getServletContext().getRealPath(""));
    	String realPath = root[0];
        response.setContentType("text/plain; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        // 上传文件的原名(即上传前的文件名字)
        String originalFilename = null;
        String newFilename = null;
        String uploadPath=null;
        String addPath = UserUtils.getUser().getLoginName()+"/image";
        // 如果只是上传一个文件,则只需要MultipartFile类型接收文件即可,而且无需显式指定@RequestParam注解
        // 如果想上传多个文件,那么这里就要用MultipartFile[]类型来接收文件,并且要指定@RequestParam注解
        // 上传多个文件时,前台表单中的所有<input type="file"/>的name都应该是file,否则参数里的file无法获取到所有上传的文件
        if (file.isEmpty()) {
            out.print("0`" +getMessage("please.select.file"));
            out.flush();
            return null;
        } else {
            originalFilename = file.getOriginalFilename();
            uploadPath ="/upload/"+root[1]+"/"+addPath;
            newFilename =UserUtils.getUser().getLoginName()+"_"+ getUploadCurrentTime()+"."+ originalFilename.substring(originalFilename.lastIndexOf(".")+1);; // 重新命名上传文件名称
            try {
           
            	if(StringUtils.isNotBlank(originalFilename) && originalFilename.endsWith(".gif")){
            		 File convFile = new File( originalFilename);
            		 file.transferTo(convFile);
            		 int flag = GifFrameUtil.getGitFrame(convFile);
            		 if(flag != -1 && flag >GIF_MAX_FRAME_SIZE){
            			 out.print("-1`" +  originalFilename+getMessage("gif.less.than"));
                         out.flush();
                         return null;
            		 }
                     org.apache.commons.io.FileUtils.copyInputStreamToFile(new FileInputStream(convFile) ,new File(realPath+"/"+addPath, newFilename));
            	}else{
                    org.apache.commons.io.FileUtils.copyInputStreamToFile(file.getInputStream(), new File(realPath+"/"+addPath, newFilename));
            	}

            } catch (IOException e) {
                logger.error("文件[" + originalFilename + "]上传失败,堆栈轨迹如下", e);
                //e.printStackTrace();
                out.print("0`"+getMessage("file.upload.fail"));
                out.flush();
                return null;
            }
        }
        out.print("1`" + uploadPath+"/"+newFilename);
        ///upload//gos/apache-tomcat-7.0.64/webapps/advs/qq/image/qq_20160816150506756.JPG
        out.flush();
        return null;
    }
    
    @RequestMapping(value = "/vedioUpload")
    @POST
    public String uploadVedioFile(@RequestParam("file") MultipartFile file,HttpServletRequest request,
            HttpServletResponse response, Model model) throws IOException{
    	/*
    	 *现在最新的文件上传路径是位于tomcat的webapps下的upload，为了防止重复，upload下的路劲为advs，在下就分为用户（比如admin），
    	 *admin下又分为vedio和image:例子：
    	 */
    	 request.getSession().getServletContext().getRealPath("/upload");//防止每次部署之后被删除，因为临时文件是上传到该文件夹下
    	String[] root = com.gospell.aas.common.utils.FileUtils.fileUploadAdr(request.getSession().getServletContext().getRealPath(""));
    	String realPath = root[0];
        response.setContentType("text/plain; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        // 上传文件的原名(即上传前的文件名字)
        String originalFilename = null;
        String newFilename = null;
        String uploadPath=null;
        Map<String,Object> map = new HashMap<String,Object>();
		map.put("delFlag", BaseEntity.DEL_FLAG_NORMAL);
		map.put("paramType", SysParam.NETWORK_VIDEO_RATE);
		map.put("enable", SysParam.ENABLE_YES);
        SysParam param = paramService.getMinParam(map);
        Integer minRate = 6;
        if(param!=null){
        	minRate =Integer.parseInt(param.getParamValue());
        }
        String addPath = UserUtils.getUser().getLoginName()+"/vedio";
        if (file.isEmpty()) {
            out.print("0`" +getMessage("please.select.file"));
            out.flush();
            return null;
        } else {
            originalFilename = file.getOriginalFilename();
            uploadPath ="/upload/"+root[1]+"/"+addPath;
            newFilename =UserUtils.getUser().getLoginName()+"_"+getUploadCurrentTime()+"."+ originalFilename.substring(originalFilename.lastIndexOf(".")+1);; // 重新命名上传文件名称
            boolean isChange = true;
   		    boolean isSetRate = false;

            try {
            	org.apache.commons.io.FileUtils.copyInputStreamToFile(file.getInputStream(), new File(realPath+"/"+addPath, newFilename));
            	String returnFilePath = uploadPath+"/"+newFilename;
                String path = com.gospell.aas.common.utils.FileUtils.getUploadFileRealPath()+returnFilePath;
                FfprobeVedioInfo vedioInfo = FfmpegUtil.getFfprobeVedioInfo("",path,"video");
                if(vedioInfo == null){
                	 com.gospell.aas.common.utils.FileUtils.delFile(path);//删除上传成功的视频
       			  	 out.print("0`" +getMessage("upload.vedio.error"));//请上传正确的视频文件 
          		      out.flush();
          		      return null;
                }else{
                	String vedio_code = vedioInfo.getVedioCodec();
                	if(vedio_code == null){//为null，表示视频的编码格式不是mpeg2或者h.264
                		 com.gospell.aas.common.utils.FileUtils.delFile(path);//删除上传成功的视频
           			  	 out.print("0`" +getMessage("vedio.code.error"));//请选择MPEG2、H.264编码的ts流文件
              		     out.flush();
              		      return null;
                	}
                	String audio_code = vedioInfo.getAudioCodec();
                	//当视频编码为H264,音频编码为aac的时候，ffmpeg只需要把ts封装成MP4，vedio就可以展示了，其他情况，都需要进行转码
                	if(vedio_code.equals(FfprobeVedioInfo.H264) && audio_code!= null && audio_code.equals(FfprobeVedioInfo.AAC)){
                		isChange = false;
                	}
                }
            	if(StringUtils.isNotBlank(originalFilename) && !originalFilename.endsWith(".ts")){//如果不是ts流  先转换成MP4  再转换成 ts
            		ConvertDTO dto = ConvertVedio.convertVedio(isSetRate,minRate,path);//转换视频流
            		vedioInfo = FfmpegUtil.getFfprobeVedioInfo("",dto.getOutput(),"video");
            		if(vedioInfo.getBitRate()>minRate*1000000){
            			System.out.println("视频码率过大1");
            			isSetRate = true;
            			dto = ConvertVedio.convertVedio(isSetRate,minRate,path);
            		}
            		if(null != dto && dto.getStatus() == 0){
            			
            			//com.gospell.aas.common.utils.FileUtils.delFile(path);//删除用户上传的文件
            			ConvertDTO imageDto = ConvertVedio.ffmpegImageFormVedio(dto.getOutput());//获取视频截图
            			if(null !=imageDto && imageDto.getStatus() == 0){
            				String[] s = newFilename.split("\\.");
            				String returnFileName="";
            				for (int i = 0; i < s.length ; i++) {
            					if(i<(s.length-1))
            						returnFileName +=s[i];
            				}
                        	String srcPath = uploadPath+"/"+returnFileName+".ts";
                    		String imagePath =uploadPath+"/"+returnFileName+".jpg";
                       		out.print("1`" + srcPath+"`"+imagePath+"`"+returnFilePath);
               		      	out.flush();
               		      return null;
            			}else{
            			  com.gospell.aas.common.utils.FileUtils.delFile(dto.getOutput());//删除上传成功的文件
            			  out.print("0`" +getMessage("file.change.error") +":"+imageDto.getMessage());
               		      out.flush();
               		      return null;
            			}
            			 
            		}else{
            			  out.print("0`" +getMessage("file.change.error") +":"+dto.getMessage());
            		      out.flush();
            		      return null;
            		}
            	}else{//用户上传的是ts流 先获取ts码率  如果码率超过上限  将降低码率再保存
            		ConvertDTO imageDto = ConvertVedio.ffmpegImageFormVedio(path);//获取视频截图
        			if(null !=imageDto && imageDto.getStatus() == 0){
        				String[] s = newFilename.split("\\.");
        				String returnFileName="";
        				for (int i = 0; i < s.length ; i++) {
        					if(i<(s.length-1))
        						returnFileName +=s[i];
        				}
                    	String srcPath = uploadPath+"/"+returnFileName+".ts";
                		String imagePath =uploadPath+"/"+returnFileName+".jpg";
                		String tsPath = com.gospell.aas.common.utils.FileUtils.getUploadFileRealPath()+srcPath;
                		ConvertDTO convert = ConvertVedio.ffmpegMp4FormTs(minRate,tsPath,false);
                		vedioInfo = FfmpegUtil.getFfprobeVedioInfo("",tsPath,"video");
                		if(vedioInfo.getBitRate()>minRate*1000000){
                			System.out.println("视频码率过大");
                			isSetRate = true;
                			convert = ConvertVedio.convertVedio(isSetRate,minRate,convert.getOutput());
                		}
                		String mp4Path="";
		        		if(convert != null && ConvertDTO.CONVERT_SUCCESS_STATUS == convert.getStatus()){
		        			mp4Path= srcPath.substring(0, srcPath.lastIndexOf(".")).toLowerCase()+".mp4";// 重新命名上传文件名称
		        		}
                		out.print("1`" + srcPath+"`"+imagePath+"`"+mp4Path);
           		      	out.flush();
           		      return null;
        			}else{
        			  com.gospell.aas.common.utils.FileUtils.delFile(path);//删除上传成功的ts流
        			  out.print("0`" +getMessage("file.change.error"));
           		      out.flush();
           		      return null;
        			}
        			}
            	 
            } catch (IOException e) {
                logger.error("文件[" + originalFilename + "]上传失败,堆栈轨迹如下", e);
                e.printStackTrace();
                out.print("0`"+getMessage("file.upload.fail")+":"+e.getMessage());
                out.flush();
                return null;
            }
        }
    }
    
    @RequestMapping(value = "/programUpload")
    @POST
    public String uploadProgram(@RequestParam("file") MultipartFile file, HttpServletRequest request,
            HttpServletResponse response, Model model) throws IOException {
    	 request.getSession().getServletContext().getRealPath("/upload");
    	String[] root = com.gospell.aas.common.utils.FileUtils.fileUploadAdr(request.getSession().getServletContext().getRealPath(""));
    	String realPath = root[0];
        response.setContentType("text/plain; charset=UTF-8");     //这句话的意思，是让浏览器用utf8来解析返回的数据
        response.setCharacterEncoding("UTF-8");//这句话的意思，是告诉servlet用UTF-8转码，而不是用默认的ISO8859
        PrintWriter out = response.getWriter();
        String originalFilename = null;
        String addPath=null;
        if (file.isEmpty()) {
        	 
            out.print("1`"+getMessage("please.select.file"));
            out.flush();
            return null;
        } else {
            originalFilename = file.getOriginalFilename();
            
            addPath=UserUtils.getUser().getLoginName()+"/"+getUploadCurrentTime();
            logger.info("文件原名: " + originalFilename);
            logger.info("文件名称: " + file.getName());
            logger.info("文件长度: " + file.getSize());
            logger.info("文件类型: " + file.getContentType());
            try {
                // 这里不必处理IO流关闭的问题,因为FileUtils.copyInputStreamToFile()方法内部会自动把用到的IO流关掉
                // 此处也可以使用Spring提供的MultipartFile.transferTo(File dest)方法实现文件的上传
                org.apache.commons.io.FileUtils.copyInputStreamToFile(file.getInputStream(), new File(realPath+"/"+addPath, originalFilename));
            } catch (IOException e) {
                logger.error("文件[" + originalFilename + "]上传失败,堆栈轨迹如下", e);
                //e.printStackTrace();
                out.print("1`"+getMessage("file.upload.fail"));
                out.flush();
                return null;
            }
        }
        //System.out.println("测试后台国际化："+getMessage("file.upload.fail"));
        String r = "/upload/"+root[1]+"/"+addPath+"/"+ originalFilename;
 
        out.print("0`" + "/upload/"+root[1]+"/"+addPath+"/"+ originalFilename);//保证路径的唯一性
        out.flush();
        return null;
    }
    
    @RequestMapping(value = "/openImageFile")
    @POST
    public String uploadOpenImageFile(@RequestParam("file") MultipartFile file,HttpServletRequest request,
            HttpServletResponse response, Model model) throws IOException{
    	 request.getSession().getServletContext().getRealPath("/upload");
     	String[] root = com.gospell.aas.common.utils.FileUtils.fileUploadAdr(request.getSession().getServletContext().getRealPath(""));
    	String realPath = root[0];
        response.setContentType("text/plain; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        // 上传文件的原名(即上传前的文件名字)
        String originalFilename = null;
        String newFilename = null;
        String uploadPath=null;
        String addPath = UserUtils.getUser().getLoginName()+"/m2v";
        if (file.isEmpty()) {
            out.print("0`"+getMessage("please.select.file"));
            out.flush();
            return null;
        } else {
            originalFilename = file.getOriginalFilename();
            uploadPath ="/upload/"+root[1]+"/"+addPath;
            newFilename =UserUtils.getUser().getLoginName()+"_"+getUploadCurrentTime()+"."+ originalFilename.substring(originalFilename.lastIndexOf(".")+1);; // 重新命名上传文件名称
            try {
            	org.apache.commons.io.FileUtils.copyInputStreamToFile(file.getInputStream(), new File(realPath+"/"+addPath, newFilename));
            	String returnFilePath = uploadPath+"/"+newFilename;
                String path = com.gospell.aas.common.utils.FileUtils.getUploadFileRealPath()+returnFilePath;
            	ConvertDTO dto = ConvertVedio.ffmpegM2vFromImage(path);
            		if(null != dto && dto.getStatus() == 0){
            				BigDecimal file_con = com.gospell.aas.common.utils.FileUtils.getFileSize(dto.getOutput());
            				BigDecimal max = new BigDecimal(M2V_MAX_SIZE);
            				int compare = file_con.compareTo(max);
            				if(compare == 1){
            					//验证不通过后删除上传的文件
            		   		     FileUtils.delFile(path);
            		   		     FileUtils.delFile(dto.getOutput());
            					 out.print("-1`" +  originalFilename+getMessage("more.m2v.max.size"));
                                 out.flush();
                                 return null;
            				}
            				String[] s = newFilename.split("\\.");
            				String returnFileName="";
            				for (int i = 0; i < s.length ; i++) {
            					if(i<(s.length-1))
            						returnFileName +=s[i];
            				}
                        	String srcPath = uploadPath+"/"+returnFileName+".m2v";
                    		String imagePath =uploadPath+"/"+newFilename;
                    		out.print("1`" + srcPath+"`"+imagePath);
               		      	out.flush();
               		      return null;
            		}else{
            			  out.print("0`" + getMessage("file.change.error"));
            		      out.flush();
            		      return null;
            		}
            
            } catch (IOException e) {
                logger.error("文件[" + originalFilename + "]上传失败,堆栈轨迹如下", e);
                //e.printStackTrace();
                out.print("0`"+getMessage("file.upload.fail"));
                out.flush();
                return null;
            }
        }
    }
    
    /**
     * 帮助文档上传
     * @throws IOException
     */
    @RequestMapping(value = "/helpFileUpload")
    @POST
    public String helpFileUpload(@RequestParam("file") MultipartFile file,HttpServletRequest request,
            HttpServletResponse response, Model model) throws IOException{
    	/*
    	 *现在最新的文件上传路径是位于tomcat的webapps下的upload，为了防止重复，upload下的路劲为advs，在下就分为用户（比如admin），
    	 *admin下又分为vedio和image:例子：
    	 */
    	 request.getSession().getServletContext().getRealPath("/upload");
    	String[] root = com.gospell.aas.common.utils.FileUtils.fileUploadAdr(request.getSession().getServletContext().getRealPath(""));
    	String realPath = root[0];
        response.setContentType("text/plain; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        // 上传文件的原名(即上传前的文件名字)
        String originalFilename = null;
        String uploadPath=null;
        String addPath = "help"+"/"+UserUtils.getUser().getLoginName();
        // 如果只是上传一个文件,则只需要MultipartFile类型接收文件即可,而且无需显式指定@RequestParam注解
        // 如果想上传多个文件,那么这里就要用MultipartFile[]类型来接收文件,并且要指定@RequestParam注解
        // 上传多个文件时,前台表单中的所有<input type="file"/>的name都应该是file,否则参数里的file无法获取到所有上传的文件
        if (file.isEmpty()) {
            out.print("0`" +getMessage("please.select.file"));
            out.flush();
            return null;
        } else {
            originalFilename = file.getOriginalFilename();
            uploadPath ="/upload/"+root[1]+"/"+addPath;
            
           try{
            	if(StringUtils.isNotBlank(originalFilename)){
            		 originalFilename = isExsit(realPath+"/"+addPath+"/", originalFilename, 1);
            		org.apache.commons.io.FileUtils.copyInputStreamToFile(file.getInputStream(), new File(realPath+"/"+addPath, originalFilename));
            	}

            } catch (IOException e) {
                logger.error("文件[" + originalFilename + "]上传失败,堆栈轨迹如下", e);
                //e.printStackTrace();
                out.print("0`"+getMessage("file.upload.fail"));
                out.flush();
                return null;
            }
        }
        out.print("1`" + uploadPath+"/"+originalFilename+"`"+originalFilename);
      
        out.flush();
        return null;
    }
    /**
     * 判断一个文件是否重复，重复了创建副本，如果副本有了，创建副本的副本，依次叠加，直到没有
     * @param folder
     * @param fileName
     * @return
     */
    public  String isExsit(String folder,String fileName,int version){
    	String f = folder+fileName;
    	File isEx=new File(f);
		if(isEx.exists()){
			String message =getMessage("rename.file");
		 
			if(version == 1){
				fileName = fileName.substring(0,fileName.lastIndexOf("."))+"-"+message+"."+fileName.substring(fileName.lastIndexOf(".")+1); // 重新命名上传文件名称
			}else if(version == 2){
				fileName = fileName.substring(0,fileName.lastIndexOf("."))+"("+version+")"+"."+fileName.substring(fileName.lastIndexOf(".")+1); // 重新命名上传文件名称
				
			}else{
				String format = fileName.substring(fileName.lastIndexOf(".")+1);
				fileName = fileName.substring(0,fileName.lastIndexOf("."));
			
				fileName = fileName.substring(0,fileName.lastIndexOf("("));
				fileName = fileName+"("+version+")"+"."+format;
			}
			
			 version ++;
			 return isExsit(folder, fileName,version);
		}else{
			return fileName;
		}
		 
    }
 
   
}
