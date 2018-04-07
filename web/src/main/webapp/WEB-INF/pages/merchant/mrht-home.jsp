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
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页 <a id="btn-refresh" onclick="refresh()" class="btn btn-success radius r size-M" style="line-height:1.6em;margin-top:3px" href="###" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="page-container">
    <div class="mt-20">
        <table class="table table-border table-bordered table-hover table-bg table-sort">
            <thead>
            <tr>
                <th colspan="8" scope="col" style="text-align: center;font-size: larger;">库存量不足</th>
            </tr>
            <tr class="text-c">
                <th width="80">ID</th>
                <th width="130">商品条形码</th>
                <th width="100">商品名称</th>
                <th width="130">商品分类</th>
                <th width="130">商品规格</th>
                <th width="130">库存量</th>
                <th width="130">供应商名称</th>
                <th width="130">操作</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${goods}" var="g">
                <tr class="text-c">
                    <td>${g.id}</td>
                    <td>${g.barCode}</td>
                    <td>${g.goodsName}</td>
                    <td>${g.typeName}</td>
                    <td>${g.goodsSpec}</td>
                    <td><span style="color: red;">${g.stockNum}</span></td>
                    <td>${g.supplierName}</td>
                    <td>
                        <a href="javascript:;" onclick="look('采购','/purchase/edit?goodsId=${g.id}','','510')" style="margin-bottom: 5px;" class="btn btn-primary radius size-M">
                            采购
                        </a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>

    <div class="mt-20">
        <table class="table table-border table-bordered table-hover table-bg table-sort">
            <thead>
            <tr>
                <th colspan="8" scope="col" style="text-align: center;font-size: larger;">采购信息状态</th>
            </tr>
            <tr class="text-c">
                <th width="80">ID</th>
                <th width="130">商品条形码</th>
                <th width="100">商品名称</th>
                <th width="130">商品分类</th>
                <th width="130">商品规格</th>
                <th width="130">采购量</th>
                <th width="130">采购状态</th>
                <th width="130">操作</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${purchases}" var="p">
                <c:if test="${!(p.status == 6 or p.status == 7)}">
                <tr class="text-c">
                    <td>${p.id}</td>
                    <td>${p.barCode}</td>
                    <td>${p.goodsName}</td>
                    <td>${p.typeName}</td>
                    <td>${p.goodsSpec}</td>
                    <td>${p.num}</td>
                    <td>
                        <c:if test="${p.status == 1}"><span style="color: #fffdef;background-color: #2D93CA;padding:3px;border-radius: 3px;">待报价</span></c:if>
                        <c:if test="${p.status == 2}"><span style="color:#fffdef;background-color: #0a6999;padding:3px;border-radius: 3px;">已报价</span></c:if>
                        <c:if test="${p.status == 3}"><span style="color: #fffdef;background-color: #0a6999;padding:3px;border-radius: 3px;">待送货</span></c:if>
                        <c:if test="${p.status == 4}"><span style="color: #fffdef;background-color: #00CC99;padding:3px;border-radius: 3px;">待收货</span></c:if>
                        <c:if test="${p.status == 5}"><span style="color: #fffdef;background-color: #770088;padding:3px;border-radius: 3px;">待收款</span></c:if>
                        <c:if test="${p.status == 6}"><span style="color: #fffdef;background-color: red;padding:3px;border-radius: 3px;">取消采购</span></c:if>
                        <c:if test="${p.status == 7}"><span style="color: #fffdef;background-color: green;padding:3px;border-radius: 3px;">采购完成</span></c:if>
                    </td>
                    <td>
                        <div><a href="javascript:;" onclick="look('采购信息','/flow/look?purId=${p.id}','','510')" style="margin-bottom: 5px;" class="btn btn-primary radius size-M">
                            查看报价
                        </a></div>
                        <div><a href="javascript:;" <c:if test="${p.status eq 6 or p.status eq 7}">style="display:none;"</c:if> onclick="cancelOrConfirm(1,'确认要取消采购吗?','/purchase/cancel','${p.id}')" style="margin-bottom: 5px;" class="btn btn-danger radius">取消采购</a></div>
                        <c:if test="${p.status eq 4}">
                           <div><a href="javascript:;" onclick="cancelOrConfirm(0,'','/purchase/confirm','${p.id}')" class="btn btn-primary radius size-M">确认收货</a></div>
                        </c:if>
                    </td>
                </tr>
                </c:if>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
</body>
<script type="text/javascript">
    /**
     * 定时刷新本页面
     */
    $(function () {
        setInterval('refresh()',300000);
    })

    function look(titel,url,w,h){
        add(titel,url,w,h);
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
