<%--
  Created by IntelliJ IDEA.
  User: dgp
  Date: 2017/8/18
  Time: 16:21
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../common/common.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
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
            <th colspan="2" scope="col" style="text-align: center;font-size: larger;">合同信息</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td width="30%">商户类型</td>
            <td>
                <span id="lbServerName">
                    <c:if test="${mrht.type eq 1}">供应商</c:if>
                    <c:if test="${mrht.type eq 2}">商户</c:if>
                </span>
            </td>
        </tr>
        <tr>
            <td>合同编号</td>
            <td>${info.compactNo}</td>
        </tr>
        <tr>
            <td>合同名称</td>
            <td>${info.compactName}</td>
        </tr>
        <tr>
            <td>合同有效期 </td>
            <td>${info.compactExpiryDate}</td>
        </tr>
        <tr>
            <td>电子合同 </td>
            <td>
                <div id="compactImageShow">
                    <script>
                        loadImg('${info.compactImage}','${imgServer}','compactImageShow',1);
                    </script>
                </div>
            </td>
        </tr>
        <thead>
        <tr>
            <th colspan="2" scope="col" style="text-align: center;font-size: larger;">商户信息</th>
        </tr>
        </thead>
        <tr>
            <td>商户名称 </td>
            <td>${mrht.realName}</td>
        </tr>
        <tr>
            <td>登录名 </td>
            <td>${mrht.loginName}</td>
        </tr>
        <tr>
            <td>商户电话 </td>
            <td>${info.mobile}</td>
        </tr>
        <tr>
            <td>营业时间 </td>
            <td>${info.officeTime}</td>
        </tr>
        <c:if test="${mrht.type eq 1}">
            <tr>
                <td>商品分类 </td>
                <td>${info.typeName}</td>
            </tr>
        </c:if>
        <tr>
            <td>门店图片 </td>
            <td>
                <div style="width:158px;position: relative;">
                    <span>
                      <a onclick="uploads('storeImage','${info.id}')" style="text-decoration:none;position: absolute;top:0px;right:-30px;z-index: 10000;" class="ml-5"  title="修改"><i class="Hui-iconfont">&#xe6df;</i></a>
                        <input id="storeImage" type="file" name="file" style="display: none;">
                    </span>

                </div>
                <div id="storeImageShow">
                    <script>
                        loadImg('${info.storeImage}','${imgServer}','storeImageShow',1);
                    </script>
                </div>
            </td>
        </tr>
        <tr>
            <td>商户地址 </td>
            <td>${info.address}</td>
        </tr>
        <tr>
            <td>地址坐标 </td>
            <td>
                经度:${info.lng}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                纬度:${info.lat}
            </td>
        </tr>
        <thead>
        <tr>
            <th colspan="2" scope="col" style="text-align: center;font-size: larger;">资质信息</th>
        </tr>
        </thead>
        <tr>
            <td>营业执照 </td>
            <td>
                <div id="busLicenseShow">
                    <script>
                        loadImg('${info.busLicense}','${imgServer}','busLicenseShow',1);
                    </script>
                </div>
            </td>
        </tr>
        <tr>
            <td>经营许可证 </td>
            <td>
                <div id="busCertShow">
                    <script>
                        loadImg('${info.busCert}','${imgServer}','busCertShow',1);
                    </script>
                </div>
            </td>
        </tr>
        <tr>
            <td>是否有烟草证 </td>
            <td>
                <div id="tobaccoLicenseShow">
                    <script>
                        loadImg('${info.tobaccoLicense}','${imgServer}','tobaccoLicenseShow','${info.hasTobacco}');
                    </script>
                </div>
            </td>
        </tr>
        <tr>
            <td>法人身份证 </td>
            <td>
                <div id="legalPersonCardShow">
                    <script>
                        loadImg('${info.legalPersonCard}','${imgServer}','legalPersonCardShow',1);
                    </script>
                    <div style="clear: both;"></div>
                </div>
            </td>
        </tr>
        </tbody>
    </table>
</div>
</body>

<script>
    function uploads(id1,id){
        $("#"+id1).trigger("click");
        $("#"+id1).on("change",function () {
            var data = new FormData();
            data.append('uploadFile', $('#'+id1)[0].files[0]);
            data.append('id',id);
            $.ajax({
                url:'/merchant/editStoreImage',
                type:'POST',
                data:data,
                contentType: false,    //不可缺
                processData: false,    //不可缺
                success:function(data){
                    if (data.code == 1){
                        succMsg(data.msg);
                        setTimeout(function () {
                            refresh();
                        },1000)
                    }
                }
            });
        })
    }

</script>
<script type="text/javascript">
    $('.example').zoomify();
</script>
</html>