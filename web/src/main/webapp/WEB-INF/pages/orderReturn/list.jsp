<%--
  Created by IntelliJ IDEA.
  User: dgb
  Date: 2017/9/4
  Time: 11:53
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
    <title>退货列表</title>
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 退货管理 <a id="btn-refresh" onclick="refresh()" class="btn btn-success radius r size-M" style="line-height:1.6em;margin-top:3px" href="###" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="page-container">
    <div class="mt-20">
        <table class="table table-border table-bordered table-hover table-bg table-sort">
            <thead>
            <tr class="text-c">
                <th width="50">ID</th>
                <th width="100">退款单号</th>
                <th width="100">订单号</th>
                <th width="80">退款人名称</th>
                <th width="70">商户名称</th>
                <th width="70">退货金额</th>
                <th width="100">实际退款金额</th>
                <th width="70">退单状态</th>
                <th width="70">退货数量</th>
                <th width="100">退款原因</th>
                <th width="100">申请退款时间</th>
                <th width="70">退还方式</th>
                <th width="130">审核备注</th>
                <th width="50">操作</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${page.results}" var="r">
                <tr class="text-c"  onclick="checked(this)">
                    <td>${r.id}</td>
                    <td>${r.retNo}</td>
                    <td>${r.orderNo}</td>
                    <td>${r.nickName}</td>
                    <td>${r.mrhtName}</td>
                    <td>${r.retPrice}</td>
                    <td>${r.actualAmount}</td>
                    <td>
                        <c:if test="${r.retStatus ==6}"><span style="color: #00b7ee;">退款审核中</span></c:if>
                        <c:if test="${r.retStatus ==7}"><span style="color: red;">拒绝退款</span></c:if>
                        <c:if test="${r.retStatus ==8}"><span style="color: green;">退款完成</span></c:if>
                    </td>
                    <td>${r.retNum}</td>
                    <td><span style="cursor: pointer;" title="${r.retReason}">${r.retReason.length() > 20 ? r.retReason.substring(0,15).concat("...") : r.retReason}</span></td>
                    <td><fmt:formatDate value="${r.retTime}" type="date" /></td>
                    <td>
                        <c:if test="${r.retType == 1}">个人退还</c:if>
                        <c:if test="${r.retType == 2}">商家来取</c:if>
                    </td>
                    <td>${r.remark}</td>
                    <td width="50">
                        <a href="/orderReturn/details?orderNo=${r.orderNo}&retNo=${r.retNo}" class="btn btn-primary radius size-M">处理</a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <div><%@include file="../common/page.jsp"%></div>
    </div>
</div>
</body>
</html>
