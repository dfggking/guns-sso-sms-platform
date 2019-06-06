package cn.stylefeng.guns.modular.web.controller;

import cn.stylefeng.guns.core.base.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/coupons")
public class CouponsController extends BaseController {
    
    private static String PREFIX = "/web/coupons/";
    
    @RequestMapping("/share")
    public String share() {
        return PREFIX + "share.html";
    }
    
    @RequestMapping("/detail")
    public String detail() {
        return PREFIX + "detail.html";
    }
}
