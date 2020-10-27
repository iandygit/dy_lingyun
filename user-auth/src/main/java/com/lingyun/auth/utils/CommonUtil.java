package com.lingyun.auth.utils;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;

/*
  @description:生成验证码图片
  @author:qiaoyn
  @date:2019/06/10
*/
public class CommonUtil {


    /**
     * 生成验证码图片
     * @param request 设置session
     * @param response 转成图片
     * @param captchaProducer 生成图片方法类
     * @param validateSessionKey session名称
     * @throws Exception
     * @return  String 获取验证码
     *
     */
    public static String validateCode(HttpServletRequest request, HttpServletResponse response, DefaultKaptcha captchaProducer, String validateSessionKey,String uuid) throws Exception{
        // Set to expire far in the past.
        response.setDateHeader("Expires", 0);
        // Set standard HTTP/1.1 no-cache headers.
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        // Set IE extended HTTP/1.1 no-cache headers (use addHeader).
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        // Set standard HTTP/1.0 no-cache header.
        response.setHeader("Pragma", "no-cache");


        // return a jpeg
        response.setContentType("image/jpeg");


        // create the text for the image
        String capText = captchaProducer.createText();
        // store the text in the session
        request.getSession().setAttribute(validateSessionKey, capText);
        //System.out.println("UUID="+uuid);
        //修改为为存放如redis
        //redisTemplate.opsForValue().set(uuid,capText);

        // create the image with the text
        BufferedImage bi = captchaProducer.createImage(capText);


        ServletOutputStream out = response.getOutputStream();


        // write the data out
        ImageIO.write(bi, "jpg", out);
        try {
            out.flush();
        } finally {
            out.close();
        }
        return  capText;
    }


    public static void main(String args[]){

        String a="uuid_key";
        System.out.println( CommonUtil.StrToBinstr(a));

    }
    private static String StrToBinstr(String str) {
        char[] strChar = str.toCharArray();
        String result = "";
        for (int i = 0; i < strChar.length; i++) {
            result += Integer.toBinaryString(strChar[i]) + " ";
        }
        return result;
    }

}
