package com.gospell.aas.common.utils.adv.rollimage;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.font.TextLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import com.gospell.aas.common.utils.font.ChineseCharUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gospell.aas.common.utils.EncodingUtil;
import com.gospell.aas.common.utils.PropertiesReadUtil;
import sun.awt.SunHints;


public class PictureUtil {

	private static final Logger logger = LoggerFactory.getLogger(PictureUtil.class);

	private static void createImage(String fileLocation, BufferedImage image ,String type) throws Exception{

		if(fileLocation != null && fileLocation.contains("\\")){
			fileLocation = fileLocation.replace("\\", "/");
		}

		String[] s=fileLocation.split("/");
		String mkdirPath = "";
		for (int i = 0; i < s.length-1; i++) {
			if(i==s.length -2){
				mkdirPath += s[i];
			}else{
				mkdirPath += s[i]+"/";
			}
		}
		File file = new File(mkdirPath);
		if(!file.exists()){
			file.mkdirs();
		}
		ImageIO.write(image, type, new File(fileLocation+"."+type));


	}



	/**
	 * 图片作为背景色生成横向素材
	 * @param path
	 * @param dto
	 * @throws Exception
	 */
	public static void imageAddText(String path,ImageDTO dto) throws Exception{
		String srcPath = dto.getBackImagePath();
		int textSize = dto.getTextSize();
		String text = dto.getText();
		int[] textColor = getRGB(dto.getTextColor());
		boolean isVertical = dto.getRollFlag().equals(0)?false:true;
		File srcImgFile = new File(srcPath);//得到文件
		Image srcImg = ImageIO.read(srcImgFile);//文件转化为图片
		int srcImgWidth = srcImg.getWidth(null);//获取图片的宽
		int srcImgHeight = srcImg.getHeight(null);//获取图片的高

		BufferedImage buffImage =  new BufferedImage(srcImgWidth, srcImgHeight, BufferedImage.TYPE_INT_RGB);
		//int height = buffImage.getHeight();
		Graphics2D graphics =(Graphics2D) buffImage.getGraphics();

		Font font = new Font(dto.getFont(),Font.PLAIN,textSize);
		graphics.drawImage(srcImg, 0, 0, srcImgWidth, srcImgHeight, null);
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		graphics.setColor(new Color(textColor[0], textColor[1], textColor[2]));
		graphics.setFont(font);

		// 横排文字:width不固定,height固定; 竖排文字:width固定,height不固定
		// 文字图片的宽
		int width = Math.round((isVertical ? srcImgWidth :srcImgHeight) );
		// 文字图片的高
		int height = Math.round((isVertical ? srcImgHeight : srcImgWidth));
		// 高、宽比例
		float radio = 1.1f;
		// 文字图片边框
		float border = (float) (textSize * 0.1);
		// 设置每行的固定高度，用于横排
		int line_height = Math.round(textSize * radio);
		// 设置每行的固定宽度度，用于竖排
		int line_width = Math.round(textSize * radio);
		String lines[] = text.split("\n");

		String line;
		TextLayout layout;
		float dwh = 0, dx = 0, dy = 0;
		for (int i = 0; i < lines.length; i++) {
			line = lines[i];
			if (StringUtils.isNotBlank(line)) {
				layout = new TextLayout(line, font, graphics.getFontRenderContext());
				dwh = Math.max(layout.getAdvance(), width);
				dy = (float) Math.min(-((isVertical ? textSize : line_height) - layout.getBounds().getHeight()) / 2, dy);
			}
		}

		graphics.setRenderingHint(SunHints.KEY_ANTIALIASING, SunHints.VALUE_ANTIALIAS_ON);
		graphics.setRenderingHint(SunHints.KEY_TEXT_ANTIALIASING, SunHints.VALUE_TEXT_ANTIALIAS_ON);
		graphics.setRenderingHint(SunHints.KEY_STROKE_CONTROL, SunHints.VALUE_STROKE_DEFAULT);
		graphics.setRenderingHint(SunHints.KEY_TEXT_ANTIALIAS_LCD_CONTRAST, 140);
		graphics.setRenderingHint(SunHints.KEY_FRACTIONALMETRICS, SunHints.VALUE_FRACTIONALMETRICS_ON);
		graphics.setRenderingHint(SunHints.KEY_RENDERING, SunHints.VALUE_RENDER_QUALITY);

		// 初始化第一个字的坐标
		float xpos = isVertical ? width : border + dx, ypos = border + dy;
		// 每行字
		for (int i = 0; i < lines.length; i++) {
			line = lines[i];
			if (isVertical) {
				xpos -= line_width;
				xpos = Math.round(xpos/2);
				ypos = border + dy;
			} else {
				xpos = border + dx;
				ypos += line_height;
			}
			// 如果该行为空行，直接跳过
			if (StringUtils.isBlank(lines[i])) {
				continue;
			}
			// 每个字符
			for (int j = 0; j < line.length(); j++) {
				char c = line.charAt(j);
				// 用于获取字的该advance
				layout = new TextLayout(String.valueOf(c), font, graphics.getFontRenderContext());
				graphics.scale(1.0, 1.0); // 比例
				if (c > 32 && c < 126 && isVertical) {
					graphics.rotate(Math.PI / 2, xpos, ypos + layout.getAdvance());
					graphics.drawString(String.valueOf(c),   xpos  , ypos+ layout.getAdvance());
					graphics.rotate(-Math.PI / 2, xpos, ypos + layout.getAdvance());
					System.out.println(c + ", xy:xpos =" + xpos + ",ypos=" + (ypos + layout.getAdvance()));

				} else {
					graphics.drawString(String.valueOf(c), xpos, isVertical ? ypos + layout.getAdvance() : ypos);
					System.out.println(c + ", xy:xpos =" + xpos + ",ypos=" + (ypos + layout.getAdvance()));
				}

				if (isVertical) {
					ypos += layout.getAdvance();
				} else {
					xpos += layout.getAdvance();
				}
			}
		}


		FileOutputStream outImg = new FileOutputStream(new File(path+".jpg"));
		ImageIO.write(buffImage,"jpg", outImg);
		outImg.flush();
		outImg.close();

	}


