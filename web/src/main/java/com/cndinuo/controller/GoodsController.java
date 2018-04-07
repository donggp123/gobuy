package com.cndinuo.controller;

import com.cndinuo.common.HtmlUtil;
import com.cndinuo.common.RespData;
import com.cndinuo.common.SessionUtils;
import com.cndinuo.domain.MrhtGoods;
import com.cndinuo.domain.MrhtGoodsClass;
import com.cndinuo.domain.MrhtInfo;
import com.cndinuo.domain.UserManager;
import com.cndinuo.page.Page;
import com.cndinuo.service.MrhtGoodsClassService;
import com.cndinuo.service.MrhtGoodsService;
import com.cndinuo.service.MrhtInfoService;
import com.cndinuo.service.SysSettingService;
import com.cndinuo.vo.GoodsClassVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("goods")
public class GoodsController {

    @Autowired
    private MrhtGoodsService mrhtGoodsService;
    @Autowired
    private MrhtInfoService infoService;
    @Autowired
    MrhtGoodsClassService goodsClassService;
    @Autowired
    private SysSettingService settingService;

    /**
     * 商品列表
     * @param params
     * @param model
     * @param
     * @return
     */
    @RequestMapping("list")
    public String list(@RequestParam Map params, Model model){
        UserManager user = SessionUtils.getCurrentSysUser();
        params.put("mrhtId", user.getId());
        Page<MrhtGoods> page = mrhtGoodsService.getByPage(params);
        String imgServer = settingService.getByKey("img_server");
        model.addAttribute("page", page);
        model.addAttribute("params", params);
        model.addAttribute("user", user);
        model.addAttribute("imgServer", settingService.getByKey("img_server"));
        return "goods/list";
    }


    /**
     * 添加和修改商品
     * @param merchantGoods
     * @param model
     * @return
     */
    @RequestMapping("edit")
    public String edit(MrhtGoods merchantGoods, Model model){
        UserManager user = SessionUtils.getCurrentSysUser();
        String imgServer = settingService.getByKey("img_server");
        if (user.getType() == 1) {//供应商
            MrhtInfo info = infoService.getById(user.getId());
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("code", info.getGoodsType());
            List<MrhtGoodsClass> goodsClasses = goodsClassService.getByMap(params);
            List<GoodsClassVO> all = goodsClassService.getSelectData();
            if (merchantGoods.getId() != null && merchantGoods.getId() > 0){
                merchantGoods = mrhtGoodsService.getById(merchantGoods.getId());
                Map<String, Object> data = HtmlUtil.clazzSelect(goodsClasses,all,"edit",merchantGoods.getGoodsType());
                model.addAttribute("goodsClass",data);
            }else{
                Map<String, Object> data = HtmlUtil.clazzSelect(goodsClasses,all,"add",info.getGoodsType());
                model.addAttribute("goodsClass",data);
            }
        }else{//商户

            Map<String, Object> params = new HashMap<String, Object>();
            params.put("parCode", 0);
            List<MrhtGoodsClass> goodsClasses = goodsClassService.getByMap(params);
            List<GoodsClassVO> all = goodsClassService.getSelectData();
            if (merchantGoods.getId() != null && merchantGoods.getId() > 0){
                merchantGoods = mrhtGoodsService.getById(merchantGoods.getId());
                Map<String, Object> data = HtmlUtil.clazzSelect(goodsClasses,all,"edit",merchantGoods.getGoodsType());
                model.addAttribute("goodsClass",data);
            }else{
                Map<String, Object> data = HtmlUtil.clazzSelect(goodsClasses,all,"add","");
                model.addAttribute("goodsClass",data);
            }
        }

        model.addAttribute("goods", merchantGoods);
        model.addAttribute("imgServer", imgServer);
        model.addAttribute("user", user);
        return "goods/edit";
    }


    /**
     * 保存商品信息
     * @param
     * @param
     * @return
     */
    @RequestMapping("save")
    @ResponseBody
    public RespData save(MrhtGoods goods){
        UserManager user = SessionUtils.getCurrentSysUser();
        int flag = 0;
        String msg = "";
        try {
            if (goods.getId() != null && goods.getId() > 0){
                flag = mrhtGoodsService.updateById(goods);
                msg = "修改成功，还要继续修改吗？";
            }else {
                goods.setMrhtId(user.getId());
                goods.setStatus((byte) 0);
                goods.setDeleted((byte) 0);
                String goodsType = goods.getGoodsType();
                if (goodsType.endsWith(",")) {
                    goods.setGoodsType(goodsType.substring(0,goodsType.length()-1));
                }
                mrhtGoodsService.insert(goods);
                msg = "添加成功，还要继续添加吗？";
            }
            if (flag >0 || goods.getId()>0){
                return RespData.successMsg(msg);
            }
        }catch (Exception e){
            e.printStackTrace();
            return RespData.errorMsg("系统异常！");
        }
        return RespData.errorMsg("操作失败！");
    }


    /**
     * 删除
     * @param params
     * @return
     */
    @RequestMapping("del")
    public @ResponseBody RespData del(@RequestParam Map params){
        try {
            if (mrhtGoodsService.deleteByMap(params)>0){
                return RespData.successMsg("删除成功");
            }
        }catch (Exception e){
            e.printStackTrace();
            return RespData.errorMsg("系统异常");
        }
        return RespData.errorMsg("删除失败");
    }


    /**
     * 商品上架下架
     * @param params
     * @param num
     * @return
     */
    @RequestMapping("release")
    public @ResponseBody RespData release(@RequestParam Map params, Integer num){
        int flag = 0;
        String msg = num == 1 ? "上架成功!" : "下架成功!";
        params.put("status",num);
        flag = mrhtGoodsService.updateByMap(params);
        if (flag > 0){
            return RespData.successMsg(msg);
        }
        return RespData.errorMsg(msg);
    }

    /**
     * 验证条形码是否存在
     * @param params
     * @return
     */
    @RequestMapping("code")
    public @ResponseBody RespData code(@RequestParam Map params){
        UserManager user = SessionUtils.getCurrentSysUser();
        params.put("mrhtId", user.getId());
        List<MrhtGoods> goods = mrhtGoodsService.getByMap(params);
        if (goods.size() > 0 && goods != null){
            return RespData.errorMsg("该条形码已存在");
        }
        return RespData.successMsg("");
    }

    /**
     * 判断状态是否可编辑
     * @param id
     * @return
     */
    @RequestMapping("isShelves")
    public @ResponseBody RespData isShelves(Integer id){
        MrhtGoods goods = mrhtGoodsService.getById(id);
        if(goods != null && goods.getStatus() ==1){
            return RespData.errorMsg("不可编辑下架以后才可编辑");
        }
        return RespData.successMsg("可以编辑");
    }

    /**
     * 加载商品分类
     * @param params
     * @return
     */
    @RequestMapping("classes")
    public @ResponseBody RespData classes(@RequestParam Map params){
        List<MrhtGoodsClass> goods = goodsClassService.getByMap(params);
        return RespData.successMsg("", goods);
    }

}
