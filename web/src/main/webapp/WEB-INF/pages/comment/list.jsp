<%--
  Created by IntelliJ IDEA.
  User: dgp
  Date: 2017/8/25
  Time: 9:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../common/common.jsp"%>
<html>
<head>
    <meta charset="utf-8">
    <meta name="renderer" content="webkit|ie-comp|ie-stand">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
    <meta http-equiv="Cache-Control" content="no-siteapp" />
    <title>评价管理</title>
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 评价管理 <a id="btn-refresh" onclick="refresh()" class="btn btn-success radius r size-M" style="line-height:1.6em;margin-top:3px" href="###" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="page-container">
    <div class="mt-20">
        <table class="table table-border table-bordered table-hover table-bg table-sort">
            <thead>
            <tr class="text-c">
                <th width="35">ID</th>
                <th width="80">订单号</th>
                <th width="100">客户名称</th>
                <th width="90">商品名称</th>
                <th width="130">评价级别</th>
                <th width="130">评价图片</th>
                <th width="130">评价内容</th>
                <th width="130">评价时间</th>
                <th width="130">操作</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${page.results}" var="mc">
                <tr class="text-c"  onclick="checked(this)">
                    <td>${mc.id}</td>
                    <td>${mc.orderNo}</td>
                    <td>${mc.nickName}</td>
                    <td>${mc.goodsName}</td>
                    <td>${mc.comLevel}</td>
                    <td>
                        <img src="${mc.comImage}" style="width: 50px;height:40px;" alt="">
                    </td>
                    <td><div title="${mc.content}" style="cursor:pointer;">${mc.content.length() > 15 ? mc.content.substring(0,15) : mc.content}</div></td>
                    <td>
                        <fmt:formatDate value="${mc.comTime}" type="date"/>
                    </td>
                    <td>
                        <a href="javascript:;" onclick="add('回复','/comment/view?id=${mc.id}&mrhtId=${user.id}','460','320');" class="btn btn-primary radius size-M">回复</a>
                        <a href="javascript:;" onclick="add('查看','/comment/read?comId=${mc.id}&mrhtId=${user.id}','','');" class="btn btn-primary radius size-M">查看</a>
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