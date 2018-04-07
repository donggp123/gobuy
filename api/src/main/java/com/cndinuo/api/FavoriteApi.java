package com.cndinuo.api;

import com.alibaba.fastjson.JSON;
import com.cndinuo.base.BaseApi;
import com.cndinuo.common.Const;
import com.cndinuo.common.RespData;
import com.cndinuo.common.RetCode;
import com.cndinuo.domain.MberFavorite;
import com.cndinuo.domain.Member;
import com.cndinuo.page.Page;
import com.cndinuo.service.MberFavoriteService;
import com.cndinuo.service.MemberService;
import com.cndinuo.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("favorite")
public class FavoriteApi extends BaseApi {

    @Autowired
    private MberFavoriteService favoriteService;
    @Autowired
    private MemberService memberService;

    @RequestMapping(value = "list",method = RequestMethod.POST)
    public RespData list(@RequestBody Map<String, Object> params) {
        log.info("用户获取收藏列表: params==" + params);
        String token = params.get("token") + "";
        if (StringUtil.isEmpty(token)) {
            return RespData.errorMsg(RetCode.LOG_IN_AGAIN.getCode(),"请先登录");
        }

        Member m = memberService.getByToken(token);
        if (m == null) {
            return RespData.errorMsg(RetCode.LOG_IN_AGAIN.getCode(),"登录已过期，请重新登录");
        }
        params.put("mberId", m.getId());
        Page<MberFavorite> page = favoriteService.getByPage(params);

        if (page.getResults() == null || page.getResults().size() == 0) {
            return RespData.errorMsg("暂时没有收藏商家！");
        }
        log.info("获取结果："+JSON.toJSONString(page));
        return RespData.successMsg("", page);
    }



    @RequestMapping(value = "edit",method = RequestMethod.POST)
    public RespData edit(@RequestBody Map<String, String> params) {
        log.info("收藏商家：params=="+params);
        RespData data = null;
        try {
            data = favoriteService.favorite(params.get("token"), params.get("mrhtId"));
        } catch (Exception e) {
            e.printStackTrace();
            return RespData.errorMsg(Const.ERROR_MSG);
        }
        log.info("收藏结果："+ JSON.toJSONString(data));
        return data;
    }

    @RequestMapping(value = "delete",method = RequestMethod.POST)
    public RespData delete(@RequestBody Map<String, Object> params) {
        log.info("批量删除收藏的商家：params=="+params);
        RespData data = null;
        try {
            data = favoriteService.delete(params);
        } catch (Exception e) {
            e.printStackTrace();
            return RespData.errorMsg(Const.ERROR_MSG);
        }
        log.info("删除结果："+ JSON.toJSONString(data));
        return data;
    }
}
