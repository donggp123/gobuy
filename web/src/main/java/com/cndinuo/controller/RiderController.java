package com.cndinuo.controller;

import com.cndinuo.common.Const;
import com.cndinuo.common.RespData;
import com.cndinuo.domain.MberInfo;
import com.cndinuo.domain.MberRiderInfo;
import com.cndinuo.page.Page;
import com.cndinuo.service.MberInfoService;
import com.cndinuo.service.MberRiderInfoService;
import com.cndinuo.service.SmsService;
import com.cndinuo.utils.TextFilterUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping("rider")
public class RiderController {

    @Autowired
    private MberInfoService infoService;
    @Autowired
    private MberRiderInfoService riderInfoService;
    @Autowired
    private SmsService smsService;

    @RequestMapping("list")
    public String list(@RequestParam Map params, Model model){
        Page<MberInfo> page = infoService.getByPage(params);
        model.addAttribute("page", page);
        return "rider/list";
    }

    @RequestMapping("info")
    public String info(@RequestParam Map params,Model model){
        MberRiderInfo riderInfo = riderInfoService.getOneByMap(params);
        model.addAttribute("riderInfo", riderInfo);
        return "rider/info";
    }

    @RequestMapping("adopt")
    public @ResponseBody RespData adopt(@RequestParam Map params){
        try {
            return riderInfoService.adopt(params);
        } catch (Exception e) {
            e.printStackTrace();
            return RespData.errorMsg(Const.ERROR_MSG);
        }
    }

    @RequestMapping("view")
    public String view(Integer id,Integer mberId,Model model){
        model.addAttribute("mberId", mberId);
        model.addAttribute("id", id);
        return "rider/view";
    }

    @RequestMapping("save")
    public @ResponseBody
    RespData save(@RequestParam Map params) {
        params.put("status", 2);
        int result = riderInfoService.updateByMap(params);
        if(result > 0){
            MberRiderInfo info = riderInfoService.getOneByMap(params);
            String content = String.format(Const.SMS_SPPROVE_RIDER_REFUSE,info.getAuthName(), TextFilterUtil.checkSensitiveWord(info.getRemark()));
            smsService.sendMsg(info.getAuthPhone(),content);
            return RespData.successMsg("操作成功");
        }
        return RespData.errorMsg("操作失败");
    }
}
