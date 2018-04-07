package com.cndinuo.controller;

import com.cndinuo.common.RespData;
import com.cndinuo.common.SessionUtils;
import com.cndinuo.domain.*;
import com.cndinuo.eunm.PurchaseStatusEnum;
import com.cndinuo.page.Page;
import com.cndinuo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("merchant")
public class MerchantController {

    @Autowired
    private MerchantService merchantService;
    @Autowired
    private SysSettingService sysSettingService;
    @Autowired
    private MrhtAccountService accountService;
    @Autowired
    private MrhtBalanceService balanceService;
    @Autowired
    private MrhtInfoService mrhtInfoService;
    @Autowired
    private MrhtStockService mrhtStockService;
    @Autowired
    private MrhtGoodsService merchantGoodsService;
    @Autowired
    private MrhtPurchaseService mrhtPurchaseService;
    @Autowired
    private UploadService uploadService;


    /**
     * 检测用户是否存在
     * @param params
     * @return
     */
    @RequestMapping("/exist")
    public @ResponseBody
    RespData isExist(@RequestParam Map params) {

        List<Merchant> merchants = merchantService.getByMap(params);
        if (null != merchants && merchants.size() > 0) {
            return RespData.errorMsg("用户已存在");
        }
        return RespData.successMsg("");
    }


    /**
     * 保存注册信息
     * @param merchant
     * @param mrhtInfo
     * @return
     */
    @RequestMapping("/save")
    public @ResponseBody RespData save(Merchant merchant , MrhtInfo mrhtInfo){

        boolean result = false;
        try {
            result = merchantService.save(merchant,mrhtInfo);
        } catch (Exception e) {
            e.printStackTrace();
            return RespData.errorMsg("系统发生异常!");
        }
        if (result){
            return RespData.successMsg("注册成功");
        }
        return RespData.errorMsg("注册失败");
    }



    /**
     * 商户个人信息
     * @param
     * @param model
     * @return
     */
    @RequestMapping("info")
    public String merchatInfo(Model model){
        UserManager user = SessionUtils.getCurrentSysUser();
        MrhtInfo mrhtInfo = mrhtInfoService.getByMrhtId(user.getId());
        model.addAttribute("mrht", user);
        model.addAttribute("info", mrhtInfo);
        model.addAttribute("imgServer", sysSettingService.getByKey("img_server"));
        return "merchant/info";
    }

    /**
     * 修改收款账户
     * @param
     * @param model
     * @return
     */
    @RequestMapping("editAccount")
    public String editAccount(Model model){
        UserManager user = SessionUtils.getCurrentSysUser();
        MrhtInfo mrhtInfo = mrhtInfoService.getByMrhtId(user.getId());
        model.addAttribute("mrht", mrhtInfo);
        return "merchant/edit-account";
    }


    /**
     * 保存账号修改信息
     * @param mrhtInfo
     * @return
     */
    @RequestMapping("saveAccount")
    public @ResponseBody RespData saveAccount(MrhtInfo mrhtInfo){
        boolean flag = mrhtInfoService.updateByMrhtAccount(mrhtInfo);
        if(flag){
            return RespData.successMsg("修改成功");
        }
        return RespData.errorMsg("修改失败");
    }

    @RequestMapping("/list")
    public String list(@RequestParam Map params, Model m){
        Page<Merchant> page = merchantService.getByPage(params);
        m.addAttribute("page",page);
        m.addAttribute("params",params);
        return "merchant/list";
    }

    @RequestMapping("/verify")
    public String verify(Merchant merchant, Model m){
        merchant = merchantService.getById(merchant.getId());
        MrhtInfo mrhtInfo = mrhtInfoService.getByMrhtId(merchant.getId());
        m.addAttribute("merchant", merchant);
        m.addAttribute("mrhtInfo", mrhtInfo);
        m.addAttribute("imgServer", sysSettingService.getByKey("img_server"));
        return "merchant/verify";
    }
    @RequestMapping("approve")
    public @ResponseBody RespData approve(Merchant merchant){
        boolean result = merchantService.updateByStatus(merchant);
        if(result){
            if (merchant.getStatus() == 2){
                return RespData.successMsg("审核通过");
            }else{
                return RespData.successMsg("审核拒绝");
            }
        }
        return RespData.errorMsg("操作失败");
    }

    /**
     * 修改门店图片
     * @param file
     * @param mrhtInfo
     * @return
     */
    @RequestMapping("editStoreImage")
    public @ResponseBody RespData editStoreMap(@RequestParam(value="uploadFile") MultipartFile file,MrhtInfo mrhtInfo){

        RespData data = uploadService.upload(file);
        Map<String,Object> map = (Map<String, Object>) data.getData();
        mrhtInfo.setStoreImage(map.get("imgPath").toString());
        boolean flag = mrhtInfoService.updateByStoreImage(mrhtInfo);
        if (flag){
            return RespData.successMsg("上传成功");
        }
        return RespData.errorMsg("上传失败！");
    }

    /**
     * 商家首页
     * @param params
     * @param model
     * @return
     */
    @RequestMapping("home")
    public String  index(@RequestParam Map params,Model model){
        UserManager user = SessionUtils.getCurrentSysUser();
        params.put("mrhtId", user.getId());
        MrhtStock  mrhtStock = mrhtStockService.getOneByMap(params);
        if (mrhtStock != null){
            params.put("stockLimit",mrhtStock.getStockLimit());
            params.put("mrhtId", user.getId());
            List<MrhtGoods> goods = merchantGoodsService.getByMap(params);
            model.addAttribute("goods", goods);
        }
        params.clear();
        params.put("mrhtId", user.getId());
        List<MrhtPurchase> mrhtPurchases = mrhtPurchaseService.getByMap(params);
        model.addAttribute("purchases", mrhtPurchases);
        return "merchant/mrht-home";
    }

    /**
     * 我的账户
     * @param params
     * @param model
     * @return
     */
    @RequestMapping("account")
    public String account(@RequestParam Map params ,Model model){
        UserManager user = SessionUtils.getCurrentSysUser();
        MrhtAccount a = accountService.getById(user.getId());
        params.put("mrhtId", user.getId());
        Page<MrhtBalance> page = balanceService.getByPage(params);
        model.addAttribute("account", a);
        model.addAttribute("page", page);
        model.addAttribute("mrhtName", user.getRealName());
        return "merchant/account";
    }

}
