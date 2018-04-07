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
            <th colspan="2" scope="col" style="text-align: center;font-size: larger;">骑手信息</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td>审核状态</td>
            <td>
                <c:if test="${riderInfo.status == 1}"><span style="color: #2D93CA;">审核中</span></c:if>
                <c:if test="${riderInfo.status == 2}"><span style="color: red;">审核拒绝</span></c:if>
                <c:if test="${riderInfo.status == 3}"><span style="color: green;">审核通过</span></c:if>
            </td>
        </tr>
        <tr>
            <td>真实名字</td>
            <td>${riderInfo.authName}</td>
        </tr>
        <tr>
            <td>电话</td>
            <td>${riderInfo.authPhone}</td>
        </tr>
        <tr>
            <td>支付宝账号</td>
            <td>${riderInfo.alipay}</td>
        </tr>
        <tr>
            <td>省份证号码 </td>
            <td>${riderInfo.authCertNo}</td>
        </tr>
        <tr>
            <td>身份证正面照 </td>
            <td>
                <img class="example" style="width: 158px; height: 88px;" src="${riderInfo.authCertFront}" alt="">
            </td>
        </tr>
        <tr>
            <td>身份证背面照 </td>
            <td>
                <img class="example" style="width: 158px; height: 88px;" src="${riderInfo.authCertBack}" alt="">
            </td>
        </tr>
        <tr>
            <td>手持身份证正面照 </td>
            <td>
                <img class="example" style="width: 158px; height: 88px;" src="${riderInfo.authCertHand}" alt="">
            </td>
        </tr>
        </tbody>
    </table>
    <div style="margin-top:25px;">
        <c:if test="${riderInfo.status == 1}">
            <a href="javascript:;" onclick="adopt('${riderInfo.mberId}','${riderInfo.id}')" class="btn btn-primary radius size-M">审核通过</a>
            <a href="javascript:;" onclick="view('拒绝理由','/rider/view?id=${riderInfo.id}&mberId=${riderInfo.mberId}','460','320');" class="btn btn-danger radius size-M">审核拒绝</a>
        </c:if>
    </div>
</div>
</body>
<script type="text/javascript">
    $('.example').zoomify();
    function adopt(mberId,id){
        layer.confirm("确定要通过吗？",function () {
            $.ajax({
                url:"/rider/adopt",
                type:"post",
                data:{mberId:mberId,id:id},
                success:function (data) {
                    succMsg(data.msg);
                    setTimeout(function () {
                        var index = parent.layer.getFrameIndex(window.name);
                        parent.$('#btn-refresh').click();
                        parent.layer.close(index);
                    },1000)
                },
                error:function () {
                    errMsg("网络异常");
                }
            })
        })
    }
    function view(title,url,w,h){
        add(title, url, w, h);
    }
</script>
</html>