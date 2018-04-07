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
    <style type="text/css">
        .orderInfo{width:100%; height:160px; padding:10px;border-bottom: 1px solid #ccc;}
        .info{width:98%; margin:0 auto;height:120px;padding:15px;}
        .info-left{width:50%;height:90px;float: left;line-height: 30px;}
        .info-right{width:50%;height:90px; float: left; padding-top:40px;text-align: right;line-height: 30px;}
        .track{width:100%;height:auto;border-top: 1px solid #ccc;border-bottom: 1px solid #ccc;margin-top:15px;padding:10px;}
        .track-info{width:97%;height:auto;margin:0 auto;padding:12px;}
        .goods{width:100%;height:auto;border-top: 1px solid #ccc;margin-top:15px;padding:10px;}
        .goods-info{width:97%;height:auto;margin:0 auto;padding:12px;}
    </style>
</head>

<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 订单详情 <a id="btn-refresh" onclick="refresh()" class="btn btn-success radius r size-M" style="line-height:1.6em;margin-top:3px" href="###" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="page-container">
    <div class="orderInfo">
        <div>订单信息</div>
        <div class="info">
            <div class="info-left">
                <div>订单编号：${order.orderNo}</div>
                <div>退款单号：${retNo}</div>
                <div>订单时间：<fmt:formatDate value="${order.orderTime}" type="date"/></div>
            </div>
            <div class="info-right">
                <div>退单状态：
                    <c:if test="${orderReturn.retStatus ==6}"><span style="color: #2D93CA;">退款审核中</span></c:if>
                    <c:if test="${orderReturn.retStatus ==7}"><span style="color: red;">拒绝退款</span></c:if>
                    <c:if test="${orderReturn.retStatus ==8}"><span style="color: limegreen;">退款完成</span></c:if>
                </div>
                <div>订单金额：<span style="color: red;">${order.totalPrice}</span> ¥</div>
            </div>
        </div>
    </div>
    <div class="track">
        <div>联系人信息</div>
        <div class="track-info">
            姓名：${order.receiptName} &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            电话：${order.receiptMobile}  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            地址：${order.receiptAddr}
        </div>
    </div>
    <div class="goods">
        <div>商品信息</div>
        <div class="goods-info">
            <table class="table mt-20">
                <tr>
                    <th>商品名称</th>
                    <th>条形码</th>
                    <th>价格</th>
                    <th>数量</th>
                    <th>支付金额</th>
                </tr>
                <c:forEach items="${orderItem}" var="oi">
                    <tr style="border-bottom: 1px solid #ccc;">
                        <td width="30%">
                            <img src="${oi.goodsImage}" style="width: 158px;height:88px;float: left;" alt="">
                            <div style="float: left;margin-left: 15px;">${oi.goodsName}</div>
                        </td>
                        <td>${oi.barCode}</td>
                        <td>${oi.unitPrice}</td>
                        <td>${oi.num}</td>
                        <td>${oi.payPrice}</td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </div>
    <div style="margin-bottom: 20px;height:80px;">
            <div style="width:50%;float: left;padding-left:40px;height: 50px;">
                 <c:if test="${orderReturn.retStatus == 6}">
                    <a href="javascript:;" onclick="accept('确认同意退款吗？','/orderReturn/save?orderNo=${order.orderNo}&retNo=${retNo}');" class="btn btn-primary radius size-M">同意退款</a>
                    <a href="javascript:;" onclick="refuse('确认拒绝退款吗？','/orderReturn/remark?orderNo=${order.orderNo}&status=7&retNo=${retNo}','450','400');" class="btn btn-danger radius size-M">拒绝退款</a>
                </c:if>
            </div>
        <div style="width:50%;float: left;text-align: right;padding-right: 40px;">
            总金额：<span style="color: red;">${order.totalPrice}</span> ¥<br>
            <a href="/orderReturn/list" class="btn btn-primary radius size-M" style="background: #fff;color: red;border-color: #ccc;margin-top:10px;">关闭</a>
        </div>
    </div>

</div>
</body>
<script type="text/javascript">
    function accept(title,url,status){

        layer.confirm(title,function () {
            $.ajax({
                url:url,
                type:"post",
                success:function (data) {
                    succMsg(data.msg);
                    setTimeout(function () {
                        refresh();
                    },1000)
                },
                error:function () {
                    errMsg("系统异常");
                }
            })
        })

    }
    function refuse(title, url, w, h){
        layer.confirm(title,function () {
            layer_show("拒绝理由", url, w, h);
        })

    }
</script>
</html>