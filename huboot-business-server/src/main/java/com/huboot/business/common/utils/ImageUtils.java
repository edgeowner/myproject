package com.huboot.business.common.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.List;

public class ImageUtils {

    public static Integer IMAGE_COUNT = 3;

    /**
     * 生产渠道码
     * @param type 公众号:1 or 小程序:2
     * @param qrcode 微信生成的二维码
     * @param text 数组，第一个为公司名称，第二个为编号
     * @return
     * @throws Exception
     */
    public static byte[] createGeneralize(Integer type, String qrcode,  String [] text) throws Exception {
        Integer font24 = 24;
        Integer font32 = 32;

        Integer qrcodeWidth = 680;//画布宽度
        Integer qrcodeHeight = 810;//画布高度
        Color color = null;
        if (type == 1) {
            color = new Color(31,124,211);
        } else {
            color = new Color(100,119,215);
        }

        BufferedImage img = new BufferedImage(qrcodeWidth, qrcodeHeight, BufferedImage.TYPE_INT_ARGB);//创建图片
        Graphics2D g = img.createGraphics();//开启画图
//        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
//        Graphics2D gs = image.createGraphics();

        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.setClip(new RoundRectangle2D.Double(0, 0, qrcodeWidth, qrcodeHeight, 15, 15));
        g.setBackground(new Color(255, 255, 255));
        g.clearRect(0, 0, qrcodeWidth, qrcodeHeight);

        //抬头背景色块
        g.setColor(color);
        g.drawRect(0, 0, qrcodeWidth, 110);
        g.fillRect(0, 0, qrcodeWidth, 110);

        int companyStartY = 70;
        int titleStartY = 100;
        g.setColor(Color.WHITE);
        g.setFont(new Font("黑体", Font.PLAIN, 50));
        String title = "\"码\"上租车";
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL,RenderingHints.VALUE_STROKE_DEFAULT);
        // 计算文字长度，计算居中的x点坐标
        FontMetrics fm = g.getFontMetrics(new Font("黑体", Font.PLAIN, 50));
        int textWidth = fm.stringWidth(title);
        int widthX = (qrcodeWidth - textWidth) / 2;
        // 表示这段文字在图片上的位置(x,y) .第一个是你设置的内容。
        g.drawString(title, widthX, companyStartY);

        titleStartY += 80;
//        BufferedImage bg = ImageIO.read(new URL("http://img1.zchz.com/img/promotion/gzh.png"));
        BufferedImage bg = getImage("http://img1.zchz.com/img/promotion/gzh.png");
        if (type != 1) {
            bg = getImage("http://img1.zchz.com/img/promotion/xcx.png");
        }
        g.drawImage(bg.getScaledInstance(430, 470, Image.SCALE_DEFAULT), qrcodeWidth / 4 - 42, 150, null); // 绘制缩小后的图

        BufferedImage er = ImageIO.read(new URL(qrcode));
        titleStartY += 5;
        g.drawImage(er.getScaledInstance(400, 400, Image.SCALE_DEFAULT), qrcodeWidth / 4 - 26, 160, null); // 绘制缩小后的图

