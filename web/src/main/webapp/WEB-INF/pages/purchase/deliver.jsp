<%--
  Created by IntelliJ IDEA.
  User: sunwei
  Date: 2017/9/4
  Time: 21:41
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
    <title>出货管理</title>
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 商店采购单 <span class="c-gray en">&gt;</span> 出货管理 <a id="btn-refresh" onclick="refresh()" class="btn btn-success radius r size-M" style="line-height:1.6em;margin-top:3px" href="###" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="page-container">
    <div class="text-c">
        <form action="/supplier/deliver" method="post">
            <input type="hidden" id="pageSize" name="pageSize" value="${page.pageSize}">
            <input type="hidden" id="pn" name="pn" value="${page.currentPage}">
        </form>
    </div>
    <div class="mt-20">
        <table class="table table-border table-bordered table-hover table-bg table-sort">
            <thead>
            <tr class="text-c">
                <th width="80">ID</th>
                <th width="130">商品条形码</th>
                <th width="130">商品名称</th>
                <th width="130">商户名称</th>
                <th width="130">商品规格</th>
                <th width="130">商品分类</th>
                <th width="100">出货数量</th>
                <th width="100">出货价格</th>
                <th width="100">出货总价</th>
                <th width="100">出货状态</th>
                <th width="80">操作</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${page.results}" var="m">
                <tr class="text-c">
                    <td>${m.id}</td>
                    <td>${m.barCode}</td>
                    <td>${m.goodsName}</td>
                    <td>${m.mrhtName}</td>
                    <td>${m.goodsSpec}</td>
                    <td>${m.typeName}</td>
                    <td>${m.num}</td>
                    <td>${m.originalPrice}</td>
                    <td>${m.totalPrice}</td>
                    <td>
                        <c:if test="${m.status eq 3}"><span style="color: #fffdef;background-color: #0a6999;padding:3px;border-radius: 3px;">待送货</span></c:if>
                        <c:if test="${m.status eq 4}"><span style="color: #fffdef;background-color: #0a6999;padding:3px;border-radius: 3px;">待收货</span></c:if>
                        <c:if test="${m.status eq 5}"><span style="color: #fffdef;background-color: #770088;padding:3px;border-radius: 3px;">待收款</span></c:if>
                        <c:if test="${m.status eq 6}"><span style="color: #fffdef;background-color: red;padding:3px;border-radius: 3px;">取消采购</span></c:if>
                        <c:if test="${m.status eq 7}"><span style="color: #fffdef;background-color: green;padding:3px;border-radius: 3px;">采购完成</span></c:if>
                    </td>
                    <td>
                        <c:if test="${m.status eq 3}">
                            <a href="javascript:;" onclick="butt('','/supplier/butt?id=${m.id}')" class="btn btn-primary radius size-M">发货</a>
                        </c:if>
                        <c:if test="${m.status eq 5}">
                            <a href="javascript:;" onclick="butt('','/supplier/butt?id=${m.id}')" class="btn btn-primary radius size-M">收款</a>
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
<script type="text/javascript">
    function butt(title,url){
        $.ajax({
            type:'post',
            url: url,
            success:function (data) {
                if(data.code == 2) {
                    errMsg(data.msg);
                    return
                }
                succMsg(data.msg);
                setTimeout(function () {
                    refresh();
                },1000)
            }
        })
    }

    /**
     * 定时刷新本页面
     */
    $(function () {
        setInterval('refresh()',300000);
    })
</script>
</html>
