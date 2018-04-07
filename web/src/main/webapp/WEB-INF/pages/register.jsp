<%--
  Created by IntelliJ IDEA.
  User: dgp
  Date: 2017/8/18
  Time: 16:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="common/common.jsp"%>
<html>
<head>
    <title>用户注册</title>
    <meta charset="utf-8">
    <meta name="renderer" content="webkit|ie-comp|ie-stand">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
    <meta http-equiv="Cache-Control" content="no-siteapp" />
    <link rel="Shortcut Icon" href="${ctx}/static/h-ui.admin/images/favicon.ico" />
    <style>
        #goodsType-error.error{
            margin-left:400px;
        }
    </style>
</head>
<body>
<div class="">
    <div style="width:100%;height:40px;background: #000;">
        <div style="color:#fff;font-size: 20px;line-height: 40px;padding-left:20px;width:48%;float:left;">内蒙古蒂诺科技有限公司</div>
        <div style="width:49%;float:right;padding-right:20px;">
            <a href="/" class="btn radius" style="float:right;background: rgba(255,255,255,0.1);color:#fff;margin-top:5px;">&nbsp;登&nbsp;&nbsp;&nbsp;&nbsp;录&nbsp;</a>
        </div>
    </div>
    <div style="width:80%;margin:0 auto; margin-top:20px;padding:30px;">
        <div style="color:red;">页面说明：1、带有 * 为必填项。2、上传的扫面文件均需加盖公章（红章）。3、因客户提供信息错误造成的一切损失由客户自行承担。</div>
        <form action="/merchant/save" method="post" class="form form-horizontal" id="form-member-add">
            <div style="border:1px solid #ccc;padding-bottom: 20px;">
                <div class="row cl">
                    <label class="form-label col-xs-2"><i class="Hui-iconfont" style="font-size: larger;">合同信息</i></label>
                </div>
                <div class="row cl">
                    <label class="form-label col-xs-2"><span class="c-red">*</span>商户类型 :</label>
                    <div class="formControls col-xs-5 col-sm-9 skin-minimal">
                        <div class="radio-box">
                            <input onclick="$('#clssses').show();"  name="mrhtType" type="radio" id="mrhtType-1" value="1">
                            <label for="mrhtType-1"> 供应商</label>
                        </div>
                        <div class="radio-box">
                            <input onclick="$('#clssses').hide();$('.goodsType').attr('checked',false);" name="mrhtType" type="radio" id="mrhtType-2" value="2">
                            <label for="mrhtType-2"> 商家</label>
                        </div>
                    </div>
                </div>
                <div class="row cl" id="clssses" style="display: none;">
                    <label class="form-label col-xs-2"><span class="c-red">*</span>商品分类 :</label>
                    <div class="radio-box">
                        <c:forEach items="${goods}" var="goods">
                             <label><input class="goodsType" name="goodsType" type="checkbox" value="${goods.code}">  ${goods.name}</label>
                        </c:forEach>
                    </div>
                </div>
                <div class="row cl">
                    <label class="form-label col-xs-2"><span class="c-red">*</span>合同编号 :</label>
                    <div class="formControls col-xs-5">
                        <input id="compactNo" name="compactNo" type="text" placeholder="合同编号" class="input-text size-M">
                    </div>
                </div>
                <div class="row cl">
                    <label class="form-label col-xs-2"><span class="c-red">*</span>合同名称 :</label>
                    <div class="formControls col-xs-5">
                        <input id="compactName" name="compactName" type="text" placeholder="合同名称" class="input-text size-M">
                    </div>
                </div>
                <div class="row cl">
                    <label class="form-label col-xs-2"><span class="c-red">*</span>合同有效期 :</label>
                    <div class="formControls col-xs-5">
                        <input id="compactExpiryDate" name="compactExpiryDate" type="text" placeholder="合同有效期" class="input-text size-M">
                    </div>
                </div>
                <div class="row cl">
                    <label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>电子合同 :</label>
                    <div class="formControls col-xs-8 col-sm-10">
                        <div id="compactImageShow" style="float:left;display:none;">
                            <input class="compactImageSave" type="hidden" value="" name="compactImage">
                        </div>
                        <div onclick="uploads('compactImage','compactImageSave','compactImageShow',3);" id="uploads" style="width:88px;height: 88px;border:1px solid #ccc;line-height:24px;padding-top:20px;text-align: center;color: #ccc;cursor:pointer;float:left;">
                            <span style="font-size: 24px;">+</span><br/>添加图片
                        </div>
                        <input id="compactImage" type="file" name="file" style="display: none;">
                    </div>
                </div>
            </div>
            <div style="border:1px solid #ccc;padding-bottom: 20px;margin-top:20px;">
                <div class="row cl">
                    <label class="form-label col-xs-2"><i class="Hui-iconfont" style="font-size: larger;">商户信息</i></label>
                </div>
                <div class="row cl">
                    <label class="form-label col-xs-2"><span class="c-red">*</span>商户名称 :</label>
                    <div class="formControls col-xs-5">
                        <input id="mrhtName" name="mrhtName" type="text" placeholder="请填写商户名称" class="input-text size-M" />
                    </div>
                    <div class="formControls col-xs-5" style="color:#666666;"><span class="c-red">*</span>需与合同中商户名称保持一致</div>
                </div>
                <div class="row cl">
                    <label class="form-label col-xs-2"><span class="c-red">*</span>登录名 :</label>
                    <div class="formControls col-xs-5">
                        <input id="loginName" name="loginName" type="text" placeholder="请填写登录名" class="input-text size-M" />
                    </div>
                    <div class="formControls col-xs-5" style="color:#666666;"><span class="c-red">*</span>注:只能使用字母或数字（A-z,a-z,0-9）</div>
                </div>
                <div class="row cl">
                    <label class="form-label col-xs-2"><span class="c-red">*</span>密码 :</label>
                    <div class="formControls col-xs-5">
                        <input id="password" name="password" type="password" placeholder="请填写密码" class="input-text size-M" />
                    </div>
                </div>
                <div class="row cl">
                    <label class="form-label col-xs-2"><span class="c-red">*</span>确认密码 :</label>
                    <div class="formControls col-xs-5">
                        <input id="okpassword" name="okPassword" type="password" placeholder="请填写确认密码" class="input-text size-M" />
                    </div>
                </div>
                <div class="row cl">
                    <label class="form-label col-xs-2"><span class="c-red">*</span>商户电话 :</label>
                    <div class="formControls col-xs-5">
                        <input id="mobile" name="mobile" type="text" placeholder="请填写商户电话" class="input-text size-M" />
                    </div>
                </div>
                <div class="row cl">
                    <label class="form-label col-xs-2"><span class="c-red">*</span>营业时间 :</label>
                    <div class="formControls col-xs-5">
                        <input id="officeTime" name="officeTime" type="text" placeholder="请选择营业时间" class="input-text size-M" />
                    </div>
                </div>
                <div class="row cl">
                    <label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>门店图片 : </label>
                    <div class="formControls col-xs-8 col-sm-9">
                        <div id="storeImageShow" style="float:left;display:none;">
                            <input class="storeImageSave" type="hidden" value="" name="storeImage">
                        </div>
                        <div onclick="uploads('storeImage','storeImageSave','storeImageShow',1);" style="width:88px;height: 88px;border:1px solid #ccc;line-height:24px;padding-top:20px;text-align: center;color: #ccc;cursor:pointer;float:left;">
                            <span style="font-size: 24px;">+</span><br/>添加图片
                        </div>
                        <input id="storeImage" type="file" name="file" style="display: none;">
                    </div>
                </div>
                <div class="row cl">
                    <label class="form-label col-xs-2"><span class="c-red">*</span>商户地址 : </label>
                    <div class="formControls col-xs-2">
                        <select name="province" id="province" onchange="loadData(this.value,'city','市');" class="input-text size-M">
                            <option value="">省</option>
                        </select>
                    </div>
                    <div class="formControls col-xs-2">
                        <select name="city" id="city" onchange="loadData(this.value,'district','区')" class="input-text size-M">
                            <option value="">市</option>
                        </select>
                    </div>
                    <div class="formControls col-xs-2">
                        <select name="district" id="district" class="input-text size-M">
                            <option value="市">区</option>
                        </select>
                    </div>
                    <div class="formControls col-xs-3">
                        <input id="address" name="address" type="text" placeholder="请填写贵商户具体办公地址" class="input-text size-M" />
                    </div>
                </div>
                <div class="row cl">
                    <label class="form-label col-xs-2"><span class="c-red">*</span>地址坐标 :</label>
                    <div class="formControls col-xs-3">
                        <input id="lng" name="lng" type="text" placeholder="请填写经度" class="input-text size-M" />
                    </div>
                    <div class="formControls col-xs-3">
                        <input id="lat" name="lat" type="text" placeholder="请填写纬度" class="input-text size-M" />
                    </div>
                    <div class="formControls col-xs-3">
                        <a href="http://lbs.amap.com/console/show/picker" target="_blank" class="btn btn-danger radius size-M">获取地图坐标</a>
                    </div>
                </div>
            </div>
            <div style="border:1px solid #ccc;padding-bottom: 20px;margin-top:20px;">
                <div class="row cl">
                    <label class="form-label col-xs-2"><i class="Hui-iconfont" style="font-size: larger;">资质信息</i></label>
                </div>
                <div class="row cl">
                    <label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>营业执照 :</label>
                    <div class="formControls col-xs-8 col-sm-9">
                        <div id="busLicenseShow" style="float:left;display:none;">
                            <input class="busLicenseSave" type="hidden" value="" name="busLicense">
                        </div>
                        <div onclick="uploads('busLicense','busLicenseSave','busLicenseShow',1);"  style="width:88px;height: 88px;border:1px solid #ccc;line-height:24px;padding-top:20px;text-align: center;color: #ccc;cursor:pointer;float:left;">
                            <span style="font-size: 24px;">+</span><br/>添加图片
                        </div>
                        <input id="busLicense" type="file" name="file" style="display: none;">
                        <div  class="formControls col-xs-6" style="color:#666666;"><span class="c-red" >*</span>组织机构代码证,税务登记证(或三证合一)</div>
                    </div>
                </div>
                <div class="row cl">
                    <label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>经营许可证 :</label>
                    <div class="formControls col-xs-8 col-sm-9">
                        <div id="busCertShow" style="float:left;display:none;">
                            <input class="busCertSave" type="hidden" name="busCert">
                        </div>
                        <div onclick="uploads('busCert','busCertSave','busCertShow',1);" style="width:88px;height: 88px;border:1px solid #ccc;line-height:24px;padding-top:20px;text-align: center;color: #ccc;cursor:pointer;float:left;">
                            <span style="font-size: 24px;">+</span><br/>添加图片
                        </div>
                        <input id="busCert" type="file" name="file" style="display: none;">
                        <div  class="formControls col-xs-5" style="color:#666666;"><span class="c-red" >*</span>食品流通许可证</div>
                    </div>
                </div>
                <div class="row cl">
                    <label class="form-label col-xs-2"><span class="c-red">*</span>是否有烟草证 :</label>
                    <div class="formControls col-xs-5">
                        <label><input  name="hasTobacco" type="radio" value="1" onclick="$('#tobaccoLicenseDiv').show();"> 是</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        <label><input  name="hasTobacco" type="radio" value="2" onclick="$('#tobaccoLicenseDiv').hide();$('.tobaccoLicenseSave').val('');$('#tobaccoLicenseShow').children('div').remove()"> 否</label>
                    </div>
                </div>
                <div class="row cl" id="tobaccoLicenseDiv" hidden>
                    <label class="form-label col-xs-4 col-sm-2">烟草证 :</label>
                    <div class="formControls col-xs-8 col-sm-9">
                        <div id="tobaccoLicenseShow" style="float:left;display:none;">
                            <input class="tobaccoLicenseSave" type="hidden" name="tobaccoLicense">
                        </div>
                        <div onclick="uploads('tobaccoLicense','tobaccoLicenseSave','tobaccoLicenseShow',1);" style="width:88px;height: 88px;border:1px solid #ccc;line-height:24px;padding-top:20px;text-align: center;color: #ccc;cursor:pointer;float:left;">
                            <span style="font-size: 24px;">+</span><br/>添加图片
                        </div>
                        <input id="tobaccoLicense" type="file" name="file" style="display: none;">
                    </div>
                </div>
                <div class="row cl">
                    <label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>法人身份证 :</label>
                    <div class="formControls col-xs-8 col-sm-9">
                        <div id="legalPersonCardShow" style="float:left;display:none;">
                            <input class="legalPersonCardSave" type="hidden" name="legalPersonCard" value="">
                        </div>
                        <div onclick="uploads('legalPersonCard','legalPersonCardSave','legalPersonCardShow',2);" style="width:88px;height: 88px;border:1px solid #ccc;line-height:24px;padding-top:20px;text-align: center;color: #ccc;cursor:pointer;float:left;">
                            <span style="font-size: 24px;">+</span><br/>添加图片
                        </div>
                        <input id="legalPersonCard" type="file" name="file" style="display: none;">
                        <div  class="formControls col-xs-5" style="color:#666666;"><span class="c-red" >*</span>需要上传身份证的正反面</div>
                    </div>
                </div>
            </div>
            <div style="border:1px solid #ccc;padding-bottom: 20px;margin-top:20px;">
                <div class="row cl">
                    <label class="form-label col-xs-2"><i class="Hui-iconfont" style="font-size: larger;">收款账号</i></label>
                </div>
                <div class="row cl">
                    <label class="form-label col-xs-2"><span class="c-red">*</span>财务联系人 :</label>
                    <div class="formControls col-xs-5">
                        <input id="financialContact" name="financialContact" type="text" placeholder="请填写财务联系人" class="input-text size-M" />
                    </div>
                </div>
                <div class="row cl">
                    <label class="form-label col-xs-2"><span class="c-red">*</span>联系电话 :</label>
                    <div class="formControls col-xs-5">
                        <input id="contactNum" name="contactNum" type="text" placeholder="请填写联系电话" class="input-text size-M" />
                    </div>
                </div>
                <div class="row cl">
                    <label class="form-label col-xs-2"><span class="c-red">*</span>开户行 :</label>
                    <div class="formControls col-xs-5">
                        <input id="openBank" name="openBank" type="text" placeholder="请填写开户行" class="input-text size-M" />
                    </div>
                    <div style="color:#666666;" class="formControls col-xs-5"><span class="c-red">*</span>例如：招商银行 </div>
                </div>
                <div class="row cl">
                    <label class="form-label col-xs-2"><span class="c-red">*</span>开户名 :</label>
                    <div class="formControls col-xs-5">
                        <input id="accountName" name="accountName" type="text" placeholder="请填写开户名" class="input-text size-M" />
                    </div>
                    <div style="color:#666666;" class="formControls col-xs-5"><span class="c-red">*</span>例如：北京东三环支行</div>
                </div>
                <div class="row cl">
                    <label class="form-label col-xs-2"><span class="c-red">*</span>账号 :</label>
                    <div class="formControls col-xs-5">
                        <input id="accountNum" name="accountNum" type="text" placeholder="请填写账号" class="input-text size-M" />
                    </div>
                </div>
            </div>
            <div class="row cl">
                <div class="formControls col-xs-6 col-xs-offset-2">
                    <input  type="submit" id="submit" class="btn btn-secondary radius size-L" value="&nbsp;注&nbsp;&nbsp;&nbsp;&nbsp;册&nbsp;">
                </div>
            </div>
        </form>
    </div>
</div>
</body>
<script type="text/javascript" src="${ctx}/lib/register/register.js"></script>
<script type="text/javascript">
    laydate.render({
        elem: '#officeTime' //指定元素
        ,type: 'time'
        ,range: true
        ,format: 'HH:mm'
    });
</script>
</html>