        g.setColor(new Color(102, 102, 102));
        g.setFont(new Font("黑体", Font.PLAIN, font32));
        String content = "打开微信扫一扫";
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL,RenderingHints.VALUE_STROKE_DEFAULT);
        // 计算文字长度，计算居中的x点坐标
        FontMetrics fm1 = g.getFontMetrics(new Font("黑体", Font.PLAIN, font32));
        textWidth = fm1.stringWidth(content);
        widthX = (qrcodeWidth - textWidth) / 2;
        // 表示这段文字在图片上的位置(x,y) .第一个是你设置的内容。
        g.drawString(content.substring(0, 2), widthX, titleStartY + 400);
        g.setColor(color);
        g.drawString("微信", widthX + 64, titleStartY + 400);
        g.setColor(new Color(102, 102, 102));
        g.drawString("扫一扫", widthX + 128, titleStartY + 400);

        g.setColor(color);
        g.setFont(new Font("黑体", Font.PLAIN, font32));
        titleStartY += 455;
        String shopName = text[0];
        int lent = 18;//公司名称截取长度
        if (shopName.length() > lent) {
            int length = shopName.length() % lent == 0 ? shopName.length() / lent : shopName.length() / lent + 1;
            for (int i = 0; i < length; i++) {
                titleStartY += 45;
                String name = shopName.substring(i * lent, (i + 1) * lent > shopName.length() ? shopName.length() : (i + 1) * lent);
                g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL,RenderingHints.VALUE_STROKE_DEFAULT);
                // 计算文字长度，计算居中的x点坐标
                FontMetrics fmShop = g.getFontMetrics(new Font("黑体", Font.PLAIN, font32));
                textWidth = fmShop.stringWidth(name);
                widthX = (qrcodeWidth - textWidth) / 2;
                // 表示这段文字在图片上的位置(x,y) .第一个是你设置的内容。
                g.drawString(name, widthX, titleStartY);
            }
        } else {
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL,RenderingHints.VALUE_STROKE_DEFAULT);
            // 计算文字长度，计算居中的x点坐标
            FontMetrics fmShop = g.getFontMetrics(new Font("黑体", Font.PLAIN, font32));
            textWidth = fmShop.stringWidth(shopName);
            widthX = (qrcodeWidth - textWidth) / 2;
            // 表示这段文字在图片上的位置(x,y) .第一个是你设置的内容。
            g.drawString(shopName, widthX, titleStartY + 45);
        }

        g.setColor(new Color(223, 223, 223));
        g.setFont(new Font("黑体", Font.PLAIN, font24));
        String code = "—— 编号:" + text[1] + " ——";
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL,RenderingHints.VALUE_STROKE_DEFAULT);
        // 计算文字长度，计算居中的x点坐标
        FontMetrics fmCode = g.getFontMetrics(new Font("黑体", Font.PLAIN, font24));
        textWidth = fmCode.stringWidth(code);
        widthX = (qrcodeWidth - textWidth) / 2;
        g.drawString("—— ", widthX, qrcodeHeight - 45);
        // 表示这段文字在图片上的位置(x,y) .第一个是你设置的内容。
        g.setColor(new Color(153, 153, 153));
        g.drawString("编号:" + text[1], widthX + 64, qrcodeHeight - 45);
        g.setColor(new Color(223, 223, 223));
        g.drawString(" ——", widthX + 204, qrcodeHeight - 45);

        // 底部背景色块
        g.setColor(color);
        g.drawRect(0, qrcodeHeight - 12, qrcodeWidth, 12);
        g.fillRect(0, qrcodeHeight - 12, qrcodeWidth, 12);

        g.dispose();
