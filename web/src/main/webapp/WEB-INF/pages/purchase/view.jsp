<%--
  Created by IntelliJ IDEA.
  User: dgp
  Date: 2017/8/18
  Time: 16:21
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../common/common.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
    <meta charset="utf-8">
    <meta name="renderer" content="webkit|ie-comp|ie-stand">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
    <meta http-equiv="Cache-Control" content="no-siteapp" />
</head>

<body>
<div class="page-container">
    <table class="table table-border table-bordered table-bg mt-20">
        <thead>
        <tr>
            <th colspan="2" scope="col" style="text-align: center;font-size: larger;">采购信息</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td width="">商品名称</td>
            <td>
                ${merchPur.goodsName}
            </td>
        </tr>
        <tr>
            <td>商品规格</td>
            <td>${merchPur.goodsSpec}</td>
        </tr>
        <tr>
            <td>商品分类</td>
            <td>${merchPur.typeName}</td>
        </tr>
        <tr>
            <td>采购数量 </td>
            <td>${merchPur.num}</td>
        </tr>
        <tr>
            <td>进价 </td>
            <td>
                ${merchPur.originalPrice}
            </td>
        </tr>
        <tr>
            <td>是否可退换 </td>
            <td>
                ${merchPur.returnName}
            </td>
        </tr>
        <tr>
            <td>采购状态</td>
            <td>
                <c:if test="${merchPur.status == 1}"><span style="color: #fffdef;background-color: #2D93CA;padding:3px;border-radius: 3px;">待报价</span></c:if>
                <c:if test="${merchPur.status == 2}"><span style="color:#fffdef;background-color: #0a6999;padding:3px;border-radius: 3px;">已报价</span></c:if>
                <c:if test="${merchPur.status == 3}"><span style="color: #fffdef;background-color: #0a6999;padding:3px;border-radius: 3px;">待送货</span></c:if>
                <c:if test="${merchPur.status == 4}"><span style="color: #fffdef;background-color: #00CC99;padding:3px;border-radius: 3px;">待收货</span></c:if>
                <c:if test="${merchPur.status == 5}"><span style="color: #fffdef;background-color: #770088;padding:3px;border-radius: 3px;">待收款</span></c:if>
                <c:if test="${merchPur.status == 6}"><span style="color: #fffdef;background-color: red;padding:3px;border-radius: 3px;">取消采购</span></c:if>
                <c:if test="${merchPur.status == 7}"><span style="color: #fffdef;background-color: green;padding:3px;border-radius: 3px;">采购完成</span></c:if>
            </td>
        </tr>
        <tr>
            <td>供应商名称 </td>
            <td>
                ${merchPur.supplierName}
            </td>
        </tr>
        <tr>
            <td>支付方式 </td>
            <td>
                <c:if test="${merchPur.payType == 1}">
                    现金支付
                </c:if>
            </td>
        </tr>
        <tr>
            <td>总价 </td>
            <td>
                ${merchPur.totalPrice}
            </td>
        </tr>
        <tr>
            <td>条形码 </td>
            <td>
                ${merchPur.barCode}
            </td>
        </tr>
        </tbody>
    </table>
    <table class="table table-border table-bordered table-bg mt-20">
        <thead>
        <tr>
            <th colspan="7" scope="col" style="text-align: center;font-size: larger;">供应商报价流水</th>
        </tr>
        </thead>
        <tr class="text-c">
            <td style="text-align: center">ID </td>
            <td style="text-align: center;width: 70px;">供应商报价</td>
            <td style="text-align: center">供应商名称</td>
            <td style="text-align: center">是否接受此价格</td>
            <td style="text-align: center">是否可退换</td>
            <td style="text-align: center">支付方式</td>
            <td style="text-align: center">备注</td>
        </tr>
        <c:forEach items="${page.results}" var="flow">
            <tr class="text-c">
                <td width="50">${flow.id} </td>
                <td width="50">${flow.quote}</td>
                <td width="100">${flow.supplierName}</td>
                <td width="100">
                    <c:if test="${flow.isAccept == 0}">无</c:if>
                    <c:if test="${flow.isAccept == 1}">接受</c:if>
                    <c:if test="${flow.isAccept == 2}">拒绝接受</c:if>
                </td>
                <td width="200">${flow.returnName}</td>
                <td width="100">
                    <c:if test="${flow.payType == 1}">现金支付</c:if>
                </td>
                <td width="150">${flow.remark}</td>
            </tr>
        </c:forEach>
    </table>
    <div><%@include file="../common/page.jsp"%></div>
</div>

</body>
<script>
</script>
</html>