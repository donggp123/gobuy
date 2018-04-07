package com.cndinuo.common;

import com.cndinuo.domain.MrhtGoodsClass;
import com.cndinuo.utils.StringUtil;
import com.cndinuo.vo.GoodsClassVO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HtmlUtil {

    /**
     * 商品分类下拉框三级联动
     */
    public static Map<String, Object> clazzSelect(List<MrhtGoodsClass> clzzs, List<GoodsClassVO> all,String flag,String goodsType){
        StringBuffer sb1 = new StringBuffer();
        StringBuffer sb2 = new StringBuffer();
        StringBuffer sb3 = new StringBuffer();
        sb1.append("<select class=\"input-text\" name=\"goodsType\" onchange=\"classes(this.value,'goods1','-请选择-')\">");
        sb1.append("<option value=\"\">-请选择-</option>");
        sb2.append("<select class=\"input-text\" name=\"goodsType\" id=\"goods1\" onchange=\"classes(this.value,'goods2','-请选择-')\">");
        sb2.append("<option value=\"\">-请选择-</option>");
        sb3.append("<select class=\"input-text\" name=\"goodsType\" id=\"goods2\">");
        sb3.append("<option value=\"\">-请选择-</option>");
        if (clzzs != null && clzzs.size() > 0) {
            for (MrhtGoodsClass clzz : clzzs) {
                String code = "";
                String code1 = "";
                if (StringUtil.isNotEmpty(goodsType) && "edit".equals(flag)) {
                    if (goodsType.contains(clzz.getCode())) {
                        code = clzz.getCode();
                        sb1.append("<option selected value=\""+clzz.getCode()+"\">"+clzz.getName()+"</option>");
                    }else{
                        sb1.append("<option value=\""+clzz.getCode()+"\">"+clzz.getName()+"</option>");
                    }
                }else{
                    sb1.append("<option value=\""+clzz.getCode()+"\">"+clzz.getName()+"</option>");
                }

                if (StringUtil.isNotEmpty(code) && "edit".equals(flag)) {
                    for (GoodsClassVO a : all) {
                        if (a.getId().equals(code)) {
                            List<GoodsClassVO> son1 = a.getSon();
                            for (GoodsClassVO s1 : son1) {
                                if (goodsType.contains(s1.getId())) {
                                    code1 = s1.getId();
                                    sb2.append("<option selected value=\""+s1.getId()+"\">"+s1.getName()+"</option>");
                                }else{
                                    sb2.append("<option value=\""+s1.getId()+"\">"+s1.getName()+"</option>");
                                }

                                if (StringUtil.isNotEmpty(code1)) {
                                    List<GoodsClassVO> son2 = s1.getSon();
                                    for (GoodsClassVO s2 : son2) {
                                        if (goodsType.contains(s2.getId())) {
                                            sb3.append("<option selected value=\""+s2.getId()+"\">"+s2.getName()+"</option>");
                                        }else{
                                            sb3.append("<option value=\""+s2.getId()+"\">"+s2.getName()+"</option>");
                                        }
                                    }
                                    code1 = "";
                                }
                            }
                        }

                    }
                }

            }
        }
        sb1.append("</select>");
        sb2.append("</select>");
        sb3.append("</select>");

        Map<String, Object> retData = new HashMap<String, Object>();
        retData.put("goods1", sb1);
        retData.put("goods2", sb2);
        retData.put("goods3", sb3);
        return retData;
    }
}
