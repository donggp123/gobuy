<%--
  Created by IntelliJ IDEA.
  User: win 10
  Date: 2017/9/8
  Time: 19:24
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
    <title>意见反馈</title>
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 评论管理 <span class="c-gray en">&gt;</span> 意见反馈 <a id="btn-refresh" onclick="refresh()" class="btn btn-success radius r size-M" style="line-height:1.6em;margin-top:3px" href="###" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="page-container">
    <div class="mt-20">
        <table class="table table-border table-bordered table-hover table-bg table-sort">
            <thead>
            <tr class="text-c">
                <th width="25"><input type="checkbox" name="ids" value=""></th>
                <th width="80">ID</th>
                <th width="100">昵称</th>
                <th width="100">头像</th>
                <th width="100">手机号</th>
                <th width="100">意见内容</th>
                <th width="130">意见时间</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${page.results}" var="p">
                <tr class="text-c" onclick="checked(this)">
                    <td><input type="checkbox" value="${p.id}" name="ids"></td>
                    <td>${p.id}</td>
                    <td>${p.nickName}</td>
                    <td>
                        <img class="example" style="max-width:158px; height:auto ;" src="${p.headIcon}">
                    </td>
                    <td>${p.mobile}</td>
                    <td>${p.content}</td>
                    <td><fmt:formatDate value="${p.opTime}" type="date"/></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <div><%@include file="../common/page.jsp"%></div>
    </div>
</div>
</body>
</html>
