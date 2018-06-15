package com.gospell.aas.common.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//import sun.misc.BASE64Encoder;

/**
 * 
 * <p> Title: 通用工具类</p>
 * 
 * <p> Description: </p>
 * 
 * <p> Copyright: Copyright (c) 2014 by ACTEC </p>
 * 
 * <p> Company: Free-Lancer </p>
 * 
 * @author: WDL & free lance
 * @version: 1.0
 * @date: 2014年2月26日 上午10:01:02
 * 
 */
public class GlobalUtil {
    private static Logger logger = LoggerFactory.getLogger(GlobalUtil.class);

    /**
     * 格式化日期
     * 
     * @param date
     * @return
     */
    public static String formatDate(Date date) {
        return new SimpleDateFormat(Constant.FORMAT).format(date);
    }

    /**
     * 字符串日期转毫秒
     * @param str
     * @return
     */
    public static long formatDateStrToLong(String str){
    	long time = 0;
		try {
			time = new SimpleDateFormat(Constant.FORMAT).parse(str).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return time;
		
    }
    /**
     * 毫秒转字符串日期
     * @param arg
     * @return
     */
    public static String formatDateLongToStr(long arg) {
		DateFormat formatter = new SimpleDateFormat(Constant.FORMAT);
		return formatter.format(arg);
    }
    /**
     * 返回用户的IP地址
     * 
     * @param request
     * @return
     */
    public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        return ip;
    }

    /**
     * 返回本地IP地址
     * 
     * @return
     */
    public static String getIpAddress() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            logger.error(e.getMessage());
            return "";
        }

    }

    /**
     * 简单的MD5加密，平常使用
     * 
     * @param password
     * @return
     */
    public static String getMd5(String password) {
        String str = "";
        if (password != null && !password.equals("")) {
            try {
                MessageDigest md = MessageDigest.getInstance("MD5");
                // BASE64Encoder base = new BASE64Encoder();
                // 加密后的字符串
                // str = base.encode(md.digest(password.getBytes("utf-8")));
                str = Encodes.encodeBase64(md.digest(password.getBytes("utf-8")));
            } catch (Exception e) {
                logger.error(e.getMessage());
                e.printStackTrace();
            }
        }
        return str;
    }

    /**
     * MD5加密(32位)
     * 
     * @param instr
     *            要加密的字符串
     * @return 返回加密后的字符串
     */
    public final static String encoderByMd5With32Bit(String instr) {
        char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
        try {
            if (instr != null && !"".equals(instr)) {
                byte[] strTemp = instr.getBytes();
                // MD5计算方法
                MessageDigest mdTemp = MessageDigest.getInstance("MD5");
                mdTemp.update(strTemp);
                byte[] md = mdTemp.digest();
                int j = md.length;
                char str[] = new char[j * 2];
                int k = 0;
                for (int i = 0; i < j; i++) {
                    byte byte0 = md[i];
                    str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                    str[k++] = hexDigits[byte0 & 0xf];
                }
                return new String(str);
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取随机的UUID字符串
     * 
     * @return String
     */
    public static String getUUID() {
        return UUID.randomUUID().toString();
    }

    /**
     * 判断变量是否为空
     * 
     * @param s
     * @return
     */
    public static boolean isEmpty(String s) {
        if (null == s || "".equals(s) || "".equals(s.trim()) || "null".equalsIgnoreCase(s)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 用来去掉List中空值和相同项的。
     * 
     * @param list
     * @return
     */
    public static List<String> removeSameItem(List<String> list) {
        List<String> difList = new ArrayList<String>();
        for (String t : list) {
            if (t != null && !difList.contains(t)) {
                difList.add(t);
            }
        }
        return difList;
    }

    /**
     * 字符在字符串中出现的次数
     * 
     * @param string
     * @param a
     * @return
     */
    public static int occurTimes(String string, String a) {
        int pos = -2;
        int n = 0;

        while (pos != -1) {
            if (pos == -2) {
                pos = -1;
            }
            pos = string.indexOf(a, pos + 1);
            if (pos != -1) {
                n++;
            }
        }
        return n;
    }

    //读文件，返回字符串
    public static String ReadFile(String path){
        File file = new File(path);
        BufferedReader reader = null;
        StringBuffer sb = new StringBuffer();
        try {
            //System.out.println("以行为单位读取文件内容，一次读一整行：");
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            //一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                sb.append(tempString);
            }
            reader.close();
        } catch (IOException e) {
            logger.error(e.toString());
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                    logger.error(e1.toString());
                }
            }
        }
        return sb.toString();
    }

    //把json格式的字符串写到文件
    public static void writeFile(String filePath, String sets) throws IOException {
        FileWriter fw = new FileWriter(filePath);
        PrintWriter out = new PrintWriter(fw);
        out.write(sets);
        out.println();
        fw.close();
        out.close();
    }

    public static String getCookieValue(Cookie[] cookies, String cookieName, String defaultValue) {
        String result = defaultValue;
        if (cookies != null) {
            for (int i = 0; i < cookies.length; i++) {
                Cookie cookie = cookies[i];
                if (cookieName.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return result;
    }

}