//        ImageIO.write(img, "png", new File("C:\\Users\\Administrator\\Desktop\\qudao1.png"));

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ImageIO.write(img, "png", out);
        return out.toByteArray();
    }

    /**
     * 生产渠道码
     * @param type 公众号:1 or 小程序:2
     * @param file 微信生成的二维码
     * @param text 数组，第一个为公司名称，第二个为编号
     * @return
     * @throws Exception
     */
    public static byte[] createGeneralize2(Integer type, File file,  String [] text) throws Exception {
        Integer font24 = 24;
        Integer font32 = 32;

        Integer qrcodeWidth = 680;//画布宽度
        Integer qrcodeHeight = 810;//画布高度
        Color color = null;
        if (type == 1) {
            color = new Color(31,124,211);
        } else {
            color = new Color(100,119,215);
        }

        BufferedImage img = new BufferedImage(qrcodeWidth, qrcodeHeight, BufferedImage.TYPE_INT_ARGB);//创建图片
        Graphics2D g = img.createGraphics();//开启画图
//        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
//        Graphics2D gs = image.createGraphics();

        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.setClip(new RoundRectangle2D.Double(0, 0, qrcodeWidth, qrcodeHeight, 15, 15));
        g.setBackground(new Color(255, 255, 255));
        g.clearRect(0, 0, qrcodeWidth, qrcodeHeight);

        //抬头背景色块
        g.setColor(color);
        g.drawRect(0, 0, qrcodeWidth, 110);
        g.fillRect(0, 0, qrcodeWidth, 110);

        int companyStartY = 70;
        int titleStartY = 100;
        g.setColor(Color.WHITE);
        g.setFont(new Font("黑体", Font.PLAIN, 50));
        String title = "\"码\"上租车";
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL,RenderingHints.VALUE_STROKE_DEFAULT);
        // 计算文字长度，计算居中的x点坐标
        FontMetrics fm = g.getFontMetrics(new Font("黑体", Font.PLAIN, 50));
        int textWidth = fm.stringWidth(title);
        int widthX = (qrcodeWidth - textWidth) / 2;
        // 表示这段文字在图片上的位置(x,y) .第一个是你设置的内容。
        g.drawString(title, widthX, companyStartY);

        titleStartY += 80;
        BufferedImage bg = getImage("http://img1.zchz.com/img/promotion/gzh.png");
        if (type != 1) {
            bg = getImage("http://img1.zchz.com/img/promotion/xcx.png");
        }
        g.drawImage(bg.getScaledInstance(430, 470, Image.SCALE_DEFAULT), qrcodeWidth / 4 - 42, 150, null); // 绘制缩小后的图

        BufferedImage er = ImageIO.read(file);
        titleStartY += 5;
        g.drawImage(er.getScaledInstance(400, 400, Image.SCALE_DEFAULT), qrcodeWidth / 4 - 26, 160, null); // 绘制缩小后的图

        g.setColor(new Color(102, 102, 102));
        g.setFont(new Font("黑体", Font.PLAIN, font32));
        String content = "打开微信扫一扫";
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL,RenderingHints.VALUE_STROKE_DEFAULT);
        // 计算文字长度，计算居中的x点坐标
        FontMetrics fm1 = g.getFontMetrics(new Font("黑体", Font.PLAIN, font32));
        textWidth = fm1.stringWidth(content);
        widthX = (qrcodeWidth - textWidth) / 2;
        // 表示这段文字在图片上的位置(x,y) .第一个是你设置的内容。
        g.drawString(content.substring(0, 2), widthX, titleStartY + 400);
        g.setColor(color);
        g.drawString("微信", widthX + 64, titleStartY + 400);
        g.setColor(new Color(102, 102, 102));
        g.drawString("扫一扫", widthX + 128, titleStartY + 400);

        g.setColor(color);
        g.setFont(new Font("黑体", Font.PLAIN, font32));
        titleStartY += 455;
        String shopName = text[0];
        int lent = 18;//公司名称截取长度
        if (shopName.length() > lent) {
            int length = shopName.length() % lent == 0 ? shopName.length() / lent : shopName.length() / lent + 1;
            for (int i = 0; i < length; i++) {
                titleStartY += 45;
                String name = shopName.substring(i * lent, (i + 1) * lent > shopName.length() ? shopName.length() : (i + 1) * lent);
                g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL,RenderingHints.VALUE_STROKE_DEFAULT);
                // 计算文字长度，计算居中的x点坐标
                FontMetrics fmShop = g.getFontMetrics(new Font("黑体", Font.PLAIN, font32));
                textWidth = fmShop.stringWidth(name);
                widthX = (qrcodeWidth - textWidth) / 2;
                // 表示这段文字在图片上的位置(x,y) .第一个是你设置的内容。
                g.drawString(name, widthX, titleStartY);
            }
        } else {
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL,RenderingHints.VALUE_STROKE_DEFAULT);
            // 计算文字长度，计算居中的x点坐标
            FontMetrics fmShop = g.getFontMetrics(new Font("黑体", Font.PLAIN, font32));
            textWidth = fmShop.stringWidth(shopName);
            widthX = (qrcodeWidth - textWidth) / 2;
            // 表示这段文字在图片上的位置(x,y) .第一个是你设置的内容。
            g.drawString(shopName, widthX, titleStartY + 45);
        }

        g.setColor(new Color(223, 223, 223));
        g.setFont(new Font("黑体", Font.PLAIN, font24));
        String code = "—— 编号:" + text[1] + " ——";
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL,RenderingHints.VALUE_STROKE_DEFAULT);
        // 计算文字长度，计算居中的x点坐标
        FontMetrics fmCode = g.getFontMetrics(new Font("黑体", Font.PLAIN, font24));
        textWidth = fmCode.stringWidth(code);
        widthX = (qrcodeWidth - textWidth) / 2;
        g.drawString("—— ", widthX, qrcodeHeight - 45);
        // 表示这段文字在图片上的位置(x,y) .第一个是你设置的内容。
        g.setColor(new Color(153, 153, 153));
        g.drawString("编号:" + text[1], widthX + 64, qrcodeHeight - 45);
        g.setColor(new Color(223, 223, 223));
        g.drawString(" ——", widthX + 204, qrcodeHeight - 45);

        // 底部背景色块
        g.setColor(color);
        g.drawRect(0, qrcodeHeight - 12, qrcodeWidth, 12);
        g.fillRect(0, qrcodeHeight - 12, qrcodeWidth, 12);

        g.dispose();
