package com.cndinuo.api;

import com.alibaba.fastjson.JSON;
import com.cndinuo.base.BaseApi;
import com.cndinuo.common.Const;
import com.cndinuo.common.RespData;
import com.cndinuo.common.RetCode;
import com.cndinuo.domain.MberComment;
import com.cndinuo.domain.MberCommentReply;
import com.cndinuo.domain.OrderItem;
import com.cndinuo.page.Page;
import com.cndinuo.service.MberCommentService;
import com.cndinuo.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("comment")
public class CommentApi extends BaseApi {


    @Autowired
    private MberCommentService commentService;

    @RequestMapping(value = "userlist", method = RequestMethod.POST)
    @ResponseBody
    public RespData userlist(@RequestBody Map<String, Object> params) {
        log.info("用户评价列表 params==" + params);
        return commentService.getCommentByMap(params);
    }

    @RequestMapping(value = "goodscommlist", method = RequestMethod.POST)
    @ResponseBody
    public RespData goodsCommList(@RequestBody Map<String, Object> params) {
        log.info("商家商品评价列表 param==" + params);
        RespData data = commentService.getGoodsByCommList(params);
        log.info("返回结果：" + JSON.toJSONString(data));
        return data;
    }

    @RequestMapping(value = "commview", method = RequestMethod.POST)
    @com.cndinuo.filter.JSON(type = OrderItem.class,include = "goodsImage,goodsName,orderNo,num,payPrice")
    public RespData commView(@RequestBody Map<String, Object> params) {
        log.info("评价页面接口 params == " + params);
        RespData data = commentService.getCommView(params);
        log.info("返回结果" + JSON.toJSONString(data));
        return data;
    }

    @RequestMapping(value = "save", method = RequestMethod.POST)
    @com.cndinuo.filter.JSON(type = MberCommentReply.class,include = "content,replyTime")
    public RespData save(@RequestBody Map<String ,Object> params){
        log.info("评价接口 , 接受用户提交评价信息 params==" + params);
        RespData data = null;
        try {
            data = commentService.save(params);
        } catch (Exception e) {
            e.printStackTrace();
            return RespData.errorMsg(Const.ERROR_MSG);
        }
        log.info("保存结果" + JSON.toJSONString(data));
        return data;
    }

    @RequestMapping(value = "satisfactionridercommlist", method = RequestMethod.POST)
    @com.cndinuo.filter.JSON(type = MberComment.class, include = "comLevel, content")
    @com.cndinuo.filter.JSON(type = Page.class,filter = "p,defaultPageSize")
    public RespData satisfactionRiderCommList(@RequestBody Map<String, Object> params) {
        log.info("用户对骑手的评价列表(满意) params == " + params);
        if (StringUtil.isEmpty(params.get("token")+"")) {
            return RespData.errorMsg(RetCode.LOG_IN_AGAIN.getCode(),"请先登录");
        }
        Page<MberComment> page = commentService.getSatisfactionRiderCommonList(params);
        log.info("返回结果" + JSON.toJSONString(page));
        return RespData.successMsg("请求成功",page);
    }

    @RequestMapping(value = "generalridercommlist", method = RequestMethod.POST)
    @com.cndinuo.filter.JSON(type = MberComment.class, include = "id,comLevel, content, reconsider")
    @com.cndinuo.filter.JSON(type = Page.class,filter = "p,defaultPageSize")
    public RespData generalRiderCommList(@RequestBody Map<String, Object> params) {
        log.info("用户对骑手的评价列表(一般) params == " + params);
        if (StringUtil.isEmpty(params.get("token")+"")) {
            return RespData.errorMsg(RetCode.LOG_IN_AGAIN.getCode(),"请先登录");
        }
        Page<MberComment> page = commentService.getGeneralRiderCommonList(params);
        log.info("返回结果" + JSON.toJSONString(page));
        return RespData.successMsg("请求成功",page);
    }

    @RequestMapping(value = "commentListForGoods",method = RequestMethod.POST)
    @ResponseBody
    public RespData commentListForGoods(@RequestBody Map<String, String> params) {
        log.info("商家下对某一商品评价：params=="+params);
        List<Map<String, Object>> list = commentService.getCommentListForGoods(params.get("mrhtId"), params.get("goodsId"));
        return RespData.successMsg("", list);
    }
}
