<%--
  Created by IntelliJ IDEA.
  User: dgb
  Date: 2017/9/25
  Time: 15:53
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
    <title>结算管理</title>
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 结算管理 <a id="btn-refresh" onclick="refresh()" class="btn btn-success radius r size-M" style="line-height:1.6em;margin-top:3px" href="###" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="page-container">
    <div class="text-c">
        <form action="/settle/mrhtList" method="post">
            <input type="hidden" id="pageSize" name="pageSize" value="${page.pageSize}">
            <input type="hidden" id="pn" name="pn" value="${page.currentPage}">
            <input type="text" class="input-text" style="width:250px" placeholder="输入结算单号" id="username" name="username" value="${params.settleNo}">
            <input type="text" class="input-text" style="width:250px" placeholder="结算账期" id="settlePeriod" name="settlePeriod" value="${params.settlePeriod}">
            <button type="button" onclick="query()" class="btn btn-success radius size-M"><i class="Hui-iconfont">&#xe665;</i> 搜索</button>
        </form>
    </div>
    <div class="mt-20">
        <table class="table table-border table-bordered table-hover table-bg table-sort">
            <thead>
            <tr class="text-c">
                <th width="80">ID</th>
                <th width="150">结算单号</th>
                <th width="150">商家名称</th>
                <th width="150">账期</th>
                <th width="150">销售额</th>
                <th width="150">退款金额</th>
                <th width="150">配送费</th>
                <th width="150">平台所得佣金</th>
                <th width="150">结算金额</th>
                <th width="150">结算比例</th>
                <th width="150">结算状态</th>
                <th width="150">结算时间</th>
                <th width="100">操作</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${page.results}" var="s">
                <tr class="text-c">
                    <td>${s.id}</td>
                    <td>${s.settleNo}</td>
                    <td>${s.mrhtName}</td>
                    <td><fmt:formatDate value="${s.settlePeriod}" type="date"/></td>
                    <td>
                            ${s.salesAmount == null ? 0 : s.salesAmount}
                    </td>
                    <td>
                            ${s.retAmount == null ? 0 : s.retAmount}
                    </td>
                    <td>
                            ${s.deliveryFee == null ? 0 : s.deliveryFee}
                    </td>
                    <td>
                            ${s.empAmount == null ? 0 : s.empAmount}
                    </td>
                    <td>
                            ${s.settleAmount == null ? 0 : s.settleAmount}
                    </td>
                    <td>${s.rate}%</td>
                    <td>
                        <c:if test="${s.settleStatus == 1}"><span style="color: #00b7ee;">待结算</span></c:if>
                        <c:if test="${s.settleStatus == 2}"><span style="color: #770088;">结算中</span></c:if>
                        <c:if test="${s.settleStatus == 3}"><span style="color: green;">结算完成</span></c:if>
                    </td>
                    <td><fmt:formatDate value="${s.settleTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                    <td>
                        <a href="javascript:;" onclick="view('${s.id}')" class="btn btn-primary radius size-M">查看</a>
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
    laydate.render({
        elem: '#settlePeriod'
    })

    function view(id) {
        layer_show("结算信息", "/settle/view?id=" + id, "1500", "800");
    }
</script>
</html>