//        ImageIO.write(img, "png", new File("C:\\Users\\Administrator\\Desktop\\3.png"));

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ImageIO.write(img, "png", out);
        return out.toByteArray();
    }

    public static byte[] createCard(File file, String [] text) throws Exception {
        Integer font24 = 24;
        Integer font32 = 32;

        Integer qrcodeWidth = 660;//画布宽度
        Integer qrcodeHeight = 780;//画布高度

        BufferedImage img = new BufferedImage(qrcodeWidth, qrcodeHeight, BufferedImage.TYPE_INT_ARGB);//创建图片
        Graphics2D g = img.createGraphics();//开启画图

        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.setClip(new RoundRectangle2D.Double(0, 0, qrcodeWidth, qrcodeHeight, 15, 15));
        g.setBackground(new Color(100,119,215));
        g.clearRect(0, 0, qrcodeWidth, qrcodeHeight);

        int companyStartY = 90;
        int titleStartY = 120;
        g.setColor(Color.WHITE);
        g.setFont(new Font("黑体", Font.PLAIN, 50));
        String title = text[0];
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL,RenderingHints.VALUE_STROKE_DEFAULT);
        // 计算文字长度，计算居中的x点坐标
        FontMetrics fm = g.getFontMetrics(new Font("黑体", Font.PLAIN, 50));
        int textWidth = fm.stringWidth(title);
        int widthX = (qrcodeWidth - textWidth) / 2;
        // 表示这段文字在图片上的位置(x,y) .第一个是你设置的内容。
        g.drawString(title, widthX, companyStartY);

        titleStartY += 75;

        g.setColor(new Color(255, 255, 255));
        g.setClip(new RoundRectangle2D.Double(titleStartY-65, titleStartY-50, 400, 400, 20, 20));
        g.drawRect(qrcodeWidth / 4 - 35, titleStartY - 50, 400, 400);
        g.fillRect(qrcodeWidth / 4 - 35, titleStartY - 50, 400, 400);

        BufferedImage er = ImageIO.read(file);
        g.drawImage(er.getScaledInstance(360, 360, Image.SCALE_DEFAULT), qrcodeWidth / 4 - 13, 170, null); // 绘制缩小后的图*/

        g.setClip(null);
        g.setColor(new Color(255, 255, 255));
        g.setFont(new Font("黑体", Font.PLAIN, font32));
        titleStartY += 400;
        String shopName = text[1];
        int lent = 18;//公司名称截取长度
        if (shopName.length() > lent) {
            int length = shopName.length() % lent == 0 ? shopName.length() / lent : shopName.length() / lent + 1;
            for (int i = 0; i < length; i++) {
                titleStartY += 45;
                String name = shopName.substring(i * lent, (i + 1) * lent > shopName.length() ? shopName.length() : (i + 1) * lent);
                g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL,RenderingHints.VALUE_STROKE_DEFAULT);
                // 计算文字长度，计算居中的x点坐标
                FontMetrics fmShop = g.getFontMetrics(new Font("黑体", Font.PLAIN, font32));
                textWidth = fmShop.stringWidth(name);
                widthX = (qrcodeWidth - textWidth) / 2;
                // 表示这段文字在图片上的位置(x,y) .第一个是你设置的内容。
                g.drawString(name, widthX, titleStartY);
            }
        } else {
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL,RenderingHints.VALUE_STROKE_DEFAULT);
            // 计算文字长度，计算居中的x点坐标
            FontMetrics fmShop = g.getFontMetrics(new Font("黑体", Font.PLAIN, font32));
            textWidth = fmShop.stringWidth(shopName);
            widthX = (qrcodeWidth - textWidth) / 2;
            // 表示这段文字在图片上的位置(x,y) .第一个是你设置的内容。
            g.drawString(shopName, widthX, titleStartY + 45);
        }

        g.setColor(new Color(223, 223, 223));
        g.setFont(new Font("黑体", Font.PLAIN, font24));
        String code = "—— " + text[2] + " ——";
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL,RenderingHints.VALUE_STROKE_DEFAULT);
        // 计算文字长度，计算居中的x点坐标
        FontMetrics fmCode = g.getFontMetrics(new Font("黑体", Font.PLAIN, font24));
        textWidth = fmCode.stringWidth(code);
        widthX = (qrcodeWidth - textWidth) / 2;
        g.drawString("—— ", widthX, qrcodeHeight - 50);
        // 表示这段文字在图片上的位置(x,y) .第一个是你设置的内容。
        g.setColor(new Color(255, 255, 255));
        g.drawString(text[2], widthX + 64, qrcodeHeight - 50);
        g.setColor(new Color(223, 223, 223));
        FontMetrics fmCode1 = g.getFontMetrics(new Font("黑体", Font.PLAIN, font24));
        textWidth = fmCode1.stringWidth(text[2]);
        g.drawString(" ——", widthX + textWidth + 66, qrcodeHeight - 50);

        g.dispose();
