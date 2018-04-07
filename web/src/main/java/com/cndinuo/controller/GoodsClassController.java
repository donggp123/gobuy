package com.cndinuo.controller;


import com.cndinuo.common.RespData;
import com.cndinuo.domain.MrhtGoodsClass;
import com.cndinuo.service.MrhtGoodsClassService;
import com.cndinuo.vo.GoodsClassVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("clazz")
public class GoodsClassController {

    @Autowired
    private MrhtGoodsClassService mrhtGoodsClassService;


    @RequestMapping("list")
    public String list(Model model){
        return "clazz/list";
    }


    /**
     * 商品分类添加
     * @param params
     * @param model
     * @return
     */
    @RequestMapping("add")
    public String classAdd(@RequestParam Map params,Model model){
        MrhtGoodsClass mrhtGoodsClass = new MrhtGoodsClass();
        if (params.size() > 0){
            List<MrhtGoodsClass> oneByMap = mrhtGoodsClassService.getByMap(params);
            if (oneByMap != null && oneByMap.size()>0){
                mrhtGoodsClass.setCode(String.valueOf(Integer.parseInt(oneByMap.get(0).getCode())+1));
                mrhtGoodsClass.setParCode(oneByMap.get(0).getParCode());
                mrhtGoodsClass.setGrade(oneByMap.get(0).getGrade());
            }else{
                String code = params.get("parCode").toString();
                Byte level = Byte.parseByte(params.get("grade").toString());
                mrhtGoodsClass.setCode(code + "1");
                mrhtGoodsClass.setParCode(code);
                mrhtGoodsClass.setGrade((byte) (level+1));
            }
            model.addAttribute("mrhtClass", mrhtGoodsClass);
        }else{
            params.put("parCode", "0");
            int result = mrhtGoodsClassService.getCountByMap(params);
            mrhtGoodsClass.setCode(result+1+""+(result+1));
            mrhtGoodsClass.setParCode("0");
            mrhtGoodsClass.setGrade((byte) 1);
            model.addAttribute("mrhtClass", mrhtGoodsClass);
        }
        return "clazz/add";
    }

    /**
     * 加载所有的分类
     * @return
     */
    @RequestMapping("data")
    public @ResponseBody RespData data() {
        List<MrhtGoodsClass> mrhtGoodsClasses = mrhtGoodsClassService.getAll();
        return RespData.successMsg("", mrhtGoodsClasses);
    }

    /**
     * 商品分类信息保存
     * @param
     * @return
     */
    @RequestMapping("save")
    public @ResponseBody RespData save(MrhtGoodsClass mrhtGoodsClass){
        MrhtGoodsClass mrhtGoodsClass1 = mrhtGoodsClassService.insert(mrhtGoodsClass);
        if(mrhtGoodsClass1.getId() != null && mrhtGoodsClass1.getId() > 0){
            return RespData.successMsg("保存成功");
        }
        return RespData.errorMsg("保存失败");
    }


    /**
     * 编辑商品分类名称
     * @param
     * @return
     */
    @RequestMapping("edit")
    public @ResponseBody RespData edit(MrhtGoodsClass mrhtGoodsClass){
        int result = mrhtGoodsClassService.updateById(mrhtGoodsClass);
        if(result>0){
            return RespData.successMsg("修改成功");
        }
        return RespData.errorMsg("修改失败");
    }

    @RequestMapping("getSelectData")
    public @ResponseBody RespData getSelectData() {
        List<GoodsClassVO> data = mrhtGoodsClassService.getSelectData();
        return RespData.successMsg("", data);
    }


}
