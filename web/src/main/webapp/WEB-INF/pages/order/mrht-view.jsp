<%--
  Created by IntelliJ IDEA.
  User: win 10
  Date: 2017/9/12
  Time: 17:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="../common/common.jsp"%>
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
    <title>订单详情</title>
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 订单详情 <a id="btn-refresh" onclick="refresh()" class="btn btn-success radius r size-M" style="line-height:1.6em;margin-top:3px" href="###" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="page-container">
    <div class="orderInfo">
        <div>订单信息</div>
        <div class="info">
            <div class="info-left">
                <div>订单编号：${order.orderNo}</div>
                <div></div>
                <div>订单时间：<fmt:formatDate value="${order.orderTime}" pattern="yyyy-MM-dd HH:mm:ss"/></div>
            </div>
            <div class="info-right">
                <div>订单状态：
                    <c:if test="${order.orderStatus ==1}"><span style="color: #1b6d85;">待商家接单</span></c:if>
                    <c:if test="${order.orderStatus ==2}"><span style="color: #2D93CA;">商家已接单</span></c:if>
                    <c:if test="${order.orderStatus ==3}"><span style="color: lightseagreen;">骑手赶往商家</span></c:if>
                    <c:if test="${order.orderStatus ==4}"><span style="color: #1b6d85;">商品已送出</span></c:if>
                    <c:if test="${order.orderStatus ==5}"><span style="color: green;">订单完成</span></c:if>
                    <c:if test="${order.orderStatus ==6}"><span style="color: #2D93CA;">退款审核中</span></c:if>
                    <c:if test="${order.orderStatus ==7}"><span style="color: red;">拒绝退款</span></c:if>
                    <c:if test="${order.orderStatus ==8}"><span style="color: limegreen;">退款完成</span></c:if>
                </div>
                <div>订单金额：<span style="color: red;">¥ ${order.totalPrice}</span></div>
            </div>
        </div>
    </div>
    <div class="track">
        <div>跟踪信息</div>
        <div class="track-info">
            <table class="table M-20">
                <tr>
                    <th>跟踪状态</th>
                    <th>跟踪时间</th>
                </tr>
                <c:forEach items="${track}" var="t">
                    <tr>
                        <td>
                            <c:if test="${t.trackStatus == 1}"><span style="color:#3bb4f2;">订单已提交</span></c:if>
                            <c:if test="${t.trackStatus == 2}"><span style="color:limegreen;">支付成功</span></c:if>
                            <c:if test="${t.trackStatus == 3}"><span style="color:#00b7ee;">商家已接单</span></c:if>
                            <c:if test="${t.trackStatus == 4}"><span style="color:lightseagreen;">骑手赶往商家</span></c:if>
                            <c:if test="${t.trackStatus == 5}"><span style="color:#1b6d85;">商品已送出</span></c:if>
                            <c:if test="${t.trackStatus == 6}"><span style="color:green;">商品送达</span></c:if>
                        </td>
                        <td><fmt:formatDate value="${t.trackTime}" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate></td>
                    </tr>
                </c:forEach>
            </table>
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
    <div style="text-align: right;padding-right:40px;">
        总金额：<span style="color: red;">¥ ${order.totalPrice}</span><br>
        <a href="/order/mrhtOrderList" class="btn btn-primary radius size-M" style="background: #fff;color: red;border-color: #ccc;margin-top:10px;">关闭</a>
    </div>

</div>
</body>
<script type="text/javascript">
    $('.example').zoomify();
</script>
</html>