	/**
	 * 当背景色为自己手动选择的纯色背景
	 * @param path 保存路径
	 * @param dto
	 * @throws Exception
	 */
	public static void graphicsImage(String path,ImageDTO dto) throws Exception{
	/*	if (dto.getIsPurity() == ImageDTO.IMAGE_PURITY_TSP) {
			if (dto.getTextColor().equals("255,255,255,1")) {
				getTmbjPic(path, dto);
			} else {
				dto.setBackground("255,255,255,1");
				getPrmPic(path, dto);
			}
		} else {
			getPrmPic(path, dto);
		}*/
		CreateFontImgWithGraphics(path, dto);
	}


	public static int[] getRGB(String color){
		int[] rgb = new int[]{255,0,0,0};
		if(org.apache.commons.lang3.StringUtils.isNotBlank(color)){
			String [] colors = color.split(",");
			if(colors.length ==4){
				for (int i = 0; i < colors.length; i++) {
					try{
						rgb[i] = Integer.parseInt(colors[i]);
					}catch(Exception e){
						rgb[i] =0;
					}
				}
			}
		}

		return rgb;

	}


	/**
	 * 根据文字的长度和文字大小计算大小
	 * @return
	 */
	private static int getWidth(String flag,String text,int textSize,int resize){
		int defaultWidth = 720;
		if(flag.equals("1")){
			defaultWidth = 1280;
		}
		if(text != null && text.trim().length() >0){
			char[] c = text.toCharArray();
			int totalWidth = 0;
			//int totalWidth = text.length() * textSize;
			for (int i = 0; i < c.length; i++) {
				if(CharUtil.isChinese(c[i])){
					int w = textSize+1;
					totalWidth +=w;
				}else{
					totalWidth += textSize/2+1;
				}
			}
			if(flag.equals("0")){
				if(totalWidth >720 && totalWidth <=7200){
					defaultWidth = totalWidth;
				}else if(totalWidth >7200){
					defaultWidth = 7200;
				}
			}else{
				if(totalWidth >1280 && totalWidth <=7200){
					defaultWidth = totalWidth;
				}else if(totalWidth >7200){
					defaultWidth = 7200;
				}
			}


		}
		return defaultWidth*resize;
	}

