package com.lingyun.auth.controller;

import com.google.code.kaptcha.impl.DefaultKaptcha;

import com.lingyun.auth.utils.CommonUtil;
import com.lingyun.auth.utils.JwtTokenUtil;
import com.lingyun.auth.utils.OrderCodeFactory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;


/*
  @description:验证码获取与校验
  @author:qiaoyn
  @date:2019/06/10
*/
@RestController
@Api(value = "验证码",tags = "验证码")
public class ValidateCodeController {
    Logger logger= LoggerFactory.getLogger(ValidateCodeController.class);
    @Autowired
    private  RedisTemplate redisTemplate;
    @Resource
    private DefaultKaptcha captchaProducer;


    /**
     *注册验证码图片SessionKey
     */
    public static final String LOGIN_VALIDATE_CODE = "regist_validate_code";
    /**
     * 注册验证码图片
     */
    @ApiOperation(value = "获取验证码", notes = "获取验证码")
    @RequestMapping(value = {"/registValidateCode"}  ,method = RequestMethod.POST)
    public void registValidateCode(HttpServletRequest request, HttpServletResponse response, @RequestParam("uuid") String uuid) throws Exception{


        String cpcode= CommonUtil.validateCode(request,response,captchaProducer,LOGIN_VALIDATE_CODE,uuid);

        logger.info("验证码为:"+cpcode);
        redisTemplate.opsForValue().set(uuid,cpcode);
        redisTemplate.expire(uuid,  2L,TimeUnit.MINUTES);

    }


    /**
     * 检查验证码是否正确
     */
    @ApiOperation(value = "检查验证码", notes = "检查验证码")
    @RequestMapping(value = "/checkRegistValidateCode",method = RequestMethod.POST)
    public ResponseEntity<HashMap> checkRegistValidateCode(HttpServletRequest request, @RequestParam("validateCode")String validateCode,@RequestParam("uuid") String uuid) {
        //String registValidateCode = request.getSession().getAttribute(LOGIN_VALIDATE_CODE).toString();
        HashMap<String,Object> map = new HashMap<String,Object>();

        Object registValidateCode = redisTemplate.opsForValue().get(uuid);

        if(registValidateCode == null){
            map.put("status",50002);//验证码过期
            map.put("msg","验证码失效");
        }else if(!registValidateCode.toString().equals(validateCode)){
            map.put("status",false);//验证码不正确
            map.put("msg","验证码不正确");
        }else {
            map.put("status",true);//验证码正确
            map.put("msg","验证码正确");
        }
        return ResponseEntity.ok(map);
    }
}
