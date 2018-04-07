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
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 商家管理 <span class="c-gray en">&gt;</span> 采购信息 <a id="btn-refresh" onclick="refresh()" class="btn btn-success radius r size-M" style="line-height:1.6em;margin-top:3px" href="###" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="page-container">
    <div class="text-c">
        <form action="/flow/look" method="post">
            <input type="hidden" id="pageSize" name="pageSize" value="${page.pageSize}">
            <input type="hidden" id="pn" name="pn" value="${page.currentPage}">
            <input type="hidden" id="purId" name="purId" value="${params.purId}">
            <input type="text" class="input-text" style="width:250px" placeholder="输入供应商名" id="supplierName" name="supplierName" value="${params.supplierName}">
            <button type="submit" class="btn btn-success radius"><i class="Hui-iconfont">&#xe665;</i> 搜商户</button>
        </form>
    </div>
    <div class="cl mt-20">
        <span class="l">
            <a style="text-decoration:none" onClick="quote('1','确认接受此报价吗?','/flow/quote?purId=${params.purId}')"  class="btn btn-primary radius size-M" href="javascript:;" title="确认报价">确认报价</a>
            <a style="text-decoration:none" onClick="quote('2','确认取消此报价吗?','/flow/quote?purId=${params.purId}')"  class="btn btn-primary radius size-M" href="javascript:;" title="取消报价">取消报价</a>
        </span>
    </div>
    <div class="mt-20">
        <table class="table table-border table-bordered table-hover table-bg table-sort">
            <thead>
            <tr class="text-c">
                <th width="25"><input type="checkbox" name="ids" value=""></th>
                <th width="100">采购单编号</th>
                <th width="100">供应商报价</th>
                <th width="100">供应商名称</th>
                <th width="100">是否可退换</th>
                <th width="100">备注</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${page.results}" var="m">
                <tr class="text-c" onclick="checked(this)">
                    <td><input type="checkbox" value="${m.id}" name="ids"></td>
                    <td>${m.purId}</td>
                    <td>${m.quote}</td>
                    <td>${m.supplierName}</td>
                    <td>${m.returnName}</td>
                    <td>${m.remark}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <div><%@include file="../common/page.jsp"%></div>
    </div>
</div>
</body>
<script type="text/javascript">
    function quote(flag,msg,url){
        var id = '';
        var count = 0;
        $.each($('input:checkbox:checked'),function(){
            if($(this).val() != '') {
                id += $(this).val() + ',';
                count ++;
            }
        });
        id = id.substring(0,id.length-1);
        if(id == '' || id == null) {
            errMsg("请选择至少一条数据编辑！");
            return;
        }
        layer.confirm(msg, function () {
            $.ajax({
                url:url,
                data:{id:id,isAccept:flag},
                type:"POSt",
                success:function (data) {
                    succMsg(data.msg);
                    setTimeout(function () {
                        var index = parent.layer.getFrameIndex(window.name);
                        parent.$('#btn-refresh').click();
                        parent.layer.close(index);
                    },1000)
                },
                error:function () {
                    errMsg("网路异常");
                }
            });

        });
    }
</script>

</html>
