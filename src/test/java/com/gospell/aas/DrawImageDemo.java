package com.gospell.aas;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.font.TextLayout;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.apache.commons.lang.StringUtils;
import sun.awt.SunHints;

public class DrawImageDemo {

    public static void main(String[] args) {
        String fileName = "simsun.ttc";// 楷体
        int bold = 0; // 是否加粗
        int italic = 0; // 是否斜体
        int size = 30;
//        String text = "横排文字:123,不固定,h51242121。固定; 竖排文字:width固定,height不固定;横排文字:width不固定,height固定; 竖排文字:width固定,height不固定;横排文。";
//        String text = "aaaaa:123不固定,h.固定; 竖排文字dsfdsa固定asdsad定;横排文字:wdsadsaidth不固定adas,height固定; 竖排文adsad字:width固asd定,height不固定;横sda排文。";
        String text = "            测试标题 \n" +
                "         左先生，你好，欢迎入住本酒店 。\n" +
                "左先生，你好，欢迎入住本酒店 。\n" +
                "左先生，你好，欢迎入住本酒店 。\n" +
                "左先生，你好，欢迎入住本酒店 。\n" +
                "左先生，你好，欢迎入住本酒店 。\n" +
                "左先生，你好，欢迎入住本酒店 。\n" +
                "\u200B                                    2018.5.14";
        //String text = "我送检单是\naab,,xxxb\n接口的零啊11食 ";
//         String text = "花夜处春\n落来处眠\n知风闻不\n多雨啼觉\n少声鸟晓\n。，。，";
        // String text = "夜   \n中\n的\n时\n大\nA\n懂\n禁\n小";
        // String text = "啊";
        // String text = "春a";
        int rgb = 125; // 颜色
        // 设置字体
        Font font = getFont(fileName);
        font = deriveFont(font, bold, italic, size);
        // generate font image
        // BufferedImage img = CreateFontImgWithGraphics(text, rgb, grid, font,
        // (int)rect.getWidth(), (int)rect.getHeight());
        BufferedImage img = CreateFontImgWithGraphics(text, rgb, false, font);
        // 图片生成路径
        File file = new File("F:\\test.jpg");
        try {
            ImageIO.write(img, "JPEG", file);
            // Iterator iter = ImageIO.getImageWritersByFormatName("PNG");
            // ImageWriter writer = (ImageWriter) iter.next();
            // ImageWriteParam iwp = writer.getDefaultWriteParam();
            // iwp.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            // iwp.setCompressionQuality(1); // best quality
            // FileImageOutputStream output = new FileImageOutputStream(file);
            // writer.setOutput(output);
            // IIOImage image = new IIOImage(img, null, null);
            // writer.write(null, image, iwp);
            // writer.dispose();
        } catch (IOException e) {
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

    // 获取字体
    private static Font getFont(String fileName) {
        File file = new File("C:\\Windows\\Fonts\\" + fileName);
        InputStream fi = null;
        BufferedInputStream fb = null;
        Font nf = null;
        try {
            // 字体文件
            fi = new FileInputStream(file);
            fb = new BufferedInputStream(fi);
            // 生成字体
            nf = Font.createFont(Font.TRUETYPE_FONT, fb);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return nf;
    }

    private static BufferedImage CreateFontImgWithGraphics(String text, int rgb, boolean isVertical, Font font) {
        // 字体大小
        int fontSize = font.getSize();
        // 高、宽比例
        float radio = 1.1f;
        // 文字图片边框
        float border = (float) (fontSize * 0.1);
        // 设置每行的固定高度，用于横排
        int line_height = Math.round(fontSize * radio);
        // 设置每行的固定宽度度，用于竖排
        int line_width = Math.round(fontSize * radio);
        // 文字
        String lines[] = text.split("\n");
        String line;
        TextLayout layout;
        // 计算图片的width,height
        BufferedImage tmp = new BufferedImage(1, 1, BufferedImage.TYPE_4BYTE_ABGR);
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
        int width = Math.round((isVertical ? line_width * lines.length : dwh) + 2 * border);
        // 文字图片的高
        int height = Math.round((isVertical ? dwh : line_height * lines.length) + 2 * border);
        // 创建文字图片
        BufferedImage image = new BufferedImage(width < 1 ? 1 : width, // width
                height < 1 ? 1 : height, // height
                BufferedImage.TYPE_4BYTE_ABGR);// RGB mode
        // get graphics context
        Graphics2D g = (Graphics2D) image.getGraphics();
        g.setColor(new Color(255,255,255));
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(new Color(rgb));
        g.setFont(font);

        g.setRenderingHint(SunHints.KEY_ANTIALIASING, SunHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(SunHints.KEY_TEXT_ANTIALIASING, SunHints.VALUE_TEXT_ANTIALIAS_ON);
        g.setRenderingHint(SunHints.KEY_STROKE_CONTROL, SunHints.VALUE_STROKE_DEFAULT);
        g.setRenderingHint(SunHints.KEY_TEXT_ANTIALIAS_LCD_CONTRAST, 140);
        g.setRenderingHint(SunHints.KEY_FRACTIONALMETRICS, SunHints.VALUE_FRACTIONALMETRICS_ON);
        g.setRenderingHint(SunHints.KEY_RENDERING, SunHints.VALUE_RENDER_QUALITY);
        // 初始化第一个字的坐标
        float xpos = isVertical ? width : border + dx, ypos = border + dy;
        // 每行字
        int cfl = 1;
        for (int i = 0; i < lines.length; i++) {
            line = lines[i];
            if (isVertical) {
                xpos -= line_width;
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
                layout = new TextLayout(String.valueOf(c), font, g.getFontRenderContext());
                g.scale(1.0, 1.0); // 比例
                if (c > 32 && c < 126 && isVertical) {
                    cfl = 0;
                    g.rotate(Math.PI / 2, xpos, ypos + layout.getAdvance());
                    g.drawString(String.valueOf(c),   xpos  , ypos+ layout.getAdvance());
                    g.rotate(-Math.PI / 2, xpos, ypos + layout.getAdvance());
                } else {
                    if(cfl == 0){
                        if (isVertical) {
                            ypos += layout.getAdvance()/2;
                        } else {
                            xpos += layout.getAdvance()/2;
                        }
                    }
                    cfl = 1;
                    g.drawString(String.valueOf(c), xpos, isVertical ? ypos + layout.getAdvance() : ypos);
                }
                System.out.println(c + ", xy:xpos =" + xpos + ",ypos=" + (ypos + layout.getAdvance()));

                if (isVertical) {
                    ypos += layout.getAdvance();
                } else {
                    xpos += layout.getAdvance();
                }
            }
        }
        g.drawString("",160, 81);
        System.out.println("width:" + width + ", height:" + height);
        // g.setStroke(new BasicStroke(4.0f));// 线条粗细
        // g.setColor(Color.blue);// 线条颜色
        // g.drawLine(440, 0, 440, 580);// 线条起点及终点位置
        // g.setStroke(new BasicStroke(4.0f));// 线条粗细
        // g.setColor(Color.red);// 线条颜色
        // g.drawLine(0, 110, 620, 110);// 线条起点及终点位置
        g.dispose();
        return image;
    }

    static class Rect {
        private float height;
        private float width;

        public Rect() {
        }

        public Rect(float height, float width) {
            super();
            this.height = height;
            this.width = width;
        }

        public float getHeight() {
            return height;
        }

        public void setHeight(float height) {
            this.height = height;
        }

        public float getWidth() {
            return width;
        }

        public void setWidth(float width) {
            this.width = width;
        }

    }
}