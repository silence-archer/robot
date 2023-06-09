package com.silence.robot.service;

import com.silence.robot.domain.UserInfo;
import org.springframework.stereotype.Service;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Random;

@Service
public class ImageService {
    public UserInfo createImageWithVerifyCode(int width, int height, int length) {
        UserInfo userInfo = new UserInfo();
        //绘制内存中的图片
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        //得到画图对象
        Graphics graphics = bufferedImage.getGraphics();
        //绘制图片前指定一个颜色
        graphics.setColor(getRandomColor());
        graphics.fillRect(0, 0, width, height);
        //绘制边框
        graphics.setColor(Color.white);
        graphics.drawRect(0, 0, width - 1, height - 1);

        // 步骤四 四个随机数字
        Graphics2D graphics2d = (Graphics2D) graphics;
        graphics2d.setFont(new Font("微软雅黑", Font.BOLD, 30));
        Random random = new Random();

        String word = getItemId(length);
        //保存到session
        userInfo.setImageCode(word);
        // 定义x坐标
        int x = 10;
        for (int i = 0; i < word.length(); i++) {
            // 随机颜色
            graphics2d.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));
            // 旋转 -30 --- 30度
            int jiaodu = random.nextInt(60) - 30;
            // 换算弧度
            double theta = jiaodu * Math.PI / 180;
            // 获得字母数字
            char c = word.charAt(i);
            //将c 输出到图片
            graphics2d.rotate(theta, x, 30);
            graphics2d.drawString(String.valueOf(c), x, 30);
            graphics2d.rotate(-theta, x, 30);
            x += 20;
        }

        // 绘制干扰线
        graphics.setColor(getRandomColor());
        int x1;
        int x2;
        int y1;
        int y2;
        for (int i = 0; i < 30; i++) {
            x1 = random.nextInt(width);
            x2 = random.nextInt(12);
            y1 = random.nextInt(height);
            y2 = random.nextInt(12);
            graphics.drawLine(x1, y1, x1 + x2, x2 + y2);
        }
        graphics.dispose();// 释放资源
        ByteArrayOutputStream baos = new ByteArrayOutputStream();//io流
        try {
            ImageIO.write(bufferedImage, "png", baos);//写入流中
        } catch (IOException e) {
            e.printStackTrace();
        }
        //转换成字节
        byte[] bytes = baos.toByteArray();
        String pngBase64 = Base64.getEncoder().encodeToString(bytes);
        //删除 \r\n
        pngBase64 = pngBase64.replaceAll("\n", "").replaceAll("\r", "");
        userInfo.setImageWithVerifyCode(pngBase64);
        return userInfo;

    }

    private Color getRandomColor() {

        Random ran = new Random();

        return new Color(ran.nextInt(256),

                ran.nextInt(256), ran.nextInt(256));
    }

    /**
     *
     * 生成字母加数字的随机数
     *
     * @author mazhaohui
     * @since 2019年9月21日 上午11:11:09
     * @param n 需要生成的长度
     * @return
     */
    private String getItemId(int n) {
        String val = "";
        Random random = new Random();
        for (int i = 0; i < n; i++) {
            String str = random.nextInt(2) % 2 == 0 ? "num" : "char";
            // 产生字母
            if ("char".equalsIgnoreCase(str)) {
                int nextInt = random.nextInt(2) % 2 == 0 ? 65 : 97;
                // System.out.println(nextInt + "!!!!"); 1,0,1,1,1,0,0
                val += (char)(nextInt + random.nextInt(26));
            }
            // 产生数字
            else if ("num".equalsIgnoreCase(str)) {
                val += String.valueOf(random.nextInt(10));
            }
        }
        return val;
    }


}
