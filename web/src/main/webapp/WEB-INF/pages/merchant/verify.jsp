<%--
  Created by IntelliJ IDEA.
  User: sunwei
  Date: 2017/8/29
  Time: 14:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../common/common.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
    <title>审核信息</title>
    <meta charset="utf-8">
    <meta name="renderer" content="webkit|ie-comp|ie-stand">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
    <meta http-equiv="Cache-Control" content="no-siteapp" />
</head>

<body>
<div class="page-container">
    <table class="table table-border table-bordered table-bg mt-20">
        <thead>
        <tr>
            <th colspan="2" scope="col" style="font-size: larger;text-align: center;">合同信息</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td width="30%">商户类型</td>
            <td>
                <span id="lbServerName">
                    <c:if test="${merchant.mrhtType eq 1}">供应商</c:if>
                    <c:if test="${merchant.mrhtType eq 2}">商户</c:if>
                </span>
            </td>
        </tr>
        <tr>
            <td>合同编号</td>
            <td>${mrhtInfo.compactNo}</td>
        </tr>
        <tr>
            <td>合同名称</td>
            <td>${mrhtInfo.compactName}</td>
        </tr>
        <tr>
            <td>合同有效期 </td>
            <td>${mrhtInfo.compactExpiryDate}</td>
        </tr>
        <tr>
            <td>电子合同 </td>
            <td>
                <div class="portfoliobox">

                    <div id="compactImageShow" class="picbox">
                        <script>
                            loadImg('${mrhtInfo.compactImage}','${imgServer}','compactImageShow',1);
                        </script>
                    </div>
                </div>
            </td>
        </tr>
        <thead>
        <tr>
            <th colspan="2" scope="col" style="font-size: larger;text-align: center;">商户信息</th>
        </tr>
        </thead>
        <tr>
            <td>商户名称 </td>
            <td>${merchant.mrhtName}</td>
        </tr>
        <tr>
            <td>登录名 </td>
            <td>${merchant.loginName}</td>
        </tr>
        <tr>
            <td>商户电话 </td>
            <td>${mrhtInfo.mobile}</td>
        </tr>
        <tr>
            <td>门店图片 </td>
            <td>
                <div id="storeImageShow">
                    <script>
                        loadImg('${mrhtInfo.storeImage}','${imgServer}','storeImageShow',1);
                    </script>
                </div>
            </td>
        </tr>
        <tr>
            <td>商户地址 </td>
            <td>${mrhtInfo.address}</td>
        </tr>
        <tr>
            <td>地址坐标 </td>
            <td>
                经度:${mrhtInfo.lng}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                纬度:${mrhtInfo.lat}
            </td>
        </tr>
        <thead>
        <tr>
            <th colspan="2" scope="col" style="font-size: larger;text-align: center;">资质信息</th>
        </tr>
        </thead>
        <tr>
            <td>营业执照 </td>
            <td>
                <div id="busLicenseShow">
                    <script>
                        loadImg('${mrhtInfo.busLicense}','${imgServer}','busLicenseShow',1);
                    </script>
                </div>
            </td>
        </tr>
        <tr>
            <td>经营许可证 </td>
            <td>
                <div id="busCertShow">
                    <script>
                        loadImg('${mrhtInfo.busCert}','${imgServer}','busCertShow',1);
                    </script>
                </div>
            </td>
        </tr>
        <tr>
            <td>是否有烟草证 </td>
            <td>
                <div id="tobaccoLicenseShow">
                    <script>
                        loadImg('${mrhtInfo.tobaccoLicense}','${imgServer}','tobaccoLicenseShow','${mrhtInfo.hasTobacco}');
                    </script>
                </div>
            </td>
        </tr>
        <tr>
            <td>法人身份证 </td>
            <td>
                <div id="legalPersonCardShow">
                    <script>
                        loadImg('${mrhtInfo.legalPersonCard}','${imgServer}','legalPersonCardShow',1);
                    </script>
                </div>
            </td>
        </tr>
        <c:if test="${merchant.status eq 1}">
            <form action="/merchant/approve" method="post" class="form form-horizontal" id="form-member-add">
                <tr>
                    <td><span style="color:red;">*</span>审核(<span style="color:red;">必选项</span>)</td>
                    <td>
                        <label><input onclick="$('#show').hide();$('#show').val('')" type="radio" name="status" value="2">审核通过&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
                        <label><input onclick="$('#show').show();" type="radio" name="status" value="3">审核拒绝</label>
                        <input class="input-text" id="show" type="text" value="" name="remark" style="display:none" placeholder="请输入拒绝原因">
                    </td>
                </tr>
                <tr>
                    <td></td>
                    <td style="">
                        <input type="hidden" value="${merchant.id}" name="id">
                        <input type="submit" value="提交" class="btn btn-secondary radius size-l">
                    </td>
                </tr>
            </form>
        </c:if>
        <c:if test="${merchant.status eq 3}">
            <tr>
                <td>拒绝理由</td>
                <td>${merchant.remark}</td>
            </tr>
        </c:if>
        </tbody>
    </table>

</div>
</body>
<script type="text/javascript">
    $(function(){
        $("#form-member-add").validate({
            rules:{
                status:{
                    required:true,
                }
            },
            onkeyup:false,
            focusCleanup:true,
            success:"valid",
            submitHandler:function(form){
                $.ajax({
                    type: 'post',
                    url: '/merchant/approve',
                    data: $(form).serializeArray(),
                    success:function (data) {
                        succMsg(data.msg);
                        setTimeout(function () {
                            var index = parent.layer.getFrameIndex(window.name);
                            parent.$('#btn-refresh').click();
                            parent.layer.close(index);
                        },1000)
                    },
                    error:function (data) {
                        errMsg("网络异常");
                    }
                });

            }
        });
    });
</script>
<script type="text/javascript">
    $('.example').zoomify();
</script>
</html>