/**
 * 
 */
package com.gospell.aas.webservice.rest.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gospell.aas.webservice.rest.Result;
import com.gospell.aas.webservice.rest.Result.Status;
import com.gospell.aas.webservice.rest.RsResponse;

/**
 * <p> Title: </p>
 * 
 * <p> Description: </p>
 * 
 * <p> Copyright: Copyright (c) 2014 by Free-Lancer </p>
 * 
 * <p> Company: Free-Lancer </p>
 * 
 * @author: free lance
 * @Email: free.lance@Gmail.com
 * @version: 2.0
 * @date: 2014-12-29 上午11:42:24
 * 
 */
@Path("/file")
public class FileRestResource {

    private static Logger logger = LoggerFactory.getLogger(FileRestResource.class);

    @Context
    ServletContext context;

    /**
     * 上传文件
     * 
     * @param inputStream
     * @param contentDisposition
     * @param context
     * @return
     */
    @POST
    @Path("/upload")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Result<String> uploadFile(@FormDataParam("file") InputStream fileInputStream,
            @FormDataParam("file") FormDataContentDisposition contentDispositionHeader) throws FileNotFoundException,
            IOException {
        String result = "文件上传失败";
        if (contentDispositionHeader != null && contentDispositionHeader.getFileName() != null
                && contentDispositionHeader.getFileName().trim().length() > 0) {
            try {
                // 处理中文编码
                String realPath = context.getRealPath("upload");

                String fileName = new String(contentDispositionHeader.getFileName().getBytes("ISO-8859-1"), "UTF-8");

                String uploadedFileLocation = realPath + File.separatorChar + getUploadCurrentTime() + fileName;

                // save the file to the server
                OutputStream outpuStream = new FileOutputStream(new File(uploadedFileLocation));
                int read = 0;
                // byte[] bytes = new byte[1024];
                byte[] buffer = new byte[4 * 1024];

                outpuStream = new FileOutputStream(new File(uploadedFileLocation));
                while ((read = fileInputStream.read(buffer)) != -1) {
                    outpuStream.write(buffer, 0, read);
                }
                outpuStream.flush();
                outpuStream.close();

                result = "文件:[" + uploadedFileLocation + "]上传成功";

            } catch (RuntimeException e) {
                logger.error(e.getMessage());
                // throw new RuntimeException(Response.Status.EXPECTATION_FAILED+": 文件上传失败");
                throw RsResponse.buildDefaultException(e);
            }
        }

        return Result.buildResult(Status.OK, result);

    }

    // 获取头上上传的当前时间
    private String getUploadCurrentTime() {
        
        return new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
    }
}
