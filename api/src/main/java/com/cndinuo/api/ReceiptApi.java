package com.cndinuo.api;

import com.alibaba.fastjson.JSON;
import com.cndinuo.base.BaseApi;
import com.cndinuo.common.Const;
import com.cndinuo.common.RespData;
import com.cndinuo.common.RetCode;
import com.cndinuo.domain.MberReceipt;
import com.cndinuo.domain.Member;
import com.cndinuo.domain.MrhtInfo;
import com.cndinuo.service.MberReceiptService;
import com.cndinuo.service.MemberService;
import com.cndinuo.service.MrhtInfoService;
import com.cndinuo.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("receipt")
public class ReceiptApi extends BaseApi{


    @Autowired
    private MberReceiptService receiptService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private MrhtInfoService mrhtInfoService;

    @RequestMapping(value = "add",method = RequestMethod.POST)
    public RespData add(@RequestBody Map<String,Object> params ){
        log.info("用户添加送货地址：params"+"【"+params+"】");
        RespData data = null;
        try {
            data = receiptService.add(params);
            log.info("保存结果：" + JSON.toJSONString(data));
        } catch (Exception e) {
            e.printStackTrace();
            return RespData.errorMsg(Const.ERROR_MSG);
        }
        return data;
    }


    @RequestMapping(value = "list",method = RequestMethod.POST)
    public RespData list(@RequestBody Map<String,Object> params){
        log.info("返回用户所用地址");
        if (StringUtil.isEmpty(params.get("token") + "")) {
            return RespData.errorMsg(RetCode.LOG_IN_AGAIN.getCode(),"请先登录");
        }
        Member member = memberService.getByToken(params.get("token").toString());
        if (member == null) {
            return RespData.errorMsg(RetCode.LOG_IN_AGAIN.getCode(),"登录已过期，请重新登录");
        }
        params.put("mberId", member.getId());
        List<MberReceipt> list = receiptService.getByMap(params);
        return RespData.successMsg("",list);
    }

    @RequestMapping(value = "edit",method = RequestMethod.POST)
    public RespData edit(@RequestBody Map<String,Object> params){
        log.info("修改送货地址：params="+params);
        RespData data = receiptService.edit(params);
        log.info("修改结果：" + JSON.toJSONString(data));
        return data;
    }

    @RequestMapping(value = "save",method = RequestMethod.POST)
    public RespData save(@RequestBody Map<String,Object> params){
        log.info("保存修改的地址");
        RespData data = null;
        try {
            data = receiptService.save(params);
            log.info("修改结果:"+ JSON.toJSONString(data));
        } catch (Exception e) {
            e.printStackTrace();
            return RespData.errorMsg(Const.ERROR_MSG);
        }
        return data;
    }

    @RequestMapping(value = "del",method = RequestMethod.POST)
    public RespData del(@RequestBody Map<String,Object> params){
        log.info("删除地址：params"+params);
        RespData data = null;
        try {
            data = receiptService.del(params);
            log.info("删除结果：" + JSON.toJSONString(data));
        } catch (Exception e) {
            e.printStackTrace();
            return RespData.errorMsg(Const.ERROR_MSG);
        }
        return data;
    }

    @RequestMapping(value = "default",method = RequestMethod.POST)
    public RespData getDefaultAddr(@RequestBody Map<String,Object> params){
        log.info("获取用户默认地址");
        if(StringUtil.isEmpty(StringUtil.toString(params.get("token")))){
            return RespData.errorMsg(RetCode.LOG_IN_AGAIN.getCode(),"请先登录");
        }
        Member m = memberService.getByToken(StringUtil.toString(params.get("token")));
        if (m == null) {
            return RespData.errorMsg(RetCode.LOG_IN_AGAIN.getCode(),"登录已过期，请重新登录");
        }
        params.put("mberId",m.getId());
        params.put("isDefault", 1);
        MberReceipt receipt = receiptService.getOneByMap(params);
        MrhtInfo info = mrhtInfoService.getByMrhtId(StringUtil.toInt(params.get("mrhtId")));
        receipt.setLng(info.getLng());
        receipt.setLat(info.getLat());
        return RespData.successMsg("获取成功!", receipt);
    }
}
