package cn.stylefeng.guns.modular.api;

import cn.stylefeng.guns.core.base.controller.BaseController;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Objects;

/**
 * 淘宝开放平台API
 * @version 1.0
 */
@RestController
@RequestMapping("/tbApi")
public class ApiTaobaoController extends BaseController {
    
    /**
     * 根据链接返回淘口令
     * @param urls
     * @return
     */
    @RequestMapping(value="/urlConvertTkl")
    public Object urlConvertTkl(@RequestParam("urls") String urls) {
        /**
         * 第三方获取淘口令接口
         */
        String POST_URL = "http://api.chaozhi.hk/tb/ulandArray";
        try {
            /**
             * 模拟Post方法
             */
            JSONObject params = new JSONObject();
            params.put("pid", "mm_367990048_412150185_108185100326");
            params.put("session", "700001011409035858ee6e8b6bfb8694cae8b3cbc9b448e519c71ccf2dae90b7c7288d5873980889");
            params.put("urls", urls);
            params.put("isTkl", "true");
            params.put("isShort", "true");
            return doPost(POST_URL, params);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * 根据淘宝分享的淘口令返回淘宝链接
     */
    @RequestMapping(value = "tklConvertUrl")
    public Object tklConvertUrl(@RequestParam("tkl") String tkl) {
        /**
         * 第三方获取淘口令接口
         */
        String POST_URL = "http://api.chaozhi.hk/tb/tklParse";
        try {
            /**
             * 模拟Post方法
             */
            JSONObject params = new JSONObject();
            params.put("tkl", tkl);
            return doPost(POST_URL, params);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    private Object doPost(String postUrl, JSONObject params) throws IOException {
        /**
         * 模拟Post方法
         */
        HttpPost pagePost = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        pagePost = new HttpPost(postUrl);
        pagePost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.77 Safari/537.36");
        pagePost.setHeader("Content-Type", "application/json;charset=UTF-8");
        if (Objects.nonNull(params)) {
            StringEntity stringEntity = new StringEntity(params.toString());
            stringEntity.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
            pagePost.setEntity(stringEntity);
        }
        HttpResponse response = httpClient.execute(pagePost);
        HttpEntity entity = response.getEntity();
        String info = EntityUtils.toString(entity);
        return JSONObject.parse(info);
    }
}
