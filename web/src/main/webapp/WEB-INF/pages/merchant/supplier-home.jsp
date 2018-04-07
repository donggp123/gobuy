<%--
  Created by IntelliJ IDEA.
  User: sunwei
  Date: 2017/8/31
  Time: 16:31
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
    <title>供应商主页</title>
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 商店采购单 <span class="c-gray en">&gt;</span> 采购管理 <a id="btn-refresh" onclick="refresh()" class="btn btn-success radius r size-M" style="line-height:1.6em;margin-top:3px" href="###" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="page-container">
    <div class="text-c">
        <form action="/supplier/home" method="post">
            <input type="hidden" id="pageSize" name="pageSize" value="${page.pageSize}">
            <input type="hidden" id="pn" name="pn" value="${page.currentPage}">
        </form>
    </div>

    <div class="mt-20">
        <table class="table table-border table-bordered table-hover table-bg table-sort">
            <thead>
            <tr>
                <th colspan="11" scope="col" style="text-align: center;font-size: larger;">报价信息状态</th>
            </tr>
            <tr class="text-c">
                <th width="80">ID</th>
                <th width="130">商品条形码</th>
                <th width="130">商品名称</th>
                <th width="130">商户名称</th>
                <th width="130">商品规格</th>
                <th width="130">商品分类</th>
                <th width="100">采购数量</th>
                <th width="100">采购价格</th>
                <th width="100">采购状态</th>
                <th width="100">操作</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${page.results}" var="p">
                <tr class="text-c">
                    <td>${p.id}</td>
                    <td>${p.barCode}</td>
                    <td>${p.goodsName}</td>
                    <td>${p.mrhtName}</td>
                    <td>${p.goodsSpec}</td>
                    <td>${p.typeName}</td>
                    <td>${p.num}</td>
                    <td>${p.originalPrice}</td>
                    <td>
                        <c:if test="${p.status eq 1}"><span style="color: #fffdef;background-color: #2D93CA;padding:3px;border-radius: 3px;">待报价</span></c:if>
                        <c:if test="${p.status eq 2}"><span style="color: #fffdef;background-color: #0a6999;padding:3px;border-radius: 3px;">已报价</span></c:if>
                    </td>
                    <td>
                        <a href="javascript:;" onclick="offer('报价','/supplier/offer?id=${p.id}','${p.id}')" class="btn btn-primary radius size-M"><i class="Hui-iconfont"></i> 报价</a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <div><%@include file="../common/page.jsp"%></div>
    </div>
</div>

<div class="page-container">
    <div class="mt-20">
        <table class="table table-border table-bordered table-hover table-bg table-sort">
            <thead>
            <tr>
                <th colspan="11" scope="col" style="text-align: center;font-size: larger;">待处理订单</th>
            </tr>
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
            <c:forEach items="${mrhtPurchase}" var="mp">
                <tr class="text-c">
                    <td>${mp.id}</td>
                    <td>${mp.barCode}</td>
                    <td>${mp.goodsName}</td>
                    <td>${mp.mrhtName}</td>
                    <td>${mp.goodsSpec}</td>
                    <td>${mp.typeName}</td>
                    <td>${mp.num}</td>
                    <td>${mp.originalPrice}</td>
                    <td>${mp.totalPrice}</td>
                    <td>
                        <c:if test="${mp.status eq 1}"><span style="color: #fffdef;background-color: #2D93CA;padding:3px;border-radius: 3px;">待报价</span></c:if>
                        <c:if test="${mp.status eq 2}"><span style="color: #fffdef;background-color: #0a6999;padding:3px;border-radius: 3px;">已报价</span></c:if>
                        <c:if test="${mp.status eq 3}"><span style="color: #fffdef;background-color: #0a6999;padding:3px;border-radius: 3px;">待送货</span></c:if>
                        <c:if test="${mp.status eq 4}"><span style="color: #fffdef;background-color: #0a6999;padding:3px;border-radius: 3px;">待收货</span></c:if>
                        <c:if test="${mp.status eq 5}"><span style="color: #fffdef;background-color: #770088;padding:3px;border-radius: 3px;">待收款</span></c:if>
                        <c:if test="${mp.status eq 6}"><span style="color: #fffdef;background-color: red;padding:3px;border-radius: 3px;">取消采购</span></c:if>
                        <c:if test="${mp.status eq 7}"><span style="color: #fffdef;background-color: green;padding:3px;border-radius: 3px;">采购完成</span></c:if>
                    </td>
                    <td>
                        <c:if test="${mp.status eq 3}">
                            <a href="javascript:;" onclick="butt('','/supplier/butt?id=${mp.id}')" class="btn btn-primary radius size-M">发货</a>
                        </c:if>
                        <c:if test="${mp.status eq 5}">
                            <a href="javascript:;" onclick="butt('','/supplier/butt?id=${mp.id}')" class="btn btn-primary radius size-M">收款</a>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
</body>
<script type="text/javascript">
    function offer(title,url,id){
        $.ajax({
            type: 'post',
            url:'/supplier/isoffer',
            data:{id:id},
            success:function (data) {
                if(data.code == 1) {
                    layer_show(title,url);
                    return
                }
                errMsg(data.msg);
            }
        })
    }

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
