package com.gospell.aas.common.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gospell.aas.common.config.Global;
/**
 * 数据库备份还原
 * @author Administrator
 *
 */
public class DataBaseManage{
private static String backPath = null;
private static Logger logger = LoggerFactory.getLogger(DataBaseManage.class);
	
    //获取数据库安装路径
    public static String getMysqlPath(){
		String sql = "select @@basedir as basePath from dual";
		DBHelper db = new DBHelper(sql);
		ResultSet ret = null;
		String path = null;
		try {  
            ret = db.pst.executeQuery();//执行语句，得到结果集  
            while (ret.next()) {  
                path = ret.getString(1);                
            }//显示数据  
            ret.close();  
            db.close();//关闭连接  
        } catch (SQLException e) {  
            e.printStackTrace();  
        }  
		return path;
		
	}
	public static String backup() {
        try {
            Runtime rt = Runtime.getRuntime();
 
            // 调用 调用mysql的安装目录的命令
            String commcand = getMysqlPath()+"/bin/mysqldump -h localhost -u"+Global.getConfig("jdbc.username")+" -p"+Global.getConfig("jdbc.password")+" "+Global.getConfig("jdbc.databaseName");
            Process child = rt.exec(commcand);
            // 设置导出编码为utf-8。这里必须是utf-8
            // 把进程执行中的控制台输出信息写入.sql文件，即生成了备份文件。注：如果不对控制台信息进行读出，则会导致进程堵塞无法运行
            InputStream in = child.getInputStream();// 控制台的输出信息作为输入流
 
            InputStreamReader xx = new InputStreamReader(in, "utf-8");
            // 设置输出流编码为utf-8。这里必须是utf-8，否则从流中读入的是乱码
 
            String inStr;
            StringBuffer sb = new StringBuffer("");
            String outStr;
            // 组合控制台输出信息字符串
            BufferedReader br = new BufferedReader(xx);
            while ((inStr = br.readLine()) != null) {
                sb.append(inStr + "\r\n");
            }
            outStr = sb.toString();  
            // 要用来做导入用的sql目标文件：
  		    String web = System.getProperty("web.root");
  		    // 获取项目的运行路径
            String realPath =web.substring(0, web.indexOf("webapps", 0))+ "webapps";
            //设置安装路径
            String path = "/upload/advs/database/";
            //设置名字
            String fileName = getUploadCurrentTime()+".sql";
            //创建文件夹
            File file = new File(realPath+path);  
            if(!file.exists()){  
                file.mkdirs();  
            } 
            //创建文件
            File file1 = new File(realPath+path+fileName);  
            if(!file1.exists()){  
                file1.createNewFile();
            }
            //保存文件
            FileOutputStream fout = new FileOutputStream(realPath+path+fileName);
            OutputStreamWriter writer = new OutputStreamWriter(fout, "utf-8");
            writer.write(outStr);
            writer.flush();
            in.close();
            xx.close();
            br.close();
            writer.close();
            fout.close();
            System.out.println(DateUtils.getDateTime());
            System.out.println("backup success!");
            logger.info("backup success!");
            backPath = path+fileName;
        } catch (Exception e) {
            e.printStackTrace();
        }
		return backPath;
 
    }
	
	public static void restore(String filepath) {
        try {
            Runtime runtime = Runtime.getRuntime();
            String commcand = "mysql -hlocalhost -u"+Global.getConfig("jdbc.username")+" -p"+Global.getConfig("jdbc.password")+" --default-character-set=utf8 "+Global.getConfig("jdbc.databaseName");
            //e:\\MySQL\\bin\\mysql.exe -hlocalhost -uroot -p123 --default-character-set=utf8 databasename"
            System.out.println(commcand);
            Process process = runtime.exec(commcand);
            OutputStream outputStream = process.getOutputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    new FileInputStream(filepath), "utf-8"));
            String str = null;
            StringBuffer sb = new StringBuffer();
            while ((str = br.readLine()) != null) {
                sb.append(str + "\r\n");
            }
            str = sb.toString();
            // System.out.println(str);
            OutputStreamWriter writer = new OutputStreamWriter(outputStream,
                    "utf-8");
            writer.write(str);
            writer.flush();
            outputStream.close();
            br.close();
            writer.close();
            System.out.println("restore success!");
            logger.info("restore success!");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	 private static String getUploadCurrentTime() {
	        return new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
	    }
}
