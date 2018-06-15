package com.gospell.aas.common.utils.adv.rollimage;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Transparency;
import java.awt.image.BufferedImage;      
import java.io.ByteArrayInputStream;      
import java.io.ByteArrayOutputStream;      
import java.io.File;      
import java.io.IOException;      
     




import javax.imageio.ImageIO;      
     




import sun.misc.BASE64Decoder;      
import sun.misc.BASE64Encoder;      
     
public class TestImageBinary {      
    static BASE64Encoder encoder = new sun.misc.BASE64Encoder();      
    static BASE64Decoder decoder = new sun.misc.BASE64Decoder();      
          
    public static void main(String[] args) {      
       /* System.out.println(getImageBinary());      
              
        base64StringToImage(getImageBinary());  */    
    	
    	int width = 720;
    	int height = 30;
    	// 创建BufferedImage对象
    	BufferedImage image = new BufferedImage(width, height,     BufferedImage.TYPE_INT_RGB);
    	// 获取Graphics2D
    	Graphics2D g2d =image.createGraphics();

    	// ----------  增加下面的代码使得背景透明  -----------------
    	image = g2d.getDeviceConfiguration().createCompatibleImage(width, height, Transparency.TRANSLUCENT);
    	g2d.dispose();
    	g2d = image.createGraphics();
    	// ----------  背景透明代码结束  -----------------


    	// 画图
    	g2d.setColor(new Color(255,255,255));
    	g2d.setStroke(new BasicStroke(1));
    	g2d.drawString("增加下面的代码使得背景透明增加下面的代码使得背景透明",0,20);
    	//释放对象
    	g2d.dispose();
    	// 保存文件    
    	try {
			ImageIO.write(image, "png", new File("c:/test.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }      
          
    static String getImageBinary(){      
        File f = new File("c://20090709442.jpg");             
        BufferedImage bi;      
        try {      
            bi = ImageIO.read(f);      
            ByteArrayOutputStream baos = new ByteArrayOutputStream();      
            ImageIO.write(bi, "jpg", baos);      
            byte[] bytes = baos.toByteArray();      
                  
            return encoder.encodeBuffer(bytes).trim();      
        } catch (IOException e) {      
            e.printStackTrace();      
        }      
        return null;      
    }      
          
    static void base64StringToImage(String base64String){      
        try {      
            byte[] bytes1 = decoder.decodeBuffer(base64String);      
                  
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes1);      
            BufferedImage bi1 =ImageIO.read(bais);      
            File w2 = new File("c://QQ.bmp");//可以是jpg,png,gif格式      
            ImageIO.write(bi1, "jpg", w2);//不管输出什么格式图片，此处不需改动      
        } catch (IOException e) {      
            e.printStackTrace();      
        }      
    }      
     
}   