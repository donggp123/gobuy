<%--
  Created by IntelliJ IDEA.
  User: sunwei
  Date: 2017/8/29
  Time: 10:20
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
    <title>商户列表</title>
</head>
<body>
    <nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 商家管理 <span class="c-gray en">&gt;</span> 商户信息 <a id="btn-refresh" onclick="refresh()" class="btn btn-success radius r size-M" style="line-height:1.6em;margin-top:3px" href="###" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
    <div class="page-container">
        <div class="text-c">
            <form action="/merchant/list" method="post">
                <input type="hidden" id="pageSize" name="pageSize" value="${page.pageSize}">
                <input type="hidden" id="pn" name="pn" value="${page.currentPage}">
                <input type="text" class="input-text" style="width:250px" placeholder="输入商户名" id="mrhtName" name="mrhtName" value="${params.mrhtName}">
                <button type="button" onclick="query()" class="btn btn-success radius size-M"><i class="Hui-iconfont">&#xe665;</i> 搜索</button>
            </form>
        </div>
        <div class="cl pd-5 bg-1 bk-gray mt-20">
            <span class="l">
                <c:if test="${params.status == 1}">
                <shiro:hasPermission name="mrht-verify"><a href="javascript:;" onclick="verifys('审核','/merchant/verify');" class="btn btn-primary radius size-M">审核</a></shiro:hasPermission>
                </c:if>
                <c:if test="${params.status == 2}">
                <shiro:hasPermission name="mrht-view"><a href="javascript:;" onclick="verifys('查看','/merchant/verify');" class="btn btn-primary radius size-M">查看</a></shiro:hasPermission>
                </c:if>
            </span>
        </div>
        <div class="mt-20">
            <table class="table table-border table-bordered table-hover table-bg table-sort">
                <thead>
                <tr class="text-c">
                    <th width="25"><input type="checkbox" name="ids" value=""></th>
                    <th width="80">ID</th>
                    <th width="100">商户名称</th>
                    <th width="100">商户类型</th>
                    <th width="100">审核状态</th>
                    <th width="100">营业时间</th>
                    <th width="100">注册时间</th>
                    <th width="100">审核时间</th>
                    <th width="100">备注</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${page.results}" var="m">
                    <tr class="text-c" onclick="checked(this)">
                        <td><input type="checkbox" value="${m.id}" name="ids"></td>
                        <td>${m.id}</td>
                        <td>${m.mrhtName}</td>
                        <td>${m.mrhtTypeName}</td>
                        <td>
                            <c:if test="${m.status == 1}">
                                <span style="color:red;">${m.statusName}</span>
                            </c:if>
                            <c:if test="${m.status == 2}">
                                <span style="color:green;">${m.statusName}</span>
                            </c:if>
                        </td>
                        <td>${m.officeTime}</td>
                        <td><fmt:formatDate value="${m.createTime}" type="date"/></td>
                        <td><fmt:formatDate value="${m.updateTime}" type="date"/></td>
                        <td>${m.remark}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <div><%@include file="../common/page.jsp"%></div>
        </div>
    </div>
</body>
<script>
    /**
     * 审核
     */
    function verifys(title,url){
        var id = '';
        var count = 0;
        $.each($('input:checkbox:checked'),function(){
            if($(this).val() != '') {
                id += $(this).val() + ',';
                count ++;
            }
        });
        id = id.substring(0,id.length-1);
        if(id == '' || id == null || count > 1) {
            errMsg("请选择一条数据审核！");
            return;
        }
        layer_show(title,url+"?id="+id);
    }
</script>
</html>
