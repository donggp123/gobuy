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
    <title>骑手认证</title>
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 骑手管理 <a id="btn-refresh" onclick="refresh()" class="btn btn-success radius r size-M" style="line-height:1.6em;margin-top:3px" href="###" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="page-container">
    <div class="text-c">
        <form action="/rider/list" method="post">
            <input type="hidden" id="pageSize" name="pageSize" value="${page.pageSize}">
            <input type="hidden" id="pn" name="pn" value="${page.currentPage}">
            <input type="text" class="input-text" style="width:250px" placeholder="输入昵称搜索" id="nickName" name="nickName" value="${params.nickName}">
            <button type="button" onclick="query()" class="btn btn-success radius size-M"><i class="Hui-iconfont">&#xe665;</i> 搜索</button>
        </form>
    </div>
    <div class="mt-20">
        <table class="table table-border table-bordered table-hover table-bg table-sort">
            <thead>
            <tr class="text-c">
                <th width="35">ID</th>
                <th width="80">昵称</th>
                <th width="100">头像</th>
                <th width="100">性别</th>
                <th width="100">生日</th>
                <th width="100">注册时间</th>
                <th width="100">最后一次登录时间</th>
                <th width="100">审核状态</th>
                <th width="100">操作</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${page.results}" var="mc">
                <tr class="text-c"  onclick="checked(this)">
                    <td>${mc.mberId}</td>
                    <td>${mc.nickName}</td>
                    <td>
                        <img style="width: 50px;height:40px;" src="${mc.headIcon}" alt="">
                    </td>
                    <td>
                        <c:if test="${mc.gender eq 1}">男</c:if>
                        <c:if test="${mc.gender eq 2}">女</c:if>
                    </td>
                    <td><fmt:formatDate value="${mc.birthday}" type="date"></fmt:formatDate></td>
                    <td><fmt:formatDate value="${mc.registerTime}" type="date"></fmt:formatDate></td>
                    <td><fmt:formatDate value="${mc.lastLogTime}" type="date"></fmt:formatDate></td>
                    <td>
                        <c:if test="${mc.status == 1}"><span style="color: #3bb4f2;">审核中</span></c:if>
                        <c:if test="${mc.status == 2}"><span style="color: red;">审核拒绝</span></c:if>
                        <c:if test="${mc.status == 3}"><span style="color: green;">审核通过</span></c:if>
                    </td>
                    <td>
                        <c:if test="${mc.status == 1}">
                            <a href="javascript:;" onclick="add('审核','/rider/info?mberId=${mc.mberId}','','510');" class="btn btn-primary radius size-M">审核</a>
                        </c:if>
                        <c:if test="${mc.status == 2 or mc.status == 3}">
                            <a href="javascript:;" onclick="add('查看','/rider/info?mberId=${mc.mberId}','','510');" class="btn btn-primary radius size-M">查看</a>
                        </c:if>
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