//        ImageIO.write(img, "png", new File("C:\\Users\\Administrator\\Desktop\\4.png"));

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ImageIO.write(img, "png", out);
        return out.toByteArray();
    }

    /**
     * 店铺二维码
     * @param url
     * @param text
     * @return
     * @throws Exception
     */
    public static byte[] createShopQRCode(String url, String [] text) throws Exception {
        Integer font34 = 34;

        Integer qrcodeWidth = 660;//画布宽度
        Integer qrcodeHeight = 780;//画布高度

        BufferedImage img = new BufferedImage(qrcodeWidth, qrcodeHeight, BufferedImage.TYPE_INT_ARGB);//创建图片
        Graphics2D g = img.createGraphics();//开启画图
        Color color = new Color(31,124,211);

        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.setClip(new RoundRectangle2D.Double(0, 0, qrcodeWidth, qrcodeHeight, 15, 15));
        g.setBackground(new Color(255,255,255));
        g.clearRect(0, 0, qrcodeWidth, qrcodeHeight);

        //抬头背景色块
        g.setColor(color);
        g.drawRect(0, 0, qrcodeWidth, 110);
        g.fillRect(0, 0, qrcodeWidth, 110);

        int titleStartY = 70;
        g.setColor(Color.WHITE);
        g.setFont(new Font("黑体", Font.PLAIN, 50));
        String title = "微信扫一扫";
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL,RenderingHints.VALUE_STROKE_DEFAULT);
        // 计算文字长度，计算居中的x点坐标
        FontMetrics fm = g.getFontMetrics(new Font("黑体", Font.PLAIN, 50));
        int textWidth = fm.stringWidth(title);
        int widthX = (qrcodeWidth - textWidth) / 2;
        // 表示这段文字在图片上的位置(x,y) .第一个是你设置的内容。
        g.drawString(title, widthX, titleStartY);

        BufferedImage bg = getImage("http://img1.zchz.com/img/promotion/gzh.png");
        g.drawImage(bg.getScaledInstance(430, 430, Image.SCALE_DEFAULT), qrcodeWidth / 4 - 46, titleStartY + 105, null); // 绘制缩小后的图

        BufferedImage er = getImage(url);
        g.drawImage(er.getScaledInstance(400, 400, Image.SCALE_DEFAULT), qrcodeWidth / 4 - 30, titleStartY + 120, null); // 绘制缩小后的图*/

        titleStartY += 560;
        g.setColor(new Color(31,124,211));
        g.setFont(new Font("黑体", Font.PLAIN, font34));
        String shopName = text[0];
        int lent = 16;//公司名称截取长度
        if (shopName.length() > lent) {
            int length = shopName.length() % lent == 0 ? shopName.length() / lent : shopName.length() / lent + 1;
            for (int i = 0; i < length; i++) {
                titleStartY += 45;
                String name = shopName.substring(i * lent, (i + 1) * lent > shopName.length() ? shopName.length() : (i + 1) * lent);
                g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL,RenderingHints.VALUE_STROKE_DEFAULT);
                // 计算文字长度，计算居中的x点坐标
                FontMetrics fmShop = g.getFontMetrics(new Font("黑体", Font.PLAIN, font34));
                textWidth = fmShop.stringWidth(name);
                widthX = (qrcodeWidth - textWidth) / 2;
                // 表示这段文字在图片上的位置(x,y) .第一个是你设置的内容。
                g.drawString(name, widthX, titleStartY);
            }
        } else {
            titleStartY = titleStartY + 60;
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL,RenderingHints.VALUE_STROKE_DEFAULT);
            // 计算文字长度，计算居中的x点坐标
            FontMetrics fmShop = g.getFontMetrics(new Font("黑体", Font.PLAIN, font34));
            textWidth = fmShop.stringWidth(shopName);
            widthX = (qrcodeWidth - textWidth) / 2;
            // 表示这段文字在图片上的位置(x,y) .第一个是你设置的内容。
            g.drawString(shopName, widthX, titleStartY);
        }

        // 底部背景色块
        g.setColor(color);
        g.drawRect(0, qrcodeHeight - 12, qrcodeWidth, 12);
        g.fillRect(0, qrcodeHeight - 12, qrcodeWidth, 12);
        g.dispose();
