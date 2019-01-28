package com.huboot.file.common.utils;/**
 * Created by toryli on 2018/12/13.
 */

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.net.URL;

/**
 * @Description
 * @Author Tory.Li
 * @Date 2018/12/13 15:30
 **/
public class ImageUtil {

    private static final String AGENT_SHARE_IMAGE_PATH = "img/promotion/agent/share.png!all";

    public static byte[] agentShare(String rootPath, String codePath) throws Exception {

        BufferedImage img = ImageIO.read(new URL(rootPath + "/" + AGENT_SHARE_IMAGE_PATH));//读取本地图片

        Graphics2D g = (Graphics2D) img.getGraphics();//开启画图
        int x = 280;
        int y = 1056;

        BufferedImage er = ImageIO.read(new URL(codePath + "!all"));
        //BufferedImage er = ImageIO.read(new URL(codePath));
        g.drawImage(er.getScaledInstance(200, 200, Image.SCALE_DEFAULT), x, y, null); // 绘制缩小后的图
        g.dispose();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ImageIO.write(img, "png", out);
        return out.toByteArray();
    }

}
