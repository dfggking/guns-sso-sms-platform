package cn.stylefeng.guns.modular.api;

import cn.stylefeng.guns.core.base.controller.BaseController;
import com.alibaba.fastjson.JSONObject;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.weixin4j.Configuration;
import org.weixin4j.Weixin;
import org.weixin4j.WeixinException;
import org.weixin4j.model.js.Ticket;
import org.weixin4j.util.SignUtil;
import org.weixin4j.util.TokenUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.UUID;

@RestController
@RequestMapping(value = "wxApi")
public class ApiWxController extends BaseController {
    
    @RequestMapping("helloToken")
    public Object helloToken(@RequestParam("signature") String signature,
                             @RequestParam("timestamp") String timestamp,
                             @RequestParam("nonce") String nonce,
                             @RequestParam("echostr") String echostr, HttpServletResponse response) throws IOException {
        
        String token = TokenUtil.get();
        System.out.println(token);
        if (TokenUtil.checkSignature(token, signature, timestamp, nonce)) {
            response.getWriter().write(echostr);
        }
        return null;
    }
    
    @GetMapping("config")
    public JSONObject getWxConfig(@RequestParam("url") String url) throws WeixinException {
        Weixin weixin = new Weixin();
        
        // 定义返回配置JSON对象
        JSONObject config = new JSONObject();
        
        Ticket jsapiTicket = weixin.getJsApiTicket();
        String noncestr = UUID.randomUUID().toString().substring(0, 15);
        String timestamp = System.currentTimeMillis() + "";
    
        String signature = SignUtil.getSignature(jsapiTicket.getTicket(), noncestr, timestamp, url);
    
        config.put("appId", Configuration.getOAuthAppId());
        config.put("timestamp", timestamp);
        config.put("nonceStr", noncestr);
        config.put("signature", signature);
        
        return config;
    }
    
    @RequestMapping("MP_verify_3vJ5gvq4bvjihQS3.txt")
    public Object downloadFile(HttpServletResponse response, HttpServletRequest request) throws IOException {
        
        File file = ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX + "MP_verify_3vJ5gvq4bvjihQS3.txt");
        if (file.exists()) {
            response.setContentType("application/force-download");// 设置强制下载不打开
            response.addHeader("Content-Disposition", "attachment;fileName=" + "MP_verify_3vJ5gvq4bvjihQS3.txt");
            byte[] buffer = new byte[1024];
            FileInputStream fis = null;
            BufferedInputStream bis = null;
            fis = new FileInputStream(file);
            bis = new BufferedInputStream(fis);
            OutputStream outputStream = response.getOutputStream();
            int i = bis.read(buffer);
            while (i != -1) {
                outputStream.write(buffer, 0, i);
                i = bis.read(buffer);
            }
            
            return "下载成功";
        }
        return null;
    }
}
