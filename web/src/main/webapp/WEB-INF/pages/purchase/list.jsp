<%--
  Created by IntelliJ IDEA.
  User: sunwei
  Date: 2017/8/31
  Time: 9:44
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
    <title>采购管理</title>
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 后台管理 <span class="c-gray en">&gt;</span> 采购管理 <a id="btn-refresh" onclick="refresh()" class="btn btn-success radius r size-M" style="line-height:1.6em;margin-top:3px" href="###" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="page-container">
    <div class="text-c">
        <form action="/purchase/list" method="post">
            <input type="hidden" id="pageSize" name="pageSize" value="${page.pageSize}">
            <input type="hidden" id="pn" name="pn" value="${page.currentPage}">
            <input type="text" class="input-text" style="width:250px" placeholder="商品名称" id="goodsName" name="goodsName" value="${params.goodsName}">
            <button type="button" onclick="query()" class="btn btn-success radius size-M"><i class="Hui-iconfont">&#xe665;</i> 搜索</button>
        </form>
    </div>
    <div class="cl pd-5 bg-1 bk-gray mt-20">
        <span class="l">
            <a href="javascript:;" onclick="add('添加采购单','/purchase/edit','','510')" class="btn btn-primary radius size-M"><i class="Hui-iconfont">&#xe600;</i> 采购</a>
            <a href="javascript:;" onclick="isEditable('编辑','/purchase/edit','','510')" class="btn btn-primary radius size-M"><i class="Hui-iconfont">&#xe6df;</i> 编辑</a>
        </span>
    </div>
    <div class="mt-20">
        <table class="table table-border table-bordered table-hover table-bg table-sort">
            <thead>
            <tr class="text-c">
                <th width="25"><input type="checkbox" name="ids" value=""></th>
                <th width="70">ID</th>
                <th width="100">商品条形码</th>
                <th width="100">商品名称</th>
                <th width="100">商品规格</th>
                <th width="100">商品分类</th>
                <th width="100">采购数量</th>
                <th width="100">进价</th>
                <th width="100">总价</th>
                <th width="100">是否可退</th>
                <th width="100">采购状态</th>
                <th width="100">供应商名称</th>
                <th width="10">操作</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${page.results}" var="p">
                <tr class="text-c" onclick="checked(this)">
                    <td><input type="checkbox" value="${p.id}" name="ids"></td>
                    <td>${p.id}</td>
                    <td>${p.barCode}</td>
                    <td>${p.goodsName}</td>
                    <td>${p.goodsSpec}</td>
                    <td>${p.typeName}</td>
                    <td>${p.num}</td>
                    <td>${p.originalPrice}</td>
                    <td>${p.totalPrice}</td>
                    <td><span style="cursor: pointer;" title="${p.returnName}">${p.returnName.length() > 15 ? p.returnName.substring(0,15).concat("...") : p.returnName}</span></td>
                    <td>
                        <c:if test="${p.status eq 1}"><span style="color: #fffdef;background-color: #2D93CA;padding:3px;border-radius: 3px;">待报价</span></c:if>
                        <c:if test="${p.status eq 2}"><span style="color: #fffdef;background-color: #0a6999;padding:3px;border-radius: 3px;">已报价</span></c:if>
                        <c:if test="${p.status eq 3}"><span style="color: #fffdef;background-color: #0a6999;padding:3px;border-radius: 3px;">待送货</span></c:if>
                        <c:if test="${p.status eq 4}"><span style="color: #fffdef;background-color: #00CC99;padding:3px;border-radius: 3px;">待收货</span></c:if>
                        <c:if test="${p.status eq 5}"><span style="color: #fffdef;background-color: #770088;padding:3px;border-radius: 3px;">待收款</span></c:if>
                        <c:if test="${p.status eq 6}"><span style="color: #fffdef;background-color: red;padding:3px;border-radius: 3px;">取消采购</span></c:if>
                        <c:if test="${p.status eq 7}"><span style="color: #fffdef;background-color: green;padding:3px;border-radius: 3px;">采购完成</span></c:if>
                    </td>
                    <td>${p.supplierName}</td>
                    <td>
                        <div><a href="javascript:;" onclick="view('查看','/purchase/view?id=${p.id}','','510')" style="margin-bottom: 5px;" class="btn btn-primary radius size-M"><i class="Hui-iconfont">&#xe6df;</i> 查看</a></div>
                        <div><a href="javascript:;" <c:if test="${p.status eq 6 or p.status eq 7}">style="display:none;"</c:if> onclick="cancelOrConfirm(1,'确认要取消采购吗?','/purchase/cancel','${p.id}')" style="margin-bottom: 5px;" class="btn btn-danger radius size-M">取消采购</a></div>
                        <c:if test="${p.status eq 4}">
                            <div><a href="javascript:;" onclick="cancelOrConfirm(0,'','/purchase/confirm','${p.id}')" class="btn btn-primary radius size-M">确认收货</a></div>
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
    function view(title,url,w,h){
        layer_show(title,url,w,h);
    }

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
            url: '/purchase/iseditable',
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
    function cancelOrConfirm(flag,msg,url,id){
        if (flag == 1){
            layer.confirm(msg,function () {
                status(url, id);
            })
        }else {
            status(url, id);
        }
    }

    function status(url,id){
        $.ajax({
            url:url,
            data:{id:id},
            type:"post",
            success:function (data) {
                succMsg(data.msg);
                setTimeout(function () {
                    refresh();
                },1000)
            },
            error:function () {
                errMsg("网路异常");
            }
        })
    }
</script>
</html>