//        ImageIO.write(img, "png", new File("C:\\Users\\Administrator\\Desktop\\4.png"));

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ImageIO.write(img, "png", out);
        return out.toByteArray();
    }

    public static Map<String, byte[]> createQrcode(String[] text) throws Exception{

        Integer font26 = 24;//字体21
        Integer font32 = 32;//字体24

        Integer qrcodeWidth = 700;//画布宽度
        Integer qrcodeHeight = 880;//画布高度

        BufferedImage img = new BufferedImage(qrcodeWidth, qrcodeHeight, BufferedImage.TYPE_INT_RGB);//创建图片

//        BufferedImage bg = ImageIO.read(new File("C:\\Users\\Administrator\\Desktop\\2.png"));//读取本地图片
        BufferedImage bg = getImage("http://img1.zchz.com/tmp/img/activity/prizedraw/qrCode_bg.png");
//            bg = ImageIO.read(new URL("http://img1.zchz.com/tmp/img/activity/prizedraw/qrCode_bg.png"));//读取互联网图片

        Graphics2D g = (Graphics2D) img.getGraphics();//开启画图
        g.setBackground(new Color(255, 255, 255));
        g.clearRect(0, 0, qrcodeWidth, qrcodeHeight);

        g.drawImage(bg.getScaledInstance(qrcodeWidth,qrcodeHeight, Image.SCALE_DEFAULT), 0, 0, null); // 绘制缩小后的图
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        g.setColor(new Color(255, 255, 255));
//        int companyStartX = (qrcodeWidth - (font32 * "二维码生成测试公司名称".length())) / 2;
        int companyStartY = 60;
//        int titleStartX = (qrcodeWidth - (font32 * "1000元无门槛优惠券".length())) / 2;
        int titleStartY = 100;
        g.setColor(Color.WHITE);
        g.setFont(new Font("黑体", Font.PLAIN, font32));
        String shopName = text[0];
        if (shopName.length() > 16) {
            shopName = shopName.substring(0, 16) + "...";
        }
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL,RenderingHints.VALUE_STROKE_DEFAULT);
        g.drawString(shopName, qrcodeWidth / 4 - 100, companyStartY);//绘制文字
        g.setFont(new Font("黑体", Font.PLAIN, font26));
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL,RenderingHints.VALUE_STROKE_DEFAULT);
        g.drawString(text[1], qrcodeWidth / 4 - 100, titleStartY);
//        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//        g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL,RenderingHints.VALUE_STROKE_DEFAULT);

//        g.setColor(new Color(223, 223, 223));
//        g.drawLine(0, titleStartY + 50, 750, titleStartY + 50);
//        g.drawLine(0, titleStartY + 650, 750, titleStartY + 650);

        BufferedImage er = encodeQrcode(text[2], text[3]);
        g.drawImage(er.getScaledInstance(qrcodeWidth / 2 + 80, qrcodeWidth / 2 + 80, Image.SCALE_DEFAULT), qrcodeWidth / 4 - 40, titleStartY + 125, null); // 绘制缩小后的图

        /*g.setColor(new Color(102, 102, 102));
        int descStartX = (qrcodeWidth - (font28 * "微信扫码领取优惠券，长按即可分享".length())) / 2;
        int descStartY = 880;
        g.setFont(new Font("黑体", Font.PLAIN, font28));
        g.drawString("微信扫码领取优惠券，长按即可分享", descStartX, titleStartY + 720);*/
        g.dispose();

        Map<String, byte[]> map = new HashMap<>();
        // 二维码大图
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ImageIO.write(img, "png", out);
        byte[] qrCode = out.toByteArray();
        map.put("qrCode", qrCode);

        // 二维码小图
        ByteArrayOutputStream out1 = new ByteArrayOutputStream();
        ImageIO.write(er, "png", out1);
        byte[] qrCodeMini = out1.toByteArray();
        map.put("qrCodeMini", qrCodeMini);

