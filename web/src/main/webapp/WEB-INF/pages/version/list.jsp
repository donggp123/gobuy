<%--
  Created by IntelliJ IDEA.
  User: dgb
  Date: 2017/8/21
  Time: 13:38
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
    <title>用户管理</title>
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 版本控制 <a id="btn-refresh" onclick="refresh()" class="btn btn-success radius r size-M" style="line-height:1.6em;margin-top:3px" href="###" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="page-container">
    <div class="text-c">
        <form action="/version/list" method="post">
            <input type="hidden" id="pageSize" name="pageSize" value="${page.pageSize}">
            <input type="hidden" id="pn" name="pn" value="${page.currentPage}">
            <input type="text" class="input-text" style="width:250px" placeholder="请输入版本号" id="version" name="version" value="${params.version}">
            <button type="button" onclick="query()" class="btn btn-success radius size-M"><i class="Hui-iconfont">&#xe665;</i> 搜索</button>
        </form>
    </div>
    <div class="cl pd-5 bg-1 bk-gray mt-20">
        <span class="l">
            <shiro:hasPermission name="user-del"><a href="javascript:;" onclick="datadel('/version/del')" class="btn btn-danger radius size-M"><i class="Hui-iconfont">&#xe6e2;</i> 批量删除</a></shiro:hasPermission>
            <shiro:hasPermission name="user-add"><a href="javascript:;" onclick="add('添加版本','/version/edit','','510')" class="btn btn-primary radius size-M"><i class="Hui-iconfont">&#xe600;</i> 添加</a></shiro:hasPermission>
            <shiro:hasPermission name="user-edit"><a href="javascript:;" onclick="isEditable('修改版本','/version/edit','','510')" class="btn btn-primary radius size-M"><i class="Hui-iconfont">&#xe6df;</i> 编辑</a></shiro:hasPermission>
             <a style="text-decoration:none" onClick="enable(1,'确定要禁用吗？','/version/enable')"  class="btn btn-primary radius" href="javascript:;" title="禁用"><i class="Hui-iconfont">&#xe6de;</i>禁用</a>
            <a style="text-decoration:none" onClick="enable(0,'确定要启用吗？','/version/enable')"  class="btn btn-primary radius" href="javascript:;" title="启用"><i class="Hui-iconfont">&#xe603;</i>启用</a>
        </span>
    </div>
    <div class="mt-20">
        <table class="table table-border table-bordered table-hover table-bg table-sort">
            <thead>
            <tr class="text-c">
                <th width="25"><input type="checkbox" name="ids" value=""></th>
                <th width="80">ID</th>
                <th width="100">版本号</th>
                <th width="60">下载地址</th>
                <th width="90">设备类型(android/IOS)</th>
                <th width="130">发布时间</th>
                <th width="130">启用/禁用</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${page.results}" var="v">
                <tr class="text-c" onclick="checked(this)">
                    <td><input type="checkbox" value="${v.id}" name="ids"></td>
                    <td>${v.id}</td>
                    <td>${v.version}</td>
                    <td>${v.url}</td>
                    <td>
                        <c:if test="${v.device eq 1 }">
                            android
                        </c:if>
                        <c:if test="${v.device eq 2 }">
                            IOS
                        </c:if>
                    </td>
                    <td><fmt:formatDate value="${v.createTime}" type="date"/></td>
                    <td>
                        <c:if test="${v.status eq 0}">
                            <span style="color: green;">启用</span>
                        </c:if>
                        <c:if test="${v.status eq 1}">
                            <span style="color: red;">禁用</span>
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
<script>
    function isEditable(title,url,a,b) {
        var id = '';
        var count = 0;
        $.each($('input:checkbox:checked'), function () {
            if ($(this).val() != '') {
                id += $(this).val() + ',';
                count++;
            }
        });
        id = id.substring(0, id.length - 1);
        if (id == '' || id == null || count > 1) {
            errMsg("请选择一条数据编辑！");
            return;
        }
        $.ajax({
            type: 'post',
            url: '/version/isShelves',
            data: {id: id},
            success: function (data) {
                if (data.code == 2) {
                    errMsg(data.msg);
                    return;
                }
                layer_show(title, url + '?id=' + id, a, b);
            }

        })
    }
    function enable(num,msg,url){
        var id = '';
        var count = 0;
        $.each($('input:checkbox:checked'), function () {
            if ($(this).val() != '') {
                id += $(this).val() + ',';
                count++;
            }
        });
        id = id.substring(0, id.length - 1);
        if (id == '' || id == null || count > 1) {
            errMsg("请选择一条数据编辑！");
            return;
        }
        layer.confirm(msg, function () {
            $.ajax({
                type: 'post',
                url: url,
                data: {id: id, num: num},
                success: function (data) {
                    if (data.code == 2) {
                        errMsg(data.msg);
                        return;
                    }
                    succMsg(data.msg);
                    setTimeout(function () {
                        refresh();
                    },1000)
                }

            })
        })
    }
</script>
</html>
