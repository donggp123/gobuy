<%--
  Created by IntelliJ IDEA.
  User: dgb
  Date: 2017/8/22
  Time: 15:44
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
    <title>系统设置列表</title>
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 系统设置 <a id="btn-refresh" onclick="refresh()" class="btn btn-success radius r size-M" style="line-height:1.6em;margin-top:3px" href="###" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="page-container">
    <div class="text-c">
        <form action="/setting/list" method="post">
            <input type="hidden" id="pageSize" name="pageSize" value="${page.pageSize}">
            <input type="hidden" id="pn" name="pn" value="${page.currentPage}">
            <input type="text" class="input-text" style="width:250px" placeholder="输入key" id="key" name="key" value="${params.key}">
            <button type="button" onclick="query()" class="btn btn-success radius size-M"><i class="Hui-iconfont">&#xe665;</i> 搜索</button>
        </form>
    </div>
    <div class="cl pd-5 bg-1 bk-gray mt-20">
        <span class="l">
            <shiro:hasPermission name="setting-del"><a href="javascript:;" onclick="datadel('/setting/del')" class="btn btn-danger radius size-M"><i class="Hui-iconfont">&#xe6e2;</i> 批量删除</a></shiro:hasPermission>
            <shiro:hasPermission name="setting-add"><a href="javascript:;" onclick="add('添加设置','/setting/edit','','510')" class="btn btn-primary radius size-M"><i class="Hui-iconfont">&#xe600;</i> 添加</a></shiro:hasPermission>
            <shiro:hasPermission name="setting-edit"><a href="javascript:;" onclick="edit('编辑设置','/setting/edit','','510')" class="btn btn-primary radius size-M"><i class="Hui-iconfont">&#xe6df;</i> 编辑</a></shiro:hasPermission>
        </span>
    </div>
    <div class="mt-20">
        <table class="table table-border table-bordered table-hover table-bg table-sort">
            <thead>
            <tr class="text-c">
                <th width="25"><input type="checkbox" name="ids" value=""></th>
                <th width="80">ID</th>
                <th width="100">key</th>
                <th width="100">name</th>
                <th width="100">value</th>
                <th width="130">说明</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${page.results}" var="m">
                <tr class="text-c" onclick="checked(this)">
                    <td><input type="checkbox" value="${m.id}" name="ids"></td>
                    <td>${m.id}</td>
                    <td>${m.key}</td>
                    <td>${m.name}</td>
                    <td>${m.value}</td>
                    <td>${m.text}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <div><%@include file="../common/page.jsp"%></div>
    </div>
</div>
</body>
</html>