//        ImageIO.write(img, "jpg", new File("C:\\Users\\Administrator\\Desktop\\3.jpg"));
        return map;
    }

    public static Map<String, byte[]> createLotteryQrcode(String[] text) throws Exception {
        Integer font24 = 24;//字体21
        Integer font32 = 32;//字体24

        Integer qrcodeWidth = 650;//画布宽度
        Integer qrcodeHeight = 720;//画布高度

        BufferedImage img = new BufferedImage(qrcodeWidth, qrcodeHeight, BufferedImage.TYPE_INT_RGB);//创建图片
//        BufferedImage bg = ImageIO.read(new File("C:\\Users\\Administrator\\Desktop\\pic.png"));//读取本地图片
//        BufferedImage bg = ImageIO.read(new URL("http://img1.zchz.com/tmp/img/activity/prizedraw/lottery_activity.png"));//读取互联网图片
        BufferedImage bg = getImage("http://img1.zchz.com/tmp/img/activity/prizedraw/lottery_activity.png");//读取互联网图片
        Graphics2D g = (Graphics2D) img.getGraphics();//开启画图
        g.setBackground(new Color(255, 255, 255));
        g.clearRect(0, 0, qrcodeWidth, qrcodeHeight);

        g.drawImage(bg.getScaledInstance(qrcodeWidth,qrcodeHeight, Image.SCALE_DEFAULT), 0, 0, null); // 绘制缩小后的图
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        g.setColor(Color.WHITE);
        g.setFont(new Font("黑体", Font.PLAIN, 32));
        String title = text[0];
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL,RenderingHints.VALUE_STROKE_DEFAULT);
        // 计算文字长度，计算居中的x点坐标
        FontMetrics fm = g.getFontMetrics(new Font("黑体", Font.PLAIN, 32));
        int textWidth = fm.stringWidth(title);
        int widthX = (qrcodeWidth - textWidth) / 2;
        // 表示这段文字在图片上的位置(x,y) .第一个是你设置的内容。
        g.drawString(title, widthX, 60);

        g.setColor(new Color(255, 255, 255));
        g.setColor(Color.WHITE);
        g.setFont(new Font("黑体", Font.PLAIN, font32));
        int companyStartY = qrcodeHeight - 130;
        String shopName = text[1];
        int lent = 12;
        if (shopName.length() > lent) {
            companyStartY -= 12;
            int length = shopName.length() % lent == 0 ? shopName.length() / lent : shopName.length() / lent + 1;
            for (int i = 0; i < length; i++) {
                String name = shopName.substring(i * lent, (i + 1) * lent > shopName.length() ? shopName.length() : (i + 1) * lent);
                g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL,RenderingHints.VALUE_STROKE_DEFAULT);

                g.drawString(name, 30, companyStartY);//绘制文字
                companyStartY += 40;
            }

            g.setColor(new Color(255, 255, 255));
            g.setColor(Color.WHITE);
            g.setFont(new Font("黑体", Font.PLAIN, font24));
            String content = "租车自驾游，超级好礼等着你！";
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL,RenderingHints.VALUE_STROKE_DEFAULT);
            g.drawString(content, 30, qrcodeHeight - 55);//绘制文字
        } else {
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL,RenderingHints.VALUE_STROKE_DEFAULT);

            g.drawString(shopName, 30, companyStartY);//绘制文字

            g.setColor(new Color(255, 255, 255));
            g.setColor(Color.WHITE);
            g.setFont(new Font("黑体", Font.PLAIN, font24));
            String content = "租车自驾游，超级好礼等着你！";
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL,RenderingHints.VALUE_STROKE_DEFAULT);
            g.drawString(content, 30, qrcodeHeight - 80);//绘制文字
        }

        BufferedImage er = encodeQrcode(text[2], text[3]);
        g.setClip(new RoundRectangle2D.Double(qrcodeWidth - 190, qrcodeHeight - 190, 160, 160, 15, 15));
        g.drawImage(er.getScaledInstance(160, 160, Image.SCALE_DEFAULT), qrcodeWidth - 190, qrcodeHeight - 190, null); // 绘制缩小后的图

        g.dispose();