	/**
	 * 根据文字的长度和文字大小计算大小
	 * @return
	 */
	private static int chenkWidth(String flag,int width){
		int defaultWidth = 720;
		if(flag.equals("1")){
			defaultWidth = 1280;
		}
		if(flag.equals("0")){
			if(width >720 && width <=7200){
				defaultWidth = width;
			}else if(width >7200){
				defaultWidth = 7200;
			}
		}else{
			if(width >1280 && width <=7200){
				defaultWidth = width;
			}else if(width >7200){
				defaultWidth = 7200;
			}
		}
		return defaultWidth;
	}

	public static void convert(String path) {
		// TODO Auto-generated constructor stub
		try {
			BufferedImage image = ImageIO.read(new File(path));
			ImageIcon imageIcon = new ImageIcon(image);
			BufferedImage bufferedImage = new BufferedImage(
					imageIcon.getIconWidth(), imageIcon.getIconHeight(),
					BufferedImage.TYPE_4BYTE_ABGR);
			Graphics2D g2D = (Graphics2D) bufferedImage.getGraphics();
			g2D.drawImage(imageIcon.getImage(), 0, 0,
					imageIcon.getImageObserver());
			int alpha = 0;
			for (int j1 = bufferedImage.getMinY(); j1 < bufferedImage
					.getHeight(); j1++) {
				for (int j2 = bufferedImage.getMinX(); j2 < bufferedImage
						.getWidth(); j2++) {
					int rgb = bufferedImage.getRGB(j2, j1);
					if (colorInRange(rgb))
						alpha = 0;
					else
						alpha = 255;
					rgb = (alpha << 24) | (rgb & 0x00ffffff);
					bufferedImage.setRGB(j2, j1, rgb);
				}
			}
			g2D.drawImage(bufferedImage, 0, 0, imageIcon.getImageObserver());
			// 生成图片为PNG
			String outFile = path.substring(0, path.lastIndexOf("."));
			ImageIO.write(bufferedImage, "png", new File(outFile + ".png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static boolean colorInRange(int color) {
		int red = (color & 0xff0000) >> 16;
		int green = (color & 0x00ff00) >> 8;
		int blue = (color & 0x0000ff);
		if (red >= color_range && green >= color_range && blue >= color_range)
			return true;
		return false;
	}

	public static int color_range = 210;
	public static Pattern pattern = Pattern.compile("[0-9]*");

	public static boolean isNo(String str) {
		return pattern.matcher(str).matches();
	}

	/**
	 * 创建横竖向的滚动广告
	 * @param path
	 * @param dto
	 */
	public static void  CreateFontImgWithGraphics(String path,ImageDTO dto){
		int resize=1;
		int bold = dto.getIsBold(); // 是否加粗
		int italic = dto.getIsItalic(); // 是否斜体
		int size = dto.getTextSize()*resize;
		String text = dto.getText().replace("\n", "");
		int[] background = getRGB(dto.getBackground());
		int[] textColor = getRGB(dto.getTextColor());
		Font font = new Font(dto.getFont(),Font.PLAIN,size);
		font = deriveFont(font, bold, italic, size);

		boolean isVertical = dto.getRollFlag().equals(1)?false:true;
		int textWidth=getWidth(dto.getFlag(),text, size,resize);
		// 字体大小
		int fontSize = font.getSize();
		// 高、宽比例
		float radio = 1.1f;
		// 文字图片边框
		float border = (float) (fontSize * 0.1);
		// 设置每行的固定高度，用于横排
		int line_height = Math.round(fontSize * radio);
		System.out.println("line_height="+line_height);
		// 设置每行的固定宽度度，用于竖排
		int line_width = Math.round(fontSize * radio);
		System.out.println("line_width="+line_width);
		// 文字
		String lines[] = text.split("\n");
		String line;
		TextLayout layout;
		// 计算图片的width,height
		BufferedImage tmp = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
		Graphics2D gtmp = (Graphics2D) tmp.getGraphics();
		// dwh用于根据实际文件来计算图片某一边的长度,dx用于对图片水平方向的空白补齐,dy用于对图片垂直方向的空白补齐
		float dwh = 0, dx = 0, dy = 0;
		for (int i = 0; i < lines.length; i++) {
			line = lines[i];
			if (StringUtils.isNotBlank(line)) {
				layout = new TextLayout(line, font, gtmp.getFontRenderContext());
				dwh = Math.max(layout.getAdvance(), dwh);
				dy = (float) Math.min(-((isVertical ? fontSize : line_height) - layout.getBounds().getHeight()) / 2, dy);
			}
		}
		// 横排文字:width不固定,height固定; 竖排文字:width固定,height不固定
		// 文字图片的宽
		int width = Math.round((isVertical ? dto.getImageHeight() : dwh+line_width) );
		// 文字图片的高
		int height = Math.round((isVertical ? dwh+line_width : dto.getImageHeight()));
		if(dto.getRollFlag().equals(1)){
			int defWid = chenkWidth(dto.getFlag(),width);
			width = defWid;
		}else{
			int defHig = chenkWidth(dto.getFlag(),height);
			height = defHig;
		}
		System.out.println("width="+width);
		System.out.println("height="+height);

		// 创建文字图片

		// 创建BufferedImage对象
		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		// 获取Graphics2D
		Graphics2D graphics = (Graphics2D) image.getGraphics();
/*
		graphics.setColor(new Color(background[0], background[1], background[2]));
		graphics.fillRect(0, 0, width, height);
		graphics.setColor(new Color(textColor[0], textColor[1], textColor[2]));
		graphics.setFont(new Font(dto.getFont(), Font.PLAIN,size));
		*/
		if (dto.getIsPurity() == ImageDTO.IMAGE_PURITY_TSP) {
			if (dto.getTextColor().equals("255,255,255,1")) {
				// ----------  增加下面的代码使得背景透明  -----------------
				image = graphics.getDeviceConfiguration().createCompatibleImage(width, height, Transparency.TRANSLUCENT);
				graphics.dispose();
				graphics = image.createGraphics();
				// ----------  背景透明代码结束  -----------------
				// 画图
				graphics.setColor(new Color(background[0], background[1], background[2]));
				graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				graphics.setColor(new Color(textColor[0], textColor[1], textColor[2]));
				graphics.setFont(font);
				graphics.setStroke(new BasicStroke(1));
			} else {
				dto.setBackground("255,255,255,1");
				background = getRGB(dto.getBackground());
				graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				graphics.setColor(new Color(background[0], background[1], background[2]));
				graphics.fillRect(0, 0, width, height);
				graphics.setColor(new Color(textColor[0], textColor[1], textColor[2]));
				graphics.setFont(font);

			}
		} else {
			graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			graphics.setColor(new Color(background[0], background[1], background[2]));
			graphics.fillRect(0, 0, width, height);
			graphics.setColor(new Color(textColor[0], textColor[1], textColor[2]));
			graphics.setFont(font);
		}


		graphics.setRenderingHint(SunHints.KEY_ANTIALIASING, SunHints.VALUE_ANTIALIAS_ON);
		graphics.setRenderingHint(SunHints.KEY_TEXT_ANTIALIASING, SunHints.VALUE_TEXT_ANTIALIAS_ON);
		graphics.setRenderingHint(SunHints.KEY_STROKE_CONTROL, SunHints.VALUE_STROKE_DEFAULT);
		graphics.setRenderingHint(SunHints.KEY_TEXT_ANTIALIAS_LCD_CONTRAST, 140);
		graphics.setRenderingHint(SunHints.KEY_FRACTIONALMETRICS, SunHints.VALUE_FRACTIONALMETRICS_ON);
		graphics.setRenderingHint(SunHints.KEY_RENDERING, SunHints.VALUE_RENDER_QUALITY);
		// 初始化第一个字的坐标
		float xpos = isVertical ? width : border + dx, ypos = border + dy;
		// 每行字
		int cfl = 1;
		for (int i = 0; i < lines.length; i++) {
			line = lines[i];
			if (isVertical) {
				xpos -= line_width;
				xpos = Math.round(xpos/2);
				ypos = border + dy;
			} else {
				xpos = border + dx;
				ypos += line_height;
			}
			// 如果该行为空行，直接跳过
			if (StringUtils.isBlank(lines[i])) {
				continue;
			}
			// 每个字符
			for (int j = 0; j < line.length(); j++) {
				char c = line.charAt(j);
				// 用于获取字的该advance
				layout = new TextLayout(String.valueOf(c), font, graphics.getFontRenderContext());
				graphics.scale(1.0, 1.0); // 比例
				if (((c > 32 && c < 126) || CharUtil.isChinesePunctuation(c)) && isVertical) {
					graphics.rotate(Math.PI / 2, xpos, ypos + layout.getAdvance());
					graphics.drawString(String.valueOf(c),   xpos  , ypos+ layout.getAdvance());
					graphics.rotate(-Math.PI / 2, xpos, ypos + layout.getAdvance());
					System.out.println(c + ", xy:xpos =" + xpos + ",ypos=" + (ypos + layout.getAdvance()));
					cfl = 0;
				} else {
					/*if(cfl == 0){
						if (isVertical) {
							ypos += layout.getAdvance()/2;
						} else {
							xpos += layout.getAdvance()/2;
						}
					}*/
					graphics.drawString(String.valueOf(c), xpos, isVertical ? ypos + layout.getAdvance() : ypos);
					cfl = 1;
					System.out.println(c + ", xy:xpos =" + xpos + ",ypos=" + (ypos + layout.getAdvance()));
				}

				if (isVertical) {
					ypos += layout.getAdvance();
				} else {
					xpos += layout.getAdvance();
				}
			}
		}
		//System.out.println("width:" + width + ", height:" + height);
		// g.setStroke(new BasicStroke(4.0f));// 线条粗细
		// g.setColor(Color.blue);// 线条颜色
		// g.drawLine(440, 0, 440, 580);// 线条起点及终点位置
		// g.setStroke(new BasicStroke(4.0f));// 线条粗细
		// g.setColor(Color.red);// 线条颜色
		// g.drawLine(0, 110, 620, 110);// 线条起点及终点位置
/*
		System.out.println("xy:xpos =" + xpos + ",ypos=" + ypos );
*/

		graphics.dispose();
		try {
			if (dto.getIsPurity() == ImageDTO.IMAGE_PURITY_TSP) {
				if (dto.getTextColor().equals("255,255,255,1")) {
					createImage(path, image,"png");
				} else {
					createImage(path, image,"jpg");
					convert(path+".jpg");
				}
			} else {
				createImage(path, image,"jpg");
			}


		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private static Font deriveFont(Font font, int bold, int italic, int size) {
		int style = Font.PLAIN;
		if (bold > 0) {
			style = style | Font.BOLD;
		}
		if (italic > 0) {
			style = style | Font.ITALIC;
		}
		return font.deriveFont(style, size);
	}

	public static void main(String[] args) {
		ImageDTO dto = new ImageDTO();
		dto.setBackground("255,255,255,1");
		dto.setFlag("1");
		dto.setImageHeight(30);
		dto.setIsPurity(1);
		dto.setRollFlag(2);
		// dto.setText("asdsadsafsafsafwqrwqrqeFfdfdsfdfsafasf15rwqr1d15fr1wqr1wr1f15erqr51df1gf4g5re6tr01g1s3f1ew51fds1fs3g4we65f14ds1fe56wg41ew1fds");
		dto.setText("asdsadsafsafsafwqrwqrqeFfdfdsfdfsafasf15rwqr1d15fr1wqr1wr1f15erqr51df1gf4g5re6tr01g1s3f1ew51fds1fs3g4we65f14ds1fe56wg41ew1fds");
		dto.setTextColor("255,0,0,1");
		dto.setTextSize(18);
		dto.setFont(PropertiesReadUtil.ROLL_CHINESE_FONT);
		dto.setBackImagePath("C:\\Users\\Administrator\\Desktop\\广告测试素材\\字幕广告背景素材\\789.png");
		//CreateFontImgWithGraphics("C:\\test123", dto);
		try {
			imageAddText("C:\\test123", dto);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}