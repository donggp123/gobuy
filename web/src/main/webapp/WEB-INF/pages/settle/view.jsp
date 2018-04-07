<%--
  Created by IntelliJ IDEA.
  User: dgb
  Date: 2017/9/25
  Time: 10:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../common/common.jsp"%>
<html>
<head>
    <style type="text/css">
        .order{ width:100%;height:auto;border:1px solid #ccc;margin: 20px auto;padding:12px;}
        .order-title{color:inherit;font-size: larger;}
        .order-list{width:97%;height:auto;margin-left:2%;}
        table tr th{text-align: center;}
        .tr td{text-align: center;}
        .order-js{text-align: right;padding-right:15px;}
        .order-settl{width:97%;height:auto;margin :10px auto;}
        .order-settl-left{width:50%;height:140px;float: left;}
        .order-settl-right{width:50%;height:140px;float: left;position: relative;}
        .order-right{text-align: right;width:100%;height:auto;position: absolute; bottom: 0px;}
    </style>
</head>
<body>
<div class="page-container">
    <div style="width:100%;color:red;">注：1、平台所得佣金=(销售总金额-退款总金额)x结算比例</div>
    <div style="width:100%;color:red;">   2、结算金额=(销售总金额-退款总金额)-平台所得佣金+商家所得配送金额</div>
    <div class="order">
        <div class="order-title">结算信息</div>
        <div class="order-settl">
            <div class="order-settl-left">
                <div>结算单号：${settle.settleNo}</div>
                <div>商家名称：${settle.mrhtName}</div>
                <div>账　　期：<fmt:formatDate value="${settle.settlePeriod}" type="date"/></div>
                <div>销售金额：${settle.salesAmount == null ? 0 : settle.salesAmount}</div>
                <div>退款金额：${settle.retAmount == null ? 0 : settle.retAmount}</div>
                <div>配送费运：${settle.deliveryFee == null ? 0 : settle.deliveryFee}</div>
                <div>平台佣金：${settle.empAmount == null ? 0 : settle.empAmount}</div>
                <div>结算金额：${settle.rate}%</div>
            </div>
            <div class="order-settl-right">
                <div class="order-right">
                    <div>
                        结算状态：
                        <c:if test="${settle.settleStatus == 1}"><span style="color: #00b7ee;">待结算</span></c:if>
                        <c:if test="${settle.settleStatus == 2}"><span style="color: #770088;">结算中</span></c:if>
                        <c:if test="${settle.settleStatus == 3}"><span style="color: green;">结算完成</span></c:if>
                    </div>
                    <div>结算金额：<span style="color:red;">¥${settle.settleAmount == null ? 0 : settle.settleAmount}</span></div>
                </div>
            </div>
            <div style="clear: both;"></div>
        </div>
    </div>
    <div class="order">
        <div class="order-title">订单信息</div>
        <div class="order-list">
            <table class="table mt-20">
                <tr>
                    <th>订单号</th>
                    <th>商家</th>
                    <th>配送方式</th>
                    <th>配送费</th>
                    <th>销售金额</th>
                    <th>总金额</th>
                    <th>支付方式</th>
                    <th>订单状态</th>
                    <th>结算状态</th>
                    <th>订单时间</th>
                </tr>
                <c:forEach items="${orders}" var="o">
                    <c:set var="allSalePrice" value="${allSalePrice+o.salesPrice}"></c:set>
                    <tr class="tr">
                        <td>${o.orderNo}</td>
                        <td>${o.mrhtName}</td>
                        <td>
                            <c:if test="${o.deliveryType == 1}">商家自送</c:if>
                            <c:if test="${o.deliveryType == 2}">骑手配送</c:if>
                        </td>
                        <td>${o.deliveryFee}</td>
                        <td>${o.salesPrice}</td>
                        <td>${o.totalPrice}</td>
                        <td>
                            <c:if test="${o.payType == 1}">微信</c:if>
                            <c:if test="${o.payType == 2}">支付宝</c:if>
                        </td>
                        <td><c:if test="${o.orderStatus == 5}"><span style="color: green;">订单完成</span></c:if></td>
                        <td>
                            <c:if test="${o.settleStatus == 1}"><span style="color: #00b7ee;">待结算</span></c:if>
                            <c:if test="${o.settleStatus == 2}"><span style="color: #770088;">结算中</span></c:if>
                            <c:if test="${o.settleStatus == 3}"><span style="color: green;">结算完成</span></c:if>
                        </td>
                        <td><fmt:formatDate value="${o.payTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                    </tr>
                </c:forEach>
            </table>
        </div>
        <div class="order-js">销售金额总计：<span style="color: green;">¥${allSalePrice}</span></div>
    </div>
    <div class="order">
        <div class="order-title">退款信息</div>
        <div class="order-list">
            <table class="table mt-20">
                <tr>
                    <th>退单号</th>
                    <th>订单号</th>
                    <th>申请退款金额</th>
                    <th>实际退款金额</th>
                    <th>退款数量</th>
                    <th>退单状态</th>
                    <th>结算状态</th>
                    <th>退款原因</th>
                    <th>退款备注</th>
                </tr>
                <c:forEach items="${returns}" var="r">
                    <c:set var="allActualAmount" value="${allActualAmount+r.actualAmount}"></c:set>
                    <tr class="tr">
                        <td>${r.retNo}</td>
                        <td>${r.orderNo}</td>
                        <td>${r.retPrice}</td>
                        <td>${r.actualAmount}</td>
                        <td>${r.retNum}</td>
                        <td>
                            <c:if test="${r.retStatus == 8}"><span style="color: green;">退款完成</span></c:if>
                        </td>
                        <td>
                            <c:if test="${r.settleStatus == 1}"><span style="color: #00b7ee;">待结算</span></c:if>
                            <c:if test="${r.settleStatus == 2}"><span style="color: #770088;">结算中</span></c:if>
                            <c:if test="${r.settleStatus == 3}"><span style="color: green;">结算完成</span></c:if>
                        </td>
                        <td><span style="cursor: pointer;" title="${r.retReason}">${r.retReason.length() > 20 ? r.retReason.substring(0,15).concat("...") : r.retReason}</span></td>
                        <td>${r.remark}</td>
                    </tr>
                </c:forEach>
            </table>
        </div>
        <div class="order-js">退款金额总计：<span style="color: green;">¥${allActualAmount}</span></div>
    </div>
    <div class="order">
        <div class="order-title">配送信息</div>
        <div class="order-list">
            <table class="table mt-20">
                <tr>
                    <th>订单号</th>
                    <th>配送费</th>
                    <th>配送时间</th>
                    <th>完成时间</th>
                </tr>
                <c:forEach items="${deliveries}" var="d">
                    <c:set var="allDeliveryFee" value="${allDeliveryFee+d.deliveryFee}"></c:set>
                    <tr class="tr">
                        <td>${d.orderNo}</td>
                        <td>${d.deliveryFee}</td>
                        <td><fmt:formatDate value="${d.deliveryTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                        <td><fmt:formatDate value="${d.serviceTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                    </tr>
                </c:forEach>
            </table>
        </div>
        <div class="order-js">配送金额总计：<span style="color: green;">${allDeliveryFee == null ? 0 : allDeliveryFee}</span></div>
    </div>
</div>
</body>
</html>