//        ImageIO.write(img, "png", new File("C:\\Users\\Administrator\\Desktop\\4.png"));

        Map<String, byte[]> map = new HashMap<>();
        // 二维码大图
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ImageIO.write(img, "png", out);
        byte[] qrCode = out.toByteArray();
        map.put("qrCode", qrCode);

        // 二维码小图
        ByteArrayOutputStream out1 = new ByteArrayOutputStream();
        ImageIO.write(er, "png", out1);
        byte[] qrCodeMini = out1.toByteArray();
        map.put("qrCodeMini", qrCodeMini);

        return map;
    }

    private static BufferedImage encodeQrcode(String content, String shopPath) throws Exception {
        int width = 435;
        int height = 435;

        Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
        // 指定纠错等级,纠错级别（L 7%、M 15%、Q 25%、H 30%）
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        // 内容所使用字符集编码
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
//      hints.put(EncodeHintType.MAX_SIZE, 350);//设置图片的最大值
//      hints.put(EncodeHintType.MIN_SIZE, 100);//设置图片的最小值
        hints.put(EncodeHintType.MARGIN, 2);//设置二维码边的空度，非负数

        BitMatrix bitMatrix = new MultiFormatWriter().encode(content,//要编码的内容
                //编码类型，目前zxing支持：Aztec 2D,CODABAR 1D format,Code 39 1D,Code 93 1D ,Code 128 1D,
                //Data Matrix 2D , EAN-8 1D,EAN-13 1D,ITF (Interleaved Two of Five) 1D,
                //MaxiCode 2D barcode,PDF417,QR Code 2D,RSS 14,RSS EXPANDED,UPC-A 1D,UPC-E 1D,UPC/EAN extension,UPC_EAN_EXTENSION
                BarcodeFormat.QR_CODE,
                width, //二维码的宽度
                height, //二维码的高度
                hints);//生成二维码时的一些配置,此项可选
//        bitMatrix = deleteWhite(bitMatrix);//删除白边
        BufferedImage image = toBufferedImage(bitMatrix);
        Graphics2D g2 = (Graphics2D) image.getGraphics();//开启画图
        RoundRectangle2D.Float round = new RoundRectangle2D.Float(
                width, height, width,
                height, 15, 15);
        g2.setColor(Color.white);
        g2.draw(round);// 绘制圆弧矩形

        image = LogoMatrix(image, shopPath);
        return image;
    }

    /**
     * 设置 logo
     *
     * @param matrixImage 源二维码图片
     * @return 返回带有logo的二维码图片
     * @throws IOException
     * @author Administrator sangwenhao
     */
    public static BufferedImage LogoMatrix(BufferedImage matrixImage, String shopPath)
            throws Exception {
        /**
         * 读取二维码图片，并构建绘图对象
         */
        Graphics2D g2 = matrixImage.createGraphics();

        int matrixWidth = matrixImage.getWidth();
        int matrixHeigh = matrixImage.getHeight();

        /**
         * 读取Logo图片
         */
//        BufferedImage logo = ImageIO.read(new File("http://img2.zchz.com/img/company/2017/683/9d4/3953328604242378752_97ddfe59f6ac283.jpg"));
//        URL url = new URL(shopPath);
//        BufferedImage logo = ImageIO.read(url);//读取互联网图片
        BufferedImage logo = getImage(shopPath);//读取互联网图片


        // 开始绘制图片
        g2.drawImage(logo, matrixWidth / 5 * 2, matrixHeigh / 5 * 2,
                matrixWidth / 5, matrixHeigh / 5, null);// 绘制
        BasicStroke stroke = new BasicStroke(5, BasicStroke.CAP_ROUND,
                BasicStroke.JOIN_ROUND);
        g2.setStroke(stroke);// 设置笔画对象
        // 指定弧度的圆角矩形
        RoundRectangle2D.Float round = new RoundRectangle2D.Float(
                matrixWidth / 5 * 2, matrixHeigh / 5 * 2, matrixWidth / 5,
                matrixHeigh / 5, 15, 15);
        g2.setColor(Color.white);
        g2.draw(round);// 绘制圆弧矩形

        g2.dispose();
        matrixImage.flush();
        return matrixImage;
    }

    private static BufferedImage toBufferedImage(BitMatrix bm) {
        final int BLACK = 0xFF000000;
        final int WHITE = 0xFFFFFFFF;

        int width = bm.getWidth();
        int height = bm.getHeight();
        BufferedImage image = new BufferedImage(width, height,
                BufferedImage.TYPE_3BYTE_BGR);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                image.setRGB(i, j, bm.get(i, j) ? BLACK : WHITE);
            }
        }
        return image;
    }

    private static BitMatrix deleteWhite(BitMatrix matrix) {
        int[] rec = matrix.getEnclosingRectangle();
        int resWidth = rec[2] + 1;
        int resHeight = rec[3] + 1;

        BitMatrix resMatrix = new BitMatrix(resWidth, resHeight);
        resMatrix.clear();
        for (int i = 0; i < resWidth; i++) {
            for (int j = 0; j < resHeight; j++) {
                if (matrix.get(i + rec[0], j + rec[1]))
                    resMatrix.set(i, j);
            }
        }
        return resMatrix;
    }

    public static BufferedImage getImage(String urlPath) throws Exception {
        BufferedImage image = null;
        URL url = new URL(urlPath);
        try {
            image = ImageIO.read(url);//读取互联网图片
        } catch (Exception e) {
            try {
                image = ImageIO.read(url);//读取互联网图片
            } catch (Exception e1) {
                try {
                    image = ImageIO.read(url);//读取互联网图片
                } catch (Exception e2) {
//                    e2.printStackTrace();
                    throw e2;
                }
            }
        }
        return image;
    }
}
