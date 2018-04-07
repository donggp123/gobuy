<%--
  Created by IntelliJ IDEA.
  User: dgp
  Date: 2017/9/25
  Time: 17:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../common/common.jsp"%>
<html>
<head>
    <title>Title</title>
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

<div class="page-container">
    <div class="track">
        <div>账户余额</div>
        <div class="track-info">
            账户余额 : <span style="color: red;">¥${account.amount == null ? 0 : account.amount}</span>
        </div>
    </div>
    <div class="goods">
        <div>账户流水明细</div>
        <div class="goods-info">
            <table class="table mt-20">
                <tr>
                    <th>ID</th>
                    <th>商户名称</th>
                    <th>明细类型</th>
                    <th>金额</th>
                    <th>时间</th>
                </tr>
                <c:forEach items="${page.results}" var="b">
                    <tr style="border-bottom: 1px solid #ccc;">
                        <td>${b.id}</td>
                        <td>${mrhtName}</td>
                        <td>
                            <c:if test="${b.type == 1}">收入</c:if>
                            <c:if test="${b.type == 2}">提现</c:if>
                        </td>
                        <td>${b.amount == null ? 0 : b.amount}</td>
                        <td><fmt:formatDate value="${b.createTime}" type="date"/></td>
                    </tr>
                </c:forEach>
            </table>
            <div><%@include file="../common/page.jsp"%></div>
        </div>
    </div>
</div>
</body>
</html>
