<%--
  Created by IntelliJ IDEA.
  User: win 10
  Date: 2017/9/12
  Time: 14:42
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
    <title>订单列表</title>
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 交易管理 <span class="c-gray en">&gt;</span> 订单管理 <a id="btn-refresh" onclick="refresh()" class="btn btn-success radius r size-M" style="line-height:1.6em;margin-top:3px" href="###" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="page-container">
    <div class="text-c">
        <form action="/order/mrhtOrderList" method="post">
            <input type="hidden" id="pageSize" name="pageSize" value="${page.pageSize}">
            <input type="hidden" id="pn" name="pn" value="${page.currentPage}">
            <input type="text" class="input-text" style="width:250px" placeholder="输入订单号" id="orderNo" name="orderNo" value="${params.orderNo}">
            <button type="button" onclick="query()" class="btn btn-success radius size-M"><i class="Hui-iconfont">&#xe665;</i> 搜索</button>
        </form>
    </div>
    <div class="mt-20">
        <table class="table table-border table-bordered table-hover table-bg table-sort">
            <thead>
            <tr class="text-c">
                <th>订单号</th>
                <th>配送方式</th>
                <th>配送费</th>
                <th>销售金额</th>
                <th>总金额</th>
                <th>支付方式</th>
                <th>下单时间</th>
                <th>支付时间</th>
                <th>订单状态</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${page.results}" var="p">
                <tr class="text-c">
                    <td>${p.orderNo}</td>
                    <td>
                        <c:if test="${p.deliveryType ==1}">商家自送</c:if>
                        <c:if test="${p.deliveryType ==2}">骑手配送</c:if>
                    </td>
                    <td>${p.deliveryFee}</td>
                    <td>${p.salesPrice}</td>
                    <td>${p.totalPrice}</td>
                    <td>
                        <c:if test="${p.payType ==1}">微信</c:if>
                        <c:if test="${p.payType ==2}">支付宝</c:if>
                    </td>
                    <td><fmt:formatDate value="${p.orderTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                    <td><fmt:formatDate value="${p.payTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                    <td>
                        <c:if test="${p.orderStatus ==1}"><span style="color: #1b6d85;">待商家接单</span></c:if>
                        <c:if test="${p.orderStatus ==2}"><span style="color: #2D93CA;">商家已接单</span></c:if>
                        <c:if test="${p.orderStatus ==3}"><span style="color: lightseagreen;">骑手赶往商家</span></c:if>
                        <c:if test="${p.orderStatus ==4}"><span style="color: #1b6d85;">商品已送出</span></c:if>
                        <c:if test="${p.orderStatus ==5}"><span style="color: green;">订单完成</span></c:if>
                        <c:if test="${p.orderStatus ==6}"><span style="color: #2D93CA;">退款审核中</span></c:if>
                        <c:if test="${p.orderStatus ==7}"><span style="color: red;">拒绝退款</span></c:if>
                        <c:if test="${p.orderStatus ==8}"><span style="color: limegreen;">退款完成</span></c:if>
                    </td>
                    <td>
                        <c:if test="${p.orderStatus == 1}">
                            <div><a href="javascript:;" onclick="delivery('${p.orderNo}')" style="margin-bottom: 5px;" class="btn btn-primary radius size-M"> 接单</a></div>
                        </c:if>
                        <div><a href="/order/mrhtOrderView?orderNo=${p.orderNo}" style="margin-bottom: 5px;" class="btn btn-primary radius size-M"> 查看</a></div>
                        <c:if test="${p.orderStatus == 4 && p.deliveryType == 1}">
                            <div><a href="javascript:;" onclick="sendTo('${p.orderNo}')" style="margin-bottom: 5px;" class="btn btn-primary radius size-M"> 确认送达</a></div>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <div><%@include file="../common/page.jsp"%></div>
    </div>
</div>
<div id="delivery" style="display:none;sposition: absolute; top: 50px;width: 20%;height: 16%;border: 1px solid #ccc;margin: 0 auto;">
    <div onclick="$('#delivery').hide()" style="float:right;color: red;font-size: 20px;width: 20px;height:20px;cursor: pointer;">✖</div>
    <form action="/order/delivery" method="post" class="form form-horizontal" id="form-member-add">
        <div class="row cl">
            <div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-2" style="margin-bottom: 20px">
                <label><input type="radio" checked value="1" name="deliveryType">商家配送</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <label><input type="radio" checked value="2" name="deliveryType">骑手配送</label>
            </div><br>
            <div style="margin: 0 auto;width:50px;height:30px;">
                <input id="order" type="hidden" name="orderNo" value="">
                <input class="btn btn-primary radius size-M" type="submit" value="&nbsp;&nbsp;提交&nbsp;&nbsp;">
            </div>
        </div>
    </form>
</div>
</body>
<script>
    function delivery(orderNo){
        $("#delivery").css("display", "block");
        $("#order").val(orderNo);
    }

    function sendTo(orderNo){
        layer.confirm('确认已送达吗？',function(){
            $.ajax({
                type: 'post',
                url: '/order/sendTo',
                data: {orderNo:orderNo},
                success: function(data){
                    succMsg(data.msg);
                    setTimeout(function () {
                        refresh();
                    },1000)
                },
                error:function(data) {
                    errMsg(data.msg);
                },
            });
        });

    }

    $(function () {
        $("#form-member-add").validate({
            onkeyup:false,
            focusCleanup:true,
            success:"valid",
            submitHandler:function(form){
                $.ajax({
                    type: 'post',
                    url: '/order/delivery',
                    data: $(form).serializeArray(),
                    success:function (data) {
                        $("#delivery").css("display", "none");
                        succMsg(data.msg);
                        setTimeout(function () {
                            var index = parent.layer.getFrameIndex(window.name);
                            parent.$('#btn-refresh').click();
                            parent.layer.close(index);
                            refresh();
                        },1000)
                    },
                    error:function (data) {
                        errMsg("网络异常");
                    }
                });

            }
        });
    })
</script>
</html>
